package br.com.core.operations.fixture

import br.com.core.operations.external.dto.CoinBase

class CoinBaseFixture {
    static CoinBase getOneValid() {
        return CoinBase.builder()
                .base("BTC")
                .currency("USD")
                .amount(3.50)
                .build()
    }
}
