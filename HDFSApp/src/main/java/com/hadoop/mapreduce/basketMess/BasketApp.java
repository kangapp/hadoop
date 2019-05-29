package com.hadoop.mapreduce.basketMess;


import com.hadoop.domain.MatchCount;
import com.hadoop.domain.MatchData;
import com.hadoop.domain.MatchKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

public class BasketApp {

    public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();

        Job job = Job.getInstance(configuration, "basketball");
        job.setJarByClass(BasketApp.class);

        job.setMapperClass(BasketMapper.class);
        job.setMapOutputKeyClass(MatchKey.class);
        job.setMapOutputValueClass(MatchData.class);

        job.setReducerClass(BasketReducer.class);
        job.setOutputKeyClass(MatchKey.class);
        job.setOutputValueClass(MatchCount.class);

        job.setGroupingComparatorClass(MatchComparator.class);
        job.setPartitionerClass(MatchPartitioner.class);

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : -1);
    }

}
