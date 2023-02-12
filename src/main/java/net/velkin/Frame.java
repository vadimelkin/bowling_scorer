package net.velkin;

class Frame {
    static final int PINS_COUNT = 10;

    private static final int ROLLS_COUNT = 2;
    private static final int BONUSES_COUNT = 2;
    private static final int INIT_INDEX = -1;

    private static final String BAD_ROLL_ERR = "Invalid knocked pins count";
    private static final String BAD_ROLLS_SUM_ERR = "Too many knocked pins for one frame";

    private static final String FRAME_COMPLETE_BUG = "Frame is already complete";
    private static final String FRAME_INCOMPLETE_BUG = "Frame must be complete to add bonus";
    private static final String NO_OPEN_FRAME_BONUS_BUG = "Open frame cannot have bonus";
    private static final String FRAME_BONUS_SET_BUG = "Frame bonus is already set";
    private static final String BAD_ROLL_INDEX_BUG = "Invalid roll index";
    private static final String BAD_BONUS_INDEX_BUG = "Invalid bonus index";

    private final int[] rolls;
    private final int[] bonuses;
    private int lastCompletedRollIndex;
    private int lastAddedBonusIndex;

    Frame() {
        rolls = new int[ROLLS_COUNT];
        bonuses = new int[BONUSES_COUNT];
        reset();
    }

    // Returns TRUE when all allowed rolls completed
    boolean addRoll(int knockedPins) throws UserError {
        if (knockedPins < 0 || knockedPins > PINS_COUNT)
            throw new UserError(BAD_ROLL_ERR);
        if (isRollingComplete())
            throw new Bug(FRAME_COMPLETE_BUG);
        if (getRollsScore() + knockedPins > PINS_COUNT)
            throw new UserError(BAD_ROLLS_SUM_ERR);
        rolls[++lastCompletedRollIndex] = knockedPins;
        return isRollingComplete();
    }

    void addBonus(int knockedPins) throws UserError {
        if (knockedPins < 0 || knockedPins > PINS_COUNT)
            throw new UserError(BAD_ROLL_ERR);
        if (!isRollingComplete())
            throw new Bug(FRAME_INCOMPLETE_BUG);
        if (isBonusComplete())
            throw new Bug(lastAddedBonusIndex == -1 ? NO_OPEN_FRAME_BONUS_BUG : FRAME_BONUS_SET_BUG);
        bonuses[++lastAddedBonusIndex] = knockedPins;
    }

    private boolean isOnlyFirstRollComplete() {
        return lastCompletedRollIndex == 0;
    }

    private boolean isSecondRollComplete() {
        return lastCompletedRollIndex == 1;
    }

    private boolean isOnlyFirstBonusAdded() {
        return lastAddedBonusIndex == 0;
    }

    private boolean isSecondBonusAdded() {
        return lastAddedBonusIndex == 1;
    }

    boolean isOpen() {
        return isSecondRollComplete() && getRollsScore() < PINS_COUNT;
    }

    boolean isSpare() {
        return isSecondRollComplete() && getRollsScore() == PINS_COUNT;
    }

    boolean isStrike() {
        return isOnlyFirstRollComplete() && rolls[0] == PINS_COUNT;
    }

    boolean isRollingComplete() {
        return isStrike() || isSecondRollComplete();
    }

    boolean isBonusComplete() {
        return isOpen() || (isSpare() && isOnlyFirstBonusAdded()) || (isStrike() && isSecondBonusAdded());
    }

    boolean isComplete() {
        return isRollingComplete() && isBonusComplete();
    }

    int getCurrentRollIndex() {
        return lastCompletedRollIndex;
    }

    int getRoll(int rollIndex) {
        if (rollIndex < 0 || rollIndex > lastCompletedRollIndex)
            throw new Bug(BAD_ROLL_INDEX_BUG);
        return rolls[rollIndex];
    }

    int getCurrentBonusIndex() {
        return lastAddedBonusIndex;
    }

    int getBonus(int bonusIndex) {
        if (bonusIndex < 0 || bonusIndex > lastAddedBonusIndex)
            throw new Bug(BAD_BONUS_INDEX_BUG);
        return bonuses[bonusIndex];
    }

    int getRollsScore() {
        return rolls[0] + rolls[1];
    }

    int getBonusScore() {
        return bonuses[0] + bonuses[1];
    }

    int getScore() {
        return getRollsScore() + getBonusScore();
    }

    void reset() {
        rolls[0] = rolls[1] = 0;
        bonuses[0] = bonuses[1] = 0;
        lastCompletedRollIndex = INIT_INDEX;
        lastAddedBonusIndex = INIT_INDEX;
    }
}
