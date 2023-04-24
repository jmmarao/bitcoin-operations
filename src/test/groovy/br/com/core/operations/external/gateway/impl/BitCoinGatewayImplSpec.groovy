package br.com.core.operations.external.gateway.impl

import br.com.core.operations.external.client.BitCoinGatewayClient
import br.com.core.operations.external.gateway.BitCoinGateway
import br.com.core.operations.fixture.CoinBaseResponseFixture
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
}
