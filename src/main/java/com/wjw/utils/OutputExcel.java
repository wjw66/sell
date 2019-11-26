package com.wjw.utils;

import com.wjw.dataobject.OrderMaster;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author : wjwjava@163.com
 * @date : 15:41 2019/11/9
 */
public class OutputExcel {
    public static ResponseEntity<byte[]> exportOrder2Excel(List<OrderMaster> orderMasterList){
        HttpHeaders httpHeaders = null;
        ByteArrayOutputStream baos = null;
        try {
            //创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建文档摘要
            workbook.createInformationProperties();
            //获取文档信息,并配置

        }catch (Exception e){

        }
        return null;
    }
}
