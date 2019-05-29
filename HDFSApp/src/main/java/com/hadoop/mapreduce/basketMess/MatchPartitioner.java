package com.hadoop.mapreduce.basketMess;


import com.hadoop.mapreduce.basketMess.domain.MatchData;
import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.mapreduce.Partitioner;

public class MatchPartitioner extends Partitioner<MatchKey, MatchData> {
    @Override
    public int getPartition(MatchKey matchKey, MatchData matchData, int numPartitions) {

        return (matchKey.getPlayerId().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}
