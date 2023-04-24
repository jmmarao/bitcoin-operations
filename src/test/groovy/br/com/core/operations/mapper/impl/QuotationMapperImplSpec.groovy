package br.com.core.operations.mapper.impl

import br.com.core.operations.fixture.CoinBaseFixture
import br.com.core.operations.mapper.QuotationMapper
import spock.lang.Specification

class QuotationMapperImplSpec extends Specification {

    private QuotationMapper quotationMapper

    def setup() {
        quotationMapper = new QuotationMapperImpl()
    }

    def "Deve transformar o retorno da API em uma Quotation"() {
        given: "Um CoinBase válido"
        def coinBase = CoinBaseFixture.getOneValid()

        when: "Ao executar mapCoinBase"
        def quotation = quotationMapper.mapCoinBase(coinBase)

        then: "Deve transformar o retorno da API em uma Quotation"
        verifyAll(quotation) {
            valor == "3,50"
            moeda == "USD"
        }
    }
}
