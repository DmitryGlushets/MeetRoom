package ru.glushets.meetroom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class MeetRoomApplicationTests {

    @LocalServerPort
    protected int port;

    protected String baseUrl = "http://localhost:";

    protected RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(port + "");
    }

    @Test
    void contextLoads() {
    }

}












