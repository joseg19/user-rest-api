DROP TABLE IF EXISTS PHONE;

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
 -- id VARCHAR(50)  PRIMARY KEY,
  id UUID NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  token VARCHAR(300),
  created TIMESTAMP NOT NULL,
  modified TIMESTAMP,
  last_login TIMESTAMP NOT NULL,
  is_active BIT NOT NULL
);

CREATE TABLE PHONE (
  id BIGINT AUTO_INCREMENT,
  number VARCHAR(20) NOT NULL,
  citycode VARCHAR(2) NOT NULL,
  countrycode VARCHAR(2) NOT NULL,
  user_id UUID NOT NULL,
  foreign key (user_id) references USER(id)
);


--INSERT INTO CLIENT (id, email) VALUES
--  ('Aliko', 'aliko_peace@gmail.com'),
 -- ('Bill', 'billgates@microsoft.com'),
  --('Folrunsho', 'folrunshoure@facebook.com');


INSERT INTO USER (id, name, email, password, token, created, modified, last_login, is_active) VALUES
 ('f215644f-ef09-4271-842e-c3b7c12ed471','admin', 'admin@admin.com', '$2a$10$EU4L8lkdFnlGt1FR5.5lQOxB2CKiKfDeRV3oDMMkIA16tWVUymNNG', NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, TRUE );