# HDFS API编程

## Java API
☞  [传送门]()
## HDFS实战
> 使用HDFS API完成文件系统上的文件的词频统计  

☞  [项目传送门]()

### 需求  
> 统计HDFS上的文件的wc，然后将结果输出到HDFS

### 功能拆解
- 读取HDFS上的文件  => HDFS API
- 词频统计  => Mapper(通过反射实现)
- 结果缓存  => Context
- 结果输出到HDFS  => HDFS API

