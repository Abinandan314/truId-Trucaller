INSERT INTO global_contact (id, phone_number, name, spam_status, created_at, updated_at)
VALUES
    (1, '1234567890', 'John Doe', 'N', NOW(), NOW()),
    (2, '0987654321', 'Jane Smith', 'N', NOW(), NOW()),
    (3, '5555555555', 'Alice Johnson', 'N', NOW(), NOW()),
    (4, '1234567890', 'John Dhoe', 'N', NOW(), NOW()), -- Different name, same phone number
    (5, '2223334444', 'Bob Brown', 'N', NOW(), NOW()),
    (6, '4445556666', 'Charlie Davis', 'N', NOW(), NOW()),
    (7, '1234567890', 'Jonathan Doe', 'N', NOW(), NOW()), -- Different name, same phone number
    (8, '5555555555', 'Alicia Johns', 'N', NOW(), NOW()), -- Similar name, same phone number
    (9, '7778889999', 'Eve Thompson', 'N', NOW(), NOW()),
    (10, '0987654321', 'Janet Smith', 'N', NOW(), NOW()); -- Different name, same phone number
