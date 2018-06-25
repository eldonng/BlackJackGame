package main.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import main.PlayerProfile;

/**
 * JAXB-friendly version of the main.PlayerProfile.
 */
@XmlRootElement
public class XmlSerializablePlayerProfile {

    @XmlElement
    private String creditBalance;
    @XmlElement
    private String numGamesPlayed;
    @XmlElement
    private String gamesWon;
    @XmlElement
    private String highestWin;
    @XmlElement
    private String freqBelow18;
    @XmlElement
    private String gamesWithoutBust;
    @XmlElement
    private String lowRiskStreak;
    @XmlElement
    private String lowRiskPct;

    public XmlSerializablePlayerProfile() {

    }

    public XmlSerializablePlayerProfile(PlayerProfile profile) {
        creditBalance = Integer.toString(profile.getCreditBalance());
        numGamesPlayed = Integer.toString(profile.getNumGamesPlayed());
        gamesWon = Integer.toString(profile.getGamesWon());
        highestWin = Integer.toString(profile.getHighestWin());
        freqBelow18 = Integer.toString(profile.getFreqBelow18());
        gamesWithoutBust = Integer.toString(profile.getGamesWithoutBust());
        lowRiskStreak = Integer.toString(profile.getLowRiskStreak());
        lowRiskPct = Float.toString(profile.getLowRiskPct());

    }

    public PlayerProfile toPlayerProfile() {
        final int creditBalance = Integer.parseInt(this.creditBalance);
        final int numGamesPlayed = Integer.parseInt(this.numGamesPlayed);
        final int gamesWon = Integer.parseInt(this.gamesWon);
        final int highestWin = Integer.parseInt(this.highestWin);
        final int freqBelow18 = Integer.parseInt(this.freqBelow18);
        final int gamesWithoutBust = Integer.parseInt(this.gamesWithoutBust);
        final int lowRiskStreak = Integer.parseInt(this.lowRiskStreak);
        final float lowRiskPct = Float.parseFloat(this.lowRiskPct);

        return new PlayerProfile(creditBalance, numGamesPlayed, gamesWon, highestWin, freqBelow18, gamesWithoutBust,
                lowRiskStreak, lowRiskPct);

    }

}
