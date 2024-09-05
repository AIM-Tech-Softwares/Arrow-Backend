ALTER TABLE tb_business_group
    ADD CONSTRAINT uc_tb_business_group_schema_name UNIQUE (schema_name);

ALTER TABLE tb_business_group
    ALTER COLUMN schema_name SET NOT NULL;