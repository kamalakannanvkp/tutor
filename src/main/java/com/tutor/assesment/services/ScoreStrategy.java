package com.tutor.assesment.services;

import com.tutor.assesment.forms.items.MultipleChoiceScoreCalculator;
import com.tutor.assesment.forms.items.SingleChoiceScoreCalculator;
import com.tutor.assesment.models.Tutor;
import com.tutor.assesment.models.TutoringExperience;
import com.tutor.assesment.models.requests.TutorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScoreStrategy {

    public static final String TUTORING_KIND = "tutoringKind";
    public static final String TUTORING_EXPERIENCE = "tutoringExperience";
    private static final String COMMA_AS_DELIMITER = ",";
    private final MultipleChoiceScoreCalculator multipleChoiceScoreCalculator;
    private final SingleChoiceScoreCalculator singleChoiceScoreCalculator;

    public Tutor calculateScore(final TutorData tutorData) {

        final List<String> kindOfExperiencesReceived = tutorData.getKindOfExperience();
        final String totalExperiencesReceived = Objects.requireNonNullElse(tutorData.getTotalExperience(), TutoringExperience.NONE.name());

        final int scoresForGivenKind = multipleChoiceScoreCalculator.getTotalScore(kindOfExperiencesReceived);
        final int scoresForTotalExperience = singleChoiceScoreCalculator.getTotalScore(List.of(totalExperiencesReceived));

        return Tutor.builder()
                .id(tutorData.getTutorId())
                .score(Integer.sum(scoresForGivenKind, scoresForTotalExperience))
                .formData(Map.of(
                        TUTORING_KIND, CollectionUtils.isEmpty(kindOfExperiencesReceived) ? "" :String.join(COMMA_AS_DELIMITER, tutorData.getKindOfExperience()),
                        TUTORING_EXPERIENCE, totalExperiencesReceived
                ))
                .build();
    }
}
