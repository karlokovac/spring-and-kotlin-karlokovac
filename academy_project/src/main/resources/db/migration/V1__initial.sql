CREATE TABLE IF NOT EXISTS car_details(
    id BIGINT NOT NULL,
    manufacturer_name VARCHAR(64) NOT NULL,
    model_name VARCHAR(64) NOT NULL,
    is_common BOOL NOT NULL,
    CONSTRAINT car_details_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS car(
    id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    date_added DATE NOT NULL,
    car_details_id BIGINT NOT NULL,
    production_year SMALLINT NOT NULL,
    serial_number BIGINT UNIQUE NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id),
    CONSTRAINT car_details_fk FOREIGN KEY (car_details_id) REFERENCES car_details(id)
);

CREATE TABLE IF NOT EXISTS checkup(
    id BIGINT NOT NULL,
    date_time TIMESTAMP(0) NOT NULL,
    worker_name VARCHAR(64) NOT NULL,
    price FLOAT NOT NULL,
    car_id BIGINT NOT NULL,
    CONSTRAINT checkup_id PRIMARY KEY (id),
    CONSTRAINT checkup_carid_fk FOREIGN KEY (car_id) REFERENCES CAR(id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS car_seq;

CREATE SEQUENCE IF NOT EXISTS checkup_seq;

CREATE SEQUENCE IF NOT EXISTS car_details_seq;

INSERT INTO car_details (id,manufacturer_name,model_name,is_common) VALUES (0,'Audi','R8',1::BOOL);