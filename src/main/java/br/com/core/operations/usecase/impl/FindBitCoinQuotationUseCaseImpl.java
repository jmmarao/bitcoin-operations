package br.com.core.operations.usecase.impl;

import br.com.core.operations.domain.QuotationResponse;
import br.com.core.operations.external.dto.CoinBaseResponse;
import br.com.core.operations.external.gateway.BitCoinGateway;
import br.com.core.operations.mapper.QuotationMapper;
import br.com.core.operations.usecase.FindBitCoinQuotationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindBitCoinQuotationUseCaseImpl implements FindBitCoinQuotationUseCase {
    private final BitCoinGateway bitCoinGateway;
    private final QuotationMapper quotationMapper;

    @Override
    public QuotationResponse getQuotationForDollarRealAndEuro() {
        List<CoinBaseResponse> coinBaseResponseList =
                Arrays.asList(
                        bitCoinGateway.getQuotation("USD"),
                        bitCoinGateway.getQuotation("BRL"),
                        bitCoinGateway.getQuotation("EUR")
                );
        return quotationMapper.mapCoinBaseResponseList(coinBaseResponseList);
    }
}
