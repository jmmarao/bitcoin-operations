package br.com.core.operations.fixture

import br.com.core.operations.external.dto.CoinBase
import br.com.core.operations.external.dto.CoinBaseResponse

class CoinBaseResponseFixture {
    static CoinBaseResponse getValidDollar() {
        def coinBase = CoinBase.builder()
                .base("BTC")
                .currency("USD")
                .amount(3.50)
                .build()
        return CoinBaseResponse.builder().data(coinBase).build()
    }

    static CoinBaseResponse getValidReal() {
        def coinBase = CoinBase.builder()
                .base("BTC")
                .currency("BRL")
                .amount(5.75)
                .build()
        return CoinBaseResponse.builder().data(coinBase).build()
    }

    static CoinBaseResponse getValidEuro() {
        def coinBase = CoinBase.builder()
                .base("BTC")
                .currency("EUR")
                .amount(7.00)
                .build()
        return CoinBaseResponse.builder().data(coinBase).build()
    }

    static List<CoinBaseResponse> getValidList() {
        return Arrays.asList(getValidDollar(), getValidReal(), getValidEuro())
    }
}
