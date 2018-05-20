import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cardDeck;

    public Deck() {
        cardDeck = newCardDeck();
    }

    public ArrayList<Card> newCardDeck() {
        ArrayList<Card> newDeck = new ArrayList<>();
        Card newCard = new Card();
        Card.Rank[] ranks = {Card.Rank.ACE, Card.Rank.TWO, Card.Rank.THREE, Card.Rank.FOUR, Card.Rank.FIVE,
                Card.Rank.SIX, Card.Rank.SEVEN, Card.Rank.EIGHT, Card.Rank.NINE, Card.Rank.TEN, Card.Rank.JACK,
                Card.Rank.QUEEN,Card.Rank. KING};

        for(int i=0; i<13; i++) {
            newCard.setCardRank(ranks[i]);
            newDeck.add(new Card(ranks[i], Card.Suit.DIAMOND));
            newDeck.add(new Card(ranks[i], Card.Suit.CLUB));
            newDeck.add(new Card(ranks[i], Card.Suit.HEART));
            newDeck.add(new Card(ranks[i], Card.Suit.SPADE));
        }

        return newDeck;
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void removeCard(Card removedCard) {
        cardDeck.remove(removedCard);
    }

    public Card drawRandomCardFromDeck() {
        int deckCards = cardDeck.size();

        Random rand = new Random();
        int drawnIndex = rand.nextInt(deckCards);

        Card chosenCard = cardDeck.get(drawnIndex);
        removeCard(chosenCard);

        return chosenCard;
    }
}