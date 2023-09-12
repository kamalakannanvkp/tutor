package com.tutor.assesment.services;

import com.tutor.assesment.forms.items.MultipleChoiceScoreCalculator;
import com.tutor.assesment.forms.items.SingleChoiceScoreCalculator;
import com.tutor.assesment.models.TutoringExperience;
import com.tutor.assesment.models.TutoringKind;
import com.tutor.assesment.models.requests.TutorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.tutor.assesment.services.ScoreStrategy.TUTORING_EXPERIENCE;
import static com.tutor.assesment.services.ScoreStrategy.TUTORING_KIND;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ScoreStrategyTest {

    @Mock
    private SingleChoiceScoreCalculator singleChoiceScoreCalculator;

    @Mock
    private MultipleChoiceScoreCalculator multipleChoiceScoreCalculator;

    @InjectMocks
    private ScoreStrategy scoreStrategy;


    @BeforeEach
    public void setUp() {
        Mockito.lenient().when(singleChoiceScoreCalculator.getTotalScore(Mockito.anyList())).thenCallRealMethod();
        Mockito.lenient().when(multipleChoiceScoreCalculator.getTotalScore(Mockito.anyList())).thenCallRealMethod();
    }

    @Test
    @DisplayName("Given a tutor questionnaire is passed for a tutor identified by tutor id then a score is\n" +
            "generated and returned with a record of the original submission")
    public void test_experienceBasedScoreCalculation_OnlineTutorAnd_1_to_2_years() {
        final var tutor = TutorData.builder()
                .tutorId("12345")
                .kindOfExperience(List.of(
                        TutoringKind.ONLINE_TUTORING.name()
                ))
                .totalExperience(TutoringExperience.FROM_1_TO_2_YEARS.name())
                .build();

        final var tutorScores = scoreStrategy.calculateScore(tutor);
        assertThat(tutorScores.getId()).isEqualTo("12345");
        assertThat(tutorScores.getScore()).isEqualTo(2);
        assertThat(tutorScores.getFormData().get(TUTORING_KIND)).isEqualTo(TutoringKind.ONLINE_TUTORING.name());
        assertThat(tutorScores.getFormData().get(TUTORING_EXPERIENCE)).isEqualTo(TutoringExperience.FROM_1_TO_2_YEARS.name());
    }

    @Test
    @DisplayName("Given a tutor has selected one of the options for the single choice question then only\n" +
            "the points associated with that choice are added to their score")
    public void test_experienceBasedScoreCalculation_onlySingleChoiceSelected() {
        final var tutor = TutorData.builder()
                .tutorId("12345")
                .totalExperience(TutoringExperience.FROM_1_TO_2_YEARS.name())
                .build();

        final var tutorScores = scoreStrategy.calculateScore(tutor);
        assertThat(tutorScores.getId()).isEqualTo("12345");
        assertThat(tutorScores.getScore()).isEqualTo(1);
        assertThat(tutorScores.getFormData().get(TUTORING_KIND)).isEqualTo("");
        assertThat(tutorScores.getFormData().get(TUTORING_EXPERIENCE)).isEqualTo(TutoringExperience.FROM_1_TO_2_YEARS.name());
    }

    @Test
    public void test_experienceBasedScoreCalculation_noExperience() {
        final var tutor = TutorData.builder()
                .tutorId("someRandomId")
                .kindOfExperience(List.of(
                        TutoringKind.NONE.name()
                ))
                .totalExperience(TutoringExperience.NONE.name())
                .build();

        final var tutorScores = scoreStrategy.calculateScore(tutor);
        assertThat(tutorScores.getId()).isEqualTo("someRandomId");
        assertThat(tutorScores.getScore()).isEqualTo(0);
        assertThat(tutorScores.getFormData().get(TUTORING_KIND)).isEqualTo(TutoringKind.NONE.name());
        assertThat(tutorScores.getFormData().get(TUTORING_EXPERIENCE)).isEqualTo(TutoringExperience.NONE.name());

    }

    @Test
    @DisplayName("Given a tutor has selected multiple options in the multiple choice question then the\n" +
            "sum of points for each choice are added to their score")
    public void test_experienceBasedScoreCalculation_multipleKindsOfExperienceOnly() {
        final var tutor = TutorData.builder()
                .tutorId("noId")
                .kindOfExperience(List.of(
                        TutoringKind.ONLINE_TUTORING.name(),
                        TutoringKind.AFTER_SCHOOL_CLUB.name(),
                        TutoringKind.HOME_SCHOOLING.name()
                ))
                .build();

        final var tutorScores = scoreStrategy.calculateScore(tutor);
        assertThat(tutorScores.getId()).isEqualTo("noId");
        assertThat(tutorScores.getScore()).isEqualTo(3);
        assertThat(tutorScores.getFormData().get(TUTORING_KIND)).isEqualTo(String.join(",", tutor.getKindOfExperience()));
        assertThat(tutorScores.getFormData().get(TUTORING_EXPERIENCE)).isEqualTo(TutoringExperience.NONE.name());

    }

    @Test
    @DisplayName("Given a tutor has answered both questions then their score is the sum of their points\n" +
            "for both questions")
    public void test_experienceBasedScoreCalculation_multipleKindsOfExperience_AndAbove3YearsInField() {
        final var tutor = TutorData.builder()
                .tutorId("noId")
                .kindOfExperience(List.of(
                        TutoringKind.ONLINE_TUTORING.name(),
                        TutoringKind.AFTER_SCHOOL_CLUB.name(),
                        TutoringKind.HOME_SCHOOLING.name()
                ))
                .totalExperience(TutoringExperience.ABOVE_3_YEARS.name())
                .build();

        final var tutorScores = scoreStrategy.calculateScore(tutor);
        assertThat(tutorScores.getId()).isEqualTo("noId");
        assertThat(tutorScores.getScore()).isEqualTo(5);
        assertThat(tutorScores.getFormData().get(TUTORING_KIND)).isEqualTo(String.join(",", tutor.getKindOfExperience()));
        assertThat(tutorScores.getFormData().get(TUTORING_EXPERIENCE)).isEqualTo(TutoringExperience.ABOVE_3_YEARS.name());

    }

}