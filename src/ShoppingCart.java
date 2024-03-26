import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ShoppingCart  {

    static  Product productToBeAddedToCart;
    static int elecCount;
    static int clthCount;

    /**
     * @param model The model of the table
     * @param products Products To access the necessary methods from the WestminsterShoppingManager
     * Will add data to the shopping cart by accessing the cart table model
     * */
    public static void addToCart(DefaultTableModel model, WestminsterShoppingManager products){
        int rowCount = model.getRowCount();



        boolean inTable = false;
        for (int i = 0; i < rowCount; i++) {
            Object firstValue = model.getValueAt(i, 0); // Assuming the first column
            if (firstValue.toString().contains(GUIControl.productToBeAddedToCart.getProductID())) {
                inTable = true;
            }
        }

        if(!inTable){
            if (GUIControl.productToBeAddedToCart instanceof Electronics) {
                ShoppingCart.elecCount += 1;
                products.removeqQty(1, GUIControl.productToBeAddedToCart.getProductID());
                Object[] row = {
                        GUIControl.productToBeAddedToCart.getProductID() + ", "+  GUIControl.productToBeAddedToCart.getProductName()+ ", "+ ((Electronics) GUIControl.productToBeAddedToCart).getBrand() + ", " + ((Electronics) GUIControl.productToBeAddedToCart).getWarrantyPeriod(),
                        1,
                        GUIControl.productToBeAddedToCart.getPrice()
                };
                model.addRow(row);
            } else  {
                ShoppingCart.clthCount += 1;
                Object[] row = {
                        GUIControl.productToBeAddedToCart.getProductID() + ", "+  GUIControl.productToBeAddedToCart.getProductName()+ ", "+ ((Clothing) GUIControl.productToBeAddedToCart).getSize() + ", " + ((Clothing) GUIControl.productToBeAddedToCart).getColour(),
                        1,
                        GUIControl.productToBeAddedToCart.getPrice()
                };
                model.addRow(row);
            }
        }


    }

    /**
     * @param model The model of the shopping cart table
     * @param textArea The text area where the final cost information will be added
     * @param existingUser  A boolean value to see if it's an already existing user(For the purpose of first time purchase discount )*/
    public static void costInfo(DefaultTableModel model, JTextArea textArea, Boolean existingUser){
        textArea.setText(""); //Reset the information so the data don't just keep adding up
        double total = 0;
        int rowCount = model.getRowCount();



        for (int i = 0; i < rowCount; i++) {
            int qty =  (int) model.getValueAt(i, 1);
            double price = ((Number) model.getValueAt(i, 2)).doubleValue();
            total += qty * price;
        }



        textArea.append("Total: " + total + "\n");
        if ((ShoppingCart.clthCount >= 3) ||(ShoppingCart.elecCount >= 3)){
            double deduct = ((double) 20 /100) * total;
            textArea.append("3 items in the same category discount: " + deduct + "\n");
            total -= deduct;
        }
        if (!existingUser){
            double deduct = ((double) 10 /100) * total; //Applying the 10% discount for the new users
            textArea.append("First purchase discount (10%): -" + deduct + "\n");
            total -= deduct;

        }
        textArea.append("Final Total: " + total);


    }


}
