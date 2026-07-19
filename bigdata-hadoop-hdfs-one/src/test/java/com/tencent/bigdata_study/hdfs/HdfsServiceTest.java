package com.tencent.bigdata_study.hdfs;

import org.apache.hadoop.fs.FileSystem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HDFS ????
 * <p>
 * ??: ???????????HDFS??
 */
@SpringBootTest
@ActiveProfiles("hdfs")
class HdfsServiceTest {

    @Autowired
    private HdfsService hdfsService;

    @Autowired
    private FileSystem fileSystem;

    @Test
    void testConnection() {
        assertNotNull(fileSystem, "FileSystem Bean ????");
        System.out.println("HDFS URI: " + fileSystem.getUri());
        System.out.println("Working dir: " + fileSystem.getWorkingDirectory());
    }

    @Test
    void testMkdirAndExists() throws IOException {
        String testDir = "/bigdata_study/test";
        hdfsService.mkdirs(testDir);
        assertTrue(hdfsService.exists(testDir), "??????");
        assertTrue(hdfsService.isDirectory(testDir), "???????");
    }

    @Test
    void testWriteAndRead() throws IOException {
        String testFile = "/bigdata_study/test/test_write.txt";
        hdfsService.mkdirs("/bigdata_study/test");

        String content = "Hello HDFS!";
        hdfsService.writeFile(testFile, content, true);

        assertTrue(hdfsService.exists(testFile), "????????");
        assertTrue(hdfsService.isFile(testFile), "???????/??");

        byte[] data = hdfsService.readFile(testFile);
        assertEquals(content, new String(data, "UTF-8"), "???????");
    }

    @Test
    void testListFiles() throws IOException {
        hdfsService.mkdirs("/bigdata_study/test");
        hdfsService.writeFile("/bigdata_study/test/file1.txt", "file1", true);
        hdfsService.writeFile("/bigdata_study/test/file2.txt", "file2", true);

        List<String> files = hdfsService.listFiles("/bigdata_study/test");
        assertTrue(files.size() >= 2, "??????2???");
    }

    @Test
    void testDelete() throws IOException {
        String testDir = "/bigdata_study/test_delete";
        hdfsService.mkdirs(testDir);
        assertTrue(hdfsService.exists(testDir));

        hdfsService.deleteRecursive(testDir);
        assertFalse(hdfsService.exists(testDir), "???");
    }

    @Test
    void testRename() throws IOException {
        hdfsService.mkdirs("/bigdata_study/test");
        String src = "/bigdata_study/test/rename_src.txt";
        String dst = "/bigdata_study/test/rename_dst.txt";

        hdfsService.writeFile(src, "rename test", true);
        assertTrue(hdfsService.exists(src));

        hdfsService.rename(src, dst);
        assertFalse(hdfsService.exists(src), "???:?Դ?ļ?Ӧ??????");
        assertTrue(hdfsService.exists(dst), "???:?Ŀ???ļ?Ӧ????");
    }

    @Test
    void testCopyFromLocal() throws IOException {
        // ?????????????? ???????HDFS????????????
        String localPath = "pom.xml";
        String hdfsPath = "/bigdata_study/test/pom_backup.xml";

        hdfsService.mkdirs("/bigdata_study/test");
        hdfsService.copyFromLocal(localPath, hdfsPath);

        assertTrue(hdfsService.exists(hdfsPath), "?????ļ?Ӧ????????HDFS");
    }
}