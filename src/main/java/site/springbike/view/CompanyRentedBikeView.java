package site.springbike.view;

import org.springframework.web.util.HtmlUtils;
import site.springbike.model.*;

import java.text.DecimalFormat;

public class CompanyRentedBikeView {
    private Client client;
    private Inventory inventory;
    private Bike bike;
    private BikeType type;
    private Lease lease;
    private Transaction transaction;
    private boolean history;

    public CompanyRentedBikeView(Client client, Inventory inventory, Bike bike, BikeType type, Lease lease, Transaction transaction, boolean history) {
        this.client = client;
        this.inventory = inventory;
        this.bike = bike;
        this.type = type;
        this.lease = lease;
        this.transaction = transaction;
        this.history = history;
    }

    public CompanyRentedBikeView() {
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
        if (client.getAvatarURL() != null) {
            builder.append(HtmlUtils.htmlEscape(client.getAvatarURL()));
        }
        builder.append("\" onerror=\"this.src='/img/default_client.png'\" />");
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Client username:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(client.getUsername()));
        builder.append("</div>");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        builder.append("<div class=\"col-lg-1\"><b>Client name:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(client.getFirstName() + " " + client.getLastName()));
        builder.append("</div>");

        if (!history) {
            builder.append("<div class=\"col-lg-1\"><b>Return interval:</b><br/> ");
        } else {
            builder.append("<div class=\"col-lg-1\"><b>Rented between:</b><br/> ");
        }
        builder.append(lease.getTimestampStart().toString() + "<br/>" + lease.getTimestampFinish().toString() + "<br/>");
        if (history) {
            builder.append("Returned at:" + transaction.getTimestampFinish().toString() + "<br/>");
        }
        if (!history && System.currentTimeMillis() - lease.getTimestampFinish().getTime() > 15 * 60000) {
            builder.append("<span style=\"color:red\">The client is ");
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
            builder.append(" behind with his return!</span>");
        }
        builder.append("</div>");

        builder.append("<div class=\"col-lg-1\"><b>Total price:</b><br/> ");
        builder.append(HtmlUtils.htmlEscape(lease.getPriceTotal().toString()));
        builder.append(" RON</div>");

        if (!history) {
            builder.append("<div class=\"col-lg-1\"><b>Code:</b><br/> ");
            builder.append(HtmlUtils.htmlEscape(lease.getRandomCode()));
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
            if (transaction.getTimestampFinish().getTime() - lease.getTimestampFinish().getTime() > 900000) {
                builder.append("<span style=\"color:red\">Client was ");
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
                builder.append(" behind with his return!</span><br/>");
            } else {
                builder.append("The client's return was on time!<br/>");
            }
            builder.append(" Code:");
            builder.append(lease.getRandomCode());
            builder.append("</div>");
        }

        return builder.toString();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
