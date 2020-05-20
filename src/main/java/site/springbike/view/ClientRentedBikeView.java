package site.springbike.view;

import org.springframework.web.util.HtmlUtils;
import site.springbike.model.*;

import java.text.DecimalFormat;

public class ClientRentedBikeView {
    private Company company;
    private Inventory inventory;
    private Bike bike;
    private BikeType type;
    private Location location;
    private Address address;
    private Lease lease;
    private Transaction transaction;
    private boolean toReturn;

    public ClientRentedBikeView(Company company, Inventory inventory, Bike bike, BikeType type, Location location, Address address, Lease lease, Transaction transaction, boolean toReturn) {
        this.company = company;
        this.inventory = inventory;
        this.bike = bike;
        this.type = type;
        this.location = location;
        this.address = address;
        this.lease = lease;
        this.transaction = transaction;
        this.toReturn = toReturn;
    }

    public ClientRentedBikeView() {
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
        builder.append(HtmlUtils.htmlEscape(company.getAvatarURL()));
        builder.append("\" onerror=\"this.src='/img/default_company.png'\" />");
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Company name:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(company.getCompanyName()));
        builder.append("</div>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        builder.append("<div class=\"col-lg-1\"><b>Address:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(address.getCountry() + " " + address.getCity() + " " + address.getName() + " " + address.getZipcode() + " (long: " + decimalFormat.format(location.getLongitude()) + " lat: " + decimalFormat.format(location.getLatitude()) + ")"));
        builder.append("</div>");

        if (toReturn) {
            builder.append("<div class=\"col-lg-1\"><b>Return interval:</b><br/> ");
        } else {
            builder.append("<div class=\"col-lg-1\"><b>Rented between:</b><br/> ");
        }
        builder.append(lease.getTimestampStart().toString() + "<br/>" + lease.getTimestampFinish().toString() + "<br/>");
        if (!toReturn) {
            builder.append("Returned at:" + transaction.getTimestampFinish().toString() + "<br/>");
        }
        if (toReturn && System.currentTimeMillis() - lease.getTimestampFinish().getTime() > 15 * 60000) {
            builder.append("<span style=\"color:red\">You are ");
            long minutes = (System.currentTimeMillis() - lease.getTimestampFinish().getTime()) / 60000;
            long hours = minutes / 60;
            minutes = minutes % 60;
            if (hours > 0) {
                builder.append(hours);
                builder.append(" hour");
                if (hours != 1) {
                    builder.append("s");
                }
                builder.append(" ");
            }
            builder.append(minutes);
            builder.append(" minute");
            if (minutes != 1) {
                builder.append("s");
            }
            builder.append(" behind with your return!</span>");
        }
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Total price:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(lease.getPriceTotal().toString()));
        builder.append(" RON</div>");

        if (toReturn) {
            builder.append("<div class=\"col-lg-1\">");
            builder.append("<form method=\"POST\">");
            builder.append(" <label for=\"hours\"><b>Return code:</b></label><input class=\"form-control\" id=\"code" + lease.getId() + "\" name=\"code\" placeholder=\"000-000-000\" type=\"text\" required/>");
            builder.append("<input type=\"hidden\" id=\"id" + lease.getId() + "\" name=\"id\" value=\"" + lease.getId() + "\" required/><br/>");
            builder.append("<button type=\"submit\">Return</button></form>");
            builder.append("</div>");
        } else {
            builder.append("<div class=\"col-lg-1\">");
            long rentHours = (lease.getTimestampFinish().getTime() - lease.getTimestampStart().getTime()) / 3600000;
            builder.append("<b>Info:</b><br/>Rented for ");
            builder.append(rentHours);
            builder.append(" hour");
            if (rentHours != 1) {
                builder.append("s");
            }
            builder.append("<br/>");
            if (transaction.getTimestampFinish().getTime() - lease.getTimestampFinish().getTime() > 15 * 60000) {
                builder.append("<span style=\"color:red\">You were ");
                long minutes = (transaction.getTimestampFinish().getTime() - lease.getTimestampFinish().getTime()) / 60000;
                long hours = minutes / 60;
                minutes = minutes % 60;
                if (hours > 0) {
                    builder.append(hours);
                    builder.append(" hours ");
                }
                builder.append(minutes);
                builder.append(" minute");
                if (minutes != 1) {
                    builder.append("s");
                }
                builder.append(" behind with your return!</span><br/>");
            } else {
                builder.append("Your return was on time!<br/>");
            }
            builder.append(" Code:");
            builder.append(lease.getRandomCode());
            builder.append("</div>");
        }

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

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public boolean isToReturn() {
        return toReturn;
    }

    public void setToReturn(boolean toReturn) {
        this.toReturn = toReturn;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
