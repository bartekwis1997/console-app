package restaurant.io;

import restaurant.repository.RestaurantRepository;
import restaurant.model.Restaurant;

public class ConsolePrinter {
    RestaurantRepository restaurantRepository;

    public ConsolePrinter(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public String print(String message) {
        System.out.println(message);
        return message;
    }

    public String printAppMenu() {
        return "Welcome to the Restaurant Manager, please see instructions below:\n" +
                "Type \"exit\", if you want to stop the program\n" +
                "Type \"1\", if you want to create a restaurant\n" +
                "Type \"2\", if you want to add a meal to a restaurant\n" +
                "Type \"3\", if you want to see all restaurants\n" +
                "Type \"4\", if you want to see all meals in a particular restaurant\n" +
                "Type \"5\", if you want to change the restaurant name\n" +
                "Type \"DUMP\", to store restaurants into a JSON file\n";
    }

    public String printAllRestaurants() {
        String allRestaurants = "";
        for (Restaurant restaurant : restaurantRepository.restaurants) {
            allRestaurants += restaurant + "\n";
        }
        print(allRestaurants);
        return allRestaurants;
    }

}