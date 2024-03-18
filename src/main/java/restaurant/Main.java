package restaurant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);
        final var restaurantRepository = new RestaurantRepository();
        final var jsonGenerator = new JSONGenerator();
        final var consolePrinter = new ConsolePrinter(restaurantRepository);
        final var restaurantProcessor = new RestaurantProcessor(consolePrinter, scanner, restaurantRepository, jsonGenerator);
        final var restaurantManager = new RestaurantManager(restaurantProcessor, consolePrinter, scanner);
        restaurantManager.runRestaurantManager();
    }
}