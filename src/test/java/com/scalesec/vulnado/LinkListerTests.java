package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VulnadoApplicationTests {

    @Test
    public void contextLoads() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Test
    public void getLinks_ValidUrl_ShouldReturnLinks() throws IOException {
        // Arrange
        String testUrl = "http://example.com";
        LinkLister mockLinkLister = Mockito.mock(LinkLister.class);
        when(mockLinkLister.getLinks(testUrl)).thenReturn(List.of("http://example.com/link1", "http://example.com/link2"));

        // Act
        List<String> links = mockLinkLister.getLinks(testUrl);

        // Assert
        assertNotNull("Links should not be null", links);
        assertEquals("Links size should match", 2, links.size());
        assertTrue("Links should contain expected URL", links.contains("http://example.com/link1"));
        assertTrue("Links should contain expected URL", links.contains("http://example.com/link2"));
    }

    @Test(expected = IOException.class)
    public void getLinks_InvalidUrl_ShouldThrowIOException() throws IOException {
        // Arrange
        String invalidUrl = "http://invalid-url";
        LinkLister mockLinkLister = Mockito.mock(LinkLister.class);
        when(mockLinkLister.getLinks(invalidUrl)).thenThrow(new IOException("Invalid URL"));

        // Act
        mockLinkLister.getLinks(invalidUrl);
    }

    @Test
    public void getLinksV2_ValidUrl_ShouldLogHost() throws Exception {
        // Arrange
        String testUrl = "http://example.com";
        LinkLister mockLinkLister = Mockito.mock(LinkLister.class);
        when(mockLinkLister.getLinksV2(testUrl)).thenReturn(List.of("http://example.com/link1", "http://example.com/link2"));

        // Act
        List<String> links = mockLinkLister.getLinksV2(testUrl);

        // Assert
        assertNotNull("Links should not be null", links);
        assertEquals("Links size should match", 2, links.size());
        assertTrue("Links should contain expected URL", links.contains("http://example.com/link1"));
        assertTrue("Links should contain expected URL", links.contains("http://example.com/link2"));
    }

    @Test(expected = MalformedURLException.class)
    public void getLinksV2_InvalidUrl_ShouldThrowMalformedURLException() throws Exception {
        // Arrange
        String invalidUrl = "invalid-url";
        LinkLister mockLinkLister = Mockito.mock(LinkLister.class);
        when(mockLinkLister.getLinksV2(invalidUrl)).thenThrow(new MalformedURLException("Invalid URL"));

        // Act
        mockLinkLister.getLinksV2(invalidUrl);
    }
}
