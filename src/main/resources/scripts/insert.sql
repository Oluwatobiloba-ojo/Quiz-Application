
INSERT INTO user (id, date_of_birth, email, first_name, last_name, password, user_role, is_locked) VALUES
  (100, "2000-10-31", "test@gmail.com", "username", "name", "password", "TEACHER", 0);

INSERT INTO user (id, date_of_birth, email, first_name, last_name, password, user_role, is_locked) VALUES
    (150, "2001-01-20", "user@gmail.com", "name", "lastName", "password", "TEACHER", 1);

INSERT INTO page (id, title, description, fk_user_id, date) VALUES
    (200, "Java", "This is a java quiz", 100, "2021-03-20");

INSERT INTO page (id, title, description, fk_user_id, date) VALUES
    (201, "Java_Docs", "Java Documentation quiz", 100, "2024-04-10");

INSERT INTO page (id, title, description, fk_user_id, date) VALUES
    (203, "Java_Docs", "Java Documentation quiz", 150, "2024-04-10");
