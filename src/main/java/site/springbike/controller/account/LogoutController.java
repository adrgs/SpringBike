package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    public static final String VIEW = "account/logout";
    public static final String PATH = "/account/logout";
    public static final String TITLE = "Logout";

    @GetMapping(PATH)
    public String getLogout(Model model) {
        model.addAttribute("title", TITLE);
        return VIEW;
    }
}
