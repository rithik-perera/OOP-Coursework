import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PrivateKey;
import java.util.ArrayList;

public class GUIControl  {


    WestminsterShoppingManager listOfProducts;
    private boolean existingUser;
    static  Product productToBeAddedToCart;

    static boolean toggleVariable = false;
    ArrayList<Product> shoppingCart;



    // Declare the DefaultCellEditor variable outside the ActionListener
    DefaultCellEditor quantityEditor;

    JButton addShoppingCart  = new JButton("Add to shopping cart ");


    /**
     * @param managerMemory  This allows all methods from the Westminster shopping manager to be accessed
     * @param existingUser  Will be taken during the login stage to notify the GUI If it's an existing user or not*/
    public GUIControl(WestminsterShoppingManager managerMemory, boolean existingUser){
      this.listOfProducts = managerMemory;
      this.shoppingCart = new ArrayList<>();
      this.existingUser = existingUser;
    }

    /**
     * Allows users to browse through the products that will  be contained in a J table
     * Allows users to sort the products in the alphabetical order
     * Allows users to add the products to the shopping cart, With the information about the product on the mainframe before adding to the shopping cart
     * Allows users to buy products with discounts (When applicable )
     * */
    public void setGUILayout() { //List of transferred products
        JFrame mainFrame = new JFrame("Westminster shopping centre");
        JFrame shoppingFrame  = new JFrame("Shopping cart");

        shoppingFrame.setSize(350, 450);
        shoppingFrame.setLayout(new GridLayout(2, 1));

        mainFrame.setSize(500, 600);
        mainFrame.setLayout(new GridLayout(2, 1));
        JPanel pannelForCart = new JPanel();
        shoppingFrame.add(pannelForCart);

        JTextArea cartFinalInfo = new JTextArea(1, 1);
        cartFinalInfo.setEditable(false);

        shoppingFrame.add(cartFinalInfo);

        JPanel topPanel = new JPanel();

        //Main layout table model

        String[] mainTblcolumnNames = {"Product ID" , "Name" , "Category" , "Price" , "Info"};
        Object[][] mainTbldata = {
            };
        DefaultTableModel modelOfMain = new DefaultTableModel(mainTbldata, mainTblcolumnNames);
        JTable prdTable = new JTable(modelOfMain);

        JButton sort = new JButton("Sort");




        //Shopping cart table layout

        String[] shpCrtcolumnNames = {"Product", "Quantity", "Price"};

        Object[][] shpCrtdata = {
        };
        DefaultTableModel modelOfShpCrt = new DefaultTableModel(shpCrtdata, shpCrtcolumnNames);
        JTable crtTable = new JTable(modelOfShpCrt);
        JScrollPane scrollPaneFrcRT = new JScrollPane(crtTable);


        pannelForCart.setLayout(new BorderLayout());


        pannelForCart.add(scrollPaneFrcRT, BorderLayout.CENTER);


        JButton processOrder = new JButton("Process Order");
        pannelForCart.add(processOrder, BorderLayout.SOUTH);

        JTextArea prdInfo = new JTextArea(1, 1);
        prdInfo.setLineWrap(true);
        prdInfo.setWrapStyleWord(true);
        prdInfo.setEditable(false);

        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GUIControl.toggleVariable = !GUIControl.toggleVariable;

                if(toggleVariable){
                    //When toggle variable is on it will be switched to sorting, to notify the user that the data is being alphabetically sorted
                    sort.setText("Sorting");
                }
                else{sort.setText("Sort");}
            }

        });
        String[] dropdownOptions = {"All" , "Electronics", "Clothing"};
        JComboBox<String> dropdown = new JComboBox<>(dropdownOptions);

        dropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String selectedOption = (String) combo.getSelectedItem();


                modelOfMain.setRowCount(0);



                if ("All".equals(selectedOption)) {
                    ArrayList<Product> allPrd;
                    //Toggle variable would change from true to false and vice versa when it is clicked
                    if(GUIControl.toggleVariable){
                        allPrd = listOfProducts.allPrdsSorted();
                    }
                    else {
                        allPrd = listOfProducts.allPrds();
                    }
                    //When toggle variable is set to true, The products will be sorted in the table
                    for (Product product : allPrd) {
                        if (product instanceof Electronics) {
                            Object[] row = {product.getProductID(), product.getProductName(), "Electronics", product.getPrice(), ((Electronics) product).getBrand() + " ," + ((Electronics) product).getWarrantyPeriod() + " weeks warranty"};
                            modelOfMain.addRow(row);
                        } else if (product instanceof Clothing) {
                            Object[] row = {product.getProductID(), product.getProductName(), "Clothing", product.getPrice(), "size: " + ((Clothing) product).getSize() + " ,Colour:  " + ((Clothing) product).getColour()};
                            modelOfMain.addRow(row);
                        }


                    }


                } else if ("Electronics".equals(selectedOption)) {
                    ArrayList<Electronics> elecs;
                    if(GUIControl.toggleVariable){
                        elecs = listOfProducts.onlyElectronicsSorted();
                    }
                    else {
                        elecs = listOfProducts.onlyElectronics();
                    }
                    listOfProducts.onlyElectronics();
                    for (Electronics elec : elecs) {
                        Object[] row = {elec.getProductID(), elec.getProductName(), "Electronics", elec.getPrice(), elec.getBrand() + " ," + elec.getWarrantyPeriod() + " weeks warranty"};
                        modelOfMain.addRow(row);
                    }


                } else if ("Clothing".equals(selectedOption)) {
                    ArrayList<Clothing> clths;
                    if(GUIControl.toggleVariable){
                        clths = listOfProducts.onlyClothingSorted();
                    }
                    else {
                        clths = listOfProducts.onlyClothing();
                    }
                    for (Clothing cloth : clths) {
                            Object[] row = {cloth.getProductID(), cloth.getProductName(), "Clothing", cloth.getPrice(), "size: " + cloth.getSize() + " ,Colour:  " + cloth.getColour()};
                        modelOfMain.addRow(row);

                    }

                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(prdTable);
        prdTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) { // To avoid multiple events from happening
                    int selectedRow = prdTable.getSelectedRow();
                    if (selectedRow != -1) {

                        prdInfo.setText(""); // Clears out all the text in the text area before adding anything else
                        Object prdID = prdTable.getValueAt(selectedRow, 0); //The selection listener would provide information on the row, The first index ( for column) to retrieve the product ID
                        Object prdType = prdTable.getValueAt(selectedRow, 2); //Since category comes second in the mock-up, directly taken from the table itself

                        Product productSltFrmTbl = listOfProducts.prdIdentifier((String) prdID); //Product selected from the table
                        prdInfo.append("\n" +"\n" + "\n" + "\n" + "\n" );
                        prdInfo.append("                                                                 Product ID: " + productSltFrmTbl.getProductID() + "\n");
                        prdInfo.append("                                                                 Category: " + prdType + "\n");
//                        prdInfo.append("                                                                 Name: " + productSltFrmTbl.getProductName() + "\n");

                        if (productSltFrmTbl instanceof  Electronics){
                            prdInfo.append("                                                                 Name: " +  ((Electronics) productSltFrmTbl).getBrand()+ " " + productSltFrmTbl.getProductName() + "\n");
                            prdInfo.append("                                                                Brand: " + ((Electronics) productSltFrmTbl).getBrand() + "\n");
                            prdInfo.append("                                                                Warranty Period : " + Integer.toString(((Electronics) productSltFrmTbl).getWarrantyPeriod()) + "\n");
                        }
                        else{
                            prdInfo.append("                                                                 Name: " +  ((Clothing) productSltFrmTbl).getColour()+ " " + productSltFrmTbl.getProductName() + "\n");

                            prdInfo.append("                                                                 Size: " + ((Clothing) productSltFrmTbl).getSize() + "\n");
                            prdInfo.append("                                                                 Colour: " + ((Clothing) productSltFrmTbl).getColour() + "\n");
                        }
                        prdInfo.append("                                                                Items available: " + Integer.toString(productSltFrmTbl.getNumItemsAvailable()) + "\n");

                        GUIControl.productToBeAddedToCart = productSltFrmTbl; //Static variable to store the product (with class Product) that was just selected


                    }
                }
            }
        });

        //Adds products to the shopping cart when it is clicked
        addShoppingCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowCount = modelOfShpCrt.getRowCount();

                ShoppingCart.addToCart(modelOfShpCrt, listOfProducts);
            }

        });

        //In the shopping cart frame, when the process order button is clicked, this method calculates the iscounts and costs
        processOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listOfProducts.saveDataToFile("someFile.txt");
                ShoppingCart.costInfo(modelOfShpCrt, cartFinalInfo, existingUser);
            }
        });





        topPanel.setLayout(new BorderLayout());
        JPanel dropdownButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownButtonPanel.add(dropdown);

        JButton shoppingCart = new JButton("Shopping cart");
        dropdownButtonPanel.add(shoppingCart);
        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingFrame.setVisible(!shoppingFrame.isVisible());
            }
        });



        topPanel.add(dropdownButtonPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.add(sort, BorderLayout.AFTER_LAST_LINE);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(prdInfo, BorderLayout.CENTER);
        bottomPanel.add(addShoppingCart, BorderLayout.SOUTH);




        mainFrame.add(topPanel);
        mainFrame.add(bottomPanel);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




}
