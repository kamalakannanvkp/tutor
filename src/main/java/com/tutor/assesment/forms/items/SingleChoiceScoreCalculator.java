package com.tutor.assesment.forms.items;

import com.tutor.assesment.forms.ScoreCalculator;
import com.tutor.assesment.models.TutoringExperience;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleChoiceScoreCalculator implements ScoreCalculator {

    private final int[] scores = {0, 1, 2, 0};

    @Override
    public int getTotalScore(final List<String> answers) {

        return answers.stream()
                .map(TutoringExperience::valueOf)
                .map(TutoringExperience::getScore)
                .reduce(Integer::sum).orElse(0);
    }
}
