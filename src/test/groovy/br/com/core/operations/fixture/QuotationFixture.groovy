package br.com.core.operations.fixture

import br.com.core.operations.domain.Quotation

class QuotationFixture {

    static Quotation getValidDollar() {
        return Quotation.builder()
                .moeda("USD")
                .valor("3,50")
                .build()
    }

    static Quotation getValidReal() {
        return Quotation.builder()
                .moeda("BRL")
                .valor("5,75")
                .build()
    }

    static Quotation getValidEuro() {
        return Quotation.builder()
                .moeda("EUR")
                .valor("7,00")
                .build()
    }
}
