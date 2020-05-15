package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.UserRepository;
import site.springbike.session.SessionUtils;
import site.springbike.view.InventoryBikeTypeView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ManageBikesController {
    public static final String VIEW = "company/manage_bikes";
    public static final String PATH = "/company/manage_bikes";
    public static final String TITLE = "Manage bikes";

    @GetMapping(PATH)
    public String getManageBikes(Model model, HttpServletRequest request) {
        model.addAttribute("title", TITLE);
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || user.getType() != "Company") {
            return "redirect:/index";
        }

        List<InventoryBikeTypeView> inventoryBikeTypeViews = new ArrayList<>();
        List<SpringBikeModel> inventoryList = ModelRepository.useModel(new Inventory()).getAllByColumn("id_company", user.getId());
        for (SpringBikeModel inventory : inventoryList) {
            Inventory inv = (Inventory) inventory;
            if (inv == null) continue;
            Bike bike = (Bike) ModelRepository.useModel(new Bike()).selectByPrimaryKey(inv.getIdBike());
            if (bike == null) continue;
            BikeType bikeType = (BikeType) ModelRepository.useModel(new BikeType()).selectByPrimaryKey(bike.getIdType());
            if (bikeType == null) continue;

            inventoryBikeTypeViews.add(new InventoryBikeTypeView(inv, bike, bikeType));
        }
        model.addAttribute("user", user);
        model.addAttribute("bikeViews", inventoryBikeTypeViews);
        return VIEW;
    }

    @PostMapping("/company/add_bike")
    public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = UserRepository.findByNameAndPassword(name, password);
        if (user == null) {
            ModelAndView modelAndView = new ModelAndView(VIEW);
            modelAndView.addObject("title", TITLE);
            modelAndView.addObject("error", "The credentials you entered did not match our records. Please double-check and try again.");
            return modelAndView;
        }

        UserCacheManager.getInstance().putUser(user);
        try {
            String session = SessionUtils.getInstance().encryptSession(user.getId());
            Cookie cookie = new Cookie("session", session);
            cookie.setPath("/");
            cookie.setMaxAge(129600);
            response.addCookie(cookie);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ModelAndView("redirect:/index"); //success - to be modified to /dashboard or something
    }
}
