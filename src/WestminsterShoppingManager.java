import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.Scanner;

public class WestminsterShoppingManager implements  ShoppingManager {
    private ArrayList<Product> productList;

    public WestminsterShoppingManager(){
        this.productList = new ArrayList<>();
    }

    public ArrayList<Product> listTransfer(){ //Allows transfer of list to  other classes
         return  this.productList;
    }




    /**
     * {@inheritDoc}
     * Override from the shopping manager interface
     * Allows to add new product objects to the productList
     * Identify the subclass of the products (If it belongs to Electronics or Clothing )*/
    @Override
    public void addProduct() {
        String prdType = Input.inputStr("Enter 'E' for electronics or enter 'C' for clothing ").toLowerCase(); //Identifies product type

        String prdID = Input.inputStr("Enter the product ID");
        if (prdNotAvailable(prdID)){ //Proceeds to create new object if there is no existing product with the given  product ID
            if (prdType.equalsIgnoreCase("E")) {  //Product type used to determine class type
                productList.add(Electronics.addElec(prdID));
            }
            else if (prdType.equalsIgnoreCase("C")) {
                productList.add(Clothing.addCloth(prdID));
            }
            else{
                System.out.println("Something went wrong, the product did not get saved ");
            }
        }
        else{ // If the product already exists, User has the option to increase the quantity
            System.out.println("There is already an existing product with the provided product ID");
            prdPrinter(prdID);
            if(Input.inputStr("Click 'C' to  increase the quantity or press any other key to abort ").equalsIgnoreCase("C")){
                addQty(
                        Input.inputInt("Enter the amount of items to be added "),
                        prdID
                );
            }

        }


    }


    /**
     *Identifies if the inputted ID is already available
     * @param prdID the product ID
     * @return a boolean value, true if the inputted value is already there*/
    public boolean prdNotAvailable(String prdID){
        return prdIdentifier(prdID) == null; //Will return true if item not found
    }

    /**
     * {@inheritDoc}
     * Override method from the shopping manager interface
     * Deducts the quantity of a given product or delete the product as a whole based on the users input   */
    @Override
    public void deleteProduct() {
        //Since products are not given a variable name can you use a for-each loop to iterate through and delete the product
        viewProduct();
        String qtyDetail = Input.inputStr("Input 'Q' to deduct the quantity of a product or input 'P' to remove a product as a whole ");

        if(qtyDetail.equalsIgnoreCase("Q")){
            String prdID = Input.inputStr("Input the ID of the product ");
            if(prdIdentifier(prdID) != null){
                System.out.println("Product found ");
                prdPrinter(prdID);
                int qty = Input.inputInt("Enter the amount to be deducted");
                removeqQty(qty, prdID);
                prdPrinter(prdID);
            }
            else{
                System.out.println("There is no product with the ID you have provided");
            }

        }
    }

    /**
     * Reduces the quantity of the products
     * @param qty The quantity needed to be reduced from the given product
     * @param prdID The product ID of the product
     *
     * */
    public void removeqQty(int qty, String prdID){
        int itemAvai = prdIdentifier(prdID).getNumItemsAvailable();
        if (qty > itemAvai){
            System.out.println("The given quantity to be reduced is higher than the products available ");
        }
        else{
            prdIdentifier(prdID).setNumItemsAvailable(itemAvai - qty);
            System.out.println(qty +" items reduced successfully");
        }
    }

    /**
     * Increases the quantity of the needed product
     * @param qty  Number of items that will be added to the already existing  quantity
     * @param prdID  The product ID */
    public void addQty(int qty, String prdID){
        int itemAvai = prdIdentifier(prdID).getNumItemsAvailable();

        prdIdentifier(prdID).setNumItemsAvailable(itemAvai + qty);

    }

    /**
     * {@inheritDoc}
     * Override method from the shopping manager interface
     * Prince out products available inside the productList to the console */
    @Override
    public void viewProduct() {

        //Two additional array lists made to distribute the products, Useful during alphabetical order sorting
        ArrayList<Electronics> electronicProducts = new ArrayList<>();
        ArrayList<Clothing> clothingProducts = new ArrayList<>();

        for(Product product: productList){
            if(product instanceof Electronics){ //Down casting to get relevant methods and attributes
                electronicProducts.add((Electronics) product);
            }
            else{
                clothingProducts.add((Clothing) product);
            }
        }


        productList.sort(Comparator.comparing(Product::getProductID));

        for(Product product: productList){
            if (product instanceof Electronics){
                devLine();
                System.out.println("Electronic");
                System.out.println(product);
                devLine();
            }
            else{
                devLine();
                System.out.println("Clothing");
                System.out.println(product);
                devLine();
            }

        }


    }

