import java.util.Scanner;

public class Game {
    private int numGamesPlayed;
    private int gamesWon;

    private Player player = new Player();
    private Player computer = new Player();
    private Deck deck;
    private boolean playerWinFlag;
    private boolean computerWinFlag;
    private String message;

    private static String BET_PROMPT_MESSAGE ="Please enter the amount you want to bet: ";
    private static String INVALID_BET = "The amount entered cannot more than your balance, or be a negative number." +
            "Please try again.";
    private static String VALID_BET = "Amount Betted: %1$s, Player Credit Balance: %2$s";
    private static String PLAYER_BALANCE = "Player's Current Balance: %1$s";

    private static String WINNING_MESSAGE = "Player Wins, Wins Bet.";
    private static String WINNING_DOUBLE_MESSAGE = "Player Wins, Wins Double Bet.";
    private static String WINNING_TRIPLE_MESSAGE = "Player Wins, Wins Triple Bet.";

    private static String LOSING_MESSAGE = "Player Loses, Loses Bet.";
    private static String LOSING_DOUBLE_MESSAGE = "Player Loses, Loses Double Bet.";
    private static String LOSING_TRIPLE_MESSAGE = "Player Loses, Loses Triple Bet.";

    private static String DRAW_GAME_MESSAGE = "Draw Game. Player keeps bet.";

    private static String DRAW_CARD_PROMPT = "Do you want to draw a card? (Y/N)";

    public Game() {
        numGamesPlayed = 0;
        gamesWon = 0;
    }

    public void newGame() {

        Card drawnCard;
        int betAmount;

        deck = new Deck();
        player.resetGame();
        computer.resetGame();
        computerWinFlag = false;
        playerWinFlag = false;
        message = "";
        numGamesPlayed++;


        betAmount = playerBet();

        for (int i = 0; i < 2; i++) {
            drawnCard = deck.drawRandomCardFromDeck();
            player.addToPlayerDeck(drawnCard);
            drawnCard = deck.drawRandomCardFromDeck();
            computer.addToPlayerDeck(drawnCard);
        }

        player.displayPlayerHand();

        if (checkWinningHand()) {
            displayAllHands();
            System.out.println(message);
            if(playerWinFlag) {
                gamesWon++;
            }
            return;
        }

        playersTurn();
        if(playerWinFlag || computerWinFlag) {
            displayAllHands();
            System.out.println(message);
            if(playerWinFlag) {
                gamesWon++;
            }
            return;
        }

        computersTurn();

        if(playerWinFlag || computerWinFlag) {
            displayAllHands();
            System.out.println(message);
            if(playerWinFlag) {
                gamesWon++;
            }
            return;
        }

        checkWinner();
        displayAllHands();
        System.out.println(message);
        System.out.println();

        if(playerWinFlag) {
            gamesWon++;
        }
    }



    private boolean askPlayerToDraw() {
        String input;
        do {
            System.out.println(DRAW_CARD_PROMPT);
            Scanner sc = new Scanner(System.in);
            input = sc.next();
            input = input.toUpperCase();

            if (!(input.equals("N") || input.equals("Y"))) {
                System.out.println(Blackjack.INVALID_COMMAND);
            }

        } while (!(input.equals("N") || input.equals("Y")));

        return input.equals("Y");
    }

    private void drawCard(Player player) {
        player.addToPlayerDeck(deck.drawRandomCardFromDeck());
        player.displayPlayerHand();
    }

    private void playersTurn() {
        boolean playerPrompt = true;

        while(playerPrompt && player.getPlayerCards().size() < 5 && player.getHandStrength() < 21) {
            if(askPlayerToDraw()) {
                drawCard(player);
            } else {
                playerPrompt = false;
            }
        }

        if(player.getPlayerCards().size() == 5) {
            if(player.getHandStrength() <= 11 || player.getHandStrength() == 21) {
                message = WINNING_TRIPLE_MESSAGE;
                playerWinFlag = true;
            } else if(player.getHandStrength() < 21) {
                message = WINNING_DOUBLE_MESSAGE;
                playerWinFlag = true;
            } else {
                message = LOSING_DOUBLE_MESSAGE;
                computerWinFlag = true;
            }
        }
    }

