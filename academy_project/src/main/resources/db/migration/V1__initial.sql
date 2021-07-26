CREATE TABLE IF NOT EXISTS car(
    id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    date_added DATE NOT NULL,
    manufacturer_name VARCHAR(64) NOT NULL,
    model_name VARCHAR(64) NOT NULL,
    production_year SMALLINT NOT NULL,
    serial_number BIGINT UNIQUE NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS checkup(
    id BIGINT NOT NULL,
    date_time TIMESTAMP(0) NOT NULL,
    worker_name VARCHAR(64) NOT NULL,
    price FLOAT NOT NULL,
    car_id BIGINT NOT NULL,
    CONSTRAINT checkup_id PRIMARY KEY (id),
    CONSTRAINT checkup_carid_fk FOREIGN KEY (car_id) REFERENCES CAR(id)
);

CREATE SEQUENCE IF NOT EXISTS car_seq;

CREATE SEQUENCE IF NOT EXISTS checkup_seq;