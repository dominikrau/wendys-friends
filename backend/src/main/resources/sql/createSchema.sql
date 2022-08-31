CREATE TABLE IF NOT EXISTS owner
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS horse
(
  ownerId     BIGINT,
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(255)  NOT NULL,
  rating      INT           CHECK (rating > 0 AND rating < 6),
  birthDate   DATETIME      NOT NULL,
  description VARCHAR(255),
  breed       VARCHAR(255)  NOT NULL,
  picture     varbinary(max),
  created_at  DATETIME     NOT NULL,
  updated_at  DATETIME     NOT NULL
)
