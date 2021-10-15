package test.springboot.restclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import test.springboot.restclient.data.*;

import java.util.List;
import java.util.Map;

@Service
public class SGXService {

    @Value("${metadata.url}")
    String metadataURL;

    @Value("${securities.url}")
    String securitiesURL;

    @Autowired
    RestTemplate restTemplate;

    public StockInfo getStockInfo(String code) {
        ParameterizedTypeReference<SGXResponse<List<StockInfo>>> parameterizedTypeReference =
                new ParameterizedTypeReference<SGXResponse<List<StockInfo>>>(){};
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(metadataURL)
                .queryParam("stock-code", code);
        SGXResponse<List<StockInfo>> response = restTemplate.exchange(
                //"https://api.sgx.com/marketmetadata/v2?stock-code={code}",
                builder.toUriString(),
                HttpMethod.GET,null, parameterizedTypeReference).getBody();
        Meta meta = response.getMeta();
        if ("200".equals(meta.getCode())) {
            List<StockInfo> data = response.getData();
            if (data == null || data.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
            } else {
                return data.get(0);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(meta.getCode()), meta.getMessage());
        }
    }

    public Quote getPrice(String code, String params) {
        Map<String, String> parameters = Map.of("code", code);
        ParameterizedTypeReference<SGXResponse<PricesData>> parameterizedTypeReference =
                new ParameterizedTypeReference<SGXResponse<PricesData>>(){};
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.sgx.com/securities/v1.1/stocks/code/{code}")
                .queryParam("params", params);
        SGXResponse<PricesData> response = restTemplate.exchange(
                //"https://api.sgx.com/securities/v1.1/stocks/code/{code}",
                builder.buildAndExpand(parameters).toUriString(),
                HttpMethod.GET,null, parameterizedTypeReference, code).getBody();
        Meta meta = response.getMeta();
        if ("200".equals(meta.getCode())) {
            PricesData prices = response.getData();
            Quote[] quotes = prices.getPrices();
            if (quotes != null && quotes.length > 0) {
                return quotes[0];
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(meta.getCode()), meta.getMessage());
        }
    }

    public List<CorporateAction> getCorporateActions(String ibmcode, int pageStart, int pageSize, String params) {
        Map<String, ?> parameters = Map.of("ibmcode", ibmcode, "pagestart", pageStart,
                "pagesize", pageSize, "params", params);
        ParameterizedTypeReference<SGXResponse<List<CorporateAction>>> parameterizedTypeReference =
                new ParameterizedTypeReference<SGXResponse<List<CorporateAction>>>() {
                };
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.sgx.com/corporateactions/v1.0")
                .queryParam("ibmcode", ibmcode)
                .queryParam("pagestart", pageStart)
                .queryParam("pagesize", pageSize)
                .queryParam("params", params);
        SGXResponse<List<CorporateAction>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET, null, parameterizedTypeReference).getBody();
        Meta meta = response.getMeta();
        if ("200".equals(meta.getCode())) {
            List<CorporateAction> data = response.getData();
            if (data != null && !data.isEmpty()) {
                return data;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(meta.getCode()), meta.getMessage());
        }

    }

}
