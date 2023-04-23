package br.com.core.operations.mapper.impl;

import br.com.core.operations.domain.Quotation;
import br.com.core.operations.external.dto.CoinBase;
import br.com.core.operations.mapper.QuotationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuotationMapperImpl implements QuotationMapper {
    @Override
    public Quotation mapCoinBaseResponse(CoinBase coinBase) {
        return Quotation.builder()
                .valor(String.format("%.2f", coinBase.getAmount()))
                .moeda(coinBase.getCurrency())
                .build();
    }
}
