package restaurant.logic;

import restaurant.io.ConsolePrinter;

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
        consolePrinter.print(consolePrinter.printAppMenu());
        while (true) {
            consolePrinter.print("Please choose an option (type \"6\" to see full menu again):");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                scanner.close();
                break;
            } else {
                switch (input) {
                    case "1" -> restaurantProcessor.createRestaurant();
                    case "2" -> restaurantProcessor.addMealToRestaurant();
                    case "3" -> consolePrinter.printAllRestaurants();
                    case "4" -> restaurantProcessor.getAllMealsInRestaurant();
                    case "5" -> restaurantProcessor.changeRestaurantName();
                    case "6" -> consolePrinter.print(consolePrinter.printAppMenu());
                    case "DUMP" -> restaurantProcessor.dumpRestaurantsToJson();
                    default -> consolePrinter.print("Invalid option, please try again");
                }
            }
        }
    }
}