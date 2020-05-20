package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.model.Client;
import site.springbike.model.Company;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.UserRepository;
import site.springbike.session.SessionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    public static final String VIEW = "account/login";
    public static final String PATH = "/account/login";
    public static final String TITLE = "Login";

    @GetMapping(PATH)
    public String getLogin(Model model) {
        model.addAttribute("title", TITLE);
        return VIEW;
    }

    @PostMapping(PATH)
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

        if (user.getType().equals("Company")) {
            user = (Company) ModelRepository.useModel(new Company()).selectByPrimaryKey(user.getId());
        } else if (user.getType().equals("Client")) {
            user = (Client) ModelRepository.useModel(new Client()).selectByPrimaryKey(user.getId());
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
