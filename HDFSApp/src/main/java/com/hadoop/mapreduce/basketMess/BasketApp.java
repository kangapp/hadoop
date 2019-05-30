package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchCount;
import com.hadoop.mapreduce.basketMess.domain.MatchData;
import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.net.URI;

public class BasketApp {

    public static void main(String[] args) throws Exception{

        System.setProperty("HADOOP_USER_NAME", "liufukang");

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://localhost:9000");

        GenericOptionsParser optionsParser = new GenericOptionsParser(configuration, args);
        String[] remainingArgs = optionsParser.getRemainingArgs();
        if ((remainingArgs.length !=2) && (remainingArgs.length != 4)) {
            System.err.println("Usage BaskerApp <in> <out> [ -choose chooseFile ]");
            System.exit(-1);
        }

        Job job = Job.getInstance(configuration, "basketball");
        job.setJarByClass(BasketApp.class);

        job.setGroupingComparatorClass(MatchComparator.class);
        job.setSortComparatorClass(MatchSortComparator.class);
        job.setPartitionerClass(MatchPartitioner.class);

        job.setMapperClass(BasketMapper.class);
        job.setMapOutputKeyClass(MatchKey.class);
        job.setMapOutputValueClass(MatchData.class);

        job.setReducerClass(BasketReducer.class);
        job.setOutputKeyClass(MatchKey.class);
        job.setOutputValueClass(MatchCount.class);

        job.setNumReduceTasks(2);

        for (int i=0; i<remainingArgs.length; i++) {
            if("-choose".equals(remainingArgs[i])){
                job.addCacheFile(new Path(remainingArgs[++i]).toUri());
                job.getConfiguration().setBoolean("basketApp.chose.state", true);
                break;
            }
        }

        FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "liufukang");
        Path outputPath = new Path("/basketball/output");
        if(fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
        }
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : -1);
    }

}
