import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Auther: 等星星溺水
 * @Date: 2022/10/18 21:45
 * @Description:
 */

public class PoiTest {
    //读取xlsl文件
    @Test
    public void test01() throws Exception{
        //加载指定文件创建一个excel对象，对应一个工作pu
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\桌面\\test.xlsx")));
        //获取当前文件的第一页,根据索引，从零开始
        XSSFSheet sheetAt = excel.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();//拿到最后一列的序号，以零开始
        System.out.println(lastRowNum);
        for (Row row : sheetAt) {//拿到行
            for (Cell cell : row) {//拿到列
                System.out.println(cell.getStringCellValue());//按照String类型取数据
            }
        }
        excel.close();
        /*XSSFWorkbook 当前excel文件，
        *XSSFSheet  当前文件的第一个sheet
        * Row 当前sheet的行对象
        * cell 当前行对象的单元格 */
    }
    @Test
    public void test02() throws Exception{
        //加载指定文件创建一个excel对象，对应一个工作pu
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\桌面\\test.xlsx")));
        //获取当前文件的第一页,根据索引，从零开始
        XSSFSheet sheetAt = excel.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();//拿到最后一列的序号，以零开始
        System.out.println(lastRowNum);
        for (int i = 0; i <=lastRowNum ; i++) {
            XSSFRow row = sheetAt.getRow(i);//根据索引获取当前行
            short lastCellNum = row.getLastCellNum();
            System.out.println(lastCellNum);
            for (int j = 0; j < lastCellNum; j++) {
                System.out.println(row.getCell(j).getStringCellValue());
            }
        }
        excel.close();
       /*1   2  你好啊  小崔说事 2  我不好   小刘说你*/
    }
    //写出xlsl文件
    @Test
    public void test03() throws Exception{
        //先有一个xlsl文件
        XSSFWorkbook excel = new XSSFWorkbook();
        //再有一个sheet
        XSSFSheet sheet = excel.createSheet("sheet1");
        //设置行
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("一杯凉白开");
        row.createCell(1).setCellValue("二杯凉白开");
        row.createCell(2).setCellValue("三杯凉白开");
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("一杯凉白开2");
        row2.createCell(1).setCellValue("二杯凉白开2");
        row2.createCell(2).setCellValue("三杯凉白开2");
        FileOutputStream fileOutput = new FileOutputStream(new File("D:\\桌面\\test2.xlsx"));
        excel.write(fileOutput);
        fileOutput.flush();
        excel.close();
    }
}
