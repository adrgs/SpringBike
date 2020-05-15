package site.springbike.controller;

import org.springframework.web.servlet.ModelAndView;

public class ControllerUtils {
    public static ModelAndView errorModelAndView(String view, String title, String error) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("title", title);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

}
