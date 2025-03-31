package librarymanagementsystem;

import java.util.List;

public abstract class Book {
    private String ISBN;
    private String title;
    private String subject;
    private List<Author> authors;

    public Book(String ISBN, String title, String subject, List<Author> authors) {
        this.ISBN = ISBN;
        this.title = title;
        this.subject = subject;
        this.authors = authors;
    }
}
