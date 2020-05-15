package site.springbike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    @GetMapping("/error")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Error");
        model.addAttribute("user", ControllerUtils.checkAuthentication(request));
        return "error";
    }
}
