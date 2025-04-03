package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VulnadoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        // Ensures the application context loads successfully
        assertNotNull("Application context should load", restTemplate);
    }

    @Test
    public void cowsay_GetRequest_ShouldReturnDefaultMessage() {
        // Test the GET request with default input
        ResponseEntity<String> response = restTemplate.getForEntity("/cowsay", String.class);
        assertEquals("Response status should be 200", 200, response.getStatusCodeValue());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response should match default message", Cowsay.run("I love Linux!"), response.getBody());
    }

    @Test
    public void cowsay_GetRequest_ShouldReturnCustomMessage() {
        // Test the GET request with custom input
        String customMessage = "Hello, World!";
        ResponseEntity<String> response = restTemplate.getForEntity("/cowsay?input=" + customMessage, String.class);
        assertEquals("Response status should be 200", 200, response.getStatusCodeValue());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response should match custom message", Cowsay.run(customMessage), response.getBody());
    }

    @Test
    public void cowsay_PostRequest_ShouldReturnDefaultMessage() {
        // Test the POST request with default input
        ResponseEntity<String> response = restTemplate.postForEntity("/cowsay", null, String.class);
        assertEquals("Response status should be 200", 200, response.getStatusCodeValue());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response should match default message", Cowsay.run("I love Linux!"), response.getBody());
    }

    @Test
    public void cowsay_PostRequest_ShouldReturnCustomMessage() {
        // Test the POST request with custom input
        String customMessage = "Spring Boot is awesome!";
        ResponseEntity<String> response = restTemplate.postForEntity("/cowsay?input=" + customMessage, null, String.class);
        assertEquals("Response status should be 200", 200, response.getStatusCodeValue());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response should match custom message", Cowsay.run(customMessage), response.getBody());
    }
}
