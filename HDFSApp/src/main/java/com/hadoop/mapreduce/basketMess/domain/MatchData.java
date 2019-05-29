package com.hadoop.mapreduce.basketMess.domain;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MatchData implements Writable {

    private String ptsType;
    private String shotResult;
    private String playerName;
    private String matchup;
    private String shotId;

    public MatchData() {
        super();
    }

    public MatchData(MatchData data) {
        this.ptsType = data.ptsType;
        this.shotResult = data.shotResult;
        this.playerName = data.playerName;
        this.matchup = data.matchup;
        this.shotId = data.shotId;
    }

    public MatchData(String ptsType, String shotResult, String playerName, String matchup, String shotId) {
        this.ptsType = ptsType;
        this.shotResult = shotResult;
        this.playerName = playerName;
        this.matchup = matchup;
        this.shotId = shotId;
    }

    public String getPtsType() {
        return ptsType;
    }

    public void setPtsType(String ptsType) {
        this.ptsType = ptsType;
    }

    public String getShotResult() {
        return shotResult;
    }

    public void setShotResult(String shotResult) {
        this.shotResult = shotResult;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMatchup() {
        return matchup;
    }

    public void setMatchup(String matchup) {
        this.matchup = matchup;
    }

    public String getShotId() {
        return shotId;
    }

    public void setShotId(String shotId) {
        this.shotId = shotId;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(ptsType);
        dataOutput.writeUTF(shotResult);
        dataOutput.writeUTF(playerName);
        dataOutput.writeUTF(matchup);
        dataOutput.writeUTF(shotId);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.ptsType = dataInput.readUTF();
        this.shotResult = dataInput.readUTF();
        this.playerName = dataInput.readUTF();
        this.matchup = dataInput.readUTF();
        this.shotId = dataInput.readUTF();
    }
}
