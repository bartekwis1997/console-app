package restaurant;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);
        final var restaurantRepository = new RestaurantRepository();
        final var restaurantService = new RestaurantService(restaurantRepository, scanner);
        restaurantService.runRestaurantManager();
    }
}
