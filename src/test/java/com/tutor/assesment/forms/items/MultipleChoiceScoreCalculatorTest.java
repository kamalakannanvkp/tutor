package com.tutor.assesment.forms.items;

import com.tutor.assesment.models.TutoringKind;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipleChoiceScoreCalculatorTest {

    private static final List<String> TUTOR_EXP_ONLY_ONLINE = List.of(TutoringKind.ONLINE_TUTORING.name());
    private static final List<String> TUTOR_EXP_ONLINE_AND_HOME_SCHOOLING = List.of(
            TutoringKind.ONLINE_TUTORING.name(),
            TutoringKind.HOME_SCHOOLING.name()
    );

    private static final List<String> TUTOR_EXP_ONLINE_AND_HOME_SCHOOLING_WITH_CLUB = List.of(
            TutoringKind.ONLINE_TUTORING.name(),
            TutoringKind.HOME_SCHOOLING.name(),
            TutoringKind.AFTER_SCHOOL_CLUB.name()
    );

    private final MultipleChoiceScoreCalculator subject = new MultipleChoiceScoreCalculator();

    @Test
    public void getTotalScore_onlineOnly_expectScoreAs0() {
        final var score = subject.getTotalScore(TUTOR_EXP_ONLY_ONLINE);
        assertEquals(1, score);
    }

    @Test
    public void getTotalScore_onlineWithHomeSchooling_expectScoreAs2() {
        final var score = subject.getTotalScore(TUTOR_EXP_ONLINE_AND_HOME_SCHOOLING);
        assertEquals(2, score);
    }


    @Test
    public void getTotalScore_onlineWithHomeAndAfterSchool_expectScoreAs3() {
        final var score = subject.getTotalScore(TUTOR_EXP_ONLINE_AND_HOME_SCHOOLING_WITH_CLUB);
        assertEquals(3, score);
    }

    @Test
    public void getTotalScore_noExperience_expectScoreAs0() {
        final var score = subject.getTotalScore(List.of());
        assertEquals(0, score);
    }
}