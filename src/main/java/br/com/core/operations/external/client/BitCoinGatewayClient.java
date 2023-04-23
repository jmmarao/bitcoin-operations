package br.com.core.operations.external.client;

import br.com.core.operations.external.dto.CoinBaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.rest.bitcoin.name}", url = "${feign.rest.bitcoin.url}")
public interface BitCoinGatewayClient {

    @GetMapping(value = "/v2/prices/spot", produces = MediaType.APPLICATION_JSON_VALUE)
    CoinBaseResponse getQuotation(@RequestParam("currency") String currency);
}
