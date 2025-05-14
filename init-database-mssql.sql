CREATE DATABASE QuanLyThuVienF;
GO
USE QuanLyThuVien;
GO
CREATE TABLE Books (
    BookID VARCHAR(10) PRIMARY KEY,
    Title NVARCHAR(255) NOT NULL,
    Author NVARCHAR(255),
    Publisher NVARCHAR(255),
    PublicationYear INT,
    ISBN NVARCHAR(20),
    CopiesAvailable INT DEFAULT 0
);
CREATE TABLE Members (
    MemberID VARCHAR(10) PRIMARY KEY,
    FirstName NVARCHAR(100),
    LastName NVARCHAR(100),
    DateOfBirth DATE,
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    Address NVARCHAR(255),
    MembershipDate DATE DEFAULT GETDATE()
);
CREATE TABLE Loans (
    LoanID VARCHAR(10) PRIMARY KEY,
    BookID VARCHAR(10) NOT NULL,
    MemberID VARCHAR(10) NOT NULL,
    LoanDate DATE DEFAULT GETDATE(),
    DueDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (BookID) REFERENCES Books(BookID),
    FOREIGN KEY (MemberID) REFERENCES Members(MemberID)
);
CREATE TABLE Staff (
    StaffID VARCHAR(10) PRIMARY KEY,
    FirstName NVARCHAR(100),
    LastName NVARCHAR(100),
    Position NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20)
);
CREATE TABLE Fines (
    FineID VARCHAR(10) PRIMARY KEY,
    LoanID VARCHAR(10) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Paid BIT DEFAULT 0,
    FOREIGN KEY (LoanID) REFERENCES Loans(LoanID)
);





CREATE TRIGGER trg_IncreaseCopiesOnReturn
ON Loans
AFTER UPDATE
AS
BEGIN
    -- Chỉ tăng CopiesAvailable nếu ReturnDate vừa được cập nhật từ NULL sang NOT NULL
    UPDATE Books
    SET CopiesAvailable = CopiesAvailable + 1
    FROM Books b
    INNER JOIN inserted i ON b.BookID = i.BookID
    INNER JOIN deleted d ON i.LoanID = d.LoanID
    WHERE d.ReturnDate IS NULL AND i.ReturnDate IS NOT NULL;
END
GO

CREATE TRIGGER trg_DecreaseCopiesOnLoan
ON Loans
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Cập nhật giảm CopiesAvailable cho mỗi cuốn sách vừa mượn
    UPDATE b
    SET b.CopiesAvailable = 
        CASE 
            WHEN b.CopiesAvailable > 0 THEN b.CopiesAvailable - 1 
            ELSE 0 
        END
    FROM Books b
    INNER JOIN inserted i
        ON b.BookID = i.BookID;
END
GO



INSERT INTO Books (BookID, Title, Author, Publisher, PublicationYear, ISBN, CopiesAvailable)
VALUES
('B001', N'Dế Mèn Phiêu Lưu Ký',             N'Tô Hoài',          N'Kim Đồng', 1941, '9786042021203', 5),
('B002', N'Chuyện Xưa Tích Cũ',               N'Trần Trọng Kim',   N'Giáo Dục', 1950, '9786041234567', 3),
('B003', N'Harry Potter và Hòn Đá Phù Thủy',  N'J.K. Rowling',     N'Bloomsbury', 1997, '9780747532699', 7),
('B004', N'Chí Phèo',                         N'Nhã Nam',          N'Xuất bản VN', 1941, '9786045870014', 4),
('B005', N'Tuổi thơ dữ dội',                  N'Doãn Quốc Sỹ',     N'Kim Đồng', 1988, '9786049912345', 6),
('B006', N'Người lái đò sông Đà',             N'Tạ Duy Anh',        N'Văn Học', 1978, '9786043021020', 2),
('B007', N'The Great Gatsby',                 N'F. Scott Fitzgerald', N'Charles Scribner''s Sons', 1925, '9780743273565', 8),
('B008', N'1984',                             N'George Orwell',    N'Harcourt', 1949, '9780451524935', 5),
('B009', N'The Hobbit',                       N'J.R.R. Tolkien',   N'Allen & Unwin', 1937, '9780007458424', 3),
('B010', N'Sapiens Lược sử loài người',      N'Yuval Noah Harari', N'Harper', 2011, '9780062316110', 9);
GO



