package br.com.core.operations.mapper;

import br.com.core.operations.domain.Quotation;
import br.com.core.operations.external.dto.CoinBase;

public interface QuotationMapper {
    Quotation mapCoinBaseResponse(CoinBase coinBase);
}
