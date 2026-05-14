package onlineAuctionSystem.models.auction;

public class Item {
    private final String itemId;
    private final String name;
    private final String description;
    private final ItemCategory category;

    public Item(String itemId, String name, String description, ItemCategory category) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemCategory getCategory() {
        return category;
    }

}
