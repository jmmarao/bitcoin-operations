package br.com.core.operations.mapper;

import br.com.core.operations.domain.QuotationResponse;
import br.com.core.operations.external.dto.CoinBaseResponse;

import java.util.List;

public interface QuotationMapper {
    QuotationResponse mapCoinBaseResponseList(List<CoinBaseResponse> coinBaseResponseList);
}