    /**
     * Returns the product object based on product ID
     * @param prdID The ID of the product
     * @return The product object(or null if not found )
     * */
    public  Product prdIdentifier(String prdID){

        for(Product product: this.productList){
            if (product.getProductID().equals(prdID)){
                return product;
            }
        }
        return null;
    }
    /**
     * Prince out the object (In the toString format ) to the console based on the product ID
     * @param prdID The ID of the product */

    public  void prdPrinter(String prdID){

        for(Product product: this.productList){

            if (product.getProductID().equals(prdID)){
                devLine();
                System.out.println(product);
                devLine();
            }
        }
    }

    /**
     * @param filename name of the file that will be used to store the data
     * Will retrieve all the product information and save it to the above file name */
    public void saveDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Product product : productList) {
                if (product instanceof Electronics){
                    writer.println("elec");
                    writer.println(product.getProductID());
                    writer.println(product.getProductName());
                    writer.println(product.getNumItemsAvailable());
                    writer.println(product.getPrice());
                    writer.println(((Electronics) product).getBrand());
                    writer.println(((Electronics) product).getWarrantyPeriod());
                }
                else{
                    writer.println("clth");
                    writer.println(product.getProductID());
                    writer.println(product.getProductName());
                    writer.println(product.getNumItemsAvailable());
                    writer.println(product.getPrice());
                    writer.println(((Clothing) product).getSize());
                    writer.println(((Clothing) product).getColour());

                }
            }

            System.out.println("Data saved");
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    /**
     * @param fileName Name of the file that will be read by
     * Reads a text file that was saved earlier and sets the values back to its previous form*/
    public  void readValuesFromFile(String fileName) {

            try {
                File file = new File(fileName);


                Scanner scanner = new Scanner(file);


                while (scanner.hasNext()) {
                    String typOfPrd = scanner.next();
                    if (typOfPrd.equals("elec")) {
                        productList.add(
                                new Electronics(scanner.next(),
                                        scanner.next(),
                                        scanner.nextInt(),
                                        scanner.nextDouble(), scanner.next(),
                                        scanner.nextInt())
                        );
                    } else if (typOfPrd.equals("clth")) {
                        productList.add(
                                new Clothing(scanner.next(),
                                        scanner.next(),
                                        scanner.nextInt(),
                                        scanner.nextDouble(), scanner.next(),
                                        scanner.next())
                        );
                    }
                }

                } catch(FileNotFoundException e){
                    System.out.println("File not found: " + e.getMessage());
                }
            }






    /**
     * Creates a simple division line for readability aid during output */
    public void devLine(){
        System.out.println("--------------------------------------");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////// GUI controller actions

    /**
     * Create a list of the Electronic products
     * @return  All the Electronic products will be returned */
    public ArrayList<Electronics> onlyElectronics(){
        ArrayList<Electronics> listOfElec = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Electronics) {
                listOfElec.add((Electronics) product);
            }
        }
        return listOfElec;
    }

    /**
     * This array method Is responsible for returning the arraylist but in a sorted way for the GUI
     * @return  An alphabetically sorted Electronics  list */

    public ArrayList<Electronics> onlyElectronicsSorted(){
        ArrayList<Electronics> listOfElec = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Electronics) {
                listOfElec.add((Electronics) product);
            }
        }
        listOfElec.sort(Comparator.comparing(Product::getProductID));
        return listOfElec;
    }

    /**
     * Create a list of the clothing products
     * @return  All the clothing products will be returned */
    public ArrayList<Clothing> onlyClothing(){
        ArrayList<Clothing> listOfClth = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Clothing) {
                listOfClth.add((Clothing) product);
            }
        }
        return listOfClth;
    }

    /**
     * This array method Is responsible for returning the arraylist but in a sorted way for the GUI
     * @return  An alphabetically sorted Clothing list */

    public ArrayList<Clothing> onlyClothingSorted(){
        ArrayList<Clothing> listOfClth = new ArrayList<>();
        for (Product product : productList) {
            if (product instanceof Clothing) {
                listOfClth.add((Clothing) product);
            }
        }
        listOfClth.sort(Comparator.comparing(Product::getProductID));
        return listOfClth;
    }

    /**
     * Returns all the products
     * @return  List of all products */
    public ArrayList<Product> allPrds(){ //Returns all the products in the Westminster shopping manager
        ArrayList<Product> listOfAllPrds = new ArrayList<>();
        for (Product product : productList) {
                listOfAllPrds.add(product);
        }
        return listOfAllPrds;
    }
    /**
     * This array method Is responsible for returning the arraylist but in a sorted way for the GUI
     * @return  An alphabetically sorted product list */
    public ArrayList<Product> allPrdsSorted(){
        ArrayList<Product> listOfAllPrds = new ArrayList<>();
        for (Product product : productList) {

                listOfAllPrds.add(product);

        }
        listOfAllPrds.sort(Comparator.comparing(Product::getProductID));
        return listOfAllPrds;
    }



}
