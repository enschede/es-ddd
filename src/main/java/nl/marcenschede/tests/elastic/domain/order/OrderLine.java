package nl.marcenschede.tests.elastic.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class OrderLine {

    private String omschrijving;
    private Integer aantal;
    private BigDecimal prijsPerStuk;

    // Used by Json deserialization
    public OrderLine() {
    }

    OrderLine(String omschrijving, Integer aantal, BigDecimal prijsPerStuk) {
        this.omschrijving = omschrijving;
        this.aantal = aantal;
        this.prijsPerStuk = prijsPerStuk;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Integer getAantal() {
        return aantal;
    }

    public BigDecimal getPrijsPerStuk() {
        return prijsPerStuk;
    }

    @JsonIgnore
    public BigDecimal getTotaalPrijs() {
        return prijsPerStuk.multiply(BigDecimal.valueOf(aantal));
    }
}
