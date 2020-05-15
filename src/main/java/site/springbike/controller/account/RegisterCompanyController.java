package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterCompanyController {
    public static final String VIEW = "account/register_client";
    public static final String PATH = "/account/register/client";
    public static final String TITLE = "Register Client";

    @GetMapping(PATH)
    public String registerCompany(Model model) {
        model.addAttribute("title", TITLE);
        return VIEW;
    }
}
