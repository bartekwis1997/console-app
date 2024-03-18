package restaurant;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

//        when(restaurantProcessor.createRestaurant()).thenReturn((new Restaurant(restaurantId, restaurantName, restaurantAddress, restaurantType)))

        //When

        //Then
    }

    @Test
    void should_add_meal_to_restaurant() {
        //Given

        //When

        //Then
    }

    @Test
    void should_get_all_meals_in_restaurant() {
        //Given

        //When

        //Then
    }

    @Test
    void should_change_restaurant_name() {
        //Given

        //When

        //Then
    }
}