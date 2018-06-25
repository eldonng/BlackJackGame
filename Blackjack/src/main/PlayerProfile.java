package main;

public class PlayerProfile {
    private Player player;
    private int creditBalance;
    private int numGamesPlayed;
    private int gamesWon;
    private int highestWin;
    private int freqBelow18;
    private int gamesWithoutBust;
    private int lowRiskStreak;
    private float lowRiskPct;


    public PlayerProfile() {
        player = new Player();
        creditBalance = 1000;
        numGamesPlayed = 0;
        gamesWon = 0;
        highestWin = 0;
        freqBelow18 = 0;
        gamesWithoutBust = 0;
        lowRiskStreak = 0;
        lowRiskPct = 0;
    }

    public PlayerProfile(int creditBalance, int numGamesPlayed, int gamesWon, int highestWin, int freqBelow18,
                         int gamesWithoutBust, int lowRiskStreak, float lowRiskPct) {
        player = new Player();
        this.creditBalance = creditBalance;
        this.numGamesPlayed = numGamesPlayed;
        this.gamesWon = gamesWon;
        this.highestWin = highestWin;
        this.freqBelow18 = freqBelow18;
        this.gamesWithoutBust = gamesWithoutBust;
        this.lowRiskStreak = lowRiskStreak;
        this.lowRiskPct = lowRiskPct;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(int newBalance) {
        if(newBalance < 0) {
            creditBalance = 0;
        } else {
            creditBalance = newBalance;
        }
    }

    public void increaseNumGamesPlayed() {
        numGamesPlayed++;
    }

    public void increaseGamesWon() {
        gamesWon++;
    }

    public int getNumGamesPlayed() {
        return numGamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getHighestWin() {
        return highestWin;
    }

    public void setHighestWin(int betAmount) {
        highestWin = betAmount;
    }

    public int getFreqBelow18() {
        return freqBelow18;
    }

    public void increaseFreqBelow18() {
        freqBelow18++;
    }

    public int getGamesWithoutBust() {
        return gamesWithoutBust;
    }

    public void increaseGamesWithoutBust() {
        gamesWithoutBust++;
    }

    public float getLowRiskPct() {
        lowRiskPct = ((float)freqBelow18)/gamesWithoutBust * 100;
        return lowRiskPct;
    }

    public int getLowRiskStreak() {
        return lowRiskStreak;
    }

    public void increaseLowRiskStreak() {
        lowRiskStreak++;
    }

    public void resetLowRiskStreak() {
        lowRiskStreak = 0;
    }
}
