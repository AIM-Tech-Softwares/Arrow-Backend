ALTER TABLE tb_company
    DROP COLUMN foundation_date;

ALTER TABLE tb_company
    ADD foundation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL;