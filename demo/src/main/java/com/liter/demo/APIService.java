package com.liter.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class APIService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "gutendex.com/books";
    private List<Book> favoriteBooks;
    JsonObject json = new JsonObject();

    public APIService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public List<Book> searchBooks(String query) {
        // Build the complete URL
        String url = API_URL;

        // Use RestTemplate to make an HTTP GET request
        String response = restTemplate.getForObject(url, String.class);

        // Parse the JSON response into a list of Book objects
        List<Book> books = new ArrayList<>();
        if (response != null) {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");

                Book book = new Book();
                book.setTitle(volumeInfo.optString("title", "Unknown"));
                book.setAuthor(volumeInfo.optJSONArray("authors") != null
                        ? String.join(", ", volumeInfo.optJSONArray("authors").toList().toArray(new String[0]))
                        : "Unknown");
                book.setDescription(volumeInfo.optString("description", "No description available"));

                books.add(book);
            }
        }

        // Return the list of books
        return books;
    }
}

