CREATE TABLE user
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name  VARCHAR(50)  NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
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
CREATE INDEX idx_venue_country ON venue (country);
CREATE INDEX idx_booking_user ON booking (user_id);
CREATE INDEX idx_booking_event_status ON booking (event_id, status);
CREATE INDEX idx_event_name ON event (name);
CREATE INDEX idx_event_venue ON event (venue_id);
CREATE INDEX idx_event_time ON event (start_at, finish_at);
CREATE INDEX idx_event_booking_time ON event (booking_start_at, booking_finish_at);

-- sample data
INSERT INTO `venue` (`id`, `name`, `image_url`, `address`, `city`, `country`) VALUES (1, 'gbk', 'https://media.istockphoto.com/id/1434062157/id/foto/lapangan-stadion-sepak-bola-rendering-3d-latar-belakang-sepak-bola.jpg?s=612x612&w=0&k=20&c=i3CMAdmpxy5mMppJ_hkaLnhHoo9HpCS_YeVfKHmuZ6g=', 'stadium gbk', 'jakarta', 'indonesia');
INSERT INTO `venue` (`id`, `name`, `image_url`, `address`, `city`, `country`) VALUES (2, 'khalifa', 'https://assets.goal.com/images/v3/blt4a73783848a321c6/d4e530341d22680854e38d7014827d1c0b4d5092.jpg?auto=webp&format=pjpg&width=3840&quality=60', 'stadium khalifa', 'khalifa', 'khalifa');
INSERT INTO `event` (`id`, `name`, `image_url`, `description`, `start_at`, `finish_at`, `booking_start_at`, `booking_finish_at`, `total_ticket`, `max_ticket_per_user`, `ticket_price`, `venue_id`) VALUES (1, 'march celebration', 'https://assets.loket.com/2024/06/tips-buat-event.jpeg', 'celebrating the wonderful march', '2026-03-02 00:00:00', '2026-03-02 03:00:00', '2026-03-01 00:00:00', '2026-03-01 10:00:00', 100, 2, 50000, 1);
INSERT INTO `event` (`id`, `name`, `image_url`, `description`, `start_at`, `finish_at`, `booking_start_at`, `booking_finish_at`, `total_ticket`, `max_ticket_per_user`, `ticket_price`, `venue_id`) VALUES (2, 'april mop concert', 'https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhcQdysvdzlKg2AN8ygE6p0zg37eW9lDE9SEh8XkG0DxxhCXrVk1aGTEbkDD7Tl_zRzQSvj-7X_I6C_0uhXFqpKYzs29EWaUz5lS1OG7l9RYt0caokqlI1v8q5FpRSH_UG9pY7sJMBs8x4/s1600/event.png', 'april mop concert, only in 2026', '2026-04-02 00:00:00', '2026-04-02 03:00:00', '2026-04-01 00:00:00', '2026-04-01 10:00:00', 50, 3, 500000, 2);
INSERT INTO `event` (`id`, `name`, `image_url`, `description`, `start_at`, `finish_at`, `booking_start_at`, `booking_finish_at`, `total_ticket`, `max_ticket_per_user`, `ticket_price`, `venue_id`) VALUES (3, 'wonderful saturday', 'https://voca-land.sgp1.cdn.digitaloceanspaces.com/-1/1769868458106/1719a5ec.jpg', 'wonderful saturday of 2026', '2026-04-03 00:00:00', '2026-04-03 03:00:00', '2026-04-02 00:00:00', '2026-04-02 03:00:00', 30, 1, 100000, 2);
INSERT INTO `event` (`id`, `name`, `image_url`, `description`, `start_at`, `finish_at`, `booking_start_at`, `booking_finish_at`, `total_ticket`, `max_ticket_per_user`, `ticket_price`, `venue_id`) VALUES (4, 'february super party', 'https://voca-land.sgp1.cdn.digitaloceanspaces.com/-1/1769868458106/1719a5ec.jpg', 'super party on the wonderful february', '2026-02-25 00:00:00', '2026-02-25 04:00:00', '2026-02-19 00:00:00', '2026-02-22 00:00:00', 20, 1, 120000, 1);
INSERT INTO `user` (`id`, `name`, `email`) VALUES (1, 'ridwan', 'ridwanadhip@gmail.com');
INSERT INTO `user` (`id`, `name`, `email`) VALUES (2, 'adhi', 'adhi@gmail.com');
