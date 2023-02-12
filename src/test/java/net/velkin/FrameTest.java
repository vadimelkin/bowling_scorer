package net.velkin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {
    private final Frame frame = new Frame();

    @AfterEach
    void tearDown() {
        frame.reset();
    }

    @Test
    public void testNoRollsState() {
        assertFalse(frame.isRollingComplete());
        assertFalse(frame.isBonusComplete());
        assertFalse(frame.isComplete());
        assertFalse(frame.isOpen());
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());

        assertEquals(-1, frame.getCurrentRollIndex());
        assertEquals(-1, frame.getCurrentBonusIndex());
        assertEquals(0, frame.getRollsScore());
        assertEquals(0, frame.getBonusScore());
        assertEquals(0, frame.getScore());

        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(-1)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(0)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(-1)).getMessage().contains("Invalid bonus index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(0)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testOneRollState() throws UserError {
        frame.addRoll(1);

        assertFalse(frame.isRollingComplete());
        assertFalse(frame.isBonusComplete());
        assertFalse(frame.isComplete());
        assertFalse(frame.isOpen());
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());

        assertEquals(0, frame.getCurrentRollIndex());
        assertEquals(-1, frame.getCurrentBonusIndex());
        assertEquals(1, frame.getRollsScore());
        assertEquals(0, frame.getBonusScore());
        assertEquals(1, frame.getScore());

        assertEquals(1, frame.getRoll(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(1)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(-1)).getMessage().contains("Invalid bonus index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(0)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testOpenFrameState() throws UserError {
        frame.addRoll(1);
        frame.addRoll(2);

        assertTrue(frame.isRollingComplete());
        assertTrue(frame.isBonusComplete());
        assertTrue(frame.isComplete());
        assertTrue(frame.isOpen());
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());

        assertEquals(1, frame.getCurrentRollIndex());
        assertEquals(-1, frame.getCurrentBonusIndex());
        assertEquals(3, frame.getRollsScore());
        assertEquals(0, frame.getBonusScore());
        assertEquals(3, frame.getScore());

        assertEquals(2, frame.getRoll(1));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(2)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(-1)).getMessage().contains("Invalid bonus index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(0)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testSpareFrameState() throws UserError {
        frame.addRoll(1);
        frame.addRoll(9);

        assertTrue(frame.isRollingComplete());
        assertFalse(frame.isBonusComplete());
        assertFalse(frame.isComplete());
        assertFalse(frame.isOpen());
        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());

        assertEquals(1, frame.getCurrentRollIndex());
        assertEquals(-1, frame.getCurrentBonusIndex());
        assertEquals(10, frame.getRollsScore());
        assertEquals(0, frame.getBonusScore());
        assertEquals(10, frame.getScore());

        assertEquals(9, frame.getRoll(1));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(2)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(-1)).getMessage().contains("Invalid bonus index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(0)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testSpareWithOneBonusFrameState() throws UserError {
        frame.addRoll(1);
        frame.addRoll(9);
        frame.addBonus(5);

        assertTrue(frame.isRollingComplete());
        assertTrue(frame.isBonusComplete());
        assertTrue(frame.isComplete());
        assertFalse(frame.isOpen());
        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());

        assertEquals(1, frame.getCurrentRollIndex());
        assertEquals(0, frame.getCurrentBonusIndex());
        assertEquals(10, frame.getRollsScore());
        assertEquals(5, frame.getBonusScore());
        assertEquals(15, frame.getScore());

        assertEquals(9, frame.getRoll(1));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(2)).getMessage().contains("Invalid roll index"));
        assertEquals(5, frame.getBonus(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(1)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testStrikeFrameState() throws UserError {
        frame.addRoll(10);

        assertTrue(frame.isRollingComplete());
        assertFalse(frame.isBonusComplete());
        assertFalse(frame.isComplete());
        assertFalse(frame.isOpen());
        assertFalse(frame.isSpare());
        assertTrue(frame.isStrike());

        assertEquals(0, frame.getCurrentRollIndex());
        assertEquals(-1, frame.getCurrentBonusIndex());
        assertEquals(10, frame.getRollsScore());
        assertEquals(0, frame.getBonusScore());
        assertEquals(10, frame.getScore());

        assertEquals(10, frame.getRoll(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(1)).getMessage().contains("Invalid roll index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(-1)).getMessage().contains("Invalid bonus index"));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(0)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testStrikeWithOneBonusFrameState() throws UserError {
        frame.addRoll(10);
        frame.addBonus(3);

        assertTrue(frame.isRollingComplete());
        assertFalse(frame.isBonusComplete());
        assertFalse(frame.isComplete());
        assertFalse(frame.isOpen());
        assertFalse(frame.isSpare());
        assertTrue(frame.isStrike());

        assertEquals(0, frame.getCurrentRollIndex());
        assertEquals(0, frame.getCurrentBonusIndex());
        assertEquals(10, frame.getRollsScore());
        assertEquals(3, frame.getBonusScore());
        assertEquals(13, frame.getScore());

        assertEquals(10, frame.getRoll(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(1)).getMessage().contains("Invalid roll index"));
        assertEquals(3, frame.getBonus(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(1)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testStrikeWithTwoBonusesFrameState() throws UserError {
        frame.addRoll(10);
        frame.addBonus(3);
        frame.addBonus(9);

        assertTrue(frame.isRollingComplete());
        assertTrue(frame.isBonusComplete());
        assertTrue(frame.isComplete());
        assertFalse(frame.isOpen());
        assertFalse(frame.isSpare());
        assertTrue(frame.isStrike());

        assertEquals(0, frame.getCurrentRollIndex());
        assertEquals(1, frame.getCurrentBonusIndex());
        assertEquals(10, frame.getRollsScore());
        assertEquals(12, frame.getBonusScore());
        assertEquals(22, frame.getScore());

        assertEquals(10, frame.getRoll(0));
        assertTrue(assertThrows(Bug.class, () -> frame.getRoll(1)).getMessage().contains("Invalid roll index"));
        assertEquals(9, frame.getBonus(1));
        assertTrue(assertThrows(Bug.class, () -> frame.getBonus(2)).getMessage().contains("Invalid bonus index"));
    }

    @Test
    public void testExceedPinsInOneRoll() {
        Exception ex = assertThrows(UserError.class, () -> {
            frame.addRoll(11);
        });
        assertTrue(ex.getMessage().contains("Invalid knocked pins count"));
    }

    @Test
    public void testExceedPinsInBothRolls() {
        Exception ex = assertThrows(UserError.class, () -> {
            frame.addRoll(6);
            frame.addRoll(8);
        });
        assertTrue(ex.getMessage().contains("Too many knocked pins for one frame"));
    }

    @Test
    public void testNoMoreRollsForOpenFrame() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(2);
            frame.addRoll(3);
            frame.addRoll(1);
        });
        assertTrue(ex.getMessage().contains("Frame is already complete"));
    }

    @Test
    public void testNoMoreRollsForSpareFrame() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(3);
            frame.addRoll(7);
            frame.addRoll(1);
        });
        assertTrue(ex.getMessage().contains("Frame is already complete"));
    }

    @Test
    public void testNoMoreRollsForStrikeFrame() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(10);
            frame.addRoll(3);
        });
        assertTrue(ex.getMessage().contains("Frame is already complete"));
    }

    @Test
    public void testNotFullyRolledFrameDisallowsBonus() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(2);
            frame.addBonus(3);
        });
        assertTrue(ex.getMessage().contains("Frame must be complete to add bonus"));
    }

    @Test
    public void testOpenFrameDisallowsBonus() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(2);
            frame.addRoll(3);
            frame.addBonus(1);
        });
        assertTrue(ex.getMessage().contains("Open frame cannot have bonus"));
    }

    @Test
    public void testSpareFrameDisallowsSecondBonus() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(2);
            frame.addRoll(8);
            frame.addBonus(1);
            frame.addBonus(1);
        });
        assertTrue(ex.getMessage().contains("Frame bonus is already set"));
    }

    @Test
    public void testStrikeFrameDisallowsThirdBonus() {
        Exception ex = assertThrows(Bug.class, () -> {
            frame.addRoll(10);
            frame.addBonus(1);
            frame.addBonus(1);
            frame.addBonus(1);
        });
        assertTrue(ex.getMessage().contains("Frame bonus is already set"));
    }

    @Test
    public void testExceedPinsInFirstBonus() {
        Exception ex = assertThrows(UserError.class, () -> {
            frame.addRoll(2);
            frame.addRoll(8);
            frame.addBonus(11);
        });
        assertTrue(ex.getMessage().contains("Invalid knocked pins count"));
    }

    @Test
    public void testExceedPinsInSecondBonus() {
        Exception ex = assertThrows(UserError.class, () -> {
            frame.addRoll(10);
            frame.addBonus(10);
            frame.addBonus(11);
        });
        assertTrue(ex.getMessage().contains("Invalid knocked pins count"));
    }
}