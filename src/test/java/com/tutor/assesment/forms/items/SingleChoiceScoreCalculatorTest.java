package com.tutor.assesment.forms.items;

import com.tutor.assesment.models.TutoringExperience;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingleChoiceScoreCalculatorTest {

    private static final List<String> UPTO_1_YEAR = List.of(TutoringExperience.UPTO_1_YEAR.name());
    private static final List<String> BETWEEN_1_AND_2_YEARS = List.of(
            TutoringExperience.FROM_1_TO_2_YEARS.name()
    );

    private static final List<String> ABOVE_3_YEARS = List.of(
            TutoringExperience.ABOVE_3_YEARS.name()
    );

    private final SingleChoiceScoreCalculator subject = new SingleChoiceScoreCalculator();

    @Test
    public void getTotalScore_lessThanYear_expectScoreAs0(){
        final var score = subject.getTotalScore(UPTO_1_YEAR);
        assertEquals(0,score);
    }

    @Test
    public void getTotalScore_betweenRange_expectScoreAs2(){
        final var score = subject.getTotalScore(BETWEEN_1_AND_2_YEARS);
        assertEquals(1,score);
    }


    @Test
    public void getTotalScore_Above3_expectScoreAs3(){
        final var score = subject.getTotalScore(ABOVE_3_YEARS);
        assertEquals(2,score);
    }

    @Test
    public void getTotalScore_noExperience_expectScoreAs0(){
        final var score = subject.getTotalScore(List.of());
        assertEquals(0,score);
    }

}