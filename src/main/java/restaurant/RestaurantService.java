package restaurant;

import java.util.Scanner;
import java.util.UUID;

public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final Scanner scanner;

    public RestaurantService(RestaurantRepository restaurantRepository, Scanner scanner) {
        this.restaurantRepository = restaurantRepository;
        this.scanner = scanner;
    }

    private void printAppMenu() {
        System.out.println("Welcome to the Restaurant Manager, please see instructions below:");
        System.out.println("Type \"exit\", if you want to stop the program");
        System.out.println("Type \"1\", if you want to create a restaurant");
        System.out.println("Type \"2\", if you want to add a meal to a restaurant");
        System.out.println("Type \"3\", if you want to see all restaurants");
        System.out.println("Type \"4\", if you want to see all meals in a particular restaurant");
        System.out.println("Type \"5\", if you want to change the restaurant name");
    }

    public void runRestaurantManager() {
        printAppMenu();
        while (true) {
            System.out.println("Please choose an option (type \"6\" to see full menu again):");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                switch (input) {
                    case "1" -> createRestaurant();
                    case "2" -> addMealToRestaurant();
                    case "3" -> printAllRestaurants();
                    case "4" -> printAllMealsInRestaurant();
                    case "5" -> changeRestaurantName();
                    case "6" -> printAppMenu();
                    default -> System.out.println("Invalid option, please try again");
                }
            }
        }
    }

    private UUID createRestaurant() {
        UUID restaurantUuid = UUID.randomUUID();

        System.out.println("Please type restaurant name:");
        String restaurantName = scanner.nextLine();

        System.out.println("Please type restaurant address");
        String restaurantAddress = scanner.nextLine();

        System.out.println("Please type restaurant type (ASIAN, ITALIAN, FRENCH, AMERICAN):");
        RestaurantType restaurantType = RestaurantType.getValidInput(scanner);

        restaurantRepository.create(new Restaurant(restaurantUuid, restaurantName, restaurantAddress, restaurantType));
        System.out.println("Restaurant have been added");

        return restaurantUuid;
    }

    private void addMealToRestaurant() {
        System.out.println("Please type restaurant ID, to which you would like to add a meal\n(below you can see all restaurants):");
        printAllRestaurants();

        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant != null) {
            System.out.println("Please type meal name:");
            final var mealName = scanner.nextLine();

            System.out.println("Please type meal price:");
            final var mealPrice = getValidMealPrice(scanner);

            Meal meal = new Meal(mealName, mealPrice, restaurantId);
            restaurant.meals.add(meal);
            System.out.println("Meal added successfully");
        } else {
            System.out.println("Restaurant not found.");
        }
    }

    private void printAllRestaurants() {
        for (Restaurant restaurant : restaurantRepository.restaurants) {
            System.out.println(restaurant);
        }
    }

    private void printAllMealsInRestaurant() {
        System.out.println("Enter restaurant ID to see its meals\n(below you can see all restaurants):");
        printAllRestaurants();

        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant.meals.isEmpty()) {
            System.out.println("There are no meals in this restaurant");
        } else {
            System.out.println("Meals in restaurant \"" + restaurant.name + "\":");
            for (Meal meal : restaurant.meals) {
                System.out.println(meal);
            }
        }
    }

    private void changeRestaurantName() {
        System.out.println("Please type restaurant ID, which you would like to change a name in \n(below you can see all restaurants):");
        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        System.out.println("Please type a new name for the restaurant currently called \"" + restaurant.name + "\"");
        restaurant.name = scanner.nextLine();
        System.out.println("Name succesfully changed to " + restaurant.name);
    }

    private UUID getValidRestaurantId() {
        UUID restaurantId;
        while (true) {
            try {
                System.out.println("Please enter the ID of the restaurant:");
                restaurantId = UUID.fromString(scanner.nextLine());
                Restaurant restaurant = restaurantRepository.getById(restaurantId);
                if (restaurant != null) {
                    return restaurantId;
                } else {
                    System.out.println("Restaurant not found. Please enter a valid restaurant ID.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid restaurant ID format. Please enter a valid UUID.");
            }
        }
    }

    private double getValidMealPrice(Scanner scanner) {
        var input = scanner.nextLine();
        while (true) {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price input, please try again");
                input = scanner.nextLine();
            }
        }
    }
}