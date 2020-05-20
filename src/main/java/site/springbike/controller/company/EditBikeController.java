package site.springbike.controller.company;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.Bike;
import site.springbike.model.BikeType;
import site.springbike.model.Inventory;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class EditBikeController {
    public static final String VIEW = "company/edit_bike";
    public static final String PATH = "/company/edit_bike/{id}";
    public static final String TITLE = "Edit bike";

    @GetMapping(PATH)
    public String getDeleteBike(Model model, HttpServletRequest request, @PathVariable Integer id) {
        User user = ControllerUtils.checkAuthentication(request);

        if (id == null || user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        Inventory inventory = new Inventory();
        inventory = (Inventory) ModelRepository.useModel(inventory).selectByPrimaryKey(id);
        if (inventory.getIdCompany() != user.getId()) {
            return "redirect:/index";
        }
        Bike bike = new Bike();
        bike = (Bike) ModelRepository.useModel(bike).selectByPrimaryKey(inventory.getIdBike());
        BikeType type = new BikeType();
        type = (BikeType) ModelRepository.useModel(type).selectByPrimaryKey(bike.getIdType());

        model.addAttribute("inventory", inventory);
        model.addAttribute("bike", bike);
        model.addAttribute("type", type);

        return VIEW;
    }

    @PostMapping(PATH)
    public ModelAndView postAddBike(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Company")) {
            return new ModelAndView("redirect:/index");
        }

        Map<String, String[]> map = request.getParameterMap();
        Inventory inventory = new Inventory();
        inventory = (Inventory) ModelRepository.useModel(inventory).selectByPrimaryKey(id);
        Bike bike = new Bike();
        bike = (Bike) ModelRepository.useModel(bike).selectByPrimaryKey(inventory.getIdBike());

        if (!ControllerUtils.parseModelFromInput(inventory, map)) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid inventory changes.", user);
        }
        if (!ControllerUtils.parseModelFromInput(bike, map)) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid bike changes.", user);
        }

        BikeType type = new BikeType();
        type = (BikeType) ModelRepository.useModel(type).findByColumn("type", request.getParameter("type"));
        if (type == null) {
            type = new BikeType();
            if (!ControllerUtils.parseModelFromInput(type, map)) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid bike type.", user);
            }
            type = (BikeType) ModelRepository.useModel(type).insertModel();
        }
        if (type == null) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid bike type.", user);
        }
        bike.setIdType(type.getId());
        ModelRepository.useModel(inventory).updateModel();
        ModelRepository.useModel(bike).updateModel();

        return new ModelAndView("redirect:/company/manage_bikes");
    }

}
