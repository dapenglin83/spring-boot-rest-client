package test.springboot.restclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestClientApplicationTests {

	@Autowired
	MockMvc mockMvc;

	private static final String DBS_CODE = "D05";
	private static final String DBS_IBM_CODE = "1L01";
	private static final String DBS_NAME = "DBS GROUP HOLDINGS LTD";

	@Test
	void contextLoads() {
	}

	@Test
	public void shouldReturnSameCodeSameName() throws Exception {
		mockMvc.perform(get("/sgx/info/{code}", DBS_CODE)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(DBS_NAME))
                .andExpect(jsonPath("$.ibmCode").value(DBS_IBM_CODE));
	}

	@Test
    public void shouldReturnClosePriceGreaterThan25() throws Exception {
        mockMvc.perform(get("/sgx/price/{code}", DBS_CODE)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.last").value(30.41));
    }

    @Test
    public void shouldReturn20ElementsAndWithSameName() throws Exception {
        mockMvc.perform(get("/sgx/corporateaction/{code}", DBS_IBM_CODE)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)))
                .andExpect(jsonPath("$[0].name").value(DBS_NAME))
                .andExpect(jsonPath("$[1].name").value(DBS_NAME))
                .andExpect(jsonPath("$[3].name").value(DBS_NAME))
                .andExpect(jsonPath("$[5].name").value(DBS_NAME))
                .andExpect(jsonPath("$[10].name").value(DBS_NAME));
    }
}
