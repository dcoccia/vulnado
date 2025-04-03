package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VulnadoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private LinkLister linkLister;

    @Test
    public void contextLoads() {
        // Ensures the application context loads successfully
        assertNotNull("Application context should load successfully", restTemplate);
    }

    @Test
    public void links_ShouldReturnLinks() throws Exception {
        // Mocking the LinkLister.getLinks method
        String testUrl = "http://example.com";
        List<String> mockLinks = Arrays.asList("http://example.com/link1", "http://example.com/link2");
        Mockito.when(linkLister.getLinks(testUrl)).thenReturn(mockLinks);

        // Sending a GET request to the /links endpoint
        ResponseEntity<List> response = restTemplate.getForEntity("/links?url=" + testUrl, List.class);

        // Asserting the response
        assertEquals("Response status should be OK", HttpStatus.OK, response.getStatusCode());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response body should match the mocked links", mockLinks, response.getBody());
    }

    @Test
    public void linksV2_ShouldReturnLinksV2() throws Exception {
        // Mocking the LinkLister.getLinksV2 method
        String testUrl = "http://example.com";
        List<String> mockLinks = Arrays.asList("http://example.com/link1", "http://example.com/link2");
        Mockito.when(linkLister.getLinksV2(testUrl)).thenReturn(mockLinks);

        // Sending a GET request to the /links-v2 endpoint
        ResponseEntity<List> response = restTemplate.getForEntity("/links-v2?url=" + testUrl, List.class);

        // Asserting the response
        assertEquals("Response status should be OK", HttpStatus.OK, response.getStatusCode());
        assertNotNull("Response body should not be null", response.getBody());
        assertEquals("Response body should match the mocked links", mockLinks, response.getBody());
    }

    @Test
    public void links_ShouldHandleIOException() throws Exception {
        // Mocking the LinkLister.getLinks method to throw IOException
        String testUrl = "http://example.com";
        Mockito.when(linkLister.getLinks(testUrl)).thenThrow(new IOException("Mocked IOException"));

        // Sending a GET request to the /links endpoint
        ResponseEntity<String> response = restTemplate.getForEntity("/links?url=" + testUrl, String.class);

        // Asserting the response
        assertEquals("Response status should be INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull("Response body should not be null", response.getBody());
    }

    @Test
    public void linksV2_ShouldHandleBadRequest() throws Exception {
        // Mocking the LinkLister.getLinksV2 method to throw BadRequest
        String testUrl = "http://example.com";
        Mockito.when(linkLister.getLinksV2(testUrl)).thenThrow(new BadRequest("Mocked BadRequest"));

        // Sending a GET request to the /links-v2 endpoint
        ResponseEntity<String> response = restTemplate.getForEntity("/links-v2?url=" + testUrl, String.class);

        // Asserting the response
        assertEquals("Response status should be BAD_REQUEST", HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull("Response body should not be null", response.getBody());
    }
}
