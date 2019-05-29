package com.hadoop.hdfs;

/**
 * 词频统计实现类
 */
public class WordCountMapper implements Mapper {
    @Override
    public void map(String line, WordCountContext context) {
        String[] words = line .split(" ");

        for (String word : words) {
            Object value  = context.get(word);
            if(value == null) {
                context.write(word, 1);
            } else {
                int temp = Integer.parseInt(value.toString());
                context.write(word, temp+1);
            }
        }
    }
}
