package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

public class HDFSApp {

    public static final String HDFS_PATH = "hdfs://localhost:9000";
    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "liufukang");
    }

    /**
     * 创建目录
     * @throws Exception
     */
    @Test
    public void mkdir() throws Exception{
        boolean result = fileSystem.mkdirs(new Path("/test"));
        System.out.println(result);
    }

    /**
     * 查看HDFS文件内容
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        FSDataInputStream in = fileSystem.open(new Path("/test/test.txt"));
        IOUtils.copyBytes(in, System.out, 1024);
    }

    /**
     * 创建文件并写入内容
     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        FSDataOutputStream out = fileSystem.create(new Path("/test/a.txt"));
        out.writeUTF("WRITING......");
        out.flush();
        out.close();
    }

    @After
    public void tearDown() {
        configuration = null;
        fileSystem = null;
    }
}
