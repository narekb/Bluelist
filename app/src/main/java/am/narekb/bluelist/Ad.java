package am.narekb.bluelist;


public class Ad {
    private String name;
    private String price; //int?
    private String url;
    private String imageUrl;

    public Ad (String newName, String newPrice, String newUrl, String newImageUrl) {
        this.name = newName;
        this.price = newPrice;
        this.url = newUrl;
        this.imageUrl = newImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
