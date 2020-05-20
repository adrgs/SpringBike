package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.Bike;
import site.springbike.model.Inventory;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteBikeController {
    public static final String VIEW = "company/delete_bike";
    public static final String PATH = "/company/delete_bike/{id}";
    public static final String TITLE = "Delete bike";

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
        inventory.setDeleted(true);
        ModelRepository.useModel(inventory).deleteModel();

        return VIEW;
    }

}
