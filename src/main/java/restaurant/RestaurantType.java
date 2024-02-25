package restaurant;

import java.util.Scanner;

enum RestaurantType {
    // creating enum for holding a type of restaurant values
    ASIAN,
    ITALIAN,
    FRENCH,
    AMERICAN,
    TURKISH;

    // creating validation method, that checks the user input for enum values
    // and throws exception if its invalid
    public static RestaurantType getValidInput(Scanner scanner) {
        String input = scanner.nextLine();
        while (true)
            try {
                return RestaurantType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid restaurant type. Please try again.");
            }
    }
}
