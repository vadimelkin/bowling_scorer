package net.velkin;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BowlingScorer {
    private static final String INTRO_INFO = "Let's bowl! Type number of knocked pins for each roll.";
    private static final String PROMPT_FORMAT = "Frame #%d, Roll #%d: ";
    private static final String QUIT_COMMAND = "quit";
    private static final String EXIT_COMMAND = "exit";
    private static final String DONE_INFO = "Bye!";
    private static final String GAME_OVER_INFO = "Game over!";
    private static final String BAD_INPUT_ERR = "I did not get it! Try a number of knocked pins, or \"" + EXIT_COMMAND + "\" or \"" + QUIT_COMMAND + "\".";
    private static final String BUG_ERR = "Looks like we messed up. Please report this error:";

    private final Game game;
    private final TextFormatter textFormatter;

    public static void main(String[] args) {
        BowlingScorer bowlingScorer = new BowlingScorer();
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        bowlingScorer.run(System.out, scanner);
    }

    BowlingScorer() {
        game = new Game();
        textFormatter = new TextFormatter();
    }

    void run(PrintStream printStream, Scanner scanner) {
        game.reset();
        try {
            printStream.println(INTRO_INFO);
            while (true) {
                try {
                    int currentFrameNum = game.getCurrentFrameIndex() + 1;
                    int nextRollNum = game.getFrame(game.getCurrentFrameIndex()).getCurrentRollIndex() + 2;
                    printStream.printf(PROMPT_FORMAT, currentFrameNum, nextRollNum);
                    if (!scanner.hasNextInt()) {
                        String command = scanner.next();
                        if (command.equalsIgnoreCase(QUIT_COMMAND) || command.equalsIgnoreCase(EXIT_COMMAND)) {
                            printStream.println(DONE_INFO);
                            break;
                        }
                        throw new UserError(BAD_INPUT_ERR);
                    }
                    int knockedPins = scanner.nextInt();
                    boolean gameComplete = game.addRoll(knockedPins);
                    printStream.println();
                    printStream.println(textFormatter.format(game));
                    if (gameComplete) {
                        printStream.println(GAME_OVER_INFO);
                        break;
                    }
                } catch (UserError userError) {
                    printStream.println(userError.getMessage());
                }
            }
        } catch (Exception exception) {
            printStream.println();
            printStream.println(BUG_ERR);
            exception.printStackTrace(printStream);
        }
    }
}