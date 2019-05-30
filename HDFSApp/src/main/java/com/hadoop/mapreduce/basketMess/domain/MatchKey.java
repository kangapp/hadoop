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

    private long gameId;
    private long playerId;
    private String playerName;
    private String matchup;

    public MatchKey() {
        super();
    }

    public MatchKey(long gameId, long playerId, String playerName, String matchup) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.playerName = playerName;
        this.matchup = matchup;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
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
        return "<"+playerName+"\t"+matchup+"\t"+">";
    }

    @Override
    public int compareTo(MatchKey o) {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.gameId) * 11 + Long.hashCode(this.playerId);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(gameId);
        dataOutput.writeLong(playerId);
        dataOutput.writeUTF(playerName);
        dataOutput.writeUTF(matchup);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.gameId = dataInput.readLong();
        this.playerId = dataInput.readLong();
        this.playerName = dataInput.readUTF();
        this.matchup = dataInput.readUTF();
    }
}
