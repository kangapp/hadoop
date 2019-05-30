package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchCount;
import com.hadoop.mapreduce.basketMess.domain.MatchData;
import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasketReducer extends Reducer<MatchKey, MatchData, Text, MatchCount> {

    static enum CountersEnum {OUTPUT_WORDS}
    @Override
    protected void reduce(MatchKey key, Iterable<MatchData> values, Context context) throws IOException, InterruptedException {

        int shotNum = 0;
        int score = 0;
        int shot_two = 0;
        int shot_three = 0;
        for(MatchData item : values) {
            String ptsType = item.getPtsType();
            String shotResult = item.getShotResult();
            shotNum ++;
            if ("made".equals(shotResult)) {
                score += Integer.parseInt(ptsType);
                if(ptsType.equals("2")){
                    shot_two ++;
                }
                else {
                    shot_three ++;
                }
            }
            Counter counter = context.getCounter(CountersEnum.class.getName(),CountersEnum.OUTPUT_WORDS.toString());
            counter.increment(1);
        }
        context.write(new Text(key.toString()), new MatchCount(shot_two, shot_three, shotNum, score));
    }
}
