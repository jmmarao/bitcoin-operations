package br.com.core.operations.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuotationResponse {
    private String moedaBase;
    private LocalDateTime data;
    private List<Quotation> cotacoes;
}
