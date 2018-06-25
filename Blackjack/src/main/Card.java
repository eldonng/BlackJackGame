package main;

public class Card {

    public enum Suit {
        DIAMOND, CLUB, HEART, SPADE
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    private Rank rank;
    private Suit suit;
    private boolean aceFlag;

    public Card() {
        rank = null;
        suit = null;
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getCardRank() {
        return this.rank;
    }

    public Suit getCardSuit() {
        return this.suit;
    }

    public void setCardRank(Rank rank) {
        this.rank = rank;
    }

    public void setCardSuit(Suit suit) {
        this.suit = suit;
    }

    public int getScore() {

        switch(rank) {
            case ACE: return 1;
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: case JACK: case QUEEN: case KING: return 10;
            default: return 0;
        }
    }

    public String toString() {
        return getCardRank().toString() + " " + getCardSuit().toString();
    }

    public boolean hasAce() {
        if(rank == Rank.ACE) {
            aceFlag = true;
        }
        return aceFlag;
    }
}