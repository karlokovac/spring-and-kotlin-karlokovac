CREATE TABLE IF NOT EXISTS cars(
    id BIGSERIAL PRIMARY KEY,
    ownerId INT NOT NULL,
    dateAdded DATE NOT NULL,
    manufacturerName TEXT NOT NULL,
    modelName TEXT NOT NULL,
    productionYear SMALLINT NOT NULL,
    serialNumber INT NOT NULL
);

CREATE TABLE IF NOT EXISTS carCheckUps(
    id BIGSERIAL PRIMARY KEY,
    dateTime TIMESTAMP(0) NOT NULL,
    workerName TEXT NOT NULL,
    price FLOAT NOT NULL,
    carId BIGINT REFERENCES cars(id)
);