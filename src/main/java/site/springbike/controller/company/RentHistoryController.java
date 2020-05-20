package site.springbike.controller.company;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;
import site.springbike.view.CompanyRentedBikeView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class RentHistoryController {
    public static final String VIEW = "company/rent_history";
    public static final String PATH = "/company/rent_history";
    public static final String TITLE = "Rent history";

    @GetMapping(PATH)
    public String getRentHistory(Model model, HttpServletRequest request) {
        return rentHistory(model, request);
    }

    @PostMapping(PATH)
    public String postRentHistory(Model model, HttpServletRequest request) {
        return rentHistory(model, request);
    }


    public String rentHistory(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }

        String strDateStart = request.getParameter("date_start");
        String strDateFinish = request.getParameter("date_finish");

        Timestamp dateStart = null;
        Timestamp dateFinish = null;

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (strDateStart != null) {
            strDateStart = strDateStart.replace("T", " ");
            System.out.println(strDateStart);
            try {
                LocalDateTime ds = LocalDateTime.from(f.parse(strDateStart));
                dateStart = new Timestamp(ds.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            } catch (Exception ex) {
            }
        }
        if (strDateFinish != null) {
            strDateFinish = strDateFinish.replace("T", " ");
            System.out.println(strDateStart);
            try {
                LocalDateTime df = LocalDateTime.from(f.parse(strDateFinish));
                dateFinish = new Timestamp(df.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            } catch (Exception ex) {
            }
        }

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }

        List<SpringBikeModel> transactions = ModelRepository.useModel(new Transaction()).getAllByColumn("finished", "1");
        List<Object> goodIds = new ArrayList<>();
        for (SpringBikeModel transaction : transactions) {
            goodIds.add(((Transaction) transaction).getIdLease());
        }
        List<SpringBikeModel> leaseList = ModelRepository.useModel(new Lease()).selectColumnIn("id", goodIds);

        HashMap<Integer, Transaction> transactionHashMap = new HashMap<>();
        HashMap<Integer, Inventory> inventoryHashMap = new HashMap<>();
        HashMap<Integer, Client> clientHashMap = new HashMap<>();
        HashMap<Integer, Location> locationHashMap = new HashMap<>();
        HashMap<Integer, Address> addressHashMap = new HashMap<>();
        HashMap<Integer, Bike> bikeHashMap = new HashMap<>();
        HashMap<Integer, BikeType> bikeTypeHashMap = new HashMap<>();

        List<CompanyRentedBikeView> bikeViews = new ArrayList<>();

        for (SpringBikeModel iter : leaseList) {
            Lease lease = (Lease) iter;
            if (lease == null) continue;
            if (!lease.getIdCompany().equals(user.getId())) continue;
            if (dateStart != null && dateStart.getTime() > lease.getTimestampStart().getTime()) continue;

            CompanyRentedBikeView rentedBikeView = new CompanyRentedBikeView();
            rentedBikeView.setHistory(true);
            rentedBikeView.setLease(lease);

            if (!transactionHashMap.containsKey(lease.getId())) {
                transactionHashMap.put(lease.getId(), (Transaction) ModelRepository.useModel(new Transaction()).findByColumn("id_lease", lease.getId()));
            }
            Transaction transaction = transactionHashMap.get(lease.getId());
            rentedBikeView.setTransaction(transaction);

            if (dateFinish != null && dateFinish.getTime() < transaction.getTimestampFinish().getTime()) continue;

            if (!inventoryHashMap.containsKey(lease.getIdInventory())) {
                inventoryHashMap.put(lease.getIdInventory(), (Inventory) ModelRepository.useModel(new Inventory()).selectByPrimaryKey(lease.getIdInventory()));
            }
            Inventory inventory = inventoryHashMap.get(lease.getIdInventory());
            rentedBikeView.setInventory(inventory);

            if (!clientHashMap.containsKey(inventory.getIdCompany())) {
                clientHashMap.put(lease.getIdClient(), (Client) ModelRepository.useModel(new Client()).selectByPrimaryKey(lease.getIdClient()));
            }
            Client client = clientHashMap.get(lease.getIdClient());
            rentedBikeView.setClient(client);

            if (!bikeHashMap.containsKey(inventory.getIdBike())) {
                bikeHashMap.put(inventory.getIdBike(), (Bike) ModelRepository.useModel(new Bike()).selectByPrimaryKey(inventory.getIdBike()));
            }
            Bike bike = bikeHashMap.get(inventory.getIdBike());
            rentedBikeView.setBike(bike);

            if (!bikeTypeHashMap.containsKey(bike.getIdType())) {
                bikeTypeHashMap.put(bike.getIdType(), (BikeType) ModelRepository.useModel(new BikeType()).selectByPrimaryKey(bike.getIdType()));
            }
            BikeType type = bikeTypeHashMap.get(bike.getIdType());
            rentedBikeView.setType(type);

            bikeViews.add(rentedBikeView);
        }

        request.setAttribute("dateStart", dateStart);
        request.setAttribute("dateFinish", dateFinish);
        request.setAttribute("bikeViews", bikeViews);
        return VIEW;
    }
}
