ALTER TABLE tb_revision_entity
    ADD ip_address VARCHAR(255);

ALTER TABLE tb_revision_entity
    ADD user_internal_id BIGINT;

ALTER TABLE tb_revision_entity
    ADD username VARCHAR(255);