BEGIN TRANSACTION;

DROP TABLE IF EXISTS holidays CASCADE;
CREATE TABLE holidays (
	pk bigserial NOT NULL,
	holiday date NOT NULL DEFAULT NOW(),
	description varchar(255),
	UNIQUE (holiday),
	PRIMARY KEY (pk)
);

COMMIT;
