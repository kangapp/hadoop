package com.hadoop;

/**
 *自定义Mapper
 */
public interface Mapper {

    /**
     *
     * @param line 读取到的每一行数据
     * @param context 上下/缓存
     */
    public void map(String line, WordCountContext context);
}
