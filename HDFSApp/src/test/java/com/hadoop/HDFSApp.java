package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

public class HDFSApp {

    public static final String HDFS_PATH = "hdfs://localhost:9000";
    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws Exception {
        //加载默认的配置文件，副本系数默认是3
        configuration = new Configuration();
        configuration.set("dfs.replication", "1");
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
        out.writeUTF("WRITING......,dfsreplication");
        out.flush();
        out.close();
    }

    /**
     * 重命名文件
     * @throws Exception
     */
    @Test
    public void rename() throws Exception {
        Path oldPath = new Path("/test/a.txt");
        Path newPath = new Path("/test/b.txt");
        fileSystem.rename(oldPath, newPath);
    }

    /**
     * 拷贝本地文件到HDFS文件系统
     * @throws Exception
     */
    @Test
    public void copyFromLocalFile() throws Exception {
        Path src = new Path("/Users/liufukang/Documents/test.txt");
        Path dst = new Path("/test/");
        fileSystem.copyFromLocalFile(src, dst);
    }

    /**
     * 拷贝大文件到HDFS文件系统：带进度
     * @throws Exception
     */
    @Test
    public void copyFromLocalBigFile() throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream(new File("/Users/liufukang/resources/hadoop-2.6.0-cdh5.7.0.tar.gz")));
        Path dst = new Path("/test/hadoop.tar.gz");
        FSDataOutputStream out = fileSystem.create(dst, new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096);
    }

    /**
     * 拷贝HDFS文件到本地：下载
     * @throws Exception
     */
    @Test
    public void copyToLocalFile() throws Exception {
        Path dst = new Path("/Users/liufukang/");
        Path src = new Path("/test/test.txt");
        fileSystem.copyToLocalFile(src, dst);
    }

    /**
     * 查看目标文件夹下的所有文件
     * @throws Exception
     */
    @Test
    public void listFiles() throws Exception {
        FileStatus[] statuses = fileSystem.listStatus(new Path("/"));
        for(FileStatus file : statuses) {
            String isDir = file.isDirectory()?"文件夹":"文件";
            String permission = file.getPermission().toString();
            short replication = file.getReplication();
            long length = file.getLen();
            String path = file.getPath().toString();
            System.out.println(isDir + "\t "+ permission +
                    "\t" + replication + "\t" + length +
                    "\t" + path);
        }
    }

    /**
     * 递归查看目标文件夹下的所有文件
     * @throws Exception
     */
    @Test
    public void listFilesRecursive() throws Exception {
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/"), true);
        while(files.hasNext()){
            LocatedFileStatus file = files.next();
            String isDir = file.isDirectory()?"文件夹":"文件";
            String permission = file.getPermission().toString();
            short replication = file.getReplication();
            long length = file.getLen();
            String path = file.getPath().toString();
            System.out.println(isDir + "\t "+ permission +
                    "\t" + replication + "\t" + length +
                    "\t" + path);
        }
    }

    /**
     * 查看文件块信息
     * @throws Exception
     */
    @Test
    public void getFileBlockLocations() throws Exception {
        FileStatus status = fileSystem.getFileStatus(new Path("/test/hadoop.tar.gz"));

        BlockLocation[] blocks = fileSystem.getFileBlockLocations(status,0, status.getLen());

        for(BlockLocation block : blocks) {

            for(String name : block.getNames()){
                for(String host : block.getHosts())
                System.out.println(name +"\t"+ block.getOffset() +"\t"+ block.getLength() +"\t"+ host);
            }
        }
    }

    /**
     * 删除文件
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        boolean result = fileSystem.delete(new Path("/test/hadoop.tar.gz"), false);
        System.out.println(result?"删除成功":"删除失败");
    }

    @After
    public void tearDown() {
        configuration = null;
        fileSystem = null;
    }
}
