package site.springbike.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import site.springbike.cache.UserCacheManager;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;
import site.springbike.view.RentBikeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class RentBikeController {
    public static final String VIEW = "client/rent_bike";
    public static final String PATH = "/client/rent_bike";
    public static final String TITLE = "Rent a bike";

    private String generateRandomCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int i = random.nextInt(1000);

        return String.format("%03d-%03d-%03d", random.nextInt(1000), random.nextInt(1000), random.nextInt(1000));
    }

    @PostMapping(PATH)
    public ModelAndView postRentBike(HttpServletRequest request, HttpServletResponse response) {
        User user = ControllerUtils.checkAuthentication(request);

        if (user == null || !user.getType().equals("Client")) {
            return new ModelAndView("redirect:/index");
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        if (id == null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid bike.", user);
        }
        Integer hours = Integer.parseInt(request.getParameter("hours"));
        if (hours == null || hours < 1) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid number of hours.", user);
        }
        Inventory inventory = new Inventory();
        inventory = (Inventory) ModelRepository.useModel(inventory).selectByPrimaryKey(id);
        if (inventory == null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid bike.", user);
        }

        Blacklist blacklist = new Blacklist();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id_company", inventory.getIdCompany());
        map.put("id_client", user.getId());
        blacklist = (Blacklist) ModelRepository.useModel(blacklist).findByColumns(map);
        if (blacklist != null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "User is blacklisted.", user);
        }

        BigDecimal sum = inventory.getRentPriceHour().multiply(new BigDecimal(hours));
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Invalid number of hours.", user);
        }
        if (user.getBalance().compareTo(sum) < 0) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Insufficient balance.", user);
        }

        Lease lease = new Lease();
        lease.setIdClient(user.getId());
        lease.setIdCompany(inventory.getIdCompany());
        lease.setIdInventory(inventory.getId());
        lease.setPriceTotal(sum);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        lease.setDateStart(now);
        long duration = (3600000 * hours);
        Timestamp then = new Timestamp(now.getTime() + duration);
        lease.setDateFinish(then);
        lease.setRandomCode(generateRandomCode());

        lease = (Lease) ModelRepository.useModel(lease).insertModel();
        if (lease == null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Server error.", user);
        }

        Transaction transaction = new Transaction();
        transaction.setIdLease(lease.getId());
        transaction.setIdLocationStart(inventory.getIdLocation());
        transaction.setIdLocationFinish(inventory.getIdLocation());

        transaction = (Transaction) ModelRepository.useModel(transaction).insertModel();
        if (transaction == null) {
            return ControllerUtils.errorModelAndView("redirect:/" + VIEW, TITLE, "Server error.", user);
        }

        user.setBalance(user.getBalance().subtract(sum));
        ModelRepository.useModel(user).updateModel();
        UserCacheManager.getInstance().putUser(user);

        return new ModelAndView("redirect:/client/rented_bikes");
    }

    @GetMapping(PATH)
    public String getRentBike(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        if (user == null || !user.getType().equals("Client")) {
            return "redirect:/index";
        }

        List<SpringBikeModel> transactions = ModelRepository.useModel(new Transaction()).getAllByColumn("finished", "0");
        List<Object> badIds = new ArrayList<>();
        for (SpringBikeModel transaction : transactions) {
            badIds.add(((Transaction) transaction).getIdLease());
        }
        List<SpringBikeModel> badLeases = ModelRepository.useModel(new Lease()).selectColumnIn("id", badIds);
        List<Object> badInventory = new ArrayList<>();
        for (SpringBikeModel lease : badLeases) {
            badInventory.add(((Lease) lease).getIdInventory());
        }
        List<SpringBikeModel> inventoryList = ModelRepository.useModel(new Inventory()).selectColumnNotIn("id", badInventory);

        HashMap<Integer, Company> companyHashMap = new HashMap<>();
        HashMap<Integer, Location> locationHashMap = new HashMap<>();
        HashMap<Integer, Address> addressHashMap = new HashMap<>();
        HashMap<Integer, Bike> bikeHashMap = new HashMap<>();
        HashMap<Integer, BikeType> bikeTypeHashMap = new HashMap<>();
        HashMap<Integer, Boolean> blacklistHashMap = new HashMap<>();

        List<RentBikeView> bikeViews = new ArrayList<>();

        for (SpringBikeModel iter : inventoryList) {
            Inventory inventory = (Inventory) iter;
            if (inventory == null) continue;
            if (blacklistHashMap.get(inventory.getIdCompany()) != null) {
                if (blacklistHashMap.get(inventory.getIdCompany()) == true)
                    continue;
            } else {
                blacklistHashMap.put(inventory.getIdCompany(), false);
                Blacklist blacklist = new Blacklist();
                HashMap<String, Object> map = new HashMap<>();
                map.put("id_company", inventory.getIdCompany());
                map.put("id_client", user.getId());
                blacklist = (Blacklist) ModelRepository.useModel(blacklist).findByColumns(map);
                if (blacklist != null) {
                    blacklistHashMap.put(inventory.getIdCompany(), true);
                    continue;
                }
            }

            RentBikeView rentBikeView = new RentBikeView();
            rentBikeView.setInventory(inventory);

            if (!companyHashMap.containsKey(inventory.getIdCompany())) {
                companyHashMap.put(inventory.getIdCompany(), (Company) ModelRepository.useModel(new Company()).selectByPrimaryKey(inventory.getIdCompany()));
            }
            Company company = companyHashMap.get(inventory.getIdCompany());
            rentBikeView.setCompany(company);

            if (!bikeHashMap.containsKey(inventory.getIdBike())) {
                bikeHashMap.put(inventory.getIdBike(), (Bike) ModelRepository.useModel(new Bike()).selectByPrimaryKey(inventory.getIdBike()));
            }
            Bike bike = bikeHashMap.get(inventory.getIdBike());
            rentBikeView.setBike(bike);

            if (!locationHashMap.containsKey(company.getIdLocation())) {
                locationHashMap.put(company.getIdLocation(), (Location) ModelRepository.useModel(new Location()).selectByPrimaryKey(company.getIdLocation()));
            }
            Location location = locationHashMap.get(company.getIdLocation());
            rentBikeView.setLocation(location);

            if (!addressHashMap.containsKey(location.getIdAddress())) {
                addressHashMap.put(location.getIdAddress(), (Address) ModelRepository.useModel(new Address()).selectByPrimaryKey(location.getIdAddress()));
            }
            Address address = addressHashMap.get(location.getIdAddress());
            rentBikeView.setAddress(address);

            if (!bikeTypeHashMap.containsKey(bike.getIdType())) {
                bikeTypeHashMap.put(bike.getIdType(), (BikeType) ModelRepository.useModel(new BikeType()).selectByPrimaryKey(bike.getIdType()));
            }
            BikeType type = bikeTypeHashMap.get(bike.getIdType());
            rentBikeView.setType(type);

            bikeViews.add(rentBikeView);
        }

        request.setAttribute("bikeViews", bikeViews);
        return VIEW;
    }
}
