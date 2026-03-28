-- Users
INSERT INTO users (name, email) VALUES 
('Dani', 'dani@email.com'),
('Ana', 'ana@email.com');

-- Books
INSERT INTO books (title, author, isbn, pages) VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 464),
('Effective Java', 'Joshua Bloch', '9780134685991', 416),
('The Pragmatic Programmer', 'Andrew Hunt and David Thomas', '9780201616224', 352),
('Refactoring', 'Martin Fowler', '9780201485677', 448);

-- Loans
INSERT INTO loans (loan_date, return_date, user_id, book_id) VALUES
('2026-03-01', NULL, 1, 1),   -- Dani tiene Clean Code (activo)
('2026-03-05', '2026-03-10', 2, 2), -- Ana devolvió Effective Java
('2026-03-15', NULL, 1, 3);   -- Dani tiene otro préstamo activo