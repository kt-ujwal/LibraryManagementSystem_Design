package librarymanagementsystem;

import java.util.Date;

public class BookReservation {
    private Date creationDate;
    private ReservationStatus reservationStatus;
    private String bookItemBarcode;
    private String memberId;

    public static BookReservation fetchReservationDetails(String bookItemBarcode){
        return null;}
    public static ReservationStatus getStatus(String bookItemBarcode){
       return null ;
    }
    public void updateStatus(ReservationStatus status){
        reservationStatus = ReservationStatus.COMPLETE;
    }

    public String getMemberId(){
        return this.memberId;
    }
    public static boolean reserveBookItem(BookItem bookItem){
        if(bookItem != null && bookItem.getStatus() == BookStatus.LOANED) {
            bookItem.updateBookStatus(BookStatus.RESERVED);

            return true;
        }
        return false;

    }

    public void sendBookAvailableNotification() {
    }
}
