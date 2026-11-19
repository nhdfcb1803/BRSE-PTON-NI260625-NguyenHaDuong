package main;

import java.sql.Timestamp;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BorrowCardManager manager = new BorrowCardManager();

        // Hiển thị danh sách phiếu mượn hiện tại
        System.out.println("===== BORROW CARD LIST =====");
        manager.displayAllBorrowCards();

        System.out.println("\n===== UPDATE BORROW CARD =====");

        // Nhập ID phiếu mượn cần cập nhật
        long cardId;

        do {
            System.out.print("Enter card ID: ");

            try {
                cardId = Long.parseLong(scanner.nextLine());

                if (cardId <= 0) {
                    System.out.println("Card ID must be greater than 0!");
                }

            } catch (NumberFormatException e) {
                cardId = 0;
                System.out.println("Card ID must be an integer!");
            }

        } while (cardId <= 0);

        // Nhập tên sách và kiểm tra dữ liệu
        String bookTitle;

        do {
            System.out.print("Enter book title: ");
            bookTitle = scanner.nextLine().trim();

            if (bookTitle.isEmpty()) {
                System.out.println("Book title cannot be empty!");
            }

        } while (bookTitle.isEmpty());

        // Nhập tên người mượn và kiểm tra dữ liệu
        String borrowerName;

        do {
            System.out.print("Enter borrower name: ");
            borrowerName = scanner.nextLine().trim();

            if (borrowerName.isEmpty()) {
                System.out.println("Borrower name cannot be empty!");
            }

        } while (borrowerName.isEmpty());

        // Nhập ngày mượn và kiểm tra định dạng
        Timestamp borrowDate = null;

        while (borrowDate == null) {

            System.out.print("Enter borrow date (yyyy-MM-dd HH:mm:ss): ");

            try {
                borrowDate = Timestamp.valueOf(scanner.nextLine());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format! Please try again.");
            }
        }

        // Nhập hạn trả và kiểm tra dữ liệu
        Timestamp returnDeadline = null;

        while (returnDeadline == null) {

            System.out.print("Enter return deadline (yyyy-MM-dd HH:mm:ss): ");

            try {
                returnDeadline = Timestamp.valueOf(scanner.nextLine());

                // Kiểm tra hạn trả phải sau ngày mượn
                if (!returnDeadline.after(borrowDate)) {
                    System.out.println("Return deadline must be after borrow date!");
                    returnDeadline = null;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format! Please try again.");
            }
        }

        // Nhập số lượng và kiểm tra dữ liệu
        int quantity;

        do {
            System.out.print("Enter quantity: ");

            try {
                quantity = Integer.parseInt(scanner.nextLine());

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0!");
                }

            } catch (NumberFormatException e) {
                quantity = 0;
                System.out.println("Quantity must be an integer!");
            }

        } while (quantity <= 0);

        // Nhập trạng thái và kiểm tra dữ liệu
        String status;

        do {
            System.out.print("Enter status: ");
            status = scanner.nextLine().trim();

            if (status.isEmpty()) {
                System.out.println("Status cannot be empty!");
            }

        } while (status.isEmpty());

        // Tạo đối tượng BorrowCard từ dữ liệu vừa nhập
        BorrowCard borrowCard = new BorrowCard(
                cardId,
                bookTitle,
                borrowerName,
                borrowDate,
                returnDeadline,
                quantity,
                status
        );

        // Gọi phương thức cập nhật phiếu mượn
        manager.updateBorrowCard(borrowCard);

        // Hiển thị danh sách sau khi cập nhật
        System.out.println("\n===== UPDATED BORROW CARD LIST =====");
        manager.displayAllBorrowCards();

        System.out.println("\n===== DELETE BORROW CARD =====");

        // Hiển thị danh sách phiếu mượn
        manager.displayAllBorrowCards();

        // Nhập ID cần xóa và kiểm tra dữ liệu
        do {
            System.out.print("Enter card ID to delete: ");

            try {
                cardId = Long.parseLong(scanner.nextLine());

                if (cardId <= 0) {
                    System.out.println("ID must be greater than 0!");
                }

            } catch (NumberFormatException e) {
                cardId = 0;
                System.out.println("ID must be an integer!");
            }

        } while (cardId <= 0);

        // Xác nhận trước khi xóa
        System.out.print("Are you sure you want to delete this card? (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            // Gọi phương thức xóa phiếu mượn
            manager.deleteBorrowCard(cardId);

            // Hiển thị danh sách sau khi xóa
            System.out.println("\n===== UPDATED BORROW CARD LIST =====");
            manager.displayAllBorrowCards();

        } else {
            System.out.println("Delete operation cancelled!");
        }

        System.out.println("\n===== SEARCH BORROW CARDS BY BOOK TITLE =====");

        // Nhập tên sách và kiểm tra dữ liệu
        bookTitle = "";

        do {
            System.out.print("Enter book title: ");
            bookTitle = scanner.nextLine().trim();

            if (bookTitle.isEmpty()) {
                System.out.println("Book title cannot be empty!");
            }

        } while (bookTitle.isEmpty());

        // Gọi phương thức tìm kiếm
        manager.searchBorrowCardsByBookTitle(bookTitle);

        scanner.close();
    }
}