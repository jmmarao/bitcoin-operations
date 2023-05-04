package br.com.core.operations.mapper.impl;

import br.com.core.operations.domain.Quotation;
import br.com.core.operations.domain.QuotationResponse;
import br.com.core.operations.external.dto.CoinBase;
import br.com.core.operations.external.dto.CoinBaseResponse;
import br.com.core.operations.mapper.QuotationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuotationMapperImpl implements QuotationMapper {
    private static final String BIT_COIN = "bitcoin";
    private static final String REQUEST_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public QuotationResponse mapCoinBaseResponseList(List<CoinBaseResponse> coinBaseResponseList) {
        List<Quotation> quotations = new ArrayList<>();

        for (CoinBaseResponse coinBaseResponse : coinBaseResponseList) {
            quotations.add(mapCoinBase(coinBaseResponse.getData()));
        }
        return getQuotationResponse(quotations);
    }

    private Quotation mapCoinBase(CoinBase coinBase) {
        return Quotation.builder()
                .valor(String.format("%.2f", coinBase.getAmount()))
                .moeda(coinBase.getCurrency())
                .build();
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
