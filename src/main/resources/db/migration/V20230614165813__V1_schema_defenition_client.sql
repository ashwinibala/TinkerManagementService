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