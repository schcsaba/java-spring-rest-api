package fr.schnitchencsaba.apirest.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInsertCategory() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/category").content("""
                {"name": "gardening",
                "vat": 2000}""").contentType(MediaType.APPLICATION_JSON);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        JsonNode contentResponse = new ObjectMapper().readTree(
                mockMvc.perform(requestBuilder)
                        .andExpect(resultStatus)
                        .andReturn().getResponse().getContentAsString());
        Assertions.assertEquals("gardening", contentResponse.get("name").asText());
        Assertions.assertEquals("2000", contentResponse.get("vat").asText());
    }
}
