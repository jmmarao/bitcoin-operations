package br.com.core.operations.usecase;

import br.com.core.operations.domain.QuotationResponse;

public interface FindBitCoinQuotationUseCase {
    QuotationResponse getQuotationForDollarRealAndEuro();
}
