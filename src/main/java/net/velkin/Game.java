package net.velkin;

public class Game {
    static final int FRAMES_COUNT = 10;

    // For all frames except the last one we need to potentially apply bonus to up to two frames prior,
    // but for the last frame it may need to be applied to itself, so all together three frames, including
    // itself, need to be checked.
    private static final int FRAMES_TO_CHECK_FOR_OWED_BONUS_COUNT = 3;

    private final Frame[] frames;
    private int currentFrameIndex;

    Game() {
        frames = new Frame[FRAMES_COUNT];
        for (int i = 0; i < FRAMES_COUNT; i++)
            frames[i] = new Frame();
        reset();
    }

    // Returns TRUE when game is complete
    boolean addRoll(int knockedPins) throws UserError {
        // Attribute the roll to the frames that are not bonus complete yet
        for (int i = 0; i < FRAMES_TO_CHECK_FOR_OWED_BONUS_COUNT; i++)
            addBonus(currentFrameIndex - i, knockedPins);

        // Attribute the roll to the current frame if rolling is not complete
        Frame currentFrame = frames[currentFrameIndex];
        if (!currentFrame.isRollingComplete() && !currentFrame.addRoll(knockedPins))
            return false;

        // Current frame has all the rolls it needs. See if there are more frames to play.
        if (currentFrameIndex < FRAMES_COUNT - 1) {
            ++currentFrameIndex;
            return false;
        }

        // We are here only if all the frames are complete but the last one still needs bonus(es)
        Frame lastFrame = frames[FRAMES_COUNT - 1];
        return lastFrame.isComplete();
    }

    private void addBonus(int frameIndex, int knockedPins) throws UserError {
        if (frameIndex < 0)
            return;

        Frame frame = frames[frameIndex];
        if (!frame.isRollingComplete() || frame.isBonusComplete())
            return;

        frame.addBonus(knockedPins);
    }

    int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    Frame getFrame(int frameIndex) {
        if (frameIndex < 0 || frameIndex > currentFrameIndex)
            throw new Bug("Invalid frame index");
        return frames[frameIndex];
    }

    int getScore() {
        int score = 0;
        for (Frame frame : frames)
            score += frame.getScore();
        return score;
    }

    void reset() {
        for (Frame frame : frames)
            frame.reset();
        currentFrameIndex = 0;
    }
}
