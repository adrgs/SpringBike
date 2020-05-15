package site.springbike.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.controller.ControllerUtils;
import site.springbike.crypto.SBCrypt;
import site.springbike.model.Client;
import site.springbike.model.User;
import site.springbike.model.sql.Column;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Map;

@Controller
public class RegisterClientController {
    public static final String PATH = "/account/register/client";
    public static final String TITLE = "Register Client";

    @GetMapping(PATH)
    public String getRegisterClient(Model model) {
        model.addAttribute("title", TITLE);
        return "account/register_client";
    }

    @PostMapping("/account/register/client")
    public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String[]> map = request.getParameterMap();
        Client client = new Client();

        String email = request.getParameter("email");
        if (email == null || !email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return ControllerUtils.errorModelAndView(PATH, TITLE, "Invalid email address.");
        }
        String username = request.getParameter("username");
        if (username == null || username.length() < 3 || username.length() > 48) {
            return ControllerUtils.errorModelAndView(PATH, TITLE, "Invalid username length.");
        }
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            return ControllerUtils.errorModelAndView(PATH, TITLE, "Invalid username characters. Only characters a-z A-Z 0-9 _ allowed");
        }

        if (ModelRepository.useModel(client).findByColumnLowerCase("username", request.getParameter("username")) != null) {
            return ControllerUtils.errorModelAndView(PATH, TITLE, "The username already exists in the database.");
        } else {
            if (ModelRepository.useModel(client).findByColumnLowerCase("email", request.getParameter("email")) != null) {
                return ControllerUtils.errorModelAndView(PATH, TITLE, "The email already exists in the database.");
            }
        }

        Class<?> myClass = client.getClass();
        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;

                String[] val = map.get(column.name());
                if (val == null) continue;
                if (val.length != 1 && !column.nullable()) {
                    return ControllerUtils.errorModelAndView(PATH, TITLE, "Required field missing");
                }

                try {
                    field.set(client, val[0]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            field.setAccessible(true);
            String[] val = map.get(column.name());
            if (val == null) continue;
            if (val.length != 1 && !column.nullable()) {
                return ControllerUtils.errorModelAndView(PATH, TITLE, "Required field missing");
            }

            try {
                field.set(client, val[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        client.setPassword(SBCrypt.hashPassword(client.getPassword()));
        client = (Client) ModelRepository.useModel(client).insertModel();
        if (client == null) {
            return ControllerUtils.errorModelAndView(PATH, TITLE, "Account creation failed.");
        }

        return new ModelAndView("redirect:/index"); //success - to be modified to /response or something
    }
}
