-- 1. Book Service DB
USE library_book_db;
CREATE TABLE IF NOT EXISTS books (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) UNIQUE,
    available_copies INT NOT NULL,
    total_copies INT NOT NULL
);

INSERT INTO books (id, title, author, isbn, available_copies, total_copies) VALUES
('b101', 'Clean Code', 'Robert C. Martin', '978-0132350884', 5, 5),
('b102', 'Effective Java', 'Joshua Bloch', '978-0134685991', 4, 4),
('b103', 'Designing Data-Intensive Applications', 'Martin Kleppmann', '978-1449373320', 3, 3),
('b104', 'Spring Boot in Action', 'Craig Walls', '978-1617292545', 6, 6),
('b105', 'Microservices Patterns', 'Chris Richardson', '978-1617294549', 2, 2)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- 2. Member Service DB
USE library_member_db;
CREATE TABLE IF NOT EXISTS members (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    membership_type VARCHAR(255),
    active BOOLEAN NOT NULL
);

INSERT INTO members (id, name, email, membership_type, active) VALUES
('m201', 'Alice Johnson', 'alice.johnson@example.com', 'PREMIUM', true),
('m202', 'Bob Smith', 'bob.smith@example.com', 'STANDARD', true),
('m203', 'Charlie Brown', 'charlie.brown@example.com', 'STANDARD', true),
('m204', 'Diana Prince', 'diana.prince@example.com', 'PREMIUM', true),
('m205', 'Ethan Hunt', 'ethan.hunt@example.com', 'STANDARD', false)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 3. Loan Service DB
USE library_loan_db;
CREATE TABLE IF NOT EXISTS loans (
    loan_id VARCHAR(255) NOT NULL PRIMARY KEY,
    book_id VARCHAR(255) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    issue_date DATE,
    due_date DATE,
    return_date DATE,
    status VARCHAR(255),
    fine_amount DOUBLE NOT NULL
);

INSERT INTO loans (loan_id, book_id, member_id, issue_date, due_date, return_date, status, fine_amount) VALUES
('l301', 'b101', 'm201', '2026-09-10', '2026-09-24', '2026-09-22', 'RETURNED', 0.0),
('l302', 'b103', 'm202', '2026-09-15', '2026-09-29', NULL, 'ISSUED', 0.0),
('l303', 'b102', 'm204', '2026-09-01', '2026-09-15', '2026-09-20', 'RETURNED', 10.0),
('l304', 'b105', 'm203', '2026-09-25', '2026-10-09', NULL, 'ISSUED', 0.0)
ON DUPLICATE KEY UPDATE status=VALUES(status);

-- 4. Notification Service DB
USE library_notification_db;
CREATE TABLE IF NOT EXISTS notifications (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    recipient_email VARCHAR(255) NOT NULL,
    subject VARCHAR(255),
    message VARCHAR(2000),
    status VARCHAR(255),
    timestamp DATETIME(6)
);

INSERT INTO notifications (id, recipient_email, subject, message, status, timestamp) VALUES
('n401', 'alice.johnson@example.com', 'Book Returned Successfully', 'You returned Clean Code on 2026-09-22. Thank you!', 'SENT', NOW(6)),
('n402', 'bob.smith@example.com', 'Loan Due Reminder', 'Designing Data-Intensive Applications is due on 2026-09-29.', 'SENT', NOW(6)),
('n403', 'diana.prince@example.com', 'Fine Notice', 'A late return fee of $10.00 was charged for Effective Java.', 'SENT', NOW(6)),
('n404', 'charlie.brown@example.com', 'Welcome to Library!', 'Your library card registration has been approved.', 'SENT', NOW(6))
ON DUPLICATE KEY UPDATE status=VALUES(status);
