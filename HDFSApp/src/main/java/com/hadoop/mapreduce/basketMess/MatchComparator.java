package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.io.WritableComparator;

public class MatchComparator extends WritableComparator {

    protected MatchComparator() {
        super(MatchKey.class, true);
    }

    @Override
    public int compare(Object a, Object b) {
        MatchKey m1 = (MatchKey) a;
        MatchKey m2 = (MatchKey) b;

        String game1 = m1.getGameId();
        String game2 = m2.getGameId();

        String player1 = m1.getPlayerId();
        String player2 = m2.getPlayerId();

        return player1.compareTo(player2);
    }
}
