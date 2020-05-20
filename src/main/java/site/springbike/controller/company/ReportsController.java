package site.springbike.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.springbike.controller.ControllerUtils;
import site.springbike.model.*;
import site.springbike.repository.ModelRepository;
import site.springbike.view.ClientRentedBikeView;
import site.springbike.view.CompanyRentedBikeView;
import site.springbike.view.RentBikeView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ReportsController {
    public static final String VIEW = "company/reports";
    public static final String PATH = "/company/reports";
    public static final String TITLE = "Reports";

    @GetMapping(PATH)
    public String getReports(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        if (user == null || !user.getType().equals("Company")) {
            return "redirect:/index";
        }

        List<SpringBikeModel> transactions = ModelRepository.useModel(new Transaction()).getAllByColumn("finished", "0");
        List<Object> goodIds = new ArrayList<>();
        for (SpringBikeModel transaction : transactions) {
            goodIds.add(((Transaction) transaction).getIdLease());
        }
        List<SpringBikeModel> leaseList = ModelRepository.useModel(new Lease()).selectColumnIn("id", goodIds);

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

            CompanyRentedBikeView rentedBikeView = new CompanyRentedBikeView();
            rentedBikeView.setHistory(false);
            rentedBikeView.setLease(lease);

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

        List<SpringBikeModel> leases = ModelRepository.useModel(new Lease()).getAllByColumn("id_company", user.getId());

        request.setAttribute("bikeViews", bikeViews);
        request.setAttribute("totalLeases", leases.size());
        return VIEW;
    }

}
