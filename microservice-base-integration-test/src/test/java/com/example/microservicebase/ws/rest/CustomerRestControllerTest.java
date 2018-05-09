package com.example.microservicebase.ws.rest;

import com.example.microservicebase.Application;
import com.example.microservicebase.model.entity.CustomerEntity;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("integration")
@Profile("integration")
public class CustomerRestControllerTest {

    private RestTemplate restTemplate;
    private ResponseEntity<CustomerEntity[]> response;

    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String MOCK_URL = "/api/v1/customers";
    private static final int MOCK_HTTP_PORT = 8089;
    private static final int MOCK_HTTPS_PORT = 8443;
    private static final String MOCK_HOST = "http://localhost";
    private static final String ENDPOINT_REST_URL = MOCK_HOST + ":" + String.valueOf(MOCK_HTTP_PORT) + MOCK_URL;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(MOCK_HTTP_PORT).httpsPort(MOCK_HTTPS_PORT));

    @Before
    public void setup() throws Exception {
        restTemplate = new RestTemplate();
        response = null;
    }

    @Test
    public void test(){
        stubFor(get(urlEqualTo(MOCK_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBodyFile("customers.json")));

        response = restTemplate.getForEntity(ENDPOINT_REST_URL, CustomerEntity[].class);

        assertTrue(response.getStatusCodeValue()==HttpStatus.OK.value());
        assertEquals(APPLICATION_JSON_UTF8_VALUE.toString(), response.getHeaders().getContentType().toString());
        assertNotNull(response.getBody());

        List<CustomerEntity> listExpected = Arrays.asList(response.getBody());
        assertTrue(listExpected.size()==3);
    }

}
