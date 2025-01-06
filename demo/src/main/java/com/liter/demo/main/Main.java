package com.liter.demo.main;

import com.liter.demo.model.Book;
import com.liter.demo.model.DataBook;
import com.liter.demo.model.Gutendex;
import com.liter.demo.services.APIService;
import com.liter.demo.services.AuthorService;
import com.liter.demo.services.BookService;
import com.liter.demo.services.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Main {

    private Scanner read = new Scanner(System.in);
    private APIService service = new APIService();
    private DataConvert converter = new DataConvert();

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    private final String ADDRESS = "https://gutendex.com/books/?search=";

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    ----------------------------
                    Escolha o número de sua opção:
                    1- buscar livro pelo título
                    2- listar livros registrados
                    3- listar autores registrados
                    4- listar autores vivos em um determinado ano
                    5- listar livros em um determinado idioma
                    0- sair                                 
                    ----------------------------
                    """;

            System.out.println(menu);
            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    LivingAuthorsTheYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    public void searchBook() {
        System.out.println("Insira o nome do livro que você deseja procurar:");
        var bookName = read.nextLine();
        var json = service.getData(ADDRESS + bookName.replace(" ", "%20"));

        Gutendex data = converter.obterDados(json, Gutendex.class);

        if (data.results() != null && !data.results().isEmpty()) {
            for (DataBook book : data.results()) {
                System.out.println(book);

                if (bookService.searchByTitle(book.title()).isEmpty()) {
                    bookService.saveBook(book);
                } else {
                    System.out.println("O livro '" + book.title() + "' já está registrado no banco de data.");
                }
            }
        } else {
            System.out.println("Nenhum resultado encontrado.");
        }
    }

    public void listRegisteredBooks() {
        List<String> FormattedBooks = bookService.listBooks();
        if (!FormattedBooks.isEmpty()) {
            for (String book : FormattedBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("Nenhum livro registrado.");
        }
    }

    public void listRegisteredAuthors() {
        List<String> formattedAuthors = authorService.listAuthors();
        if (!formattedAuthors.isEmpty()) {
            for (String author : formattedAuthors) {
                System.out.println(author);
            }
        } else {
            System.out.println("Nenhum autor registrado.");
        }
    }

    public void LivingAuthorsTheYear() {
        System.out.println("Insira o ano que deseja pesquisar");
        int year = read.nextInt();
        read.nextLine();


        List<String> livingAuthors = authorService.listLivingAuthorsInYear(year);
        if (!livingAuthors.isEmpty()) {
            for (String author : livingAuthors) {
                System.out.println(author);
            }
        } else {
            System.out.println("Nenhum autor estava vivo no ano especificado.");
        }
    }


    public void listBooksByLanguage() {
        System.out.println("Insira o idioma para realizar a busca:");
        System.out.println("es- espanhol");
        System.out.println("en- inglês");
        System.out.println("fr- francês");
        System.out.println("pt- português");

        String selectedLanguage = read.nextLine();


        List<Book> books = bookService.searchBooksByLanguage(selectedLanguage);
        if (!books.isEmpty()) {
            for(Book livro : books) {
                System.out.println("-------------- Livro ----------------");
                System.out.println("Título: " + livro.getTitle());
                System.out.println("Autores: " + livro.getAuthors().stream()
                        .map(autor -> autor.getName()) // Formata os nomes dos autores
                        .reduce((a, b) -> a + ", " + b) // Junta os nomes em uma string
                        .orElse("Nenhum autor encontrado")); // Caso não haja autor
                System.out.println("Idioma: " + livro.getLanguage());
                System.out.println("Número de Downloads: " + livro.getDowloadsCounts());
                System.out.println("--------------------------------------");
            }
        } else {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        }
    }
}
