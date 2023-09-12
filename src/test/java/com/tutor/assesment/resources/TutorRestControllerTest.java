package com.tutor.assesment.resources;

import com.tutor.assesment.models.Tutor;
import com.tutor.assesment.models.requests.TutorData;
import com.tutor.assesment.services.ScoreStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class TutorRestControllerTest {


    @Mock
    private ScoreStrategy scoreStrategy;

    @InjectMocks
    private TutorRestController tutorRestController;

    @Test
    public void calculateScores() {

        final var request = TutorData.builder()
                .kindOfExperience(List.of("123"))
                .totalExperience("15")
                .tutorId("UUID")
                .build();

        final var response = Tutor.builder()
                .score(12345)
                .formData(Map.of())
                .id("UUID")
                .build();

        //Given
        Mockito.when(scoreStrategy.calculateScore(eq(request)))
                .thenReturn(response);
        //WHEN
        final var result = tutorRestController.handleTutorDataAndCalculateScores(request);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getFormData()).isEmpty();
        assertThat(result.getBody().getId()).isEqualTo("UUID");
        assertThat(result.getBody().getScore()).isEqualTo(12345);

        //Verify
        Mockito.verify(scoreStrategy).calculateScore(eq(request));
        Mockito.verifyNoMoreInteractions(scoreStrategy);
    }

}