package restaurant.logic;

import restaurant.io.ConsolePrinter;
import restaurant.util.JSONGenerator;
import restaurant.repository.RestaurantRepository;
import restaurant.model.Meal;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;

import java.util.*;

public class RestaurantProcessor {
    ConsolePrinter consolePrinter;
    Scanner scanner;
    RestaurantRepository restaurantRepository;
    JSONGenerator jsonGenerator;

    public RestaurantProcessor(ConsolePrinter consolePrinter, Scanner scanner, RestaurantRepository restaurantRepository, JSONGenerator jsonGenerator) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
        this.restaurantRepository = restaurantRepository;
        this.jsonGenerator = jsonGenerator;
    }

    List<Restaurant> dumpRestaurantsToJson() {
        List<Restaurant> dumpedRestaurants = new ArrayList<>(restaurantRepository.restaurants);
        jsonGenerator.generateJSON(dumpedRestaurants);
        consolePrinter.print("Restaurants dumped to JSON file.");
        return dumpedRestaurants;
    }

    UUID createRestaurant() {
        UUID restaurantUuid = UUID.randomUUID();

        consolePrinter.print("Please type restaurant name:");
        String restaurantName = scanner.nextLine();

        consolePrinter.print("Please type restaurant address");
        String restaurantAddress = scanner.nextLine();

        consolePrinter.print("Please type restaurant type (ASIAN, ITALIAN, FRENCH, AMERICAN):");
        RestaurantType restaurantType = RestaurantType.getValidInput(scanner);

        restaurantRepository.create(new Restaurant(restaurantUuid, restaurantName, restaurantAddress, restaurantType));
        consolePrinter.print("Restaurant have been added");

        return restaurantUuid;
    }

    Meal addMealToRestaurant() {
        consolePrinter.print("Please type restaurant ID, to which you would like to add a meal\n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();

        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant != null) {
            consolePrinter.print("Please type meal name:");
            final var mealName = scanner.nextLine();

            consolePrinter.print("Please type meal price:");
            final var mealPrice = getValidMealPrice(scanner);

            Meal meal = new Meal(mealName, mealPrice, restaurantId);
            restaurant.meals.add(meal);
            consolePrinter.print("Meal added successfully");
            return meal;
        } else {
            consolePrinter.print("Restaurant not found.");
            return null;
        }
    }

    public Set<Meal> getAllMealsInRestaurant() {
        Set<Meal> meals = new HashSet<>();

        consolePrinter.print("Enter restaurant ID to see its meals\n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();

        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant.meals.isEmpty()) {
            consolePrinter.print("There are no meals in this restaurant");
        } else {
            consolePrinter.print("Meals in restaurant \"" + restaurant.name + "\":");
            meals.addAll(restaurant.meals);
        }
        consolePrinter.print(meals.toString());
        return meals;
    }

    String changeRestaurantName() {
        consolePrinter.print("Please type restaurant ID, which you would like to change a name in \n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();
        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        consolePrinter.print("Please type a new name for the restaurant currently called \"" + restaurant.name + "\"");
        restaurant.name = scanner.nextLine();
        consolePrinter.print("Name succesfully changed to " + restaurant.name);
        return restaurant.name;
    }

    private UUID getValidRestaurantId() {
        UUID restaurantId;
        while (true) {
            try {
                consolePrinter.print("Please enter the ID of the restaurant:");
                restaurantId = UUID.fromString(scanner.nextLine());
                Restaurant restaurant = restaurantRepository.getById(restaurantId);
                if (restaurant != null) {
                    return restaurantId;
                } else {
                    consolePrinter.print("Restaurant not found. Please enter a valid restaurant ID.");
                }
            } catch (IllegalArgumentException e) {
                consolePrinter.print("Invalid restaurant ID format. Please enter a valid UUID.");
            }
        }
    }

    private double getValidMealPrice(Scanner scanner) {
        var input = scanner.nextLine();
        while (true) {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                consolePrinter.print("Invalid price input, please try again");
                input = scanner.nextLine();
            }
        }
    }

}