package br.com.core.operations.mapper.impl

import br.com.core.operations.fixture.CoinBaseResponseFixture
import br.com.core.operations.mapper.QuotationMapper
import spock.lang.Specification
import spock.lang.Subject

class QuotationMapperImplSpec extends Specification {

    @Subject
    private QuotationMapper quotationMapper

    def setup() {
        quotationMapper = new QuotationMapperImpl()
    }

    def "Deve transformar o retorno da API em uma Quotation"() {
        given: "Uma lista de CoinBaseResponse válida"
        def coinBaseResponseList = CoinBaseResponseFixture.getValidList()

        when: "Ao executar mapCoinBase"
        def quotation = quotationMapper.mapCoinBaseResponseList(coinBaseResponseList)

        then: "Deve transformar o retorno da API em uma Quotation"
        quotation.moedaBase == "bitcoin"
//        quotation.data ==
        quotation.cotacoes.size() == 3
        verifyAll(quotation.cotacoes) {
            get(0).valor == "3,50"
            get(0).moeda == "USD"

            get(1).valor == "5,75"
            get(1).moeda == "BRL"

            get(2).valor == "7,00"
            get(2).moeda == "EUR"
        }
    }
}
