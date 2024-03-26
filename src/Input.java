import java.util.Scanner;

public class Input {

    /**
     * A method created with an inbuilt scanner to take user inputs
     * @param label A string that displays what is required from the user
     * @return The String value inputted by the user */
    public static String inputStr(String label){
        boolean err;
        String input = null;

        do {
            try {
                err = false;
                Scanner scan = new Scanner(System.in);

                System.out.print(label + ": ");
                input = scan.nextLine();
            } catch (Exception e) {
                err = true;
                System.out.println("Error: " + e.getMessage());
            }
        } while (err);

        return input;
    }

    /**
     * A method created with an inbuilt scanner to take user inputs
     * @param label A string that displays what is required from the user
     * Simplifies the process of error handling Since  mismatching data  types are fixed here itself
     * @return The Integer value inputted by the user */
    public static Integer inputInt(String label){ //try: Create a class named input util, insert all scanners there
        boolean err;
        int input = 0;

        do {
            try {
                err = false;
                Scanner scan = new Scanner(System.in);

                System.out.print(label + ": ");
                input = scan.nextInt();
            } catch (Exception e) {
                err = true;
                System.out.println("The type of data entered was incorrect. Please try again ");
            }
        } while (err);

        return input;
    }

    /**
     * A method created with an inbuilt scanner to take user inputs
     * @param label A string that displays what is required from the user
     * @return The Double value inputted by the user */
    public static Double inputDouble(String label){
        boolean err;
        double input = 0;

        do {
            try {
                err = false;
                Scanner scan = new Scanner(System.in);

                System.out.print(label + ": ");
                input = scan.nextDouble();

                if (input == (int) input) {
                    input = Double.parseDouble(String.format("%.2f", input));
                }
            } catch (Exception e) {
                err = true;
                System.out.println("Error: " + e.getMessage());
            }
        } while (err);

        return input;
    }



}
