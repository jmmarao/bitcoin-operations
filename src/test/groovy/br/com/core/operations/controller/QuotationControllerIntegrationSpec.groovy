package br.com.core.operations.controller

import br.com.core.operations.external.dto.CoinBaseResponse
import br.com.core.operations.external.gateway.BitCoinGateway
import br.com.core.operations.fixture.CoinBaseResponseFixture
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
class QuotationControllerIntegrationSpec extends Specification {
    private static final String REQUEST_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @SpringBean
    private BitCoinGateway bitCoinGateway = Mock()

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper objectMapper = new ObjectMapper()

    def "O endpoint quotations deve retornar um QuotationResponse contendo as cotações das moedas: USD, BRL e EUR"() {
        given: "Uma resposta válida da API ao buscar por dólar"
        CoinBaseResponse usdResponse = CoinBaseResponseFixture.getValidDollar()
        1 * bitCoinGateway.getQuotation("USD") >> usdResponse

        and: "Uma resposta válida da API ao buscar por real"
        CoinBaseResponse brlResponse = CoinBaseResponseFixture.getValidReal()
        1 * bitCoinGateway.getQuotation("BRL") >> brlResponse

        and: "Uma resposta válida da API ao buscar por euro"
        CoinBaseResponse eurResponse = CoinBaseResponseFixture.getValidEuro()
        1 * bitCoinGateway.getQuotation("EUR") >> eurResponse

        when: "O endpoint quotations for chamado"
        def mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/bitcoin-operations/quotations")
        ).andReturn()

        then: "Deve retornar status 200"
        mvcResult.getResponse().status == 200

        and: "Deve retornar um QuotationResponse válido com as cotações de dólar, real e euro"
        def contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)
        def jsonNode = objectMapper.readTree(contentAsString)

        jsonNode.get("moedaBase").asText() == "bitcoin"
        //jsonNode.get("data").asText() == setLocalDateTimeNowToString()
        jsonNode.get("cotacoes").size() == 3

        verifyAll(jsonNode.get("cotacoes")) {
            get(0).get("valor").asText() == "3,50"
            get(0).get("moeda").asText() == "USD"

            get(1).get("valor").asText() == "5,75"
            get(1).get("moeda").asText() == "BRL"

            get(2).get("valor").asText() == "7,00"
            get(2).get("moeda").asText() == "EUR"
        }
    }

//    private String setLocalDateTimeNowToString() {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(REQUEST_DATE_TIME_PATTERN)
//        String nowString = LocalDateTime.now().format(dateTimeFormatter)
//        return LocalDateTime.parse(nowString, dateTimeFormatter).toString()
//    }
}
