package com.hadoop;

import com.hadoop.domain.MatchData;
import com.hadoop.domain.MatchKey;
import org.apache.hadoop.mapreduce.Partitioner;

public class MatchPartitioner extends Partitioner<MatchKey, MatchData> {
    @Override
    public int getPartition(MatchKey matchKey, MatchData matchData, int numPartitions) {

        return (matchKey.getGameId().hashCode() & Integer.MAX_VALUE) % numPartitions;
    }
}
