package site.springbike.view;

import org.springframework.web.util.HtmlUtils;
import site.springbike.model.Bike;
import site.springbike.model.BikeType;
import site.springbike.model.Inventory;

public class InventoryBikeTypeView {
    private Inventory inventory;
    private Bike bike;
    private BikeType type;

    public InventoryBikeTypeView(Inventory inventory, Bike bike, BikeType type) {
        this.inventory = inventory;
        this.bike = bike;
        this.type = type;
    }

    public InventoryBikeTypeView() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div>");

        builder.append("<img width=\"200px\" height=\"200px\" src=\"");
        builder.append(HtmlUtils.htmlEscape(bike.getAvatarURL()));
        builder.append("\" onerror=\"this.src='/img/default_bike.png'\" />");

        builder.append("<div>Bike name: ");
        builder.append(HtmlUtils.htmlEscape(bike.getName()));
        builder.append("</div>");

        builder.append("<div>Bike price: ");
        builder.append(HtmlUtils.htmlEscape(bike.getPrice().toString()));
        builder.append("</div>");

        builder.append("<div>Rent price: ");
        builder.append(HtmlUtils.htmlEscape(inventory.getRentPriceHour().toString()));
        builder.append(" RON/h</div>");

        builder.append("<div>Description: ");
        builder.append(HtmlUtils.htmlEscape(bike.getDescription()));
        builder.append("</div>");

        builder.append("<a href=\"/company/edit_bike/");
        builder.append(inventory.getId());
        builder.append("\">Edit bike</a><br/>");

        builder.append("<a href=\"/company/delete_bike/");
        builder.append(inventory.getId());
        builder.append("\">Delete bike</a><br/>");

        builder.append("</div><br/>");
        return builder.toString();
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
}
