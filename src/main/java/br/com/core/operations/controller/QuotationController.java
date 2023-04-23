package br.com.core.operations.controller;

import br.com.core.operations.domain.QuotationResponse;
import br.com.core.operations.usecase.FindBitCoinQuotationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bitcoin-operations")
public class QuotationController {
    private final FindBitCoinQuotationUseCase findBitCoinQuotationUseCase;

    @GetMapping("/quotations")
    public QuotationResponse findBitCoinQuotation() {
        log.info("Looking for quotation for dollar, real and euro");
        return findBitCoinQuotationUseCase.getQuotationForDollarRealAndEuro();
    }
}
