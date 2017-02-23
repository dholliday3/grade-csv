import java.util.Random;
import java.lang.StringBuilder;

/**
 * @author: dholliday3
 *
 */

public class VendingMachine {

    /**
     * Initial values
     *
     */
    private static double totalSales;
    private VendingItem[][][] grid = new VendingItem[6][3][5]; //row, col, pos
    private int freeChance = 0; //perc chance stu wins something
    private Random rand;

    /**
     * Restocks the vending machine by calling the restock method
     *
     */
    public VendingMachine() {

        restock();

    }

    /**
     * @return vendingItem: randomly populated item in vending machine in
     *                      specified location by
     */
    public VendingItem vend(String code) {

        char char1 = code.charAt(0); //**** need to use char
        char char2 = code.charAt(1);
        int col2 = Character.getNumericValue(char2);
        int row = 0;
        int col = 0;

        // get rows from char
        if (char1 == 'A') {
            row = 0;
        } else if (char1 == 'B') {
            row = 1;
        } else if (char1 == 'C') {
            row = 2;
        } else if (char1 == 'D') {
            row = 3;
        } else if (char1 == 'E') {
            row = 4;
        } else if (char1 == 'F') {
            row = 5;
        } else {
            System.out.println("Something went wrong with your input.");
            return null;
        }
        // get column
        if ((col2 - 1) >= 0 && (col2 - 1) <= 2) {
            col = col2 - 1;
        } else {
            System.out.println("Something went wrong with your input.");
            return null;
        }

        // get item after finding row and column
        VendingItem myVendingItem = grid[row][col][0];

        //get item in position and pop out of list
        if (grid[row][col][0] == null) {
            System.out.println("nothing in row/col");
            return null;
        } else {
            if (free() == true) {
                System.out.print("This item was free");
            } else if (free() == false) {
                VendingItem myItem = grid[row][col][0];
                totalSales += myItem.getPrice();
            }
            // pull items and replace last one with null
            for (int i = 0; i < 4; i++) {
                grid[row][col][i] = grid[row][col][i + 1];
            }
            grid[row][col][4] = null;
        }

        return myVendingItem;

    }

    /**
     * @return true or false depending on if freechance returns true/false
     *         Determines if person gets free item.
     */
    private boolean free() {
        rand = new Random();
        if (rand.nextInt(100) + 1 <= freeChance) {
            freeChance = 0;
            return true;
        } else {
            freeChance++;
            return false;
        }
    }

    /**
     * Restocks/places a randomly selected item from VendingItem.class into
     * vending machine grid
     */
    public void restock() {
        Random newRand = new Random();
        for (int m = 0; m < 6; m++) {
            for (int n = 0; n < 3; n++) {
                for (int j = 0; j < 5; j++) {

                    VendingItem newItem = VendingItem.values()[newRand.nextInt(15)];
                    grid[m][n][j] = newItem;
                }
            }
        }
    }

    /**
     * @return: Static getter for totalSales
     *
     */
    public static double getTotalSales() {

        return totalSales;

    }

    /**
     * @return current number of items in the vending machine
     *
     */
    public int getNumberOfItems() {
        
        int currNumItems = 0;
        for (int r = 0; r < 6; r++) {
            for (int t = 0; t < 3; t++) {
                for (int u = 0; u < 5; u++) {
                    if (grid[r][t][u] != null) {
                        currNumItems++;
                    }
                }
            }
        }

        return currNumItems;

    }
    /**
     * @return Total sales made in current vending machine interaction
     *
     */
    public double getTotalValue() {

        int totValueItems = 0;
        for (int a = 0; a < 6; a++) {
            for (int s = 0; s < 3; s++) {
                for (int d = 0; d < 5; d++) {
                    if (grid[a][s][d] != null) {
                        VendingItem currItem = grid[a][s][d];
                        totValueItems += currItem.getPrice();
                    }
                }
            }
        }

        return totValueItems;

    }

    /**
     * @return string formatted output output
     *
     */
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("----------------------------------------------------------"
            + "------------\n");
        s.append("                            VendaTron 9000                "
            + "            \n");
        for (int i = 0; i < grid.length; i++) {
            s.append("------------------------------------------------------"
                + "----------------\n");
            for (int j = 0; j < grid[0].length; j++) {
                VendingItem item = grid[i][j][0];
                String str = String.format("| %-20s ",
                    (item == null ? "(empty)" : item.name()));
                s.append(str);
            }
            s.append("|\n");
        }
        s.append("----------------------------------------------------------"
            + "------------\n");
        s.append(String.format("There are %d items with a total "
            + "value of $%.2f.%n", getNumberOfItems(), getTotalValue()));
        s.append(String.format("Total sales across vending machines "
            + "is now: $%.2f.%n", getTotalSales()));
        return s.toString();

    }

}
