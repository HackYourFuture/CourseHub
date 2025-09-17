package net.hackyourfuture.coursehub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is an end-to-end test for the CourseController class. We start the full application on a random port,
 * and use the HttpClient to send a request to the /courses endpoint. We verify that the response is a JSON and
 * contains courses from our '001-demo-data.sql'.
 *
 * <p>The advantage of this end-to-end test is that we test the full stack, including the web layer, service layer,
 * repository layer, and database layer.
 *
 * <p>The disadvantage is that the test is slower, it requires a running database
 * (we use docker compose for that), and we need to make sure the database is populated with known data
 * (we use '001-demo-data.sql' for that). Also, it becomes more difficult to set up different test scenarios compared to
 * the unit or integration tests.
 */
// start the full application listening on a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerEndToEndTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnJsonResponseWithDemoData() throws Exception {
        try (var httpClient = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/courses")).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.headers().firstValue("Content-Type"))
                    .hasValue("application/json");

            assertThat(response.body()).isNotNull();
            var jsonNode = objectMapper.readTree(response.body());
            assertThat(jsonNode)
                    .extracting(node -> node.get("courses"))
                    .isNotEmpty()
                    .anySatisfy(course -> {
                        assertThat(course.get("name").asText()).isEqualTo("Introduction to Calculus");
                        assertThat(course.get("description").asText()).isEqualTo("Fundamental concepts of calculus including limits, derivatives, and integrals.");
                        assertThat(course.get("instructor").asText()).isEqualTo("Alan Murray");
                    })
                    .anySatisfy(course -> {
                        assertThat(course.get("name").asText()).isEqualTo("General Physics I");
                        assertThat(course.get("description").asText()).isEqualTo("Mechanics, motion, energy, and basic physical laws.");
                        assertThat(course.get("instructor").asText()).isEqualTo("Brenda Stone");
                    });
        }
    }
}
