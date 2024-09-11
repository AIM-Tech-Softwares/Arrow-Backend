CREATE TABLE tb_log_country
(
    rev          INTEGER NOT NULL,
    revtype      SMALLINT,
    internal_id  BIGINT  NOT NULL,
    country_name VARCHAR(100),
    iso_code     VARCHAR(3),
    is_active    BOOLEAN DEFAULT TRUE,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_country PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_log_state
(
    rev         INTEGER NOT NULL,
    revtype     SMALLINT,
    internal_id BIGINT  NOT NULL,
    state_name  VARCHAR(100),
    state_code  VARCHAR(2),
    country_id  BIGINT,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_state PRIMARY KEY (rev, internal_id)
);

ALTER TABLE tb_country
    ADD created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now();

ALTER TABLE tb_country
    ADD is_active BOOLEAN DEFAULT TRUE;

ALTER TABLE tb_country
    ADD updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_country
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE tb_state
    ADD created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now();

ALTER TABLE tb_state
    ADD is_active BOOLEAN DEFAULT TRUE;

ALTER TABLE tb_state
    ADD updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tb_state
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE tb_country
    ALTER COLUMN is_active SET NOT NULL;

ALTER TABLE tb_state
    ALTER COLUMN is_active SET NOT NULL;

ALTER TABLE tb_log_country
    ADD CONSTRAINT FK_TB_LOG_COUNTRY_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_log_state
    ADD CONSTRAINT FK_TB_LOG_STATE_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);