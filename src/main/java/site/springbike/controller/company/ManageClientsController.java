package site.springbike.controller.company;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.controller.ControllerUtils;
import site.springbike.email.EmailSender;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManageClientsController {
    public static final String VIEW = "company/manage_clients";
    public static final String PATH = "/company/manage_clients";
    public static final String TITLE = "Manage users";

    @GetMapping(PATH)
    public String getManageUsers(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        List<SpringBikeModel> blacklistList = ModelRepository.useModel(new Blacklist()).getAllByColumn("id_company", user.getId());
        HashMap<String, String> blacklistViewMap = new HashMap<>();
        for (SpringBikeModel iter : blacklistList) {
            Blacklist blacklist = (Blacklist) iter;
            Client client = (Client) ModelRepository.useModel(new Client()).selectByPrimaryKey(blacklist.getIdClient());
            blacklistViewMap.put(client.getUsername() + " (" + client.getFirstName() + " " + client.getLastName() + ")", blacklist.getReason());
        }

        List<String> lateClients = new ArrayList<>();

        List<SpringBikeModel> transactions = ModelRepository.useModel(new Transaction()).getAllByColumn("finished", "0");
        List<Object> goodIds = new ArrayList<>();
        for (SpringBikeModel transaction : transactions) {
            goodIds.add(((Transaction) transaction).getIdLease());
        }
        List<SpringBikeModel> leaseList = ModelRepository.useModel(new Lease()).selectColumnIn("id", goodIds);

        for (SpringBikeModel iter : leaseList) {
            Lease lease = (Lease) iter;
            if (lease == null) continue;
            if (!lease.getIdCompany().equals(user.getId())) continue;

            if (System.currentTimeMillis() - lease.getTimestampFinish().getTime() > 900000) {
                Client client = (Client) ModelRepository.useModel(new Client()).selectByPrimaryKey(lease.getIdClient());
                lateClients.add(client.getUsername() + " (" + client.getFirstName() + " " + client.getLastName() + ")");
            }
        }

        model.addAttribute("blacklistView", blacklistViewMap);
        model.addAttribute("lateClientsView", lateClients);
        return VIEW;
    }

    @PostMapping(PATH)
    public ModelAndView postManageUsers(HttpServletRequest request, HttpServletResponse response) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return new ModelAndView("redirect:/index");
        }

        String action = request.getParameter("action");
        if (action == null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid action.", user);
        } else if (action.equals("blacklist")) {
            String username = request.getParameter("username");
            if (username == null) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Missing username.", user);
            }
            Client client = (Client) ModelRepository.useModel(new Client()).findByColumn("username", username);
            if (client == null || !client.getType().equals("Client")) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid username.", user);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id_company", user.getId());
            map.put("id_client", client.getId());
            Blacklist blacklist = (Blacklist) ModelRepository.useModel(new Blacklist()).findByColumns(map);
            if (blacklist != null) {
                boolean b = ModelRepository.useModel(blacklist).deleteModel();
                if (!b) {
                    return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Error removing user from blacklist.", user);
                }
            } else {
                blacklist = new Blacklist();
                blacklist.setIdClient(client.getId());
                blacklist.setIdCompany(user.getId());
                blacklist.setReason(request.getParameter("reason"));
                blacklist = (Blacklist) ModelRepository.useModel(blacklist).insertModel();

                if (blacklist == null) {
                    return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Error blacklist.", user);
                }
            }
        } else if (action.equals("email")) {

            String username = request.getParameter("username");
            String subject = request.getParameter("subject");
            String body = request.getParameter("body");
            if (username == null || username.isBlank()) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Username field is empty.", user);
            }
            if (subject == null || subject.isBlank()) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Subject field is empty.", user);
            }
            if (body == null || body.isBlank()) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Body field is empty.", user);
            }

            Client client = (Client) ModelRepository.useModel(new Client()).findByColumn("username", username);
            if (client == null || !client.getType().equals("Client")) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid username.", user);
            }

            List<SpringBikeModel> transactions = ModelRepository.useModel(new Transaction()).getAllByColumn("finished", "0");
            List<Object> goodIds = new ArrayList<>();
            for (SpringBikeModel transaction : transactions) {
                goodIds.add(((Transaction) transaction).getIdLease());
            }
            List<SpringBikeModel> leaseList = ModelRepository.useModel(new Lease()).selectColumnIn("id", goodIds);

            boolean good = false;
            for (SpringBikeModel iter : leaseList) {
                Lease lease = (Lease) iter;
                if (lease == null) continue;
                if (!lease.getIdCompany().equals(user.getId())) continue;
                if (!lease.getIdClient().equals(client.getId())) continue;

                if (System.currentTimeMillis() - lease.getTimestampFinish().getTime() > 900000) {
                    good = true;
                    break;
                }
            }

            if (!good) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "The client is not late on his return!.", user);
            }

            Message message = new Message();
            message.setIdUserSender(user.getId());
            message.setIdUserReceiver(client.getId());
            message.setSubject(subject);
            message.setBody(body);
            message.setEmail(true);
            ModelRepository.useModel(message).insertModel();

            boolean b = EmailSender.sendEmail(client.getEmail(), subject, body);
            if (!b) {
                return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Error sending mail.", user);
            }
        }

        return new ModelAndView("redirect:/company/manage_clients");
    }

}
