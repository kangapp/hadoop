package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchData;
import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

public class BasketMapper extends Mapper<LongWritable, Text, MatchKey, MatchData> {

    static enum CountersEnum {INPUT_WORDS}
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split(",");
        String gameId = values[0];
        String playId = values[values.length - 1];
        String playerName = values[values.length - 2];
        String matchup = values[1] + values[2];
        String shotId = values[6];
        String ptsType = values[13];
        String shotResult = values[14];

        context.write(new MatchKey(gameId, playId, playerName, matchup), new MatchData(ptsType, shotResult,playerName,matchup,shotId));
        Counter counter = context.getCounter(CountersEnum.class.getName(),CountersEnum.INPUT_WORDS.toString());
        counter.increment(1);
    }
}
