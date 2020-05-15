package site.springbike.controller;

import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.model.User;
import site.springbike.session.SessionUtils;
import site.springbike.session.UserSession;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
    public static ModelAndView errorModelAndView(String view, String title, String error) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("title", title);
        modelAndView.addObject("error", error);
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

}
