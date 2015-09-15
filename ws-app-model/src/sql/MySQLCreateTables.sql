-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------


-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));
-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE Reply;
DROP TABLE Event;


CREATE TABLE Event ( eventId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) COLLATE latin1_bin NOT NULL,
    description VARCHAR(1024) COLLATE latin1_bin NOT NULL,
    dateInit TIMESTAMP DEFAULT 0 NOT NULL,
    dateFin TIMESTAMP DEFAULT 0 NOT NULL, 
    aforo SMALLINT NOT NULL,
    attendees BIGINT DEFAULT 0 NOT NULL,
    CONSTRAINT EventPK PRIMARY KEY(eventId)) ENGINE = InnoDB;
	

CREATE TABLE Reply ( responseId BIGINT NOT NULL AUTO_INCREMENT,
	response BIT,
	description VARCHAR(1024) COLLATE latin1_bin,
	dateResponse TIMESTAMP DEFAULT 0 NOT NULL,
	userId VARCHAR(1024) NOT NULL,
	eventId BIGINT NOT NULL,
	CONSTRAINT ResponsePK PRIMARY KEY(responseId),
	CONSTRAINT eventFK FOREIGN KEY(eventId) REFERENCES Event(eventId) ) ENGINE = InnoDB;
	


