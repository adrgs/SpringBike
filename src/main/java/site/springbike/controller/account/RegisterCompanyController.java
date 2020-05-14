package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterCompanyController {
    @GetMapping("/account/register/company")
    public String registerCompany(Model model) {
        model.addAttribute("title", "Register Company");
        return "account/register_company";
    }
}
