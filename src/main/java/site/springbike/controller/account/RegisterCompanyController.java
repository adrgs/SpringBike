package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping(PATH)
    public ModelAndView postRegisterCompany(HttpServletRequest request, HttpServletResponse response){

    }
}
