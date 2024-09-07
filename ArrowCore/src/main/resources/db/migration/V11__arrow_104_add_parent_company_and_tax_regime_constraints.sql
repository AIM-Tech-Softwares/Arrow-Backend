ALTER TABLE tb_company
    ADD parent_company_id BIGINT;

ALTER TABLE tb_company
    ADD tax_regime_id BIGINT;

ALTER TABLE tb_company
    ALTER COLUMN tax_regime_id SET NOT NULL;

ALTER TABLE tb_company
    ADD CONSTRAINT FK_TB_COMPANY_ON_PARENT_COMPANY FOREIGN KEY (parent_company_id) REFERENCES tb_company (internal_id);

ALTER TABLE tb_company
    ADD CONSTRAINT FK_TB_COMPANY_ON_TAX_REGIME FOREIGN KEY (tax_regime_id) REFERENCES tb_tax_regime (internal_id);