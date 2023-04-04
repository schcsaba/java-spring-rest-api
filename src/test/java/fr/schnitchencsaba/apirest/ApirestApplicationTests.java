package fr.schnitchencsaba.apirest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApirestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(ApirestApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    void testHelloWorld() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/hello/world")
                                .contentType(MediaType.TEXT_PLAIN_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello World!"));
    }

    @Test
    void testDatabase() {
        Query query = entityManager.createNativeQuery("SHOW tables;");
        List<String> results = ((List<String>) query.getResultList());
        String resultList = String.join(" - ", results);
        logger.info("Connection to the DB :: SUCCESS");
        logger.info("List of tables: [{}]", resultList);
    }

}
