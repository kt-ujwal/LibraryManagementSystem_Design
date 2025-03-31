package librarymanagementsystem;

import java.util.Date;

public class BookLending {
    private Date creationDate;
    private Date returnDate;
    private Date dueDate;
    private String bookItemBarcode;
    private String memberId;

    public static boolean lendBook(BookItem bookItem, String memberId) { return true;}
    public static BookLending fetchLendingDetails(String bookItemBarcode){return null;}

    public Date getDueDate() {
        return this.dueDate;
    }
}
