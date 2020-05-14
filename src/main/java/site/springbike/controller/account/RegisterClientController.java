package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterClientController {
    @GetMapping("/account/register/client")
    public String registerClient(Model model) {
        model.addAttribute("title", "Register Client");
        return "account/register_client";
    }
}
