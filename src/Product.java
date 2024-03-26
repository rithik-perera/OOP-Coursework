public abstract class Product {
    private String productID;
    private String productName;
    private int numItemsAvailable = 0;
    private double price;

    private static int totNumItem = 0;  //Is used to store the total number of product types

    public Product(String productID, String productName, int numItemsAvailable, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numItemsAvailable = numItemsAvailable;
        this.price = price;

        totNumItem += 1;
    }

    public Product(String productID, String productName, double price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumItemsAvailable() {
        return numItemsAvailable;
    }

    public void setNumItemsAvailable(int numItemsAvailable) {
        this.numItemsAvailable = numItemsAvailable;
    }

    public void incrementNumItems () {
        this.numItemsAvailable += 1;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "productID: " + productID + "\n" +
                "productName: " + productName + "\n" +
                "numItemsAvailable: " + numItemsAvailable + "\n" +
                "price: " + price + "\n";
    }
}
