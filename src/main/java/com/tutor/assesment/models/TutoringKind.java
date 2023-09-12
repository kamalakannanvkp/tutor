package com.tutor.assesment.models;

public enum TutoringKind {

    ONLINE_TUTORING(1),
    HOME_SCHOOLING(1),
    AFTER_SCHOOL_CLUB(1),
    NONE(0);

    private final int score;

    TutoringKind(final int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
