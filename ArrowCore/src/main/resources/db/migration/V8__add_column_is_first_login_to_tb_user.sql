ALTER TABLE tb_user
    ADD is_first_login BOOLEAN DEFAULT TRUE;

ALTER TABLE tb_user
    ALTER COLUMN is_first_login SET NOT NULL;