INSERT INTO Members (MemberID, FirstName, LastName, DateOfBirth, Email, Phone, Address, MembershipDate)
VALUES
('M001', N'Nguyễn', N'Văn A', '2000-05-12', 'vana@example.com',  '0123456789', N'123 Lê Lợi, Hà Nội',     '2024-01-10'),
('M002', N'Trần',   N'Thị B', '1998-10-03', 'thib@example.com',  '0987654321', N'456 Hùng Vương, TP.HCM', '2024-03-15'),
('M003', N'Lê',     N'Văn C', '1995-02-20', 'vanc@example.com',  '0932123456', N'789 Nguyễn Trãi, Đà Nẵng','2024-05-01'),
('M004', N'Phạm',   N'Thị D', '2001-07-08', 'phamd@example.com', '0911122334', N'12 Trần Phú, Hải Phòng',   '2024-02-20'),
('M005', N'Hoàng',  N'Văn E', '1992-11-30', 'hoange@example.com','0909988776', N'34 Phan Đình Phùng, Huế',  '2024-04-05'),
('M006', N'Đinh',   N'Thị F', '1988-09-15', 'thif@example.com',  '0913344556', N'56 Lý Thái Tổ, Bắc Ninh',  '2024-01-25'),
('M007', N'Bùi',    N'Văn G', '1990-12-12', 'vang@example.com',  '0987212345', N'78 Trần Hưng Đạo, Cần Thơ','2024-03-30'),
('M008', N'Ngô',    N'Thị H', '1999-06-22', 'ngoh@example.com',  '0915566778', N'90 Điện Biên Phủ, Đà Lạt', '2024-02-10'),
('M009', N'Hồ',     N'Văn I', '1985-04-05', 'vani@example.com',  '0977123456', N'11 Nguyễn Huệ, Vũng Tàu',  '2024-05-03'),
('M010', N'Vũ',     N'Thị K', '2002-01-17', 'thik@example.com',  '0934455667', N'22 Pasteur, Nha Trang',    '2024-04-18');
GO


INSERT INTO Staff (StaffID, FirstName, LastName, Position, Email, Phone)
VALUES
('S001', N'Phạm',  N'Thị D',   N'Thủ thư',     'thuthu@example.com',   '0912345678'),
('S002', N'Hoàng', N'Văn E',   N'Quản lý',     'quanly@example.com',   '0923456789'),
('S003', N'Nguyễn',N'Thị L',   N'Thủ kho',     'thukho@example.com',   '0933123456'),
('S004', N'Lê',    N'Văn M',   N'Tư vấn viên', 'tuvany@example.com',   '0944234567'),
('S005', N'Trần',  N'Văn N',   N'Bảo trì',     'baotri@example.com',   '0955345678'),
('S006', N'Đặng',  N'Thị O',   N'Kế toán',     'ketoan@example.com',   '0966456789'),
('S007', N'Bùi',   N'Văn P',   N'Thư ký',      'thuky@example.com',    '0977567890'),
('S008', N'Vũ',    N'Thị Q',   N'IT Support',  'itsupport@example.com','0988678901'),
('S009', N'Phan',  N'Văn R',   N'Giám sát',    'giamsat@example.com',  '0919789012'),
('S010', N'Hà',    N'Thị S',   N'Trợ lý',      'troly@example.com',    '0920890123');
GO


INSERT INTO Loans (LoanID, BookID, MemberID, LoanDate, DueDate, ReturnDate)
VALUES
('L25051401', 'B001', 'M001', '2025-05-14', '2025-05-21', NULL),
('L25051402', 'B002', 'M002', '2025-05-14', '2025-05-21', '2025-05-20'),
('L25051403', 'B003', 'M003', '2025-05-14', '2025-05-21', '2025-05-21'),
('L25051404', 'B004', 'M004', '2025-05-14', '2025-05-21', NULL),
('L25051405', 'B005', 'M005', '2025-05-14', '2025-05-21', '2025-05-19'),
('L25051406', 'B006', 'M006', '2025-05-14', '2025-05-21', NULL),
('L25051407', 'B007', 'M007', '2025-05-14', '2025-05-21', '2025-05-18'),
('L25051408', 'B008', 'M008', '2025-05-14', '2025-05-21', NULL),
('L25051409', 'B009', 'M009', '2025-05-14', '2025-05-21', '2025-05-20'),
('L25051410', 'B010', 'M010', '2025-05-14', '2025-05-21', NULL),
  -- mượn ngày 2025-05-10, quá hạn 4 ngày (tính đến 14/05/2025)
('L25051001', 'B001', 'M001', '2025-05-10', '2025-05-10', NULL),
  -- mượn ngày 2025-05-12, quá hạn 2 ngày
('L25051201', 'B003', 'M004', '2025-05-12', '2025-05-12', NULL),
  -- mượn ngày 2025-05-01, quá hạn 13 ngày
('L25050101', 'B005', 'M007', '2025-05-01', '2025-05-01', NULL);
  GO


INSERT INTO Fines (FineID, LoanID, Amount, Paid)
VALUES
    ('F001', 'L25051402', 10000, 1),
    ('F002', 'L25051403',  5000, 1),
    ('F003', 'L25051405', 12500, 0),
    ('F004', 'L25051407',  7000, 1),
    ('F005', 'L25051409',  3000, 1),
    ('F006', 'L25051401', 15000, 0),
    ('F007', 'L25051404',  8000, 0),
    ('F008', 'L25051406',  6500, 1),
    ('F009', 'L25051408', 20000, 0),
    ('F010', 'L25051410',  4000, 1);
GO