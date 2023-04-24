package br.com.core.operations.external.gateway.impl;

import br.com.core.operations.external.client.BitCoinGatewayClient;
import br.com.core.operations.external.dto.CoinBaseResponse;
import br.com.core.operations.external.gateway.BitCoinGateway;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BitCoinGatewayImpl implements BitCoinGateway {

    private final BitCoinGatewayClient client;

    private static final String ERROR_EXCEPTION_MESSAGE =
            "Erro genérico ao chamar a API do Coinbase. Exception message={}";

    private static final String ERROR_FEIGN_MESSAGE =
            "Erro ao chamar a API do Coinbase. [FeignException: 'statusCode' = {}, 'requestUrl' = {}, "
                    + "'content'={}, 'errorMessage'={}]";

    public CoinBaseResponse getQuotation(final String currency) {

        try {
            log.info("[BitCoinGatewayImpl] Buscando a cotação do Bitcoin para moeda: {}", currency);
            return client.getQuotation(currency);
        } catch (final FeignException feignException) {
            log.error(ERROR_FEIGN_MESSAGE, feignException.status(), feignException.request().url(),
                    feignException.contentUTF8(), feignException.getMessage());
            throw new RuntimeException("Erro ao gerar um token no GACB", feignException);
        } catch (final Exception e) {
            log.error(ERROR_EXCEPTION_MESSAGE, e.getMessage());
            throw new RuntimeException("Erro genérico ao gerar um token GACB", e);
        }
    }
}
