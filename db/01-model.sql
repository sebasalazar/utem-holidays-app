BEGIN TRANSACTION;

DROP TABLE IF EXISTS days CASCADE;
CREATE TABLE days (
    pk bigserial NOT NULL,
    working_date date NOT NULL DEFAULT NOW(),
    holiday boolean NOT NULL DEFAULT '0',
    created timestamptz NOT NULL DEFAULT NOW(),
    updated timestamptz NOT NULL DEFAULT NOW(),
    UNIQUE (working_date),
    PRIMARY KEY (pk)
);

COMMIT;
