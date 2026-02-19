CREATE TABLE user
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name  VARCHAR(50)  NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE venue
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name      VARCHAR(50)  NOT NULL,
    image_url VARCHAR(250) NOT NULL,
    address   VARCHAR(250) NOT NULL,
    city      VARCHAR(50)  NOT NULL,
    country   VARCHAR(50)  NOT NULL
);

CREATE TABLE event
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name                VARCHAR(50)   NOT NULL,
    image_url           VARCHAR(250)  NOT NULL,
    description         VARCHAR(1000) NOT NULL,
    start_at            TIMESTAMP     NOT NULL,
    finish_at           TIMESTAMP     NOT NULL,
    booking_start_at    TIMESTAMP     NOT NULL,
    booking_finish_at   TIMESTAMP     NOT NULL,
    total_ticket        INT           NOT NULL,
    max_ticket_per_user INT           NOT NULL,
    ticket_price        FLOAT         NOT NULL,
    venue_id            BIGINT        NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES venue (id)
);

CREATE TABLE booking
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id       BIGINT             NOT NULL,
    event_id      BIGINT             NOT NULL,
    ticket_serial VARCHAR(30) UNIQUE NOT NULL,
    booked_at     TIMESTAMP          NOT NULL,
    status        VARCHAR(30)        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE INDEX idx_venue_name ON venue (name);
CREATE INDEX idx_venue_city ON venue (city);
CREATE INDEX idx_booking_user ON booking (user_id);
CREATE INDEX idx_booking_event ON booking (event_id);
CREATE INDEX idx_event_name ON event (name);
CREATE INDEX idx_event_time ON event (start_at, finish_at);
CREATE INDEX idx_event_booking_time ON event (booking_start_at, booking_finish_at);