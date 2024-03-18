package restaurant;

import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantProcessorTest {

    private ConsolePrinter consolePrinter = mock(ConsolePrinter.class);
    private Scanner scanner = mock(Scanner.class);
    private RestaurantRepository restaurantRepository = mock(RestaurantRepository.class);
    private JSONGenerator jsonGenerator = mock(JSONGenerator.class);
    RestaurantProcessor restaurantProcessor = new RestaurantProcessor(consolePrinter, scanner, restaurantRepository, jsonGenerator);

    @Test
    void should_dump_restaurants_to_json() {
        //Given
        when(restaurantRepository.getRestaurants()).thenReturn(Set.of(new Restaurant(randomUUID(), "Kebab u grubego", "Warszawska 11, Kraków 31-222",
                        RestaurantType.TURKISH),
                new Restaurant(randomUUID(), "Milano Pizza", "Sobczyka 12/3, Warszawa 33-312", RestaurantType.ITALIAN)));
//        jsonGenerator.generateJSON(dumpedRestaurants);
//        consolePrinter.print("Restaurants dumped to JSON file.");

        //When
        List<Restaurant> restaurants = restaurantProcessor.dumpRestaurantsToJson();

        //Then
        assertEquals(2, restaurants.size());
        //zmienne do sekcji then
    }

    @Test
    void should_create_restaurant() {
        //Given
        UUID restaurantId = randomUUID();
        String restaurantName = "New restaurant";
        String restaurantAddress = "New Address";
        RestaurantType restaurantType = RestaurantType.AMERICAN;

        when(scanner.nextLine())
                .thenReturn(restaurantName)
                .thenReturn(restaurantAddress)
                .thenReturn("AMERICAN");

        //When
        Restaurant newRestaurant = restaurantProcessor.createRestaurant();

        //Then
        assertNotNull(newRestaurant);
        assertEquals(restaurantName, newRestaurant.name);
        assertEquals(restaurantAddress, newRestaurant.address);
        assertEquals(restaurantType, newRestaurant.type);
    }

    @Test
    void should_add_meal_to_restaurant() {
        //Given
        UUID restaurantId = randomUUID();
        when(scanner.nextLine()).thenReturn(restaurantId.toString());
        String mealName = "Meal";
        double mealPrice = 599.9;

        when(scanner.nextLine())
                .thenReturn(restaurantId.toString())
                .thenReturn(mealName)
                .thenReturn(String.valueOf(mealPrice));

        Restaurant newRestaurant = new Restaurant(restaurantId, "Name", "Address", RestaurantType.FRENCH);
        when(restaurantRepository.getById(restaurantId)).thenReturn(newRestaurant);

        //When
        Meal newMeal = restaurantProcessor.addMealToRestaurant();

        //Then
        assertNotNull(newMeal);
        assertEquals(mealName, newMeal.getName());
        assertEquals(mealPrice, newMeal.getPrice());
        assertEquals(restaurantId, newMeal.getId());
    }

    @Test
    void should_get_all_meals_in_restaurant() {
        // Given
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurant Name", "Restaurant Address", RestaurantType.ITALIAN);

        when(scanner.nextLine())
                .thenReturn(restaurantId.toString());

        when(restaurantRepository.getById(restaurantId)).thenReturn(restaurant);

        // When
        Set<Meal> actualMeals = restaurantProcessor.getAllMealsInRestaurant();

        // Then
        assertNotNull(actualMeals);

    }

    @Test
    void should_change_restaurant_name() {
        //Given
        UUID restaurantId = UUID.randomUUID();
        String newName = "Some new name";

        when(scanner.nextLine())
                .thenReturn(restaurantId.toString())
                .thenReturn(newName);

        Restaurant restaurant = new Restaurant(restaurantId, "Old name", "Address", RestaurantType.FRENCH);
        when(restaurantRepository.getById(restaurantId)).thenReturn(restaurant);

        //When
        restaurantProcessor.changeRestaurantName();

        //Then
        assertEquals(newName, restaurant.name);
    }
}