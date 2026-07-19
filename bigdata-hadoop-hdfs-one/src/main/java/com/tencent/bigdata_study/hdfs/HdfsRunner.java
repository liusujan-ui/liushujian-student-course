package com.tencent.bigdata_study.hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * HDFS ??? Runner
 * <p>
 * ??? hdfs profile ????Spring Boot ???HDFS??????
 * ??: --spring.profiles.active=hdfs
 */
@Component
@Profile("hdfs")
public class HdfsRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(HdfsRunner.class);

    @Autowired
    private HdfsService hdfsService;

    @Override
    public void run(String... args) throws Exception {
        log.info("===== HDFS ??????? =====");

        try {
            // 1. ?? HDFS ???
            testListRoot();

            // 2. ?????
            testMkdir();

            // 3. ???????
            testWriteReadFile();

            // 4. ????
            testDelete();

            log.info("===== HDFS ????????? =====");
        } catch (Exception e) {
            log.error("HDFS ????: {}", e.getMessage(), e);
        }
    }

    private void testListRoot() throws IOException {
        log.info("--- 1. ?? HDFS ??? ---");
        List<String> files = hdfsService.listFiles("/");
        for (String file : files) {
            log.info("  ?? {}", file);
        }
    }

    private void testMkdir() throws IOException {
        log.info("--- 2. ???? ---");
        String testDir = "/bigdata_study/test";
        boolean result = hdfsService.mkdirs(testDir);
        log.info("  ?????? {}: {}", testDir, result);
    }

    private void testWriteReadFile() throws IOException {
        log.info("--- 3. ????????? ---");
        String testFile = "/bigdata_study/test/hello.txt";

        // ??
        String content = "Hello HDFS! ???:" + System.currentTimeMillis();
        hdfsService.writeFile(testFile, content, true);
        log.info("  ???? {} ??", testFile);

        // ??
        byte[] data = hdfsService.readFile(testFile);
        log.info("  ???? {}: {}", testFile, new String(data, "UTF-8"));

        // ?????
        FileStatus status = hdfsService.getFileStatus(testFile);
        log.info("  ???? {}: ???={}, ????={}", testFile, status.getLen(), status.getReplication());
    }

    private void testDelete() throws IOException {
        log.info("--- 4. ??? ---");
        String testDir = "/bigdata_study";
        boolean result = hdfsService.deleteRecursive(testDir);
        log.info("  ??? {}: {}", testDir, result);
    }
}