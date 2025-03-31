package librarymanagementsystem;

import java.time.LocalDate;
import java.util.Date;

public class Member extends Account {
    private Date dateofMembership;
    private int totalBooksCheckedOut;

    public int getTotalBooksCheckedOut() {return totalBooksCheckedOut;}

    public boolean reserveBookItem(BookItem bookItem){
        return BookReservation.reserveBookItem(bookItem);
    }

    public String getId(){
       return this.id;
    }

    public boolean checkoutBookItem(BookItem bookItem){
        if(this.getTotalBooksCheckedOut() >= Constants.MAX_BOOKS_ISSUES_TO_USER){
            System.out.println("Max number of books issues reached, please return a book first!");
            return false;
        }

        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if(bookReservation !=null && bookReservation.getMemberId().equals(this.getId())){
            System.out.println("Reserved by another member!");
            return false;
        }
        else if(bookReservation != null ){
            bookReservation.updateStatus(ReservationStatus.COMPLETE);
        }

        if(!bookItem.checkout(this.getId())){
            System.out.println("Checkout Failed");
            return false;
        }

        this.incrementTotalBooksCheckedout();
        return true;
    }

    private void checkForFine(String bookItembarcode){
        BookLending bookLending = BookLending.fetchLendingDetails(bookItembarcode);
        Date dueDate = bookLending.getDueDate();
        Date today = new Date();

        if(today.compareTo(dueDate) > 0){
            long diff = today.getTime() - dueDate.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            Fine.calculateFine(this.getId(),days);
        }
    }

    public void returnBookItem(BookItem bookItem){
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null) {
            // book item has a pending reservation
            bookItem.updateBookStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
        }
        bookItem.updateBookStatus(BookStatus.AVAILABLE);
    }

    public boolean renewBookItem(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        // check if this book item has a pending reservation from another member
        if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
            System.out.println("This book is reserved by another member");
            this.decrementTotalBooksCheckedout();
            bookItem.updateBookStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from this member
            bookReservation.updateStatus(ReservationStatus.COMPLETE);
        }
        BookLending.lendBook(bookItem, this.getId());
        bookItem.updateDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
        return true;
    }

    private void incrementTotalBooksCheckedout() {
        this.totalBooksCheckedOut += 1;
    }

    private void decrementTotalBooksCheckedout() {
        this.totalBooksCheckedOut -= 1;
    }
}
