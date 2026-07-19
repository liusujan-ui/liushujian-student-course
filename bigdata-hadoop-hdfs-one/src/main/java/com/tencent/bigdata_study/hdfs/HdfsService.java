package com.tencent.bigdata_study.hdfs;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * HDFS ???
 * <p>
 * ???? HDFS ?????????
 */
@Service
public class HdfsService {

    private static final Logger log = LoggerFactory.getLogger(HdfsService.class);

    @Autowired
    private FileSystem fileSystem;

    // ==================== ???? ====================

    /**
     * ??????????
     *
     * @param path HDFS ??
     * @return ?????
     */
    public List<String> listFiles(String path) throws IOException {
        List<String> result = new ArrayList<>();
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path(path));
        for (FileStatus status : fileStatuses) {
            result.add(status.getPath().toString());
        }
        log.info("???? {} ????????? {} ?", path, result.size());
        return result;
    }

    /**
     * ??????????? (????)
     *
     * @param path HDFS ??
     * @return ?????
     */
    public List<FileStatus> listFilesRecursive(String path) throws IOException {
        List<FileStatus> result = new ArrayList<>();
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path(path), true);
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        log.info("???? {} ????????? (????) {} ?", path, result.size());
        return result;
    }

    // ==================== ???/???? ====================

    /**
     * ?? HDFS ??
     *
     * @param path HDFS ??
     * @return ???
     */
    public boolean exists(String path) throws IOException {
        boolean exists = fileSystem.exists(new Path(path));
        log.info("?? {} ???? {}", path, exists ? "??" : "???");
        return exists;
    }

    /**
     * ?????
     *
     * @param path HDFS ??
     * @return ????
     */
    public boolean isFile(String path) throws IOException {
        return fileSystem.isFile(new Path(path));
    }

    /**
     * ?????
     *
     * @param path HDFS ??
     * @return ????
     */
    public boolean isDirectory(String path) throws IOException {
        return fileSystem.isDirectory(new Path(path));
    }

    // ==================== ???? ====================

    /**
     * ????HDFS?????
     *
     * @param dirPath ???·?
     * @return ?????
     */
    public boolean mkdir(String dirPath) throws IOException {
        Path path = new Path(dirPath);
        if (fileSystem.exists(path)) {
            log.warn("??? {} ?????", dirPath);
            return false;
        }
        boolean created = fileSystem.mkdirs(path);
        log.info("???? {} {}", dirPath, created ? "??" : "??");
        return created;
    }

    /**
     * ??????????
     *
     * @param dirPath ?????
     * @return ?????
     */
    public boolean mkdirs(String dirPath) throws IOException {
        boolean created = fileSystem.mkdirs(new Path(dirPath));
        log.info("???? {} {}", dirPath, created ? "??" : "??");
        return created;
    }

    // ==================== ???? ====================

    /**
     * ????/????
     *
     * @param srcPath ?????
     * @param dstPath ?????
     * @return ????
     */
    public boolean rename(String srcPath, String dstPath) throws IOException {
        boolean renamed = fileSystem.rename(new Path(srcPath), new Path(dstPath));
        log.info("?? {} -> {} {}", srcPath, dstPath, renamed ? "??" : "??");
        return renamed;
    }

    /**
     * ???? (????????)
     *
     * @param srcPath ??????
     * @param dstPath ???????
     * @return ????
     */
    public boolean copyFromLocal(String srcPath, String dstPath) throws IOException {
        fileSystem.copyFromLocalFile(new Path(srcPath), new Path(dstPath));
        log.info("????? {} -> {} ??", srcPath, dstPath);
        return true;
    }

    /**
     * ?? HDFS ???? (??????)
     *
     * @param srcPath HDFS ??
     * @param dstPath ?????
     * @return ????
     */
    public boolean copyToLocal(String srcPath, String dstPath) throws IOException {
        fileSystem.copyToLocalFile(false, new Path(srcPath), new Path(dstPath), true);
        log.info("?? HDFS {} -> {} ??", srcPath, dstPath);
        return true;
    }

    // ==================== ???? ====================

    /**
     * ??? HDFS ?????
     *
     * @param path ?????
     * @return ???????
     */
    public byte[] readFile(String path) throws IOException {
        Path filePath = new Path(path);
        if (!fileSystem.exists(filePath)) {
            throw new FileNotFoundException("?? HDFS ???? " + path);
        }

        try (FSDataInputStream in = fileSystem.open(filePath);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            IOUtils.copyBytes(in, out, fileSystem.getConf());
            byte[] data = out.toByteArray();
            log.info("?? HDFS ?? {} (?? {} byte)", path, data.length);
            return data;
        }
    }

    /**
     * ??? HDFS ?????? (???)
     *
     * @param path  ?????
     * @param bytes ??
     * @return ????
     */
    public boolean writeFile(String path, byte[] bytes) throws IOException {
        Path filePath = new Path(path);
        try (FSDataOutputStream out = fileSystem.create(filePath, true)) {
            out.write(bytes);
            out.flush();
            log.info("?? HDFS ?? {} (?? {} byte)", path, bytes.length);
            return true;
        }
    }

    /**
     * ??? HDFS ???????
     *
     * @param path      ?????
     * @param content   ???
     * @param overwrite ?????
     * @return ????
     */
    public boolean writeFile(String path, String content, boolean overwrite) throws IOException {
        Path filePath = new Path(path);
        try (FSDataOutputStream out = fileSystem.create(filePath, overwrite)) {
            out.write(content.getBytes("UTF-8"));
            out.flush();
            log.info("?? HDFS ???? {} (?? {} byte)", path, content.getBytes("UTF-8").length);
            return true;
        }
    }

    /**
     * ??? HDFS ???? (????)
     *
     * @param path      ?????
     * @param content   ???
     * @return ????
     */
    public boolean appendFile(String path, String content) throws IOException {
        Path filePath = new Path(path);
        if (!fileSystem.exists(filePath)) {
            return writeFile(path, content, true);
        }
        try (FSDataOutputStream out = fileSystem.append(filePath)) {
            out.write(content.getBytes("UTF-8"));
            out.flush();
            log.info("???? HDFS ?? {} (?? {} byte)", path, content.getBytes("UTF-8").length);
            return true;
        }
    }

    // ==================== ???? ====================

    /**
     * ???? HDFS ???
     *
     * @param path ??????
     * @return ????
     */
    public boolean delete(String path) throws IOException {
        Path targetPath = new Path(path);
        if (!fileSystem.exists(targetPath)) {
            log.warn("?? HDFS ??? {} ??????·", path);
            return false;
        }
        boolean deleted = fileSystem.delete(targetPath, false);
        log.info("?? HDFS {} {}", path, deleted ? "??" : "??");
        return deleted;
    }

    /**
     * ???? HDFS ?? (????)
     *
     * @param path ?????????
     * @return ????
     */
    public boolean deleteRecursive(String path) throws IOException {
        Path targetPath = new Path(path);
        if (!fileSystem.exists(targetPath)) {
            log.warn("?? HDFS ??? {} ??????·", path);
            return false;
        }
        boolean deleted = fileSystem.delete(targetPath, true);
        log.info("?? HDFS {} (????) {}", path, deleted ? "??" : "??");
        return deleted;
    }

    // ==================== ?????? ====================

    /**
     * ???? HDFS ??????
     *
     * @param path  ?????
     * @return ??????? (Path)
     */
    public FileStatus getFileStatus(String path) throws IOException {
        FileStatus status = fileSystem.getFileStatus(new Path(path));
        log.info("?? HDFS {} ????: ??={}, ????={}, ????={}, ???={}",
                path, status.getLen(), status.getBlockSize(),
                status.getReplication(), status.getModificationTime());
        return status;
    }

    /**
     * ? HDFS ???????
     *
     * @param path ?????????
     * @return ?????
     */
    public ContentSummary getContentSummary(String path) throws IOException {
        ContentSummary summary = fileSystem.getContentSummary(new Path(path));
        log.info("?? {} ???? ??={}, ??????={}",
                path, summary.getLength(), summary.getFileCount());
        return summary;
    }

    // ==================== ?? ?? ====================

    /**
     * ???? HDFS
     */
    public void close() {
        try {
            if (fileSystem != null) {
                fileSystem.close();
                log.info("?? HDFS ??");
            }
        } catch (IOException e) {
            log.error("?? HDFS ????", e);
        }
    }
}