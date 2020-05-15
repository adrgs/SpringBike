package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.model.User;
import site.springbike.repository.UserRepository;

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

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = UserRepository.findByNameAndPassword(name, password);
        if (user == null) {
            ModelAndView modelAndView = new ModelAndView("/account/login");
            modelAndView.addObject("title", "Login");
            modelAndView.addObject("error", "The credentials you entered did not match our records. Please double-check and try again.");
            return modelAndView;
        }

        return new ModelAndView("redirect:/index"); //success - to be modified to /dashboard or something
    }
}
