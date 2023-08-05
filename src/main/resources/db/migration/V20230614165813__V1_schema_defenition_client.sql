CREATE TABLE IF NOT EXISTS category (
    id              BIGSERIAL           NOT NULL,
    name            VARCHAR(255)        NOT NULL,
    description     VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS client (
    id                  BIGSERIAL           NOT NULL,
    username            VARCHAR(255)        NOT NULL,
    password            VARCHAR(255)        NOT NULL,
    lastname            VARCHAR(255)        NOT NULL,
    firstname           VARCHAR(255)        NOT NULL,
    phone               VARCHAR(255)        NOT NULL,
    email               VARCHAR(255)        NOT NULL,
    address             VARCHAR(255)        NOT NULL,
    category_id          BIGINT              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_category_id FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS status (
    id                  BIGSERIAL           NOT NULL,
    name                VARCHAR(255)        NOT NULL,
    description         VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS timeslot (
    id                  BIGSERIAL           NOT NULL,
    starttime           VARCHAR(255)        NOT NULL,
    endtime             VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clientavailability (
    id                  BIGSERIAL           NOT NULL,
    date                VARCHAR(255)        NOT NULL,
    client_id           BIGINT              NOT NULL,
    timeslot_id         BIGINT              NOT NULL,
    status_id           BIGINT              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_clientavailability_client_id FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT fk_clientavailability_timeslot_id FOREIGN KEY (timeslot_id) REFERENCES timeslot (id),
    CONSTRAINT fk_clientavailability_status_id FOREIGN KEY (status_id) REFERENCES status (id)
);

CREATE TABLE IF NOT EXISTS customer (
    id                  BIGSERIAL           NOT NULL,
    lastname            VARCHAR(255)        NOT NULL,
    firstname           VARCHAR(255)        NOT NULL,
    phone               VARCHAR(255)        NOT NULL,
    email               VARCHAR(255)        NOT NULL,
    address             VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS appointment
(
    id                  BIGSERIAL           NOT NULL,
    date                VARCHAR(255)        NOT NULL,
    client_id           BIGINT              NOT NULL,
    timeslot_id         BIGINT              NOT NULL,
    status_id           BIGINT              NOT NULL,
    customer_id         BIGINT              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_appointment_client_id FOREIGN KEY (client_id) REFERENCES client (id),
    CONSTRAINT fk_appointment_timeslot_id FOREIGN KEY (timeslot_id) REFERENCES timeslot (id),
    CONSTRAINT fk_appointment_status_id FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT fk_appointment_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);
