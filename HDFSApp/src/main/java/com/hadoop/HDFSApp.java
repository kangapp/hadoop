package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * wordcount项目实战
 */
public class HDFSApp {

    public static void main(String[] args) throws Exception{

        // 读取HDFS上的文件

        Properties properties = ParamsUtils.getProperties();

        Path input = new Path(properties.getProperty(Constants.INPUT_PATH));

        //获取HDFS文件系统
        FileSystem fileSystem = FileSystem.get(new URI(properties.getProperty(Constants.HDFS_URI)), new Configuration(), "liufukang");

        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(input, false);

        /**
         * 通过反射创建MAPPER对象
         */
        Class<?> clazz = Class.forName(properties.getProperty(Constants.MAPPER_CLASS));
        Mapper mapper = (Mapper)clazz.newInstance();
//        Mapper mapper = new WordCountMapper();
        WordCountContext context = new WordCountContext();

        while (iterator.hasNext()){
            LocatedFileStatus file = iterator.next();
            FSDataInputStream inputStream = fileSystem.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while((line = reader.readLine()) != null) {

                //词频统计
                mapper.map(line, context);
            }
            reader.close();
            inputStream.close();
        }

        //将结果缓存起来
        Map<Object, Object> contextMap = context.getCacheMap();

        //将结果写入到HDFS
        Path output = new Path(properties.getProperty(Constants.OUTPUT_PATH));
        FSDataOutputStream out = fileSystem.create(new Path(output, properties.getProperty(Constants.OUTPUT_FILE)));

        Set<Map.Entry<Object, Object>> entries = contextMap.entrySet();
        for(Map.Entry<Object, Object> entry : entries) {
            out.write((entry.getKey().toString() +"\t"+ entry.getValue().toString() + "\n").getBytes());
        }
        out.flush();
        out.close();
        fileSystem.close();

        System.out.println("mission complete");
    }
}
