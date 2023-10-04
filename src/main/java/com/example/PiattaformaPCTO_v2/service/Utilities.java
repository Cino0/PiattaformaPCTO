package com.example.PiattaformaPCTO_v2.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private XSSFSheet sheet;

    public static boolean isEmail(String s){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher =  pattern.matcher(s);
        return matcher.matches();
    }
    public static String comune2regione(String comune,XSSFSheet sheet){
        for (Row row:sheet){
            if(row.getCell(1).getStringCellValue().equals(comune)){
                return row.getCell(2).getStringCellValue();
            }
        }
        return "";
    }

}
