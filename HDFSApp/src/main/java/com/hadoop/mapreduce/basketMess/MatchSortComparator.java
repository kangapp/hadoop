package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;


public class MatchSortComparator extends WritableComparator {

    protected MatchSortComparator() {
        super(MatchKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MatchKey key1 = (MatchKey) a;
        MatchKey key2 = (MatchKey) b;

        long game1 = key1.getGameId();
        long game2 = key2.getGameId();

        long play1 = key1.getPlayerId();
        long play2 = key2.getPlayerId();

        int ret = play1 == play2 ? 0 : (play1 > play2 ? 1 :-1);
        if (ret == 0) {
            ret = game1 == game2 ? 0 : (game1 > game2 ? 1 : -1);
        }
        return ret;
    }
}
