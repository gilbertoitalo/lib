package com.liter.demo;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final APIService APIService;
    private final Scanner scanner;
    private boolean running;

    public ConsoleUI(APIService APIService) {
        this.APIService = APIService;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void start() {
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== Book Catalog Menu ===");
        System.out.println("1. Search Books");
        System.out.println("2. View Favorite Books");
        System.out.println("3. Add Book to Favorites");
        System.out.println("4. Remove Book from Favorites");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                searchBooks();
                break;
            case 2:
                viewFavoriteBooks();
                break;
            case 3:
                addBookToFavorites();
                break;
            case 4:
                removeBookFromFavorites();
                break;
            case 5:
                System.out.println("Exiting Book Catalog. Goodbye!");
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void searchBooks() {
        System.out.print("Enter book search term: ");
        String query = scanner.nextLine();
        List<Book> searchResults = APIService.searchBooks(query);

        if (searchResults.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("\nSearch Results:");
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + searchResults.get(i).getTitle());
        }
    }

    private void viewFavoriteBooks() {
        List<Book> favorites = APIService.getFavoriteBooks();

        if (favorites.isEmpty()) {
            System.out.println("No favorite books.");
            return;
        }

        System.out.println("\nFavorite Books:");
        for (Book book : favorites) {
            System.out.println(book);
        }
    }

    private void addBookToFavorites() {
        System.out.print("Enter book search term: ");
        String query = scanner.nextLine();
        List<Book> searchResults = APIService.searchBooks(query);

        if (searchResults.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        System.out.println("\nSearch Results:");
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + searchResults.get(i).getTitle());
        }

        System.out.print("Enter the number of the book to add to favorites: ");
        int choice = getUserChoice() - 1;

        if (choice >= 0 && choice < searchResults.size()) {
            Book selectedBook = searchResults.get(choice);
            APIService.addToFavorites(selectedBook);
            System.out.println("Book added to favorites: " + selectedBook.getTitle());
        } else {
            System.out.println("Invalid book selection.");
        }
    }

    private void removeBookFromFavorites() {
        List<Book> favorites = APIService.getFavoriteBooks();

        if (favorites.isEmpty()) {
            System.out.println("No favorite books to remove.");
            return;
        }

        System.out.println("\nFavorite Books:");
        for (int i = 0; i < favorites.size(); i++) {
            System.out.println((i + 1) + ". " + favorites.get(i).getTitle());
        }

        System.out.print("Enter the number of the book to remove from favorites: ");
        int choice = getUserChoice() - 1;

        if (choice >= 0 && choice < favorites.size()) {
            Book bookToRemove = favorites.get(choice);
            APIService.removeFromFavorites(choice);
            System.out.println("Book removed from favorites: " + bookToRemove.getTitle());
        } else {
            System.out.println("Invalid book selection.");
        }
    }
}
