package com.tencent.bigdata_study.hdfs;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.net.URI;

/**
 * HDFS ????
 * <p>
 * ???? HDFS FileSystem Bean, Spring ???? HDFS ???
 */
@Configuration
@PropertySource("classpath:application-hdfs.properties")
public class HdfsConfig {

    private static final Logger log = LoggerFactory.getLogger(HdfsConfig.class);

    // Windows 下需要 hadoop.home.dir 指向 winutils.exe 所在目录；Linux 下不需要
    static {
        if (System.getProperty("hadoop.home.dir") == null) {
            String os = System.getProperty("os.name", "").toLowerCase();
            if (os.contains("win")) {
                String projectDir = System.getProperty("user.dir", ".");
                String hadoopHome = projectDir + File.separator + "hadoop-bin";
                System.setProperty("hadoop.home.dir", hadoopHome);
                log.info("hadoop.home.dir 设置为 {}", hadoopHome);
            }
        }
    }

    @Value("${hdfs.uri}")
    private String hdfsUri;

    @Value("${hdfs.user}")
    private String hdfsUser;

    @Bean
    public FileSystem fileSystem() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();


        conf.set("fs.defaultFS", hdfsUri);

        // Docker 环境：客户端通过 DataNode 的 hostname 而非 IP 来建立数据连接
        conf.set("dfs.client.use.datanode.hostname", "true");

        conf.setInt("io.file.buffer.size", 4096);


        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("fs.file.impl", "org.apache.hadoop.fs.LocalFileSystem");

        FileSystem fs;
        try {
            URI uri = new URI(hdfsUri);
            fs = FileSystem.get(uri, conf, hdfsUser);
            log.info("???? HDFS ?????? uri={}, user={}", hdfsUri, hdfsUser);
        } catch (Exception e) {
            log.error("???? HDFS ????: {}", e.getMessage(), e);
            throw new RuntimeException("???? HDFS ????", e);
        }
        return fs;
    }

}