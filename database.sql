CREATE TABLE user
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE venue
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(50),
    image_url VARCHAR(100),
    address   VARCHAR(250),
    city      VARCHAR(50),
    country   VARCHAR(50)
);

CREATE TABLE event
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(50),
    image_url           VARCHAR(100),
    description         VARCHAR(1000),
    start_at            TIMESTAMP,
    finish_at           TIMESTAMP,
    booking_start_at    TIMESTAMP,
    booking_finish_at   TIMESTAMP,
    total_ticket        INT,
    max_ticket_per_user INT,
    ticket_price        FLOAT,
    venue_id            BIGINT,
    FOREIGN KEY (venue_id) REFERENCES venue (id)
);

CREATE TABLE booking
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT,
    event_id      BIGINT,
    ticket_serial VARCHAR(30) UNIQUE,
    booked_at     TIMESTAMP,
    status        VARCHAR(30),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE INDEX idx_venue_city ON venue (city);
CREATE INDEX idx_booking_user ON booking (user_id);
CREATE INDEX idx_booking_event ON booking (event_id);
CREATE INDEX idx_event_time ON event (start_at, finish_at);
CREATE INDEX idx_event_booking_time ON event (booking_start_at, booking_finish_at);