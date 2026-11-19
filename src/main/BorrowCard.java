package main;
import java.sql.Timestamp;

public class BorrowCard {
    private long cardId;
    private String bookTitle;
    private String borrowerName;
    private Timestamp borrowDate;
    private Timestamp returnDeadline;
    private int quantity;
    private String status;

    // Con
    public BorrowCard() {
    }
    // Constructor có tham số để khởi tạo đối tượng BorrowCard
    public BorrowCard(long cardId, String bookTitle, String borrowerName, Timestamp borrowDate, Timestamp returnDeadline, int quantity, String status) {
        this.cardId = cardId;
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        this.returnDeadline = returnDeadline;
        this.quantity = quantity;
        this.status = status;
    }
    // Getter và Setter cho các thuộc tính

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Timestamp getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Timestamp getReturnDeadline() {
        return returnDeadline;
    }

    public void setReturnDeadline(Timestamp returnDeadline) {
        this.returnDeadline = returnDeadline;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //toString

    @Override
    public String toString() {
        return "BorrowCard{" +
                "cardId=" + cardId +
                ", bookTitle='" + bookTitle + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDeadline=" + returnDeadline +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }
}
