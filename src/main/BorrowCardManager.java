package main;
import java.sql.Timestamp;
import database.ConnectDatabase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowCardManager {
    // Hiển thị danh sách tất cả phiếu mượn sách
    public void displayAllBorrowCards() {
        // Gọi Stored Procedure để lấy danh sách phiếu mượn
        String sql = "{CALL sp_get_all_borrow_cards()}";

        // Kết nối Database và thực hiện truy vấn
        try (Connection conn = ConnectDatabase.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql);
             ResultSet rs = cstmt.executeQuery()) {

            // Định dạng
            System.out.println("---------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-20s %-22s %-22s %-10s %-15s%n",
                    "ID", "BOOK TITLE", "BORROWER",
                    "BORROW DATE", "RETURN DEADLINE", "QUANTITY", "STATUS");
            System.out.println("---------------------------------------------------------------------------------------------");

            // Duyệt từng dòng dữ liệu trả về
            while (rs.next()) {
                // Tạo đối tượng BorrowCard từ dữ liệu trong Database
                BorrowCard borrowCard = new BorrowCard(
                        rs.getLong("card_id"),
                        rs.getString("book_title"),
                        rs.getString("borrower_name"),
                        rs.getTimestamp("borrow_date"),
                        rs.getTimestamp("return_deadline"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
                // Hiển thị thông tin phiếu mượn
                System.out.printf(
                        "%-5d %-20s %-20s %-22s %-22s %-10d %-15s%n",
                        borrowCard.getCardId(),
                        borrowCard.getBookTitle(),
                        borrowCard.getBorrowerName(),
                        borrowCard.getBorrowDate(),
                        borrowCard.getReturnDeadline(),
                        borrowCard.getQuantity(),
                        borrowCard.getStatus()
                );
            }
        } catch (SQLException e) {
            // Hiển thị lỗi nếu truy vấn thất bại
            e.printStackTrace();
        }
    }
    // Thêm phiếu mượn sách
    public void addBorrowCard(BorrowCard borrowCard) {

        // Gọi Stored Procedure để thêm phiếu mượn sách
        String sql = "{CALL sp_add_borrow_card(?, ?, ?, ?, ?, ?)}";

        // Kết nối Database và thực hiện Stored Procedure
        try (Connection conn = ConnectDatabase.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Truyền các tham số vào Stored Procedure
            cstmt.setString(1, borrowCard.getBookTitle());
            cstmt.setString(2, borrowCard.getBorrowerName());
            cstmt.setTimestamp(3, borrowCard.getBorrowDate());
            cstmt.setTimestamp(4, borrowCard.getReturnDeadline());
            cstmt.setInt(5, borrowCard.getQuantity());
            cstmt.setString(6, borrowCard.getStatus());

            // Thực thi Stored Procedure
            cstmt.executeUpdate();

            // Thông báo thêm thành công
            System.out.println("Added borrow card successfully!");

        } catch (SQLException e) {

            // Hiển thị lỗi nếu thêm phiếu mượn thất bại
            e.printStackTrace();
        }
    }
    // Cập nhật phiếu mượn sách
    public void updateBorrowCard(BorrowCard borrowCard) {

        // Gọi Stored Procedure để cập nhật phiếu mượn
        String sql = "{CALL sp_update_borrow_card(?, ?, ?, ?, ?, ?, ?)}";

        // Kết nối Database và thực hiện Stored Procedure
        try (Connection conn = ConnectDatabase.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Truyền các tham số vào Stored Procedure
            cstmt.setLong(1, borrowCard.getCardId());
            cstmt.setString(2, borrowCard.getBookTitle());
            cstmt.setString(3, borrowCard.getBorrowerName());
            cstmt.setTimestamp(4, borrowCard.getBorrowDate());
            cstmt.setTimestamp(5, borrowCard.getReturnDeadline());
            cstmt.setInt(6, borrowCard.getQuantity());
            cstmt.setString(7, borrowCard.getStatus());

            int rowsAffected = cstmt.executeUpdate();

            // Kiểm tra kết quả cập nhật
            if (rowsAffected > 0) {
                System.out.println("Updated borrow card successfully!");
            } else {
                System.out.println("Borrow card ID not found!");
            }

        } catch (SQLException e) {

            // Hiển thị lỗi nếu cập nhật thất bại
            e.printStackTrace();
        }
    }
    // Xóa phiếu mượn sách
    public void deleteBorrowCard(long cardId) {

        // Gọi Stored Procedure để xóa phiếu mượn
        String sql = "{CALL sp_delete_borrow_card(?)}";

        // Kết nối Database và thực hiện Stored Procedure
        try (Connection conn = ConnectDatabase.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Truyền ID phiếu mượn cần xóa vào Stored Procedure
            cstmt.setLong(1, cardId);

            // Thực thi Stored Procedure
            int rowsAffected = cstmt.executeUpdate();

            // Kiểm tra kết quả xóa
            if (rowsAffected > 0) {
                System.out.println("Deleted borrow card successfully!");
            } else {
                System.out.println("Borrow card ID not found!");
            }

        } catch (SQLException e) {

            // Hiển thị lỗi nếu xóa phiếu mượn thất bại
            e.printStackTrace();
        }
    }
    // Tìm kiếm phiếu mượn theo tên sách
    public void searchBorrowCardsByBookTitle(String bookTitle) {

        // Gọi Stored Procedure để tìm kiếm phiếu mượn theo tên sách
        String sql = "{CALL sp_search_borrow_cards_by_book_title(?)}";

        // Kết nối Database và thực hiện tìm kiếm
        try (Connection conn = ConnectDatabase.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Truyền tên sách vào Stored Procedure
            cstmt.setString(1, bookTitle);

            // Thực thi Stored Procedure
            ResultSet rs = cstmt.executeQuery();

            // Hiển thị tiêu đề bảng
            System.out.println("---------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-20s %-22s %-22s %-10s %-15s%n",
                    "ID", "BOOK TITLE", "BORROWER",
                    "BORROW DATE", "RETURN DEADLINE", "QUANTITY", "STATUS");
            System.out.println("---------------------------------------------------------------------------------------------");

            // Kiểm tra kết quả tìm kiếm
            boolean found = false;

            while (rs.next()) {

                found = true;

                // Tạo đối tượng BorrowCard từ dữ liệu Database
                BorrowCard borrowCard = new BorrowCard(
                        rs.getLong("card_id"),
                        rs.getString("book_title"),
                        rs.getString("borrower_name"),
                        rs.getTimestamp("borrow_date"),
                        rs.getTimestamp("return_deadline"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );

                // Hiển thị thông tin phiếu mượn
                System.out.printf(
                        "%-5d %-20s %-20s %-22s %-22s %-10d %-15s%n",
                        borrowCard.getCardId(),
                        borrowCard.getBookTitle(),
                        borrowCard.getBorrowerName(),
                        borrowCard.getBorrowDate(),
                        borrowCard.getReturnDeadline(),
                        borrowCard.getQuantity(),
                        borrowCard.getStatus()
                );
            }

            // Thông báo nếu không tìm thấy kết quả
            if (!found) {
                System.out.println("No borrow cards found with this book title!");
            }

            rs.close();

        } catch (SQLException e) {

            // Hiển thị lỗi nếu tìm kiếm thất bại
            e.printStackTrace();
        }
    }
}
