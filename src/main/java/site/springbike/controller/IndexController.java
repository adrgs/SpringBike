package site.springbike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    public static final String VIEW = "index";
    public static final String PATH = "/index";
    public static final String TITLE = "Index";

    @GetMapping({"/", PATH})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("title", TITLE);
        model.addAttribute("user", ControllerUtils.checkAuthentication(request));

        return VIEW;
    }
}
