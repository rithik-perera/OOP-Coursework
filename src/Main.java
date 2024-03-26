public class Main {
    public static void main(String[] args) {
        boolean existingUser = false;
        WestminsterShoppingManager manager = new WestminsterShoppingManager();


//        manager.productList.add( new Clothing("CL001", "Shirt", 47, 289, "S", "Red")); //Data set for testing Purpose
//        manager.productList.add(new Electronics("EL001", "Phone", 65, 1876, "Samsung", 10));
//        manager.productList.add(new Clothing("CL002", "Pants", 83, 512, "M", "Blue"));
//        manager.productList.add(new Electronics("EL002", "Laptop", 22, 4210, "Apple", 18));
//        manager.productList.add(new Clothing("CL003", "Dress", 12, 76, "S", "Green"));
//        manager.productList.add(new Electronics("EL003", "TV", 91, 3289, "Sony", 14));
//        manager.productList.add(new Clothing("CL004", "Skirt", 62, 158, "M", "Yellow"));
//        manager.productList.add(new Electronics("EL004", "Tablet", 37, 899, "Google", 8));
//        manager.productList.add(new Clothing("CL005", "Jacket", 35, 443, "S", "Black"));
//        manager.productList.add(new Electronics("EL005", "Camera", 58, 1299, "Canon", 12));
//        manager.productList.add(new Clothing("CL006", "T-Shirt", 72, 95, "S", "White"));
//        manager.productList.add(new Electronics("EL006", "Headphones", 40, 299, "Bose", 9));
//        manager.productList.add(new Clothing("CL007", "Jeans", 27, 210, "S", "Brown"));
//        manager.productList.add(new Electronics("EL007", "Smartwatch", 23, 569, "Fitbit", 10));
//        manager.productList.add(new Clothing("CL008", "Blouse", 56, 127, "M", "Pink"));
//        manager.productList.add(new Electronics("EL008", "Speaker", 55, 199, "JBL", 7));
//        manager.productList.add(new Clothing("CL009", "Shorts", 43, 189, "S", "Purple"));
//        manager.productList.add(new Electronics("EL009", "Router", 80, 129, "Linksys", 6));
//        manager.productList.add(new Electronics("EL010", "Monitor", 17, 799, "Dell", 11));

        manager.readValuesFromFile("someFile.txt");

        if(Input.inputStr("Press '1' to enter as manager\nPress '2' to enter as user").equals("1")){
            boolean breakSequance = false;
            while (!breakSequance){
                System.out.println("---------------------------------");
                String funcSelec = Input.inputStr("Click '1' to add results\nClick '2' to delete results\nClick '3' to view results\nClick '4' to save data\nClick '5' to exit "); //Functionality selector


                switch (funcSelec){
                    case "1":
                        do {
                            manager.addProduct();
                        }while (loopAgain("add"));
                        break;
                    case "2":
                        do {
                            manager.deleteProduct();
                        }while (loopAgain("delete"));
                        break;
                    case "3":
                            manager.viewProduct();
                        break;
                    case "4":
                        manager.saveDataToFile("someFile.txt");
                        break;
                    case "5":
                        breakSequance = true;
                        break;



                }

            }

        }
        else{
            do {
                UserAccountManager accountManager = new UserAccountManager();
                accountManager.restoreAccounts("accountFile.txt");
                if (Input.inputStr("Enter '1' to create an account and enter '2' to sign in").equals("1")) {
                    String username = Input.inputStr("Enter your username");
                    String password = Input.inputStr("Enter your password");
                    if (!accountManager.existingUser(username)) {
                        accountManager.addAccount(new User(username, password));

                        GUIControl gui = new GUIControl(manager, false);
                        gui.setGUILayout();
                    } else {
                        System.out.println("There is already an existing account with the provided username");
                    }

                } else {
                    String username = Input.inputStr("Enter your username");
                    String password = Input.inputStr("Enter your password");

                    if (accountManager.existingUser(username)) {
                        if (accountManager.authorizeUser(username, password)) {
                            GUIControl gui = new GUIControl(manager, true);
                            gui.setGUILayout();
                            System.out.println("Checking to see if this code is actually running ");
                        } else {
                            System.out.println("The entered username or password is incorrect ");
                        }
                    } else {
                        System.out.println("No existing user with the entered username");
                    }
                }
            }while (loopAgainForAccount());
        }




    }

    /**
     * A method that checks if a particular function should loop again
     * @param func A string value that describes the wording of the function
     * This can range from add, delete and view
     * @return  A boolean value of the users decision */
    public static Boolean loopAgain(String func ) { //Functionality
        return Input.inputStr("Press 'Y' to " + func + " more results or press any other key to go to main menu").equalsIgnoreCase("Y");
    }
    public static Boolean loopAgainForAccount() { //Functionality
        return Input.inputStr("Press 'Y' to continue or any other key to end the program").equalsIgnoreCase("Y");
    }


}