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
import site.springbike.view.ClientRentedBikeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class TransactionHistoryController {
    public static final String VIEW = "client/transaction_history";
    public static final String PATH = "/client/transaction_history";
    public static final String TITLE = "My transaction history";

    @GetMapping(PATH)
    public String getRentedBikes(Model model, HttpServletRequest request) {
        User user = ControllerUtils.checkAuthentication(request);
        model.addAttribute("title", TITLE);
        model.addAttribute("user", user);

        if (user == null || !user.getType().equals("Client")) {
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
        HashMap<Integer, Company> companyHashMap = new HashMap<>();
        HashMap<Integer, Location> locationHashMap = new HashMap<>();
        HashMap<Integer, Address> addressHashMap = new HashMap<>();
        HashMap<Integer, Bike> bikeHashMap = new HashMap<>();
        HashMap<Integer, BikeType> bikeTypeHashMap = new HashMap<>();

        List<ClientRentedBikeView> bikeViews = new ArrayList<>();

        for (SpringBikeModel iter : leaseList) {
            Lease lease = (Lease) iter;
            if (lease == null) continue;
            if (!lease.getIdClient().equals(user.getId())) continue;

            ClientRentedBikeView rentedBikeView = new ClientRentedBikeView();
            rentedBikeView.setToReturn(false);
            rentedBikeView.setLease(lease);

            if (!transactionHashMap.containsKey(lease.getId())) {
                transactionHashMap.put(lease.getId(), (Transaction) ModelRepository.useModel(new Transaction()).findByColumn("id_lease", lease.getId()));
            }
            Transaction transaction = transactionHashMap.get(lease.getId());
            rentedBikeView.setTransaction(transaction);

            if (!inventoryHashMap.containsKey(lease.getIdInventory())) {
                inventoryHashMap.put(lease.getIdInventory(), (Inventory) ModelRepository.useModel(new Inventory()).selectByPrimaryKey(lease.getIdInventory()));
            }
            Inventory inventory = inventoryHashMap.get(lease.getIdInventory());
            rentedBikeView.setInventory(inventory);

            if (!companyHashMap.containsKey(inventory.getIdCompany())) {
                companyHashMap.put(inventory.getIdCompany(), (Company) ModelRepository.useModel(new Company()).selectByPrimaryKey(inventory.getIdCompany()));
            }
            Company company = companyHashMap.get(inventory.getIdCompany());
            rentedBikeView.setCompany(company);

            if (!bikeHashMap.containsKey(inventory.getIdBike())) {
                bikeHashMap.put(inventory.getIdBike(), (Bike) ModelRepository.useModel(new Bike()).selectByPrimaryKey(inventory.getIdBike()));
            }
            Bike bike = bikeHashMap.get(inventory.getIdBike());
            rentedBikeView.setBike(bike);

            if (!locationHashMap.containsKey(company.getIdLocation())) {
                locationHashMap.put(company.getIdLocation(), (Location) ModelRepository.useModel(new Location()).selectByPrimaryKey(company.getIdLocation()));
            }
            Location location = locationHashMap.get(company.getIdLocation());
            rentedBikeView.setLocation(location);

            if (!addressHashMap.containsKey(location.getIdAddress())) {
                addressHashMap.put(location.getIdAddress(), (Address) ModelRepository.useModel(new Address()).selectByPrimaryKey(location.getIdAddress()));
            }
            Address address = addressHashMap.get(location.getIdAddress());
            rentedBikeView.setAddress(address);

            if (!bikeTypeHashMap.containsKey(bike.getIdType())) {
                bikeTypeHashMap.put(bike.getIdType(), (BikeType) ModelRepository.useModel(new BikeType()).selectByPrimaryKey(bike.getIdType()));
            }
            BikeType type = bikeTypeHashMap.get(bike.getIdType());
            rentedBikeView.setType(type);

            bikeViews.add(0, rentedBikeView);
        }

        request.setAttribute("bikeViews", bikeViews);
        return VIEW;
    }
}
