package test.springboot.restclient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.springboot.restclient.data.CorporateAction;
import test.springboot.restclient.data.Quote;
import test.springboot.restclient.data.StockInfo;
import test.springboot.restclient.service.SGXService;

import java.util.List;

@RestController
@RequestMapping("/sgx")
public class Controller {

    @Autowired
    SGXService sgxService;

    @GetMapping("/info/{code}")
    public StockInfo getStockInfo(@PathVariable String code) {
        return sgxService.getStockInfo(code);
    }

    @GetMapping("/price/{code}")
    public Quote getQuote(@PathVariable String code, @RequestParam(defaultValue = "b,s,o,h,l,lt") String params) {
        return sgxService.getPrice(code, params);
    }

    @GetMapping("/corporateaction/{ibmcode}")
    public List<CorporateAction> getQuote(@PathVariable String ibmcode, @RequestParam(defaultValue = "0") int pagestart,
                                          @RequestParam(defaultValue = "20") int pagesize,
                                          @RequestParam(defaultValue = "id,anncType,datePaid,exDate,name,particulars,recDate") String params) {
        return sgxService.getCorporateActions(ibmcode, pagestart, pagesize, params);
    }
}
