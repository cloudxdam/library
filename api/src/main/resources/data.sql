-- USERS
INSERT INTO users (id, name, email) VALUES (1, 'Dani', 'dani@email.com');
INSERT INTO users (id, name, email) VALUES (2, 'Ana', 'ana@email.com');

-- BOOKS
INSERT INTO books (id, title, author, isbn, pages) VALUES (1, 'Clean Code', 'Robert C. Martin', '123456789', 464);
INSERT INTO books (id, title, author, isbn, pages) VALUES (2, 'Effective Java', 'Joshua Bloch', '987654321', 416);

-- LOANS
INSERT INTO loans (id, loan_date, return_date, user_id, book_id)
VALUES (1, CURRENT_DATE, NULL, 1, 1);