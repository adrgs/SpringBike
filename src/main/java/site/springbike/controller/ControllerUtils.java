package site.springbike.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.model.SpringBikeModel;
import site.springbike.model.User;
import site.springbike.model.sql.Column;
import site.springbike.session.SessionUtils;
import site.springbike.session.UserSession;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

public class ControllerUtils {
    public static ModelAndView errorModelAndView(String view, String title, String error, User user) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("error", error);
        if (!view.startsWith("redirect:/")) {
            modelAndView.addObject("title", title);
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }

    public static User checkAuthentication(HttpServletRequest request) {
        if (request == null) return null;
        Cookie[] cookies = request.getCookies();
        Cookie sessionCookie = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("session")) {
                sessionCookie = cookies[i];
                break;
            }
        }
        if (sessionCookie == null) return null;
        try {
            String encryptedSession = sessionCookie.getValue();
            UserSession session = SessionUtils.getInstance().getUserSession(encryptedSession);
            if (session == null) return null;

            return UserCacheManager.getInstance().getUser(session.getId());
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean parseModelFromInput(SpringBikeModel model, Map<String, String[]> map){

        Class<?> myClass = model.getClass();
        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column == null) continue;

                String[] val = map.get(column.name());
                if (val == null) continue;
                if (val.length != 1 && !column.nullable()) {
                    return false;
                }

                try {
                    if (field.getType() == BigDecimal.class) {
                        field.set(model, new BigDecimal(val[0]));
                    } else {
                        field.set(model, val[0]);
                    }
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
                return false;
            }

            try {
                if (field.getType() == BigDecimal.class) {
                    field.set(model, new BigDecimal(val[0]));
                } else {
                    field.set(model, val[0]);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
