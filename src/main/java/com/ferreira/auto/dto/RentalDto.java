package com.ferreira.auto.dto;

public class RentalDto {

    private Long qtdeDaysRent;
    private Long percentageInterest;

    public RentalDto() {}

    public RentalDto(Long qtdeDaysRent, Long percentageInterest) {
        this.qtdeDaysRent = qtdeDaysRent;
        this.percentageInterest = percentageInterest;
    }

    public Long getQtdeDaysRent() {
        return qtdeDaysRent;
    }

    public void setQtdeDaysRent(Long qtdeDaysRent) {
        this.qtdeDaysRent = qtdeDaysRent;
    }

    public Long getPercentageInterest() {
        return percentageInterest;
    }

    public void setPercentageInterest(Long percentageInterest) {
        this.percentageInterest = percentageInterest;
    }
}
