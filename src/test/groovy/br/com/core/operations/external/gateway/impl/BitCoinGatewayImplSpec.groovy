package br.com.core.operations.external.gateway.impl

import br.com.core.operations.external.client.BitCoinGatewayClient
import br.com.core.operations.external.gateway.BitCoinGateway
import br.com.core.operations.fixture.CoinBaseResponseFixture
import feign.FeignException
import feign.Request
import spock.lang.Specification

class BitCoinGatewayImplSpec extends Specification {
    private BitCoinGateway bitCoinGateway

    private BitCoinGatewayClient bitCoinGatewayClient = Mock()

    def setup() {
        bitCoinGateway = new BitCoinGatewayImpl(bitCoinGatewayClient)
    }

    def "Deve buscar os dados de CoinBase na API baseado na moeda passada"() {
        given: "Uma moeda"
        String currency = "BRL"

        and: "Uma chamada para a API"
        def coinBaseResponseFixture = CoinBaseResponseFixture.getValidReal()
        1 * bitCoinGatewayClient.getQuotation(currency) >> coinBaseResponseFixture

        when: "For executado getQuotation"
        def coinBaseResponse = bitCoinGateway.getQuotation(currency)

        then: "Deve retornar um CoinBaseResponse com os dados de CoinBase"
        coinBaseResponse
        verifyAll(coinBaseResponse.data) {
            base == "BTC"
            it.currency == "BRL"
            amount == 5.75
        }
    }

    def "Deve lançar uma FeignException ao ocorrer um problema na chamada com o bitCoinGatewayClient"() {
        given: "Uma moeda válida"
        String currency = "BRL"

        and: "Um mock do Feign Request"
        Request requestMock = GroovyMock()

        and: "Uma mensagem de erro"
        def errorMessage = "Ops, houve um problema..."

        and: "Uma chamada para a API lançando uma FeignException"
        1 * bitCoinGatewayClient.getQuotation(currency) >> { { throw new FeignException.FeignClientException(400, errorMessage, requestMock, null, null) } }

        when: "Buscar uma cotação"
        bitCoinGateway.getQuotation(currency)

        then: "Deve retornar uma"
        def exception = thrown(RuntimeException)
        exception.getMessage() == "Erro ao gerar um token no GACB"
    }

    def "Deve lançar uma RuntimeException ao informar uma moeda inválida"() {
        given: "Uma moeda inválida"
        String currency = "invalid currency"

        and: "Uma chamada para a API lançando uma RuntimeException"
        1 * bitCoinGatewayClient.getQuotation(currency) >> { { throw new RuntimeException("Moeda inválida: " + currency) } }

        when: "Buscar uma cotação"
        bitCoinGateway.getQuotation(currency)

        then: "Deve lançar uma RuntimeException com a mensagem de erro esperada"
        def ex = thrown(RuntimeException)
        ex.message == "Erro genérico ao gerar um token GACB"
    }
}
