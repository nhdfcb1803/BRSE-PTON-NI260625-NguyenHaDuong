CREATE DATABASE db_library_management;
USE db_library_management;

CREATE TABLE borrow_cards (
    card_id SERIAL PRIMARY KEY,
    book_title VARCHAR(150) NOT NULL,
    borrower_name VARCHAR(100) NOT NULL,
    borrow_date TIMESTAMP NOT NULL,
    return_deadline TIMESTAMP NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(30) NOT NULL
);

SHOW TABLES;

-- Lấy danh sách tất cả phiếu mượn
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_get_all_borrow_cards$$
CREATE PROCEDURE sp_get_all_borrow_cards()
BEGIN
    SELECT *
    FROM borrow_cards;
END$$

-- Thêm phiếu mượn mới
DROP PROCEDURE IF EXISTS sp_add_borrow_card$$

CREATE PROCEDURE sp_add_borrow_card(
    IN p_book_title VARCHAR(150),
    IN p_borrower_name VARCHAR(100),
    IN p_borrow_date TIMESTAMP,
    IN p_return_deadline TIMESTAMP,
    IN p_quantity INT,
    IN p_status VARCHAR(30)
)
BEGIN
    INSERT INTO borrow_cards (
        book_title,
        borrower_name,
        borrow_date,
        return_deadline,
        quantity,
        status
    )
    VALUES (
               p_book_title,
               p_borrower_name,
               p_borrow_date,
               p_return_deadline,
               p_quantity,
               p_status
           );
END$$

-- Tìm phiếu mượn theo tên độc giả
DROP PROCEDURE IF EXISTS sp_get_borrow_cards_by_borrower_name$$

CREATE PROCEDURE sp_get_borrow_cards_by_borrower_name(
    IN p_borrower_name VARCHAR(100)
)
BEGIN
    SELECT *
    FROM borrow_cards
    WHERE borrower_name = p_borrower_name;
END$$

-- Cập nhật phiếu mượn theo card_id
DROP PROCEDURE IF EXISTS sp_update_borrow_card$$

CREATE PROCEDURE sp_update_borrow_card(
    IN p_card_id BIGINT UNSIGNED,
    IN p_book_title VARCHAR(150),
    IN p_borrower_name VARCHAR(100),
    IN p_borrow_date TIMESTAMP,
    IN p_return_deadline TIMESTAMP,
    IN p_quantity INT,
    IN p_status VARCHAR(30)
)
BEGIN
    UPDATE borrow_cards
    SET
        book_title = p_book_title,
        borrower_name = p_borrower_name,
        borrow_date = p_borrow_date,
        return_deadline = p_return_deadline,
        quantity = p_quantity,
        status = p_status
    WHERE card_id = p_card_id;
END$$

-- Xoá phiếu mượn theo card_id
DROP PROCEDURE IF EXISTS sp_delete_borrow_card$$

CREATE PROCEDURE sp_delete_borrow_card(
    IN p_card_id BIGINT UNSIGNED
)
BEGIN
    DELETE FROM borrow_cards
    WHERE card_id = p_card_id;
END$$

-- Tìm kiếm phiếu mượn theo tên sách
DROP PROCEDURE IF EXISTS sp_search_borrow_cards_by_book_title$$

CREATE PROCEDURE sp_search_borrow_cards_by_book_title(
    IN p_book_title VARCHAR(150)
)
BEGIN
    SELECT *
    FROM borrow_cards
    WHERE LOWER(book_title) LIKE LOWER(CONCAT('%', p_book_title, '%'));
END$$

DELIMITER ;

SHOW PROCEDURE STATUS
    WHERE Db = 'db_library_management';

CALL sp_add_borrow_card(
        'Harry Potter',
        'JK Rowling',
        '2026-11-19 10:00:00',
        '2026-11-26 10:00:00',
        2,
        'Borrowing'
     );

CALL sp_search_borrow_cards_by_book_title('Harry');
CALL sp_delete_borrow_card(1);
CALL sp_delete_borrow_card(2);
SELECT * FROM borrow_cards;

CALL sp_add_borrow_card(
        'Doraemon',
        'Nobita',
        '2026-11-19 10:00:00',
        '2026-11-26 10:00:00',
        2,
        'Đang mượn'
     );

CALL sp_get_all_borrow_cards();