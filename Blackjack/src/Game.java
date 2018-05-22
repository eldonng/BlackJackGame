import java.util.Scanner;

public class Game {
    private int numGamesPlayed;
    private int gamesWon;
    private int multiplier;

    private Player player = new Player();
    private Player computer = new Player();
    private Deck deck;
    private boolean playerWinFlag;
    private boolean computerWinFlag;
    private String message;

    public static int MINIMUM_BET = 20;

    private static String BET_PROMPT_MESSAGE ="Please enter the amount you want to bet: ";
    private static String NEGATIVE_BET = "The amount entered cannot be a negative number. Please try again.";
    private static String UNDER_BET = "The minimum amount for betting is %1$s. Please try again.";
    private static String OVER_BET = "The amount entered cannot more than your balance. Please try again.";
    private static String VALID_BET = "Amount Bet: %1$s, Player Credit Balance: %2$s";
    private static String PLAYER_BALANCE = "Player's Current Balance: %1$s";
    private static String INSUFFICIENT_BALANCE = "You do not have enough balance to play. Minimum Bet: %1$s";

    private static String WINNING_MESSAGE = "Player Wins, Wins Bet.";
    private static String WINNING_DOUBLE_MESSAGE = "Player Wins, Wins Double Bet.";
    private static String WINNING_TRIPLE_MESSAGE = "Player Wins, Wins Triple Bet.";

    private static String LOSING_MESSAGE = "Player Loses, Loses Bet.";
    private static String LOSING_DOUBLE_MESSAGE = "Player Loses, Loses Double Bet.";
    private static String LOSING_TRIPLE_MESSAGE = "Player Loses, Loses Triple Bet.";

    private static String DRAW_GAME_MESSAGE = "Draw Game. Player keeps bet.";

    private static String DRAW_CARD_PROMPT = "Do you want to draw a card? (Y/N)";

    private static final int LOSE_TRIPLE = -3;
    private static final int LOSE_DOUBLE = -2;
    private static final int LOSE_SINGLE = -1;
    private static final int DRAW = 0;
    private static final int WIN_SINGLE = 1;
    private static final int WIN_DOUBLE = 2;
    private static final int WIN_TRIPLE = 3;


    public Game() {
        numGamesPlayed = 0;
        gamesWon = 0;
        multiplier = 0;
    }

