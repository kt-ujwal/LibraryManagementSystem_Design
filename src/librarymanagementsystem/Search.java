package librarymanagementsystem;

import java.awt.print.Book;
import java.util.List;

public interface Search {
    public List<Book> searchByTitle(String title);
    public List<Book> searchByAuthor(String author);
}
