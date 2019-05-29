package com.hadoop.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * KEYIN Map任务读数据的key类型，offset，每行数据的起始位置的偏移量，Long
 * VALUEIN Map任务读数据的value类型，每一行字符串，String
 *
 * KEYOUT map方法自定义实现输出的key的类型，String
 * VALUEOUT map方法自定义实现输出的value的类型，Integer
 *
 * Hadoop自定义类型：序列化和反序列化
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //把value对应的行数据按照指定的分隔符拆开
        String[] words = value.toString().split(" ");

        for(String word : words) {
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
