package net.vishwaraj.ronavirustracker.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CaseData {

    private BigDecimal confirmed;
    private BigDecimal recovered;
    private BigDecimal deaths;
    private String lastUpdate;
}
