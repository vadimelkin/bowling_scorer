package net.velkin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static net.velkin.TestGames.*;
import static org.junit.jupiter.api.Assertions.*;

class TextFormatterTest {
    private final Game game = new Game();
    private final TextFormatter tf = new TextFormatter();

    @AfterEach
    void tearDown() {
        game.reset();
    }

    private void playAndFormatGame(TestData testData) throws UserError {
        playGame(testData, game);
        assertEquals(testData.output, tf.format(game));
    }

    @Test
    public void testUnrolledFirstFrameGame() throws UserError {
        playAndFormatGame(UNROLLED_FIRST_FRAME);
    }

    @Test
    public void testOneRollFirstFrameGame() throws UserError {
        playAndFormatGame(ONE_ROLL_FIRST_FRAME);
    }

    @Test
    public void testOpenFirstFrameGame() throws UserError {
        playAndFormatGame(OPEN_FIRST_FRAME);
    }

    @Test
    public void testSpareFirstFrameGame() throws UserError {
        playAndFormatGame(SPARE_FIRST_FRAME);
    }

    @Test
    public void testSpareRollBonusFirstFrameGame() throws UserError {
        playAndFormatGame(SPARE_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testSpareStrikeBonusFirstFrameGame() throws UserError {
        playAndFormatGame(SPARE_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_FIRST_FRAME);
    }

    @Test
    public void testStrikeRollBonusFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeStrikeBonusFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeTwoRollBonusFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_TWO_ROLL_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeSpareBonusFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_SPARE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testStrikeTwoStrikeBonusFirstFrameGame() throws UserError {
        playAndFormatGame(STRIKE_TWO_STRIKE_BONUS_FIRST_FRAME);
    }

    @Test
    public void testUnrolledLastFrameGame() throws UserError {
        playAndFormatGame(UNROLLED_LAST_FRAME);
    }

    @Test
    public void testOneRollLastFrameGame() throws UserError {
        playAndFormatGame(ONE_ROLL_LAST_FRAME);
    }

    @Test
    public void testOpenLastFrameGame() throws UserError {
        playAndFormatGame(OPEN_LAST_FRAME);
    }

    @Test
    public void testSpareLastFrameGame() throws UserError {
        playAndFormatGame(SPARE_LAST_FRAME);
    }

    @Test
    public void testSpareRollBonusLastFrameGame() throws UserError {
        playAndFormatGame(SPARE_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testSpareStrikeBonusLastFrameGame() throws UserError {
        playAndFormatGame(SPARE_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_LAST_FRAME);
    }

    @Test
    public void testStrikeRollBonusLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeStrikeBonusLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeTwoRollBonusLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_TWO_ROLL_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeSpareBonusLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_SPARE_BONUS_LAST_FRAME);
    }

    @Test
    public void testStrikeTwoStrikeBonusLastFrameGame() throws UserError {
        playAndFormatGame(STRIKE_TWO_STRIKE_BONUS_LAST_FRAME);
    }

    @Test
    public void testMiserableGame() throws UserError {
        playAndFormatGame(MISERABLE);
    }

    @Test
    public void testRandomGame() throws UserError {
        playAndFormatGame(RANDOM);
    }

    @Test
    public void testAllSparesGame() throws UserError {
        playAndFormatGame(ALL_SPARES);
    }

    @Test
    public void testRockingGame() throws UserError {
        playAndFormatGame(ROCKING);
    }
}