package com.hadoop.mapreduce.basketMess.domain;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 作为map输出的key，GAME_ID, PLAYER_ID
 *
 */
public class MatchKey implements WritableComparable<MatchKey> {

    private String gameId;
    private String playerId;
    private String playerName;
    private String matchup;

    public MatchKey() {
        super();
    }

    public MatchKey(String gameId, String playerId, String playerName, String matchup) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.playerName = playerName;
        this.matchup = matchup;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    @Override
    public String toString() {
        return "<"+playerName+"\t"+matchup+">";
    }

    @Override
    public int compareTo(MatchKey o) {
        return (getGameId()+getPlayerId()).compareTo(o.getGameId()+o.getPlayerId());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(gameId);
        dataOutput.writeUTF(playerId);
        dataOutput.writeUTF(playerName);
        dataOutput.writeUTF(matchup);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.gameId = dataInput.readUTF();
        this.playerId = dataInput.readUTF();
        this.playerName = dataInput.readUTF();
        this.matchup = dataInput.readUTF();
    }
}
