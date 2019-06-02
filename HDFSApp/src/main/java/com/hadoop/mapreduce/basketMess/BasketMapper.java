package com.hadoop.mapreduce.basketMess;

import com.hadoop.mapreduce.basketMess.domain.MatchData;
import com.hadoop.mapreduce.basketMess.domain.MatchKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class BasketMapper extends Mapper<LongWritable, Text, MatchKey, MatchData> {

    static enum CountersEnum {INPUT_WORDS}
    private Set<String> patternToChose = new HashSet<String>();
    boolean chooseFlag = false;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        chooseFlag = conf.getBoolean("basketApp.chose.state",false);
        if(chooseFlag){
            URI[] patternURLs = Job.getInstance(conf).getCacheFiles();
            for (URI patternURL : patternURLs) {
                Path patternPath = new Path(patternURL.getPath());
                String patternFileName = patternPath.getName();
                parseChoseFile(patternFileName);
            }
        }
    }

    private void parseChoseFile(String fileName) {
        try {
            // 也可以用fileSystem读取文件
            BufferedReader fis = new BufferedReader(new FileReader(fileName));
            String pattern = null;
            while ((pattern = fis.readLine()) != null) {
                patternToChose.add(pattern);
            }
        } catch (IOException ioe) {
            System.err.println("Caught exception while parsing the cached file '"
                    + StringUtils.stringifyException(ioe));
        }
    }

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

        if(chooseFlag){
            for (String pattern : patternToChose){
                if (playerName.contains(pattern)){
                    context.write(new MatchKey(Long.parseLong(gameId), Long.parseLong(playId), playerName, matchup),
                            new MatchData(ptsType, shotResult));
                }
            }
        } else {
            context.write(new MatchKey(Long.parseLong(gameId), Long.parseLong(playId), playerName, matchup),
                    new MatchData(ptsType, shotResult));
        }

        Counter counter = context.getCounter(CountersEnum.class.getName(),CountersEnum.INPUT_WORDS.toString());
        counter.increment(1);
    }
}
