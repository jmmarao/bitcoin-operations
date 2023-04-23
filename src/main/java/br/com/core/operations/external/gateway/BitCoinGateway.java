package br.com.core.operations.external.gateway;

import br.com.core.operations.external.dto.CoinBaseResponse;

public interface BitCoinGateway {
    CoinBaseResponse getQuotation(final String currency);
}
