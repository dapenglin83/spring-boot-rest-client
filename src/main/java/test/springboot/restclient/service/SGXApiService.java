package test.springboot.restclient.service;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import test.springboot.restclient.data.CorporateAction;
import test.springboot.restclient.data.PricesData;
import test.springboot.restclient.data.SGXResponse;
import test.springboot.restclient.data.StockInfo;

import java.util.List;

@FeignClient(name="api.sgx", url = "${api.sgx.url}")
public interface SGXApiService {

    @GetMapping("/marketmetadata/v2")
    SGXResponse<List<StockInfo>> getStockInfo(@RequestParam("stock-code") String code);

    @GetMapping("/securities/v1.1/stocks/code/{code}")
    SGXResponse<PricesData> getQuote(@PathVariable("code") String code,
                                          @RequestParam("params") @DefaultValue("b,s,o,h,l,lt") String params);

    @GetMapping("/corporateactions/v1.0")
    SGXResponse<List<CorporateAction>> getCorporateActions(@RequestParam("ibmcode") String ibmCode, @RequestParam("pagestart") @DefaultValue("0") int pageStart,
                                                            @RequestParam("pagesize") @DefaultValue("20") int pageSize,
                                                            @RequestParam("params") @DefaultValue("id,anncType,datePaid,exDate,name,particulars,recDate") String params);
}
