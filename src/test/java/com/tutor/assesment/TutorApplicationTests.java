package com.tutor.assesment;

import com.tutor.assesment.models.Tutor;
import com.tutor.assesment.models.TutoringExperience;
import com.tutor.assesment.models.TutoringKind;
import com.tutor.assesment.models.requests.TutorData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration Test suites
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TutorApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int localServerPort;

    @Test
    void setUpDoneRight() {
        assertThat(localServerPort).isGreaterThan(0);
    }

    @DisplayName("success path :  all scores calculated for given tutor")
    @Test
    void testCalculateEndpoint_expectValidResponseWhenRightInputAreGiven() {
        final var requestPayload = TutorData.builder()
                .tutorId("12345")
                .kindOfExperience(List.of(TutoringKind.AFTER_SCHOOL_CLUB.name()))
                .totalExperience(TutoringExperience.ABOVE_3_YEARS.name())
                .build();

        final var result = this.restTemplate.postForObject(
                "http://localhost:" + localServerPort + "/tutor/v1/calculate",
                requestPayload, Tutor.class);
        assertThat(result).isNotNull();
        System.out.println("result " + result);
        assertThat(result.getId()).isEqualTo(requestPayload.getTutorId());
        assertThat(result.getScore()).isEqualTo(3);
        assertThat(result.getFormData()).isNotEmpty();
    }

    @DisplayName("failure path :  when a wrong enum experience is passed from UI")
    @Test
    void testCalculateEndpoint_expectException_WhenBasketContainsInvalidProduct() {
        final var requestPayload = TutorData.builder()
                .tutorId("12345")
                .kindOfExperience(List.of("wrong_value"))
                .totalExperience("1000 years")
                .build();

        final var result = this.restTemplate.exchange(
                "http://localhost:" + localServerPort + "/tutor/v1/calculate",
                HttpMethod.POST,
                new HttpEntity<>(requestPayload),
                String.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode().is4xxClientError()).isTrue();
        assertThat(result.getStatusCodeValue()).isEqualTo(400);
        assertThat(result.getBody()).contains("No enum constant");
    }
}
