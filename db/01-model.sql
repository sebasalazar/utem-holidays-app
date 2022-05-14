BEGIN TRANSACTION;

DROP TABLE IF EXISTS process_days CASCADE;
CREATE TABLE process_days (
    pk bigserial NOT NULL,
    today date NOT NULL DEFAULT NOW(),
    description varchar(255) NOT NULL,
    holiday boolean NOT NULL DEFAULT '0',
    UNIQUE (today),
    PRIMARY KEY (pk)
);

COMMIT;
