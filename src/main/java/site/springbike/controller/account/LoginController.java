package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @GetMapping("/account/login")
    public String getLogin(Model model) {
        model.addAttribute("title", "Login");
        return "account/login";
    }

    @PostMapping("/account/login")
    public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        return new ModelAndView("redirect:/index"); //success - to be modified to /dashboard or something
    }
}
