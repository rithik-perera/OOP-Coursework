public class Clothing extends  Product {
    private String size;
    private String colour;

//    public Clothing(String productID, String productName, int numItemsAvailable, int price) {
//        super(productID, productName, numItemsAvailable, price);
//    }

    /**
     * Create a clothing object
     * @param productID The product ID
     * @param productName Product name
     * @param numItemsAvailable The quantity of the available items in that particular product
     * @param price Price of the product
     * @param size The clothing size
     * @param colour The color of the clothing item */
    public Clothing(String productID, String productName, int numItemsAvailable, double price, String size, String colour) {
        super(productID, productName, numItemsAvailable, price);
        this.size = size;
        this.colour = colour;
    }
    public Clothing(String productID, String productName, double price, String size, String colour) {
        super(productID, productName, price);
        this.size = size;
        this.colour = colour;
        incrementNumItems();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Create a new clothing object with the inputs directly taken from the user
     * @return  A clothing object */
    public static Clothing addCloth(){
        return new Clothing(
                        Input.inputStr("Enter product ID"),
                        Input.inputStr("Enter product name"),
                        Input.inputInt("Enter the quantity "),
                        Input.inputDouble("Enter product price"),
                        Input.inputStr("Enter the clothing size"),
                        Input.inputStr("Enter the clothing colour")
                ); //Alert: In case of any issue, insert back to the original Westminster shopping manager class
    }

    public static Clothing addCloth(String prdID){
        return new Clothing(
                prdID,
                Input.inputStr("Enter product name"),
                Input.inputInt("Enter the quantity "),
                Input.inputDouble("Enter product price"),
                Input.inputStr("Enter the clothing size"),
                Input.inputStr("Enter the clothing colour")
        ); //Alert: In case of any issue, insert back to the original Westminster shopping manager class
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * {@inheritDoc}
     * A customized toString method
     * @return  The values of the instance variables in a visually pleasing manner */
    @Override
    public String toString() {
        return super.toString()  +
                "size: " + size + "\n" +
                "colour: " + colour;
    }


}
