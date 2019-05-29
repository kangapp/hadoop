package com.hadoop.mapreduce.basketMess;

import com.hadoop.domain.MatchKey;
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

        return game1.compareTo(game2);
    }
}
