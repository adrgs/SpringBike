package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.model.Company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RegisterCompanyController {
    public static final String VIEW = "account/register_company";
    public static final String PATH = "/account/register/company";
    public static final String TITLE = "Register Company";

    @GetMapping(PATH)
    public String registerCompany(Model model) {
        model.addAttribute("title", TITLE);
        return VIEW;
    }

    @PostMapping(PATH)
    public ModelAndView postRegisterCompany(HttpServletRequest request, HttpServletResponse response){
        Map<String,String[]> map = request.getParameterMap();
        Company company =  new Company();


    }
}
