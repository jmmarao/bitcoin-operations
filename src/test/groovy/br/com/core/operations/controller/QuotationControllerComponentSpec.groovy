package br.com.core.operations.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.WireMockSpring
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest
@AutoConfigureMockMvc
class QuotationControllerComponentSpec extends Specification {

    @Shared
    private WireMockServer wireMock = new WireMockServer(WireMockSpring.options()
            .port(8888)
            .usingFilesUnderDirectory("config/wiremock/mocks"))

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper objectMapper = new ObjectMapper()

    def setupSpec() {
        wireMock.start()
    }

    def cleanupSpec() {
        wireMock.stop()
    }

    def "O endpoint quotations deve retornar um QuotationResponse contendo as cotações das moedas: USD, BRL e EUR"() {
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
        //jsonNode.get("data").asText() == "2023-04-23T21:44:11"
        jsonNode.get("cotacoes").size() == 3

//        verifyAll(jsonNode.get("cotacoes")) {
//            get(0).get("valor").asText() == "27437,28"
//            get(0).get("moeda").asText() == "USD"
//
//            get(1).get("valor").asText() == "138541,80"
//            get(1).get("moeda").asText() == "BRL"
//
//            get(2).get("valor").asText() == "24990,73"
//            get(2).get("moeda").asText() == "EUR"
//        }
    }
}
