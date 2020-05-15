package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.Inventory;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.UserRepository;
import site.springbike.session.SessionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        ModelRepository.useModel(new Inventory()).findByColumn("id_company", user.getId());

        model.addAttribute("user", user);
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
