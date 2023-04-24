package br.com.core.operations.fixture

import br.com.core.operations.domain.QuotationResponse

import java.time.LocalDateTime

class QuotationResponseFixture {
    static QuotationResponse getOneValid() {
        return QuotationResponse.builder()
                .moedaBase("bitcoin")
                .data(LocalDateTime.parse("2000-10-10T15:45:15"))
                .cotacoes(
                        [QuotationFixture.validDollar,
                         QuotationFixture.validReal,
                         QuotationFixture.validEuro])
                .build()
    }
}
