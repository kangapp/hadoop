package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;


public class MatchComparator extends WritableComparator {

    protected MatchComparator() {
        super(MatchKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MatchKey key1 = (MatchKey) a;
        MatchKey key2 = (MatchKey) b;

        String game1 = key1.getGameId();
        String game2 = key2.getPlayerId();

        String play1 = key1.getPlayerId();
        String play2 = key2.getPlayerId();

        return (game1+play1).compareTo(game2+play2);
    }
}
