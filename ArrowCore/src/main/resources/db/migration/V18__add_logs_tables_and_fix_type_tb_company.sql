CREATE SEQUENCE IF NOT EXISTS tb_revision_entity_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE tb_company_representative_association_aud
(
    company_id        BIGINT  NOT NULL,
    representative_id BIGINT  NOT NULL,
    rev               INTEGER NOT NULL,
    revtype           SMALLINT,
    CONSTRAINT pk_tb_company_representative_association_aud PRIMARY KEY (company_id, representative_id, rev)
);

CREATE TABLE tb_log_city
(
    rev         INTEGER NOT NULL,
    revtype     SMALLINT,
    internal_id BIGINT  NOT NULL,
    city_name   VARCHAR(100),
    city_code   VARCHAR(20),
    latitude    DECIMAL,
    longitude   DECIMAL,
    is_active   BOOLEAN DEFAULT TRUE,
    state_id    BIGINT,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_city PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_log_company
(
    rev                    INTEGER NOT NULL,
    revtype                SMALLINT,
    internal_id            BIGINT  NOT NULL,
    external_id            UUID,
    trade_name             VARCHAR(255),
    corporate_name         VARCHAR(255),
    cnpj                   VARCHAR(14),
    state_registration     VARCHAR(20),
    municipal_registration VARCHAR(20),
    foundation_date        TIMESTAMP WITHOUT TIME ZONE,
    tax_regime_id          BIGINT,
    email                  VARCHAR(255),
    phone                  VARCHAR(15),
    street_type_id         BIGINT,
    street_name            VARCHAR(255),
    street_number          VARCHAR(20),
    neighborhood           VARCHAR(100),
    complement             VARCHAR(100),
    zip_code               VARCHAR(8),
    city_id                BIGINT,
    logo_url               VARCHAR(255),
    is_active              BOOLEAN DEFAULT TRUE,
    parent_company_id      BIGINT,
    business_group         BIGINT,
    created_at             TIMESTAMP WITHOUT TIME ZONE,
    updated_at             TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_company PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_log_company_representative
(
    rev          INTEGER NOT NULL,
    revtype      SMALLINT,
    internal_id  BIGINT  NOT NULL,
    cpf          VARCHAR(11),
    name         VARCHAR(255),
    phone_fixed  VARCHAR(15),
    phone_mobile VARCHAR(15),
    email        VARCHAR(255),
    position     VARCHAR(100),
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_log_company_representative PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_log_profile
(
    rev          INTEGER NOT NULL,
    revtype      SMALLINT,
    internal_id  BIGINT  NOT NULL,
    external_id  UUID,
    profile_name VARCHAR(255),
    description  VARCHAR(255),
    is_active    BOOLEAN,
    CONSTRAINT pk_tb_log_profile PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_log_user
(
    rev            INTEGER NOT NULL,
    revtype        SMALLINT,
    internal_id    BIGINT  NOT NULL,
    external_id    UUID,
    first_name     VARCHAR(255),
    last_name      VARCHAR(255),
    username       VARCHAR(255),
    password       VARCHAR(255),
    last_login     TIMESTAMP WITHOUT TIME ZONE,
    is_active      BOOLEAN,
    is_first_login BOOLEAN DEFAULT TRUE,
    business_group BIGINT,
    CONSTRAINT pk_tb_log_user PRIMARY KEY (rev, internal_id)
);

CREATE TABLE tb_profile_role_aud
(
    profile_id BIGINT  NOT NULL,
    rev        INTEGER NOT NULL,
    revtype    SMALLINT,
    role_id    BIGINT  NOT NULL,
    CONSTRAINT pk_tb_profile_role_aud PRIMARY KEY (profile_id, rev, role_id)
);

CREATE TABLE tb_revision_entity
(
    id        INTEGER NOT NULL,
    timestamp BIGINT  NOT NULL,
    CONSTRAINT pk_tb_revision_entity PRIMARY KEY (id)
);

CREATE TABLE tb_user_profile_aud
(
    profile_id BIGINT  NOT NULL,
    rev        INTEGER NOT NULL,
    revtype    SMALLINT,
    user_id    BIGINT  NOT NULL,
    CONSTRAINT pk_tb_user_profile_aud PRIMARY KEY (profile_id, rev, user_id)
);

ALTER TABLE tb_log_city
    ADD CONSTRAINT FK_TB_LOG_CITY_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_log_company
    ADD CONSTRAINT FK_TB_LOG_COMPANY_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_log_company_representative
    ADD CONSTRAINT FK_TB_LOG_COMPANY_REPRESENTATIVE_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_log_profile
    ADD CONSTRAINT FK_TB_LOG_PROFILE_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_log_user
    ADD CONSTRAINT FK_TB_LOG_USER_ON_REV FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_company_representative_association_aud
    ADD CONSTRAINT fk_tb_company_representative_association_aud_on_rev FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_profile_role_aud
    ADD CONSTRAINT fk_tb_profile_role_aud_on_rev FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_user_profile_aud
    ADD CONSTRAINT fk_tb_user_profile_aud_on_rev FOREIGN KEY (rev) REFERENCES tb_revision_entity (id);

ALTER TABLE tb_company
    DROP COLUMN foundation_date;

ALTER TABLE tb_company
    ADD foundation_date date NOT NULL;