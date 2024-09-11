CREATE TABLE tb_log_street_type
(
    rev                      INTEGER NOT NULL,
    revtype                  SMALLINT,
    internal_id              BIGINT  NOT NULL,
    street_type_name         VARCHAR(50),
    street_type_abbreviation VARCHAR(10),
    is_active                BOOLEAN DEFAULT TRUE,
    created_at               TIMESTAMP WITHOUT TIME ZONE,
    updated_at               TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_street_type PRIMARY KEY (rev, internal_id)
);

ALTER TABLE tb_street_type
    ADD created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now();

ALTER TABLE tb_street_type
    ADD is_active BOOLEAN DEFAULT TRUE;

ALTER TABLE tb_street_type
    ADD updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_street_type
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE tb_street_type
    ALTER COLUMN is_active SET NOT NULL;

ALTER TABLE tb_log_street_type
    ADD CONSTRAINT FK_TB_LOG_STREET_TYPE_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);