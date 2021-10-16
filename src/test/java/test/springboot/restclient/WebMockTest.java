package test.springboot.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import test.springboot.restclient.controller.Controller;
import test.springboot.restclient.data.CorporateAction;
import test.springboot.restclient.data.Quote;
import test.springboot.restclient.data.StockInfo;
import test.springboot.restclient.service.SGXService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SGXService service;

    private static final String DBS_CODE = "D05";
    private static final String DBS_IBM_CODE = "1L01";
    private static final String DBS_NAME = "DBS GROUP HOLDINGS LTD";

    private static final String DBS_INFO_RESP = "{\"fisn\":\"DBS GRP HLDG/ORDSHS\",\"ibmCode\":\"1L01\",\"fullName\":\"DBS GROUP HOLDINGS LTD\",\"stockCode\":\"D05\",\"isinCode\":\"SG1L01001701\"}";
    private static final String DBS_PRICE_RESP = "{\"last\":30.41,\"bid\":30.4,\"ask\":30.42,\"open\":30.62,\"high\":30.68,\"low\":30.41}";
    private static final String DBS_CORPORATE_ACTION_RESP = "[{\"datePaid\":\"25/08/2021 04:00:00\",\"name\":\"DBS GROUP HOLDINGS LTD\",\"anncType\":\"DIVIDEND\",\"exDate\":\"12/08/2021 04:00:00\",\"recDate\":\"15/08/2021 04:00:00\",\"particulars\":\"Rate: SGD 0.33  Per Security\"}]";

    static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void infoShouldReturnMessageFromService() throws Exception {
        StockInfo info = objectMapper.readValue(DBS_INFO_RESP, StockInfo.class);
        when(service.getStockInfo(DBS_CODE)).thenReturn(info);
        this.mockMvc.perform(get("/sgx/info/{code}", DBS_CODE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(DBS_INFO_RESP));
    }

    @Test
    public void priceShouldReturnMessageFromService() throws Exception {
        Quote quote = objectMapper.readValue(DBS_PRICE_RESP, Quote.class);
        when(service.getPrice(DBS_CODE, "b,s,o,h,l,lt")).thenReturn(quote);
        this.mockMvc.perform(get("/sgx/price/{code}", DBS_CODE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(DBS_PRICE_RESP));
    }

    @Test
    public void corporateActionShouldReturnMessageFromService() throws Exception {
        List<CorporateAction> corporateActions = objectMapper.readValue(DBS_CORPORATE_ACTION_RESP,
                new TypeReference<List<CorporateAction>>(){});
        when(service.getCorporateActions(DBS_IBM_CODE, 0, 20, "id,anncType,datePaid,exDate,name,particulars,recDate")).thenReturn(corporateActions);
        this.mockMvc.perform(get("/sgx/corporateaction/{code}", DBS_IBM_CODE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(DBS_CORPORATE_ACTION_RESP));
    }
}
