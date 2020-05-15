package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AddBikeController {

    public static final String VIEW = "company/add_bike";
    public static final String PATH = "/company/add_bike";
    public static final String TITLE = "Add bikes";

    @GetMapping(PATH)
    public String getAddBike(Model model, HttpServletRequest request) {

        model.addAttribute("title", TITLE);
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }

        return VIEW;
    }

    @PostMapping("/company/add_bike")
    public ModelAndView postAddBike(HttpServletRequest request, HttpServletResponse response) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return new ModelAndView("redirect:/index");
        }

        Map<String, String[]> map = request.getParameterMap();
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        if (quantity == null) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Quantity field missing");
        }

        for (int i = 0; i < quantity; i++) {

        }

        return new ModelAndView("redirect:/company/manage_bikes"); //success - to be modified to /dashboard or something
    }

}
