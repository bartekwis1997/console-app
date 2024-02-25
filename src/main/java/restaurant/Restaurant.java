package restaurant;

import java.util.*;

public class Restaurant {
    // creating restaurant class attributes
    // creating and initializing restaurant id, list of meals
    // creating and initializing map of restaurants and scanner object
    private String name;
    private final String address;
    private final RestaurantType type;
    private static int restaurantId = 1;
    private final static List<Meal> MEALS = new ArrayList<>();
    private final static Map<Integer, Restaurant> RESTAURANTS = new HashMap<>();
    private final static Scanner scanner = new Scanner(System.in);

    // creating class constructor, with parameters type string for name and address
    // and type restaurant type for type
    public Restaurant(String name, String address, RestaurantType type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }

    // creating setter to be able to change restaurant name in a method
    public void setName(String name) {
        this.name = name;
    }

    // overriding toString() method
    @Override
    public String toString() {
        return name + ", " + address + ", " + type;
    }

    // creating final static method to be able to run the app without its object instance
    public final static void runRestaurantManager() {
        // creating while loop, that accepts user input, to keep the app going until user types "exit"
        while (true) {
            System.out.println("Please choose an option (type \"6\" to see full menu again):");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                //creating switch statement, with user input, to choose option 1-5 and run methods corresponding to each case
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

    // creating final static method, without returning anything and without parameters
    // that creates 3 initial restaurants and put them inside a map with
    // their IDs
    public final static void createInitialRestaurants() {
        RESTAURANTS.put(restaurantId++, new Restaurant("Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH));
        RESTAURANTS.put(restaurantId++, new Restaurant("Milano Pizza", "Sobczyka 12/3, Warszawa 33-312", RestaurantType.ITALIAN));
        RESTAURANTS.put(restaurantId++, new Restaurant("Meat Burger", "Lipowa 31b/1, Gdańsk 18-111", RestaurantType.AMERICAN));
    }

    // creating final static method, without returning anything and without parameters
    // that prints menu of the app to show options to a user
    public final static void printAppMenu() {
        System.out.println("Welcome to the Restaurant Manager, please see instructions below:");
        System.out.println("Type \"exit\", if you want to stop the program");
        System.out.println("Type \"1\", if you want to create a restaurant");
        System.out.println("Type \"2\", if you want to add a meal to a restaurant");
        System.out.println("Type \"3\", if you want to see all restaurants");
        System.out.println("Type \"4\", if you want to see all meals in a particular restaurant");
        System.out.println("Type \"5\", if you want to change the restaurant name");
    }

    // creating a final static method, wwithout returning anything and without parameters
    // that asks user for input and creates a new restaurant
    // based on users input, and puts it in the map with their ids
    private final static void createRestaurant() {
        System.out.println("Please type restaurant name:");
        String restaurantName = scanner.nextLine();

        System.out.println("Please type restaurant address");
        String restaurantAddress = scanner.nextLine();

        System.out.println("Please type restaurant type (ASIAN, ITALIAN, FRENCH, AMERICAN):");
        RestaurantType restaurantType = RestaurantType.getValidInput(scanner);

        RESTAURANTS.put(restaurantId, new Restaurant(restaurantName, restaurantAddress, restaurantType));
        System.out.println("Following restaurant have been added - ID: " + restaurantId + ", " + RESTAURANTS.get(restaurantId));
    }

    // creating a final static method, without returning anything and without parameters
    // that asks user for input and creates a new meal for a
    // corresponding restaurant id, and adds it to a list of meals
    private final static void addMealToRestaurant() {
        System.out.println("Please type meal name:");
        String mealName = scanner.nextLine();

        System.out.println("Please type meal price:");
        double mealPrice = getValidMealPrice(scanner);

        System.out.println("Please type restaurant ID, to which you would like to add a meal\n(below you can see all restaurants):");
        printAllRestaurants();
        int restaurantId = Integer.parseInt(scanner.nextLine());
        // checking if restaurant of ID provided by user exists, adding it to a list if it passes the condition successfully
        if (RESTAURANTS.get(restaurantId) != null) {
            MEALS.add(new Meal(mealName, mealPrice, restaurantId));
            System.out.println("Meal named \"" + mealName + "\" has been added");
        } else {
            // if not, a message is printed about not finding the restaurant with provided ID
            System.out.println("Restaurant with provided ID not found");
        }
    }

    // creating a private final static method, returning type double, with scanner
    // type parameter, to validate input for meal price and handle exception if given wrong input
    private final static double getValidMealPrice(Scanner scanner) {
        String input = scanner.nextLine();
        while (true) {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price input, please try again");
                input = scanner.nextLine();
            }
        }
    }

    // creating a private final static method, without returning anything and without parameters
    // that uses an entry set on a map, to display all existing restaurants to a user
    private final static void printAllRestaurants() {
        for (Map.Entry<Integer, Restaurant> restaurant : RESTAURANTS.entrySet()) {
            System.out.println("Restaurant ID " + restaurant.getKey() + ": " + restaurant.getValue());
        }
    }

    // creating a private final static method, without returning anything and without parameters
    // that accepts restaurant ID from a user, to print its meals (if contains any)
    private final static void printAllMealsInRestaurant() {
        System.out.println("Enter restaurant ID to see its meals\n(below you can see all restaurants):");
        printAllRestaurants();
        int restaurantId = Integer.parseInt(scanner.nextLine());
        // checking if restaurant ID provided by user exists, if not it prints out a message
        if (RESTAURANTS.get(restaurantId) == null) {
            System.out.println("Restaurant with provided ID not found");
            // checking if list of meals is empty, if yes printing out a message
        } else if (MEALS.isEmpty()) {
            System.out.println("Restaurant with provided ID does not have any meals");
            // if conditions above are passed with success, it prints list of all meals using for each loop
        } else {
            for (Meal meal : MEALS) {
                System.out.println(meal);
            }
        }
    }

    // creating a private final static method, without returning anything and without parameters
    // that accepts restaurant ID from a user, to change a corresponding restaurant name
    private final static void changeRestaurantName() {
        System.out.println("Please type restaurant ID, which you would like to change a name in \n(below you can see all restaurants):");
        printAllRestaurants();
        int restaurantId = Integer.parseInt(scanner.nextLine());
        // checking if a restaurant with provided ID exists, if not then prints out a message
        if (RESTAURANTS.get(restaurantId) == null) {
            System.out.println("Restaurant with provided ID not found");
        } else {
            // if yes, then accepting a new user input for a new restaurant name
            // and changing it using a setter
            System.out.println("Please provide a new name for the restaurant:");
            String newName = scanner.nextLine();
            RESTAURANTS.get(restaurantId).setName(newName);
            System.out.println("Name of restaurant with ID: " + restaurantId + " has been changed to \"" + newName + "\" successfully");
        }
    }

}
