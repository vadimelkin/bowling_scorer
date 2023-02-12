package net.velkin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static net.velkin.Game.FRAMES_COUNT;
import static net.velkin.TestGames.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private final Game game = new Game();

    @AfterEach
    void tearDown() {
        game.reset();
    }

    private void playAndAssertGame(TestData testData) throws UserError {
        assertEquals(testData.isComplete, playGame(testData, game));

        int gameScore = 0;
        int framesCount = Math.min(testData.rolls.length, FRAMES_COUNT);
        for (int i = 0; i < framesCount; i++) {
            int expectedFrameScore = 0;
            Frame frame = game.getFrame(i);

            if (testData.rolls[i][0] >= 0) {
                assertEquals(testData.rolls[i][0], frame.getRoll(0));
                expectedFrameScore += testData.rolls[i][0];

                if (testData.rolls[i][1] >= 0) {
                    assertEquals(testData.rolls[i][1], frame.getRoll(1));
                    expectedFrameScore += testData.rolls[i][1];
                } else
                    assertEquals(0, frame.getCurrentRollIndex());

                if (testData.rolls[i][2] >= 0) {
                    assertEquals(testData.rolls[i][2], frame.getBonusScore());
                    expectedFrameScore += testData.rolls[i][2];
                } else
                    assertEquals(-1, frame.getCurrentBonusIndex());
            } else
                assertEquals(-1, frame.getCurrentRollIndex());

            int frameScore = frame.getScore();
            assertEquals(expectedFrameScore, frameScore);
            gameScore += frameScore;
        }
        assertEquals(gameScore, game.getScore());
    }

    @Test
    public void testUnrolledFirstFrameGame() throws UserError {
        playAndAssertGame(UNROLLED_FIRST_FRAME);
    }

    @Test
    public void testOneRollFirstFrameGame() throws UserError {
        playAndAssertGame(ONE_ROLL_FIRST_FRAME);
    }

    @Test
    public void testOpenFirstFrameGame() throws UserError {
        playAndAssertGame(OPEN_FIRST_FRAME);
    }

    @Test
    public void testSpareFirstFrameGame() throws UserError {
        playAndAssertGame(SPARE_FIRST_FRAME);
    }

    @Test
    public void testSpareRollBonusFirstFrameGame() throws UserError {
        playAndAssertGame(SPARE_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testSpareStrikeBonusFirstFrameGame() throws UserError {
        playAndAssertGame(SPARE_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_FIRST_FRAME);
    }

    @Test
    public void testStrikeRollBonusFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeStrikeBonusFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeTwoRollBonusFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_TWO_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeSpareBonusFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_SPARE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeTwoStrikeBonusFirstFrameGame() throws UserError {
        playAndAssertGame(STRIKE_TWO_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testUnrolledLastFrameGame() throws UserError {
        playAndAssertGame(UNROLLED_LAST_FRAME);
    }

    @Test
    public void testOneRollLastFrameGame() throws UserError {
        playAndAssertGame(ONE_ROLL_LAST_FRAME);
    }

    @Test
    public void testOpenLastFrameGame() throws UserError {
        playAndAssertGame(OPEN_LAST_FRAME);
    }

    @Test
    public void testSpareLastFrameGame() throws UserError {
        playAndAssertGame(SPARE_LAST_FRAME);
    }

    @Test
    public void testSpareRollBonusLastFrameGame() throws UserError {
        playAndAssertGame(SPARE_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testSpareStrikeBonusLastFrameGame() throws UserError {
        playAndAssertGame(SPARE_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_LAST_FRAME);
    }

    @Test
    public void testStrikeRollBonusLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeStrikeBonusLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeTwoRollBonusLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_TWO_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeSpareBonusLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_SPARE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeTwoStrikeBonusLastFrameGame() throws UserError {
        playAndAssertGame(STRIKE_TWO_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testMiserableGame() throws UserError {
        playAndAssertGame(MISERABLE);
    }

    @Test
    public void testRandomGame() throws UserError {
        playAndAssertGame(RANDOM);
    }

    @Test
    public void testAllSparesGame() throws UserError {
        playAndAssertGame(ALL_SPARES);
    }

    @Test
    public void testRockingGame() throws UserError {
        playAndAssertGame(ROCKING);
    }
}