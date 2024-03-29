# --- !Ups
CREATE TABLE account(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  is_admin BOOLEAN DEFAULT false,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL,
  deleted_at DATETIME DEFAULT NULL,
  PRIMARY KEY (ID)
) DEFAULT CHARSET=utf8;

INSERT INTO ACCOUNT (user_name, password, is_admin) VALUES ('admin', 'passwd', true);

# --- !Downs
DROP TABLE ACCOUNT;