CREATE DATABASE QuanLySinhVien;
USE QuanLySinhVien;


CREATE TABLE users
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255)       NOT NULL
);

CREATE TABLE students
(
    student_id    VARCHAR(20) PRIMARY KEY,
    full_name     VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    gender        ENUM ('Nam', 'Nữ'),
    email         VARCHAR(100) UNIQUE,
    phone         VARCHAR(15),
    address       TEXT
);
CREATE TABLE subjects
(
    subject_id   INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    credit       INT          NOT NULL
);


CREATE TABLE grades
(
    grade_id         INT AUTO_INCREMENT PRIMARY KEY,
    student_id       VARCHAR(20),
    subject_id       INT,
    attendance_score DECIMAL(5, 2),
    midterm_score    DECIMAL(5, 2),
    final_score      DECIMAL(5, 2),
    FOREIGN KEY (student_id) REFERENCES students (student_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects (subject_id) ON DELETE CASCADE
);


insert into users(username, password_hash) value ('admin', 'admin');


INSERT INTO students (student_id, full_name, date_of_birth, gender, email, phone, address)
VALUES ('SV001', 'Nguyen Van A', '2002-05-10', 'Nam', 'a.nguyen@example.com', '0912345678', 'Hanoi, Vietnam'),
       ('SV002', 'Tran Thi B', '2003-08-22', 'Nam', 'b.tran@example.com', '0987654321', 'Ho Chi Minh, Vietnam'),
       ('SV003', 'Le Van C', '2001-12-15', 'Nam', 'c.le@example.com', '0903123456', 'Da Nang, Vietnam'),
       ('SV004', 'Pham Thi D', '2002-03-30', 'Nam', 'd.pham@example.com', '0911122334', 'Can Tho, Vietnam'),
       ('SV005', 'Hoang Van E', '2000-11-25', 'Nam', 'e.hoang@example.com', '0965789012', 'Hai Phong, Vietnam'),
       ('SV006', 'Bui Thi F', '2001-07-14', 'Nam', 'f.bui@example.com', '0976543210', 'Hue, Vietnam'),
       ('SV007', 'Dang Van G', '2002-09-18', 'Nam', 'g.dang@example.com', '0954321098', 'Vung Tau, Vietnam'),
       ('SV008', 'Do Thi H', '2003-04-05', 'Nam', 'h.do@example.com', '0932109876', 'Nha Trang, Vietnam'),
       ('SV009', 'Ngo Van I', '2001-06-28', 'Nam', 'i.ngo@example.com', '0923456789', 'Da Lat, Vietnam'),
       ('SV010', 'Ly Thi K', '2000-10-01', 'Nam', 'k.ly@example.com', '0916782345', 'Quang Ninh, Vietnam');

-- Chèn dữ liệu vào bảng subjects
INSERT INTO subjects (subject_name, credit)
VALUES ('Mathematics', 3),
       ('Physics', 4),
       ('Chemistry', 3),
       ('Computer Science', 4),
       ('Biology', 3),
       ('History', 2),
       ('Geography', 2),
       ('English', 3),
       ('Economics', 3),
       ('Programming', 4);

-- Chèn dữ liệu vào bảng grades
INSERT INTO grades (student_id, subject_id, attendance_score, midterm_score, final_score)
VALUES ('SV001', 1, 9.0, 8.5, 7.5),
       ('SV002', 2, 8.0, 7.0, 6.5),
       ('SV003', 3, 7.5, 8.0, 9.0),
       ('SV004', 4, 9.5, 9.0, 8.5),
       ('SV005', 5, 6.0, 5.5, 7.0),
       ('SV006', 6, 7.0, 6.5, 6.0),
       ('SV007', 7, 8.5, 8.0, 7.5),
       ('SV008', 8, 9.0, 8.5, 8.0),
       ('SV009', 9, 7.5, 7.0, 6.5),
       ('SV010', 10, 9.5, 9.0, 9.0);


select * from students;

update students set gender = 'Nam';

select g.*, s.full_name, sj.subject_name from grades g
                                                  join subjects sj on sj.subject_id = g.subject_id
                                                  join students s on g.student_id = s.student_id where s.student_id = ?


SELECT s.subject_id, s.subject_name
FROM subjects s
         LEFT JOIN grades g ON s.subject_id = g.subject_id AND g.student_id = 'sv001'
WHERE g.subject_id IS NULL;


select * from subjects;

select * from grades where student_id = 'sv001';