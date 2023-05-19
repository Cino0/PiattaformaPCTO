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

import com.example.PiattaformaPCTO_v2.collection.Professore;
import com.google.gson.Gson;

public class DocenteService {
    private String pathFile = "src\\main\\resources\\Docenti_attivita.xlsx";
    
    private Professore[] professori;

    /**
     * Costruttore
     */
    public DocenteService(){
        ArrayList tmp = readRawFile();
        String[][] data = formatFile(tmp);
        this.professori = data2Class(data);
        //print();    
    }

    /**
     * Prende il file che si trova nel percorso path e ritorna una matrice di celle 
     * @return
     */
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
    /**
     * Prende in input i dati non lavorati e ritorna un array di stringhe contenenti le celle
     * @param rawData
     * @return
     */
    public String[][] formatFile(ArrayList<ArrayList<String>> rawData){
        String[][] data = new String[rawData.size()][5];
        System.out.println("Inizio Formattazione");
        for(int i=0;i<rawData.size();i++){
            for(int j=0;j<5;j++){
                data[i][j] = rawData.get(i).get(j);
            }
        }
        return data;
    }
    /**
     * Stampa l'array di oggetti
     */
    public void print(){
        
        for (Professore professore : professori) {
            System.out.println(professore.toString());
        }
        
    }
    /**
     * Converte l'array di stringhe in array di Oggetti Proffessore
     * @param data
     * @return
     */
    public Professore[] data2Class(String[][] data){
        ArrayList<Professore> tmp = new ArrayList<Professore>();

        for (String[] row : data) {
            String nome = row[1];
            String email = row[2];
            String scuola = row[3];
            String citta = row[4];
            Professore professore = new Professore(nome, email, scuola, citta);
            if(checkProfessore(professore))
                tmp.add(professore);
        }
        tmp = removeDuplicates(tmp);
        Professore[] professori = new Professore[tmp.size()];
        return tmp.toArray(professori);
    }

    /**
     * Controlla se un oggetto Professore ha parametri nulli, se si ritorna Falso altrimenti Vero
     * @param professore
     * @return
     */
    private boolean checkProfessore(Professore professore){
        if(professore.getCitta() == "" && professore.getEmail() == "" && professore.getNome() == "" && professore.getScuolaImp() == "")
        return false;
        return true;
    }

    /**
     * Funzione che serve a rimuovere oggetti ridondanti all'interno dell'array;
     * @param professori
     * @return
     */
    private ArrayList<Professore> removeDuplicates(ArrayList<Professore> professori){
        System.out.println("Numero campi con duplicati :"+professori.size());

        ArrayList<Professore> newProfessori = new ArrayList<>();
        for (Professore professore : professori) {
            boolean isGood= true;
            for(Professore newProfessore: newProfessori){
                if(professore.getEmail().equals(newProfessore.getEmail())){
                    isGood = false;
                    break;
                }
            }
            if(isGood)
                newProfessori.add(professore);
        }

        System.out.println("Numero campi senza duplicati: "+newProfessori.size());
        print();
        return newProfessori;
    }
    public static void main(String[] args) {
        DocenteService dos = new DocenteService();
    }
    //TODO riscrivere tutti i metodi in modo che controllino all'aggiunta se una classe è duplicata o mento
}
