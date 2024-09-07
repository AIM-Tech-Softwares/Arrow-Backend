ALTER TABLE tb_company
    DROP COLUMN foundation_date;

ALTER TABLE tb_company
    ADD foundation_date date NOT NULL;