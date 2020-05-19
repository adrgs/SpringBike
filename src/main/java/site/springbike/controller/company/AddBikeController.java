package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;

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
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

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
            BikeType bikeType = new BikeType();
            bikeType = (BikeType) ModelRepository.useModel(bikeType).findByColumn("type", request.getParameter("type"));
            if (bikeType == null) {
                if (!ControllerUtils.parseModelFromInput(bikeType, map)) {
                    return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing.");
                }
                bikeType = (BikeType) ModelRepository.useModel(bikeType).insertModel();
                if (bikeType == null) {
                    return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid bike type.");
                }
            }
            Bike bike = new Bike();
            if (!ControllerUtils.parseModelFromInput(bike, map)) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing.");
            }
            bike.setIdType(bikeType.getId());
            bike = (Bike) ModelRepository.useModel(bike).insertModel();
            if (bike == null) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid bike.");
            }
            Inventory inventory = new Inventory();
            if (!ControllerUtils.parseModelFromInput(inventory, map)) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing.");
            }
            inventory.setIdBike(bike.getId());
            inventory.setIdCompany(user.getId());
            inventory.setIdLocation(((Company) user).getIdLocation());
            inventory = (Inventory) ModelRepository.useModel(inventory).insertModel();
            if (inventory == null) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid inventory.");
            }
        }

        return new ModelAndView("redirect:/company/manage_bikes");
    }

}
