package site.springbike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Index");
        return "index";
    }
}
