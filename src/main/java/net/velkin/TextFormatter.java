package net.velkin;

import static net.velkin.Frame.PINS_COUNT;
import static net.velkin.Game.FRAMES_COUNT;

public class TextFormatter {
    private static final String TOP_BORDER =    "┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐\n";
    private static final String MIDDLE_BORDER = "│   └───┤   └───┤   └───┤   └───┤   └───┤   └───┤   └───┤   └───┤   └───┼───┴───┴───┤\n";
    private static final String BOTTOM_BORDER = "└───────┴───────┴───────┴───────┴───────┴───────┴───────┴───────┴───────┴───────────┘\n";

    String format(Game game) {
        StringBuilder sb = new StringBuilder();
        sb.append(TOP_BORDER);

        for (int frameIndex = 0; frameIndex < FRAMES_COUNT - 1; frameIndex++)
            appendRegularFrameFirstLine(frameIndex <= game.getCurrentFrameIndex() ? game.getFrame(frameIndex) : null, sb);
        appendLastFrameFirstLine(game.getCurrentFrameIndex() == FRAMES_COUNT - 1 ? game.getFrame(FRAMES_COUNT - 1) : null, sb);

        sb.append(MIDDLE_BORDER);

        int score = 0;
        for (int frameIndex = 0; frameIndex < FRAMES_COUNT - 1; frameIndex++)
            score = appendRegularFrameSecondLine(score, frameIndex <= game.getCurrentFrameIndex() ? game.getFrame(frameIndex) : null, sb);
        appendLastFrameSecondLine(score, game.getCurrentFrameIndex() == FRAMES_COUNT - 1 ? game.getFrame(FRAMES_COUNT - 1) : null, sb);

        sb.append(BOTTOM_BORDER);
        return sb.toString();
    }

    private void appendRegularFrameFirstLine(Frame frame, StringBuilder sb) {
        sb.append("│ ");
        if (frame != null) {
            if (frame.isStrike()) {
                sb.append("  │ ╳");
            } else if (frame.isSpare()) {
                appendScore(frame.getRoll(0), sb);
                sb.append(" │ ╱");
            } else {
                appendOpenFrameFirstLine(frame, sb);
            }
        } else {
            sb.append("  │  ");
        }
        sb.append(" ");
    }

    private void appendLastFrameFirstLine(Frame frame, StringBuilder sb) {
        sb.append("│ ");
        if (frame != null) {
            if (frame.isStrike()) {
                sb.append("╳ │ ");
                appendBonus(frame, 0, sb);
                sb.append(" │ ");
                if (frame.getCurrentBonusIndex() >= 0 && frame.getBonus(0) != PINS_COUNT && frame.getBonusScore() == PINS_COUNT)
                    sb.append("╱");
                else
                    appendBonus(frame, 1, sb);
            } else if (frame.isSpare()) {
                appendScore(frame.getRoll(0), sb);
                sb.append(" │ ╱ │ ");
                appendBonus(frame, 0, sb);
            } else {
                appendOpenFrameFirstLine(frame, sb);
                sb.append(" │  ");
            }
        } else {
            sb.append("  │   │  ");
        }
        sb.append(" │\n");
    }

    private int appendRegularFrameSecondLine(int accumulatedScore, Frame frame, StringBuilder sb) {
        sb.append("│ ");
        if (frame != null && frame.isComplete() && frame.getScore() > 0) {
            accumulatedScore += frame.getScore();
            sb.append(String.format(" %3d  ", accumulatedScore));
        } else {
            sb.append("      ");
        }
        return accumulatedScore;
    }

    private void appendLastFrameSecondLine(int accumulatedScore, Frame frame, StringBuilder sb) {
        sb.append("│ ");
        if (frame != null && frame.isComplete() && frame.getScore() > 0) {
            accumulatedScore += frame.getScore();
            sb.append(String.format("   %3d   ", accumulatedScore));
        } else {
            sb.append("         ");
        }
        sb.append(" │\n");
    }

    private void appendOpenFrameFirstLine(Frame frame, StringBuilder sb) {
        if (frame.getCurrentRollIndex() >= 0)
            appendScore(frame.getRoll(0), sb);
        else
            sb.append(" ");
        sb.append(" │ ");
        if (frame.getCurrentRollIndex() == 1)
            appendScore(frame.getRoll(1), sb);
        else
            sb.append(" ");
    }

    private void appendBonus(Frame frame, int bonusIndex, StringBuilder sb) {
        if (bonusIndex > frame.getCurrentBonusIndex())
            sb.append(" ");
        else
            appendScore(frame.getBonus(bonusIndex), sb);
    }

    private void appendScore(int score, StringBuilder sb) {
        if (score == PINS_COUNT)
            sb.append("╳");
        else if (score == 0)
            sb.append("─");
        else
            sb.append(score);
    }
}
