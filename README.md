# hadoop

## 大数据概述

### 技术概念
- 数据采集 （Flume，Sqoop）
- 数据存储（Hadoop）
- 数据处理、分析、挖掘（Hadoop，Spark，Flink）
- 可视化

## 初识Hadoop

### Hadoop概述
**Hadoop官网地址：http://hadoop.apache.org/**  

#### 官方介绍  

>The Apache™ Hadoop® project develops open-source software for reliable, scalable,distributed computing.
>
>The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models. It is designed to scale up from single servers to thousands of machines, each offering local computation and storage. Rather than rely on hardware to deliver high-availability, the library itself is designed to detect and handle failures at the application layer, so delivering a highly-available service on top of a cluster of computers, each of which may be prone to failures. 

#### 模块

- Hadoop Common  
支持其他模块的公共工具
- HDFS  
提供对应用数据高吞吐量访问的分布式文件系统
- Hadoop YARN
作业调度和集群资源管理框架
- Hadoop Mapreduce  
基于YARN，用于并行处理大数据集的系统
- Others

### Hadoop Distributeed File System  

Hadoop官网介绍：http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html  

#### 官方介绍
*`distributed file system`* *`fault-tolerant`* *`commodity hardware`* *`high throughput`* *`large data sets`*  
>The Hadoop Distributed File System (HDFS) is a distributed file system designed to run on commodity hardware. It has many similarities with existing distributed file systems. However, the differences from other distributed file systems are significant. HDFS is highly fault-tolerant and is designed to be deployed on low-cost hardware. HDFS provides high throughput access to application data and is suitable for applications that have large data sets. HDFS relaxes a few POSIX requirements to enable streaming access to file system data. HDFS was originally built as infrastructure for the Apache Nutch web search engine project. HDFS is part of the Apache Hadoop Core project. The project URL is http://hadoop.apache.org/.  

