package restaurant;

import java.util.*;

public class RestaurantProcessor {

    private final ConsolePrinter consolePrinter;
    private final Scanner scanner;
    private final RestaurantRepository restaurantRepository;
    private final JSONGenerator jsonGenerator;

    public RestaurantProcessor(ConsolePrinter consolePrinter, Scanner scanner, RestaurantRepository restaurantRepository, JSONGenerator jsonGenerator) {
        this.consolePrinter = consolePrinter;
        this.scanner = scanner;
        this.restaurantRepository = restaurantRepository;
        this.jsonGenerator = jsonGenerator;
    }

    public List<Restaurant> dumpRestaurantsToJson() {
        List<Restaurant> dumpedRestaurants = new ArrayList<>(restaurantRepository.getRestaurants());
        jsonGenerator.generateJSON(dumpedRestaurants);
        System.out.println(("Restaurants dumped to JSON file."));
        return dumpedRestaurants;
    }

    public String createRestaurant() {
        UUID restaurantId = UUID.randomUUID();

        System.out.println("Please type restaurant name:");
        String restaurantName = scanner.nextLine();

        System.out.println("Please type restaurant address");
        String restaurantAddress = scanner.nextLine();

        System.out.println("Please type restaurant type (ASIAN, ITALIAN, FRENCH, AMERICAN):");
        RestaurantType restaurantType = RestaurantType.getValidInput(scanner);

        restaurantRepository.create(new Restaurant(restaurantId, restaurantName, restaurantAddress, restaurantType));

        return String.format("Restaurant with ID %s has been created", restaurantId);
    }

    public Meal addMealToRestaurant() {
        System.out.println("Please type restaurant ID, to which you would like to add a meal\n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();

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
            return meal;
        } else {
            System.out.println("Restaurant not found.");
            return null;
        }
    }

    public Set<Meal> getAllMealsInRestaurant() {
        Set<Meal> meals = new HashSet<>();

        System.out.println("Enter restaurant ID to see its meals\n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();

        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant.meals.isEmpty()) {
            System.out.println("There are no meals in this restaurant");
        } else {
            System.out.println("Meals in restaurant \"" + restaurant.name + "\":");
            meals.addAll(restaurant.meals);
        }
        System.out.println(meals);
        return meals;
    }

    String changeRestaurantName() {
        System.out.println("Please type restaurant ID, which you would like to change a name in \n(below you can see all restaurants):");
        consolePrinter.printAllRestaurants();
        UUID restaurantId = getValidRestaurantId();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        System.out.println("Please type a new name for the restaurant currently called \"" + restaurant.name + "\"");
        restaurant.name = scanner.nextLine();
        System.out.println("Name succesfully changed to " + restaurant.name);
        return restaurant.name;
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