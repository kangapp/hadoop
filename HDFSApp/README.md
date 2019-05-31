# HDFS API编程

## Java API
☞  [传送门](https://github.com/kangapp/hadoop/blob/master/HDFSApp/src/test/java/com/hadoop/HDFSApp.java)
## HDFS实战
> 使用HDFS API完成文件系统上的文件的词频统计  

☞  [项目传送门](https://github.com/kangapp/hadoop/blob/master/HDFSApp/src/main/java/com/hadoop/hdfs/HDFSApp.java)

### 需求  
> 统计HDFS上的文件的wc，然后将结果输出到HDFS

### 功能拆解
- 读取HDFS上的文件  => HDFS API
- 词频统计  => Mapper(通过反射实现)
- 结果缓存  => Context
- 结果输出到HDFS  => HDFS API

---

# MapReduce编程

## WordCount实战

- hdfs版
☞  [传送门](https://github.com/kangapp/hadoop/blob/master/HDFSApp/src/test/java/com/hadoop/HDFSApp.java)
- local版
☞  [传送门](https://github.com/kangapp/hadoop/blob/master/HDFSApp/src/main/java/com/hadoop/HDFSApp.java)

## 投篮统计实战

☞  [项目传送门](https://github.com/kangapp/hadoop/blob/master/HDFSApp/src/main/java/com/hadoop/mapreduce/basketMess/BasketApp.java)  

[数据地址](https://www.kaggle.com/dansbecker/nba-shot-logs)  

### 需求（后续增加）

> 根据投篮日志信息，统计相关的数据  
> 需求一：统计的是每一个球员在每一场比赛的两分球命中数、三分球命中数、投篮数、得分

### 核心知识点

#### 命令参数
`bin/hadoop jar wc.jar WordCount2 -Dwordcount.case.sensitive=true /user/joe/wordcount/input /user/joe/wordcount/output -skip /user/joe/wordcount/patterns.txt`  

- 代码获取参数

```java
//GenericOptionsParser类可以获取命令行参数
GenericOptionsParser optionsParser = new GenericOptionsParser(configuration, args);
String[] remainingArgs = optionsParser.getRemainingArgs();
if ((remainingArgs.length !=2) && (remainingArgs.length != 4)) {
    System.err.println("Usage BaskerApp <in> <out> [ -choose chooseFile ]");
    System.exit(-1);
}
```
- -D用法  
`-Dwordcount.case.sensitive=true`
> 动态添加配置属性

```java
//一般用于map、reduce的setup方法中
conf = context.getConfiguration();
//指定默认值为true
caseSensitive = conf.getBoolean("wordcount.case.sensitive", true);
```

#### 文件读取

- 从命令参数获取

> -cacheFile/-cacheArchive 或  Job.addCacheFile(URI)/ Job.addCacheArchive(URI)

```java
//配置作业时把文件缓存起来
job.addCacheFile(new Path(remainingArgs[++i]).toUri());  


if (conf.getBoolean("wordcount.skip.patterns", false)) {
    //读取缓存文件信息
    URI[] patternsURIs = Job.getInstance(conf).getCacheFiles();
    for (URI patternsURI : patternsURIs) {
      Path patternsPath = new Path(patternsURI.getPath());
      String patternsFileName = patternsPath.getName().toString();
      parseSkipFile(patternsFileName);
    }
}

//读取文件
private void parseSkipFile(String fileName) {
  try {
    fis = new BufferedReader(new FileReader(fileName));
    String pattern = null;
    while ((pattern = fis.readLine()) != null) {
      patternsToSkip.add(pattern);
    }
  } catch (IOException ioe) {
    System.err.println("Caught exception while parsing the cached file '"
        + StringUtils.stringifyException(ioe));
  }
}
```

#### 自定义数据类型

//TODO

#### 二次排序

> 执行顺序：map --> 分区 --> 排序 --> 分组 --> reduce  

>当分区数大于1时会执行分区方法，默认是HashPartitioner,根据key的hashcode方法进行分区  
job.setPartitionerClass(MatchPartitioner.class);

>如果没有指定排序方法，会使用key对用compareTo方法  
job.setSortComparatorClass(MatchSortComparator.class);

>
