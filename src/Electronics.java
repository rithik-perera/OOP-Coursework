import java.security.PrivateKey;

public class  Electronics extends  Product {
    private String brand;
    private int warrantyPeriod; //provided in weeks




    /**
     * Create a new Electronic object with the inputs directly taken from the user
     * @return  A clothing object */
    public static Electronics addElec(){ //Create electronics object using user input
        return  new Electronics(
                Input.inputStr("Enter product ID"),
                Input.inputStr("Enter product name"),
                Input.inputInt("Enter the quantity "),
                Input.inputInt("Enter product price"),
                Input.inputStr("Enter the brand of the item"),
                Input.inputInt("Enter the warranty in weeks")
        );
    }

    public static Electronics addElec(String prdID){ //Create electronics object using user input
        return  new Electronics(
                prdID,
                Input.inputStr("Enter product name"),
                Input.inputInt("Enter the quantity "),
                Input.inputDouble("Enter product price"),
                Input.inputStr("Enter the brand of the item"),
                Input.inputInt("Enter the warranty in weeks")
        );
    }


    public Electronics(String productID, String productName, int numItemsAvailable, double price, String brand, int warrantyPeriod) {
        super(productID, productName, numItemsAvailable, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }


    public Electronics(String productID, String productName, double price, String brand, int warrantyPeriod) {
        super(productID, productName, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
        incrementNumItems();
    }

    //

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return super.toString() + "brand: " + brand + "\n"
                + "warrantyPeriod: " + warrantyPeriod ;
    }
}