    private void computersTurn() {
        while (computer.getHandStrength() < 16 && computer.getPlayerCards().size() < 5) {
            drawCard(computer);
        }

        if(computer.getPlayerCards().size() == 5) {
            if(computer.getHandStrength() == 21 || computer.getHandStrength() < 11) {
                message = LOSING_TRIPLE_MESSAGE;
                computerWinFlag = true;
            } else if (computer.getHandStrength() < 21) {
                message = LOSING_DOUBLE_MESSAGE;
                computerWinFlag = true;
            } else {
                message = WINNING_DOUBLE_MESSAGE;
                playerWinFlag = true;
            }
        }

        if(computer.getHandStrength() >= 16 && computer.getHandStrength() <= 18
                && computer.getPlayerCards().size() < 4) {
            if (deck.getCardDeck().size() == 48) {
                drawCard(computer);
            }
        }

    }

    private boolean checkWinningHand() {
        if(checkTwoAces(player) && !checkTwoAces(computer)) {
            playerWinFlag = true;
            message = WINNING_TRIPLE_MESSAGE;
        } else if (!checkTwoAces(player) && checkTwoAces(computer)) {
            computerWinFlag = true;
            message = LOSING_TRIPLE_MESSAGE;
        } else if (checkTwoAces(player) && checkTwoAces(computer)) {
            message = DRAW_GAME_MESSAGE;
        } else if (checkBlackJack(player) && !checkBlackJack(computer)) {
            playerWinFlag = true;
            message = WINNING_DOUBLE_MESSAGE;
        } else if (!checkBlackJack(player) && checkBlackJack(computer)) {
            computerWinFlag = true;
            message = LOSING_DOUBLE_MESSAGE;
        } else if (checkBlackJack(player) && checkBlackJack(computer)) {
            message = DRAW_GAME_MESSAGE;
        }

        return checkBlackJack(player) || checkBlackJack(computer)|| checkTwoAces(player) || checkTwoAces(computer);
    }

    private boolean checkBlackJack(Player player) {
        return (player.getHandStrength() == 21);
    }

    private boolean checkTwoAces(Player player) {
        return (player.getPlayerCards().get(0).hasAce() && player.getPlayerCards().get(1).hasAce());
    }

    private void displayAllHands() {
        System.out.println("DEALER'S HAND");
        computer.displayPlayerHand();
        System.out.println();
        System.out.println("PLAYER'S HAND");
        player.displayPlayerHand();
        System.out.println();
    }

    private void checkWinner() {
        if (player.getHandStrength() != 21 && computer.getHandStrength() == 21) {
            computerWinFlag = true;
            message = LOSING_DOUBLE_MESSAGE;
        } else if (player.getHandStrength() == 21 && computer.getHandStrength() != 21) {
            playerWinFlag = true;
            message = WINNING_DOUBLE_MESSAGE;
        } else if (player.getHandStrength() > 21 && computer.getHandStrength() <= 21) {
            computerWinFlag = true;
            message = LOSING_MESSAGE;
        } else if (player.getHandStrength() <= 21 && computer.getHandStrength() > 21) {
            playerWinFlag = true;
            message = WINNING_MESSAGE;
        } else if (player.getHandStrength() > 21 && computer.getHandStrength() > 21) {
            message = DRAW_GAME_MESSAGE;
        } else if (player.getHandStrength() < computer.getHandStrength()) {
            computerWinFlag = true;
            message = LOSING_MESSAGE;
        } else if (player.getHandStrength() > computer.getHandStrength()) {
            playerWinFlag = true;
            message = WINNING_MESSAGE;
        } else if (player.getHandStrength() == computer.getHandStrength()) {
            message = DRAW_GAME_MESSAGE;
        }
    }

    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    private int playerBet() {
        boolean validBet = false;
        int betAmount;

        do {
            System.out.println(String.format(PLAYER_BALANCE, player.getCreditBalance()));
            System.out.println(BET_PROMPT_MESSAGE);
            Scanner sc = new Scanner(System.in);
            betAmount = sc.nextInt();

            if (betAmount < 0 || betAmount > player.getCreditBalance()) {
                System.out.println(INVALID_BET);
            } else {
                validBet = true;
                player.setCreditBalance(player.getCreditBalance() - betAmount);
                System.out.println(String.format(VALID_BET, betAmount, player.getCreditBalance()));
            }
        } while (!validBet);

        return betAmount;
    }
}