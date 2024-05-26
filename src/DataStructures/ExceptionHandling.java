package DataStructures;

import java.util.*;

public class ExceptionHandling {

    public Scanner input = new Scanner(System.in);

    public ExceptionHandling() {

    }

    public double doubleValueInput(String text) {

        boolean found = false;
        double userValue = 0;
        while (found == false) {
            try {
                System.out.print(text);
                userValue = this.input.nextDouble();
                found = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Please enter a double value. ");
            } catch (Exception e) {
                System.out.println("ERROR: Please enter a valid value. ");
            }
            this.input.nextLine();
        }
        return userValue;

    }

    public int integerValueInput(String text) {

        boolean found = false;
        int userValue = 0;
        while (found == false) {
            try {
                System.out.print(text);
                userValue = this.input.nextInt();
                found = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Please enter an integer value. ");
            } catch (Exception e) {
                System.out.println("ERROR: Please enter a valid value. ");
            }
            this.input.nextLine();
        }
        return userValue;

    }
    
    public long longValueInput(String text) {

        boolean found = false;
        long userValue = 0;
        while (found == false) {
            try {
                System.out.print(text);
                userValue = this.input.nextLong(); 
                found = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Please enter an integer value. ");
            } catch (Exception e) {
                System.out.println("ERROR: Please enter a valid value. ");
            }
            this.input.nextLine();
        }
        return userValue;

    }

    public int integerValueRange(int userValue, double startRange, double endRange,
            double exceptionWithinRange, String text) {

        boolean correctValue = false;
        while (correctValue == false) {
            if (userValue >= startRange && userValue <= endRange && userValue != exceptionWithinRange) {
                correctValue = true;
            } else {
                System.out.println("ERROR: Please enter a value between " + startRange + " and " + endRange);
                userValue = integerValueInput(text);
            }
        }
        return userValue;

    }

    public double doubleValueRange(double userValue, double startRange, double endRange,
            double exceptionWithinRange, String text) {

        boolean correctValue = false;
        while (correctValue == false) {
            if (userValue >= startRange && userValue <= endRange && userValue != exceptionWithinRange) {
                correctValue = true;
            } else {
                System.out.println("ERROR: Please enter a value between " + startRange + " and " + endRange);
                userValue = integerValueInput(text);
            }
        }
        return userValue;

    }

}
