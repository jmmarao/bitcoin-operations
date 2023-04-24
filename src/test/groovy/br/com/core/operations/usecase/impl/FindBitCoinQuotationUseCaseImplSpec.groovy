package br.com.core.operations.usecase.impl

import br.com.core.operations.external.dto.CoinBaseResponse
import br.com.core.operations.external.gateway.BitCoinGateway
import br.com.core.operations.fixture.CoinBaseResponseFixture
import br.com.core.operations.fixture.QuotationFixture
import br.com.core.operations.mapper.QuotationMapper
import br.com.core.operations.usecase.FindBitCoinQuotationUseCase
import spock.lang.Specification
import spock.lang.Subject

class FindBitCoinQuotationUseCaseImplSpec extends Specification {

    @Subject
    private FindBitCoinQuotationUseCase findBitCoinQuotationUseCase

    private BitCoinGateway bitCoinGateway = Mock()
    private QuotationMapper quotationMapper = Mock()

    def setup() {
        findBitCoinQuotationUseCase = new FindBitCoinQuotationUseCaseImpl(bitCoinGateway, quotationMapper)
    }

    def "Deve buscar a cotação de bitcoin de três moedas na API de coinbase - USD, BRL e EUR"() {
        given: "Uma resposta válida da API ao buscar por dólar"
        CoinBaseResponse usdResponse = CoinBaseResponseFixture.getValidDollar()
        1 * bitCoinGateway.getQuotation("USD") >> usdResponse

        and: "Uma resposta válida da API ao buscar por real"
        CoinBaseResponse brlResponse = CoinBaseResponseFixture.getValidReal()
        1 * bitCoinGateway.getQuotation("BRL") >> brlResponse

        and: "Uma resposta válida da API ao buscar por euro"
        CoinBaseResponse eurResponse = CoinBaseResponseFixture.getValidEuro()
        1 * bitCoinGateway.getQuotation("EUR") >> eurResponse

        and: "Um mapeamento válido do retorno da API para cada tipo de moeda: USD, BRL, EUR"
        1 * quotationMapper.mapCoinBase(usdResponse.data) >> QuotationFixture.getValidDollar()
        1 * quotationMapper.mapCoinBase(brlResponse.data) >> QuotationFixture.getValidReal()
        1 * quotationMapper.mapCoinBase(eurResponse.data) >> QuotationFixture.getValidEuro()

        when: "A função de buscar a cotação de dólar, real e euro for chamada"
        def quotations = findBitCoinQuotationUseCase.getQuotationForDollarRealAndEuro()

        then: "Deve retornar uma resposta de bitcoin válida"
        quotations.moedaBase == "bitcoin"

        verifyAll(quotations.cotacoes) {
            get(0).valor == "3,50"
            get(0).moeda == "USD"

            get(1).valor == "5,75"
            get(1).moeda == "BRL"

            get(2).valor == "7,00"
            get(2).moeda == "EUR"
        }
    }
}
