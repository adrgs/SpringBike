package site.springbike.view;

import org.springframework.web.util.HtmlUtils;
import site.springbike.model.*;

import java.text.DecimalFormat;

public class RentBikeView {
    private Company company;
    private Inventory inventory;
    private Bike bike;
    private BikeType type;
    private Location location;
    private Address address;

    public RentBikeView(Company company, Inventory inventory, Bike bike, BikeType type, Location location, Address address) {
        this.company = company;
        this.inventory = inventory;
        this.bike = bike;
        this.type = type;
        this.location = location;
        this.address = address;
    }

    public RentBikeView() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<div class=\"col-lg-2\" style=\"text-align:center;\">");
        builder.append("<img width=\"150px\" height=\"150px\" src=\"");
        builder.append(HtmlUtils.htmlEscape(bike.getAvatarURL()));
        builder.append("\" onerror=\"this.src='/img/default_bike.png'\" />");
        builder.append("</div>");


        builder.append("<div class=\"col-lg-1\"><b>Bike name:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(bike.getName()));
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Bike description:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(bike.getDescription()));
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Bike type:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(type.getType()));
        builder.append("</div>");

        builder.append("<div class=\"col-lg-2\" style=\"text-align:center;\">");
        builder.append("<img width=\"150px\" height=\"150px\" src=\"");
        if (company.getAvatarURL() != null) {
            builder.append(HtmlUtils.htmlEscape(company.getAvatarURL()));
        }
        builder.append("\" onerror=\"this.src='/img/default_company.png'\" />");
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Company name:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(company.getCompanyName()));
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Company description:</b><br/> ");
        if (company.getDescription() != null) {
            builder.append(HtmlUtils.htmlEscape(company.getDescription()));
        }
        builder.append("</div>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        builder.append("<div class=\"col-lg-1\"><b>Address:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(address.getCountry() + " " + address.getCity() + " " + address.getName() + " " + address.getZipcode() + " (long: " + decimalFormat.format(location.getLongitude()) + " lat: " + decimalFormat.format(location.getLatitude()) + ")"));
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Rent price:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(inventory.getRentPriceHour().toString()));
        builder.append(" RON/h</div>");

        builder.append("<div class=\"col-lg-1\">");
        builder.append("<form method=\"POST\">");
        builder.append(" <label for=\"hours\"><b>Hours:</b></label><input class=\"form-control\" id=\"hours" + inventory.getId() + "\" name=\"hours\" value=\"1\" type=\"number\" required/>");
        builder.append("<input type=\"hidden\" id=\"id" + inventory.getId() + "\" name=\"id\" value=\"" + inventory.getId() + "\" required/><br/>");
        builder.append("<button type=\"submit\">Rent</button></form>");
        builder.append("</div>");

        return builder.toString();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
