ALTER TABLE tb_business_group
    DROP COLUMN external_id;

ALTER TABLE tb_business_group
    ADD external_id UUID NOT NULL;

ALTER TABLE tb_business_group
    ADD CONSTRAINT uc_tb_business_group_external UNIQUE (external_id);

ALTER TABLE tb_profile
    DROP COLUMN external_id;

ALTER TABLE tb_profile
    ADD external_id UUID NOT NULL;

ALTER TABLE tb_profile
    ADD CONSTRAINT uc_tb_profile_external UNIQUE (external_id);

ALTER TABLE tb_user
    DROP COLUMN external_id;

ALTER TABLE tb_user
    ADD external_id UUID NOT NULL;

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_external UNIQUE (external_id);