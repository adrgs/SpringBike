package site.springbike.controller.account;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.controller.ControllerUtils;
import site.springbike.crypto.SBCrypt;
import site.springbike.model.Address;
import site.springbike.model.Company;
import site.springbike.model.Location;
import site.springbike.repository.ModelRepository;

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
        Address address = new Address();
        Location location = new Location();
        Company company =  new Company();

        String email = request.getParameter("email");
        if (email == null || !email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid email address.", null);
        }

        String username = request.getParameter("username");
        if (username == null || username.length() < 3 || username.length() > 48) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid username length.", null);
        }
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid username characters. Only characters a-z A-Z 0-9 _ allowed", null);
        }

        if (ModelRepository.useModel(company).findByColumnLowerCase("username", request.getParameter("username")) != null) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "The username already exists in the database.", null);
        } else {
            if (ModelRepository.useModel(company).findByColumnLowerCase("email", request.getParameter("email")) != null) {
                return ControllerUtils.errorModelAndView(VIEW, TITLE, "The email already exists in the database.", null);
            }
        }

        if(!ControllerUtils.parseModelFromInput(address,map))
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing", null);
        address = (Address) ModelRepository.useModel(address).insertModel();
        if(address==null)
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid address", null);

        if(!ControllerUtils.parseModelFromInput(location,map))
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing", null);
        location.setIdAddress(address.getId());
        location = (Location) ModelRepository.useModel(location).insertModel();
        if(location==null)
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid location", null);

        if(!ControllerUtils.parseModelFromInput(company,map))
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Required field missing", null);
        company.setIdLocation(location.getId());

        company.setPassword(SBCrypt.hashPassword(company.getPassword()));
        company = (Company) ModelRepository.useModel(company).insertModel();

        if(company==null)
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Account creation failed.", null);

        location.setIdCompany(company.getId());
        ModelRepository.useModel(location).updateModel();

        return new ModelAndView("redirect:/index");

    }
}
