package restaurant;

import java.util.Scanner;

public class RestaurantManager {
    
    private final RestaurantProcessor restaurantProcessor;
    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;

    public RestaurantManager(RestaurantProcessor restaurantProcessor, ConsolePrinter consolePrinter, Scanner scanner) {
        this.restaurantProcessor = restaurantProcessor;
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
    }

    public void runRestaurantManager() {
        System.out.println(consolePrinter.printAppMenu());
        while (true) {
            System.out.println("Please choose an option (type \"6\" to see full menu again):");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                scanner.close();
                break;
            } else {
                switch (input) {
                    case "1" -> System.out.println(restaurantProcessor.createRestaurant());
                    case "2" -> restaurantProcessor.addMealToRestaurant();
                    case "3" -> consolePrinter.printAllRestaurants();
                    case "4" -> restaurantProcessor.getAllMealsInRestaurant();
                    case "5" -> restaurantProcessor.changeRestaurantName();
                    case "6" -> System.out.println(consolePrinter.printAppMenu());
                    case "DUMP" -> restaurantProcessor.dumpRestaurantsToJson();
                    default -> System.out.println("Invalid option, please try again");
                }
            }
        }
    }
}