ALTER TABLE tb_company_representative
    DROP CONSTRAINT fk_tb_company_representative_on_company;

ALTER TABLE tb_street_type
    ADD street_type_abbreviation VARCHAR(10);

ALTER TABLE tb_street_type
    ALTER COLUMN street_type_abbreviation SET NOT NULL;

ALTER TABLE tb_company_representative
    DROP COLUMN company_id;