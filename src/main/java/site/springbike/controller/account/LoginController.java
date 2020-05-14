package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/account/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "account/login";
    }
}
