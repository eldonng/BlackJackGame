import com.sun.javafx.binding.StringFormatter;

import java.util.Scanner;

public class Blackjack {

    public static String INVALID_COMMAND = "Invalid Command, please try again.";

    private static String PROMPT_PLAYER_MESSAGE = "Do you want to continue playing? (Y/N)";
    private static String EXIT_MESSAGE = "Thank you for playing! Games Played: %1$s, Games Won: %2$s";

    public static void main(String args[]) {
        boolean playGame = true;
        Game game = new Game();
        while(playGame) {
            game.newGame();
            playGame = promptReplay();
        }

        exitGame(game);
    }

    private static boolean promptReplay() {

        String input;

        do {
            System.out.println(PROMPT_PLAYER_MESSAGE);
            Scanner sc = new Scanner(System.in);
            input = sc.next();
            input = input.toUpperCase();
            if (!(input.equals("Y") || input.equals("N"))) {
                System.out.println(INVALID_COMMAND);
            }
        } while (!(input.equals("Y") || input.equals("N")));

        return input.equals("Y");
    }

    private static void exitGame(Game game) {
        System.out.println(String.format(EXIT_MESSAGE, game.getNumGamesPlayed(), game.getGamesWon()));
    }
}