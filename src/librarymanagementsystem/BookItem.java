package librarymanagementsystem;

import java.time.LocalDate;
import java.util.List;

public class BookItem extends Book {
    private String barCode;
    private boolean available;
    private boolean isReferenceOnly;
    private Rack placedAt;
    private double price;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private BookFormat format;
    private BookStatus status;
    private int count;

    public BookItem(String ISBN, String title, String subject, List<Author> authors) {
        super(ISBN, title, subject, authors);
        this.available = false;
        this.isReferenceOnly = true;
        this.placedAt = null;
    }

    public BookItem(String ISBN, String title, String subject, List<Author> authors,String barCode, boolean available, Rack rack, double price,int count) {
        super(ISBN, title, subject, authors);
        this.barCode = barCode;
        this.available = available;
        this.isReferenceOnly = false;
        this.placedAt = null;
        this.price = price;
        this.borrowedDate = null;
        this.dueDate = null;
        this.format = null;
        this.status = BookStatus.AVAILABLE;
        this.count = count;
    }

    public boolean getIsReferenceOnly() {
        return isReferenceOnly;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getBarcode() {
        return barCode;
    }



    public void updateBookStatus(BookStatus newStatus) {
        this.status = newStatus;
    }


    public boolean checkout(String memberId) {
        if(this.getIsReferenceOnly()) {
            System.out.println("Reference only");
        }
        if(this.isAvailable()) {
            if(BookLending.lendBook(this, memberId)){
                this.decrementBookCount();
                if(this.count == 0){ this.available = false; }
                this.updateBookStatus(BookStatus.LOANED);
                return true;
            }
        }
        return false;
    }

    private void decrementBookCount() {
        this.count--;
    }

    private void incrementBookCount() {
        this.count++;
    }

    public BookStatus getStatus() {
    return status;
    }

    public void updateDueDate(LocalDate localDate) {
        this.dueDate = localDate;
    }
}
