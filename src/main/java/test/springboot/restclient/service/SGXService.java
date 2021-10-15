package test.springboot.restclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import test.springboot.restclient.data.*;

import java.util.List;

@Service
public class SGXService {

    @Autowired
    SGXApiService sgxApiService;

    public StockInfo getStockInfo(String code) {
        SGXResponse<List<StockInfo>> response = sgxApiService.getStockInfo(code);
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
        SGXResponse<PricesData> response = sgxApiService.getQuote(code, params);
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
        SGXResponse<List<CorporateAction>> response = sgxApiService.getCorporateActions(ibmcode, pageStart, pageSize, params);
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
