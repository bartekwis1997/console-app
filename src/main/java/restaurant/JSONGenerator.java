package restaurant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONGenerator {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public boolean generateJSON(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) {
            System.err.println("No restaurants provided.");
            return false;
        }

        try {
            ObjectNode rootNode = objectMapper.createObjectNode();

            // Get or create the "restaurants" array
            ArrayNode restaurantArray = rootNode.withArray("restaurants");

            // Update existing restaurants or add new ones
            for (Restaurant restaurant : restaurants) {
                boolean updated = false;
                for (JsonNode node : restaurantArray) {
                    String restaurantId = node.get("id").asText();
                    if (restaurantId.equals(restaurant.id.toString())) {
                        // Restaurant already exists, update its meals
                        updateRestaurantWithMeals((ObjectNode) node, restaurant.meals);
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    // Restaurant does not exist, add it to the array
                    addRestaurantToJSON(restaurantArray, restaurant);
                }
            }

            // Construct the file path
            String filePath = "src/main/resources/restaurants.json";

            // Write updated JSON data back to the file
            objectMapper.writeValue(new File(filePath), rootNode);
            System.out.println("JSON data successfully updated in the file: " + filePath);
            return true; // Operation successful
        } catch (IOException e) {
            System.err.println("Error writing JSON data: " + e.getMessage());
            e.printStackTrace(); // Log the full stack trace for debugging
            return false; // Operation failed
        }
    }

    private ObjectNode updateRestaurantWithMeals(ObjectNode restaurantNode, List<Meal> meals) {
        ArrayNode mealsArray = restaurantNode.withArray("meals");
        for (Meal meal : meals) {
            ObjectNode mealNode = objectMapper.createObjectNode();
            mealNode.put("id", meal.getId().toString());
            mealNode.put("name", meal.getName());
            mealsArray.add(mealNode);
        }
        return restaurantNode;
    }

    private ObjectNode addRestaurantToJSON(ArrayNode restaurantArray, Restaurant restaurant) {
        ObjectNode restaurantNode = objectMapper.createObjectNode();
        restaurantNode.put("id", restaurant.id.toString());
        restaurantNode.put("name", restaurant.name);
        restaurantNode.put("type", restaurant.type.toString());

        ArrayNode mealsArray = restaurantNode.withArray("meals");
        for (Meal meal : restaurant.meals) {
            ObjectNode mealNode = objectMapper.createObjectNode();
            mealNode.put("id", meal.getId().toString());
            mealNode.put("name", meal.getName());
            mealsArray.add(mealNode);
        }

        restaurantArray.add(restaurantNode);
        return restaurantNode;
    }
}
