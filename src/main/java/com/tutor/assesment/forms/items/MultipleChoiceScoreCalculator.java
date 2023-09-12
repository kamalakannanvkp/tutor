package com.tutor.assesment.forms.items;

import com.tutor.assesment.forms.ScoreCalculator;
import com.tutor.assesment.models.TutoringKind;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleChoiceScoreCalculator implements ScoreCalculator {

    @Override
    public int getTotalScore(final List<String> answers) {

        return answers.stream()
                .map(TutoringKind::valueOf)
                .map(TutoringKind::getScore)
                .reduce(Integer::sum).orElse(0);
    }
}
