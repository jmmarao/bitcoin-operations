package br.com.core.operations.usecase.impl;

import br.com.core.operations.domain.Quotation;
import br.com.core.operations.domain.QuotationResponse;
import br.com.core.operations.external.dto.CoinBaseResponse;
import br.com.core.operations.external.gateway.BitCoinGateway;
import br.com.core.operations.mapper.QuotationMapper;
import br.com.core.operations.usecase.FindBitCoinQuotationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindBitCoinQuotationUseCaseImpl implements FindBitCoinQuotationUseCase {
    private static final String BIT_COIN = "bitcoin";
    private static final String REQUEST_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private final BitCoinGateway bitCoinGateway;

    private final QuotationMapper quotationMapper;

    @Override
    public QuotationResponse getQuotationForDollarRealAndEuro() {
        List<Quotation> quotations = fillQuotationList();
        return getQuotationResponse(quotations);
    }

    private List<Quotation> fillQuotationList() {
        List<CoinBaseResponse> coinBaseResponseList =
                Arrays.asList(
                        bitCoinGateway.getQuotation("USD"),
                        bitCoinGateway.getQuotation("BRL"),
                        bitCoinGateway.getQuotation("EUR")
                );

        List<Quotation> quotations = new ArrayList<>();
        for (CoinBaseResponse coinBaseResponse : coinBaseResponseList) {
            quotations.add(quotationMapper.mapCoinBaseResponse(coinBaseResponse.getData()));
        }
        return quotations;
    }

    private QuotationResponse getQuotationResponse(List<Quotation> quotations) {
        QuotationResponse quotationResponse = new QuotationResponse();
        quotationResponse.setMoedaBase(BIT_COIN);
        quotationResponse.setData(setLocalDateTimeNowFormatted());
        quotationResponse.setCotacoes(quotations);
        return quotationResponse;
    }

    private LocalDateTime setLocalDateTimeNowFormatted() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(REQUEST_DATE_TIME_PATTERN);
        String nowString = LocalDateTime.now().format(dateTimeFormatter);
        return LocalDateTime.parse(nowString, dateTimeFormatter);
    }
}
