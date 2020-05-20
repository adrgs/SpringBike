package site.springbike.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.Coupon;
import site.springbike.model.CouponTransaction;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BalanceController {
    public static final String VIEW = "client/balance";
    public static final String PATH = "/client/balance";
    public static final String TITLE = "Balance";

    @GetMapping(PATH)
    public String getBalance(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Client")) {
            return "redirect:/index";
        }
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        return VIEW;
    }

    @PostMapping(PATH)
    public ModelAndView postBalance(HttpServletRequest request, HttpServletResponse response) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Client")) {
            return new ModelAndView("redirect:/index");
        }

        Map<String, String[]> map = request.getParameterMap();
        Coupon userCoupon = new Coupon();
        if (!ControllerUtils.parseModelFromInput(userCoupon, map)) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Code field missing.", user);
        }
        Coupon serverCoupon = (Coupon) ModelRepository.useModel(userCoupon).findByColumn("code", userCoupon.getCode());
        if (serverCoupon == null) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Invalid code.", user);
        }

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id_user", user.getId());
        hashMap.put("id_coupon", serverCoupon.getId());
        if (ModelRepository.useModel(new CouponTransaction()).findByColumns(hashMap) != null) {
            return ControllerUtils.errorModelAndView(VIEW, TITLE, "Code already claimed.", user);
        }

        //insert coupon
        CouponTransaction couponTransaction = new CouponTransaction();
        couponTransaction.setIdUser(user.getId());
        couponTransaction.setIdCoupon(serverCoupon.getId());
        ModelRepository.useModel(couponTransaction).insertModel();

        //update user
        user.setBalance(user.getBalance().add(serverCoupon.getValue()));
        user = (User) ModelRepository.useModel(user).updateModel();
        UserCacheManager.getInstance().putUser(user);

        return new ModelAndView("redirect:/client/balance");
    }

}
