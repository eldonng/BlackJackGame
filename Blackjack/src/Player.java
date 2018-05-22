import java.util.ArrayList;

public class Player {
    private ArrayList<Card> playerCards;
    private int handStrength;
    private PlayerProfile profile;

    public Player() {
        playerCards = new ArrayList<>();
    }

    public void addToPlayerDeck(Card playerCard) {
        playerCards.add(playerCard);
    }

    public void displayPlayerHand() {
        for(int i=0; i<playerCards.size(); i++) {
            System.out.println(playerCards.get(i).toString());
        }
        System.out.println("Total Points: " + getHandStrength());
        System.out.println();
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public int getHandStrength() {
        boolean hasAce = false;
        handStrength = 0;
        for (int i = 0; i < playerCards.size(); i++) {
            handStrength += playerCards.get(i).getScore();
            if (playerCards.get(i).hasAce()) {
                hasAce = true;
            }
        }

        if (hasAce && handStrength <= 11 && playerCards.size() == 2) {
            return handStrength + 10;
        } else if (hasAce && handStrength <= 12 && playerCards.size() == 3) {
            return handStrength + 9;
        } else {
            return handStrength;
        }
    }

    public void resetGame() {
        playerCards = new ArrayList<>();
        handStrength = 0;
    }

    public PlayerProfile getProfile() {
        return profile;
    }
}