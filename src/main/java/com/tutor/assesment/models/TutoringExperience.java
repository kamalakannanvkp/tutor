package com.tutor.assesment.models;

public enum TutoringExperience {

    UPTO_1_YEAR(0),
    FROM_1_TO_2_YEARS(1),
    ABOVE_3_YEARS(2),
    NONE(0);

    private final int score;

    TutoringExperience(final int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
