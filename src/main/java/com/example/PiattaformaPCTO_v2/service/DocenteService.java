package com.example.PiattaformaPCTO_v2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.print.Doc;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DocenteService {
    private String pathFile = "src\\main\\resources\\Docenti_attivita.xlsx";
    private String[][] data;

    public DocenteService(){
        ArrayList tmp = readRawFile();
        data = new String[tmp.size()][5];
        formatFile(tmp);
        print();    
    }

    public ArrayList readRawFile(){
        ArrayList<ArrayList<String>> rawContent = new ArrayList<ArrayList<String>>();
        File file = new File(pathFile);
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        Iterator<Row> rowIterator = null;
        try{
            fis = new FileInputStream(file);
        }catch(FileNotFoundException e){
            
        }
        try{
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            rowIterator= sheet.iterator();
        }catch(Exception e){

        }
        
        //Leggo le righe
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            ArrayList<String> tmp = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                tmp.add(cell.getStringCellValue());
            }
            rawContent.add(tmp);
        }
        return rawContent;
    }

    public void formatFile(ArrayList<ArrayList<String>> rawData){
        System.out.println("Inizio Formattazione");
        for(int i=0;i<rawData.size();i++){
            for(int j=0;j<5;j++){
                this.data[i][j] = rawData.get(i).get(j);
            }
        }
        System.out.println("Fine Formattazione");
    }
    public void print(){
        System.out.println("Inizio Stampa");
        System.out.println(this.data);
        for(int i = 1; i<this.data.length-1;i++){
            for(int j = 0; j< this.data[0].length;j++){
                System.out.print(this.data[i][j]+"\t\t");
            }
            System.out.println();
        }
        System.out.println("Fine Stampa");
    }
    
    public void data2Gson(String[][] data){
        ArrayList<Gson> jsonData = new ArrayList<Gson>();
        for (String[] row : data) {
            
        }
    }

    public static void main(String[] args) {
        DocenteService dos = new DocenteService();
        
    }
}
