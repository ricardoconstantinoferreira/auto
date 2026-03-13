package com.ferreira.auto.dto;

public class StockDto {

    private Integer qtde;

    private Long modelId;

    public StockDto() {
    }

    public StockDto(Integer qtde, Long modelId) {
        this.qtde = qtde;
        this.modelId = modelId;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
