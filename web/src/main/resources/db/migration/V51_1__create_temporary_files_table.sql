START TRANSACTION ;

CREATE TABLE temporary_files(
    id VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
    CONSTRAINT pk_temporary_files PRIMARY KEY (id)
);
CREATE INDEX idx_temporary_files_upload_date ON temporary_files USING BTREE (upload_date);

COMMIT ;