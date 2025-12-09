package com.tencent.JVMAnalysis;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 观自在
 * @description fullGC的相关内容
 * @date 2025-12-09 08:03
 */
public class FullGCTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            // 使用 FileInputStream 读取文件
            FileInputStream fis = new FileInputStream(
                    new File(FullGCTest.class.getClassLoader().getResource("fullGCTest.xls").getFile()));

            // 创建 HSSFWorkbook 对象
            HSSFWorkbook workbook = new HSSFWorkbook(fis);

            // 获取第一个工作表对象
            Sheet sheet = workbook.getSheetAt(0);

            // 获取第一列第一行的单元格
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);

            // 获取单元格内容
            String result = cell.getStringCellValue();
            System.out.println(result);

            // 关闭工作簿和文件流
            workbook.close();
            fis.close();

            // 休眠2秒
            Thread.sleep(2000L);
        }
    }
}
