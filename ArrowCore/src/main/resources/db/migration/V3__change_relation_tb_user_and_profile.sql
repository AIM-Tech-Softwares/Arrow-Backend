ALTER TABLE tb_user
    DROP CONSTRAINT fk_tb_user_on_profile;

ALTER TABLE tb_user
    DROP CONSTRAINT uc_tb_user_profile;

CREATE TABLE tb_user_profile
(
    user_id    BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    CONSTRAINT pk_tb_user_profile PRIMARY KEY (user_id, profile_id)
);

ALTER TABLE tb_user_profile
    ADD CONSTRAINT fk_tb_user_profile_on_user FOREIGN KEY (user_id) REFERENCES tb_user (internal_id);

ALTER TABLE tb_user_profile
    ADD CONSTRAINT fk_tb_user_profile_on_profile FOREIGN KEY (profile_id) REFERENCES tb_profile (internal_id);

ALTER TABLE tb_user
    DROP COLUMN profile_id;