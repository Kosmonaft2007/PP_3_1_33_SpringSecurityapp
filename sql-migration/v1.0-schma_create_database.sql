
-- Table: users
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age       INT(255)     NOT NULL
)
    ENGINE = InnoDB;

-- Table: roles
DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

# -- Table for mapping user and roles: user_roles
DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,

    UNIQUE (user_id, role_id)
)
    ENGINE = InnoDB;




# Insert data

INSERT INTO users
VALUES (1, 'proselyte', '$2a$12$GWNj/lij3jXIqgstV6eq6e0vB/gLkk7fBjSVt8XcvwRw4cjCtaOOi', 'demo', 1);

INSERT INTO roles
VALUES (1, 'ROLE_USER');
INSERT INTO roles
VALUES (2, 'ROLE_ADMIN');

INSERT INTO users_roles
VALUES (1, 2);