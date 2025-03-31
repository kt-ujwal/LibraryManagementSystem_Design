package librarymanagementsystem;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

public class Catalog implements Search {
    private Map<String,List<Book>> bookTitles;
    private Map<String,List<Book>> bookAuthors;
    @Override
    public List<Book> searchByTitle(String title) {
        return bookTitles.get(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookAuthors.get(author);
    }

}
