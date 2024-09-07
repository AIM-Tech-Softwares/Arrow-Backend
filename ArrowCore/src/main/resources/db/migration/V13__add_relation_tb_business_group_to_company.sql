ALTER TABLE tb_company
    ADD business_group BIGINT;

ALTER TABLE tb_company
    ADD CONSTRAINT FK_TB_COMPANY_ON_BUSINESS_GROUP FOREIGN KEY (business_group) REFERENCES tb_business_group (internal_id);