package br.com.core.operations.controller

import br.com.core.operations.fixture.QuotationResponseFixture
import br.com.core.operations.usecase.FindBitCoinQuotationUseCase
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class QuotationControllerSpec extends Specification {
    @Subject
    private QuotationController quotationController

    private FindBitCoinQuotationUseCase findBitCoinQuotationUseCase = Mock()

    def setup() {
        quotationController = new QuotationController(findBitCoinQuotationUseCase)
    }

    def "O endpoint quotations deve retornar um QuotationResponse contendo as cotações das moedas: USD, BRL e EUR"() {
        given: "Uma resposta válida da API"
        def quotationResponseFixture = QuotationResponseFixture.getOneValid()
        1 * findBitCoinQuotationUseCase.getQuotationForDollarRealAndEuro() >> quotationResponseFixture

        when: "Executar findBitCoinQuotation"
        def quotationResponseExpected = quotationController.findBitCoinQuotation()

        then: "Deve retornar um QuotationResponse válido com as cotações de dólar, real e euro"
        quotationResponseExpected.moedaBase == "bitcoin"
        quotationResponseExpected.data == LocalDateTime.parse("2000-10-10T15:45:15")
        quotationResponseExpected.cotacoes.size() == 3
        verifyAll(quotationResponseExpected.cotacoes) {
            get(0).valor == "3,50"
            get(0).moeda == "USD"

            get(1).valor == "5,75"
            get(1).moeda == "BRL"

            get(2).valor == "7,00"
            get(2).moeda == "EUR"
        }
    }
}
