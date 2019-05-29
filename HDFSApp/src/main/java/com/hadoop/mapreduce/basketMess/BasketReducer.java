package com.hadoop;

import com.hadoop.domain.MatchCount;
import com.hadoop.domain.MatchData;
import com.hadoop.domain.MatchKey;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class BasketReducer extends Reducer<MatchKey, MatchData, MatchKey, MatchCount> {

    @Override
    protected void reduce(MatchKey key, Iterable<MatchData> values, Context context) throws IOException, InterruptedException {

        int shotNum = 0;
        int score = 0;

        for(MatchData item : values) {
            String ptsType = item.getPtsType();
            String shotResult = item.getShotResult();
            shotNum ++;
            if ("made".equals(shotResult)) {
                score += Integer.parseInt(ptsType);
            }
        }
        context.write(key, new MatchCount(shotNum, score));
    }
}
