ALTER TABLE tb_profile
    ADD profile_name VARCHAR(255);

ALTER TABLE tb_profile
    ALTER COLUMN profile_name SET NOT NULL;

ALTER TABLE tb_profile
    ADD CONSTRAINT uc_tb_profile_profile_name UNIQUE (profile_name);

ALTER TABLE tb_profile
    ALTER COLUMN description DROP NOT NULL;