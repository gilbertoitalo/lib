package com.liter.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import com.google.gson.JsonObject;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class APIService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "gutendex.com/books";
    private final ObjectMapper objectMapper;
    private List<Book> favoriteBooks;
    JsonObject json = new JsonObject();

    public APIService() {

        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.favoriteBooks = new ArrayList<>();
    }

    public List<Book> searchBooks(String query) {
        // Build the complete URL
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("search", query)
                .build()
                .toUriString();

        // Use RestTemplate to make an HTTP GET request
        String response = restTemplate.getForObject(url, String.class);

        return parseBookResponse(response);
    }

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void addToFavorites(Book book) {
        if (!favoriteBooks.contains(book)) {
            favoriteBooks.add(book);
        }
    }

    public void removeFromFavorites(int index) {
        if (index >= 0 && index < favoriteBooks.size()) {
            favoriteBooks.remove(index);
        }
    }

    private List<Book> parseBookResponse(String response) {
        List<Book> books = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode results = rootNode.get("results");

            if (results != null && results.isArray()) {
                for (JsonNode bookNode : results) {
                    Book book = new Book();

                    book.setId(bookNode.get("id").asToken().asString());
                    book.setTitle(getStringValue(bookNode, "title"));
                    book.setAuthors(parseAuthors(bookNode.get("authors")));
                    book.setPublisher(getStringValue(bookNode, "publisher"));
                    book.setPublishedDate(getStringValue(bookNode, "publisher_date"));
                    book.setDescription(getStringValue(bookNode, "descripton"));

                    //Parse authors
                    List<String> authors = new ArrayList<>();
                    JsonNode authorsNode = bookNode.get("authors");
                    if (authorsNode != null && authorsNode.isArray()){
                        for (JsonNode authorNode : authorsNode){
                            authors.add(getStringValue(authorNode, "name"));
                        }
                    }
                    book.setAuthors(authors);
                    JsonNode pageCountNode = bookNode.get("page_count");
                    if (pageCountNode != null && !pageCountNode.isNull()) {
                        book.setPageCount(pageCountNode.asInt());
                    }


                    books.add(book);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing book response: " + e.getMessage());
        }
        return books;
    }
    private String getStringValue(JsonNode json, String key) {
        JsonNode node = json.get(key);
        return node != null && !node.isNull() ? node.asText() : "N/A";
    }

    private List<String> parseAuthors(JsonNode authorsNode) {
        List<String> authors = new ArrayList<>();
        if (authorsNode != null && authorsNode.isArray()) {
            for (JsonNode author : authorsNode) {
                String name = author.get("name").asText();
                authors.add(name);
            }
        }
        return authors;
    }

    private List<String> parseJsonArrayToList(JsonNode arrayNode) {
        List<String> list = new ArrayList<>();
        if (arrayNode != null && arrayNode.isArray()) {
            for (JsonNode element : arrayNode) {
                list.add(element.asText());
            }
        }
        return list;
    }

    private Map<String, String> parseFormats(JsonNode formatsNode) {
        Map<String, String> formatMap = new HashMap<>();
        if (formatsNode != null && formatsNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = formatsNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                formatMap.put(entry.getKey(), entry.getValue().asText());
            }
        }
        return formatMap;
    }
}
