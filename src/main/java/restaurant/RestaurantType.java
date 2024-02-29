package restaurant;

import java.util.Scanner;

enum RestaurantType {
    ASIAN,
    ITALIAN,
    FRENCH,
    AMERICAN,
    TURKISH;
    
    public static RestaurantType getValidInput(Scanner scanner) {
        String input = scanner.nextLine().toUpperCase();
        while (true)
            try {
                return RestaurantType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid restaurant type. Please try again.");
                input = scanner.nextLine().toUpperCase();
            }
    }
}