    public void newGame() {

        Card drawnCard;
        int betAmount;

        deck = new Deck();
        player.resetGame();
        computer.resetGame();
        multiplier = 0;
        computerWinFlag = false;
        playerWinFlag = false;
        message = "";
        numGamesPlayed++;

        if(player.getCreditBalance() < MINIMUM_BET) {
            System.out.println(String.format(INSUFFICIENT_BALANCE, MINIMUM_BET));
            System.out.println(String.format(PLAYER_BALANCE, player.getCreditBalance()));
            return;
        } else {
            betAmount = playerBet();
        }

        for (int i = 0; i < 2; i++) {
            drawnCard = deck.drawRandomCardFromDeck();
            player.addToPlayerDeck(drawnCard);
            drawnCard = deck.drawRandomCardFromDeck();
            computer.addToPlayerDeck(drawnCard);
        }

        player.displayPlayerHand();

        checkWinningHand();

        if(!playerWinFlag && !computerWinFlag) {
            playersTurn();
            computersTurn();
        }

        if(!playerWinFlag && !computerWinFlag) {
            checkWinner();
        }

        displayAllHands();
        setMessage(getMultiplier());
        showMessage();
        System.out.println();

        if(playerWinFlag) {
            gamesWon++;
        }

        player.setCreditBalance(player.getCreditBalance() + betAmount + getMultiplier()*betAmount);

        System.out.println(String.format(PLAYER_BALANCE, player.getCreditBalance()));
        System.out.println();
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
                playerWinFlag = true;
                setMultiplier(WIN_TRIPLE);
            } else if(player.getHandStrength() < 21) {
                playerWinFlag = true;
                setMultiplier(WIN_DOUBLE);
            } else {
                computerWinFlag = true;
                setMultiplier(LOSE_DOUBLE);
            }
        }
    }

    private void computersTurn() {
        while (computer.getHandStrength() < 16 && computer.getPlayerCards().size() < 5) {
            drawCard(computer);
        }

        if(computer.getPlayerCards().size() == 5) {
            if(computer.getHandStrength() == 21 || computer.getHandStrength() < 11) {
                computerWinFlag = true;
                setMultiplier(LOSE_TRIPLE);
            } else if (computer.getHandStrength() < 21) {
                computerWinFlag = true;
                setMultiplier(LOSE_DOUBLE);
            } else {
                playerWinFlag = true;
                setMultiplier(WIN_DOUBLE);
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
            setMultiplier(WIN_TRIPLE);
        } else if (!checkTwoAces(player) && checkTwoAces(computer)) {
            computerWinFlag = true;
            setMultiplier(LOSE_TRIPLE);
        } else if (checkTwoAces(player) && checkTwoAces(computer)) {
           setMultiplier(DRAW);
        } else if (checkBlackJack(player) && !checkBlackJack(computer)) {
            playerWinFlag = true;
            setMultiplier(WIN_DOUBLE);
        } else if (!checkBlackJack(player) && checkBlackJack(computer)) {
            computerWinFlag = true;
            setMultiplier(LOSE_DOUBLE);
        } else if (checkBlackJack(player) && checkBlackJack(computer)) {
            setMultiplier(DRAW);
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
            setMultiplier(LOSE_DOUBLE);
        } else if (player.getHandStrength() == 21 && computer.getHandStrength() != 21) {
            playerWinFlag = true;
            setMultiplier(WIN_DOUBLE);
        } else if (player.getHandStrength() > 21 && computer.getHandStrength() < 21) {
            computerWinFlag = true;
            setMultiplier(LOSE_SINGLE);
        } else if (player.getHandStrength() < 21 && computer.getHandStrength() > 21) {
            playerWinFlag = true;
            setMultiplier(WIN_SINGLE);
        } else if (player.getHandStrength() > 21 && computer.getHandStrength() > 21) {
            setMultiplier(DRAW);
        } else if (player.getHandStrength() < computer.getHandStrength()) {
            computerWinFlag = true;
            setMultiplier(LOSE_SINGLE);
        } else if (player.getHandStrength() > computer.getHandStrength()) {
            playerWinFlag = true;
            setMultiplier(WIN_SINGLE);
        } else if (player.getHandStrength() == computer.getHandStrength()) {
            setMultiplier(DRAW);
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
        String input;
        int betAmount = 0;

        do {
            System.out.println(String.format(PLAYER_BALANCE, player.getCreditBalance()));
            System.out.println(BET_PROMPT_MESSAGE);
            Scanner sc = new Scanner(System.in);
            input = sc.next();

            try {
                betAmount = Integer.parseInt(input);

                if (betAmount < 0) {
                    System.out.println(NEGATIVE_BET);
                } else if (betAmount > player.getCreditBalance()) {
                    System.out.println(OVER_BET);
                } else if (betAmount < MINIMUM_BET) {
                    System.out.println(String.format(UNDER_BET, MINIMUM_BET));
                } else {
                    validBet = true;
                    player.setCreditBalance(player.getCreditBalance() - betAmount);
                    System.out.println(String.format(VALID_BET, betAmount, player.getCreditBalance()));
                }
             } catch (NumberFormatException nfe) {
                System.out.println(Blackjack.INVALID_COMMAND);
            }
        } while (!validBet);

        return betAmount;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMessage(int multiplier) {
        switch(multiplier) {
            case LOSE_TRIPLE: message = LOSING_TRIPLE_MESSAGE; break;
            case LOSE_DOUBLE: message = LOSING_DOUBLE_MESSAGE; break;
            case LOSE_SINGLE: message = LOSING_MESSAGE; break;
            case DRAW: message = DRAW_GAME_MESSAGE; break;
            case WIN_SINGLE: message = WINNING_MESSAGE; break;
            case WIN_DOUBLE: message = WINNING_DOUBLE_MESSAGE; break;
            case WIN_TRIPLE: message = WINNING_TRIPLE_MESSAGE; break;
            default: message = ""; //to-do: exception handling
        }
    }

    public void showMessage() {
        System.out.println(message);
    }
}