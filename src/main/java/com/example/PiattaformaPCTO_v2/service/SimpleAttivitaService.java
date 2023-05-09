package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.Attivita;
import com.example.PiattaformaPCTO_v2.collection.Scuola;
import com.example.PiattaformaPCTO_v2.collection.Studente;
import com.example.PiattaformaPCTO_v2.collection.Universitario;
import com.example.PiattaformaPCTO_v2.dto.ActivityViewDTOPair;
import com.example.PiattaformaPCTO_v2.repository.AttivitaRepository;
import com.example.PiattaformaPCTO_v2.repository.ScuolaRepository;
import com.example.PiattaformaPCTO_v2.repository.UniversitarioRepository;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SimpleAttivitaService implements AttivitaService {

    @Autowired
    private AttivitaRepository attivitaRepository;

    /**
     * Universitario repository instance.
     */
    @Autowired
    private UniversitarioRepository universitarioRepository;

    @Autowired
    private ScuolaRepository scuolaRepository;

    private ScuolaHelperService scuolaHelperService;

    @Override
    public String save(Attivita attivita) {
        return attivitaRepository.save(attivita).getNome();
    }

    @Override
    public void upload() {
        String filePath = "src/main/resources/Progetto-NERD-2021-2022.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name, 4043, new ArrayList<>());
            System.out.println(filePath);
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row riga = iterator.next();
                if (riga.getCell(3) != null) {
                    Scuola scuola = scuolaRepository.getScuolaByNome(riga.getCell(8).getStringCellValue());
                    if (scuola != null) {
                        String nome = riga.getCell(3).getStringCellValue();
                        String cognome = riga.getCell(4).getStringCellValue();
                        String id = nome + cognome + scuola.getNome();
                        Studente stud = new Studente(id, nome, cognome, scuola);
                        System.out.println(stud);
                        attivita.getStudPartecipanti().add(stud);
                        System.out.println(attivita.getStudPartecipanti().size());
                    }

                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadSummer() {
        String filePath = "src/main/resources/SummerSchoolSTEMPartecipanti.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name, 4043, new ArrayList<>());
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row riga = iterator.next();
                if (riga.getCell(1) != null) {
                    Scuola scuola = scuolaRepository.getScuolaById(riga.getCell(4).getStringCellValue());
                    if (scuola != null) {
                        //System.out.println(scuola.toString());
                        //System.out.println(riga.getCell(1).getStringCellValue());
                        String nome = riga.getCell(1).getStringCellValue();
                        String cognome = riga.getCell(2).getStringCellValue();
                        String id = nome + cognome + scuola.getNome();
                        Studente stud = new Studente(id, nome, cognome, scuola);
                        //System.out.println(stud.toString());
                        attivita.getStudPartecipanti().add(stud);
                    }
                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void uploadCartel() {
        String filePath = "src/main/resources/Cartel1.xlsx";
        System.out.println(filePath);
        try {
            FileInputStream excel = new FileInputStream(new File(filePath));
            File file = new File(filePath);
            String name = FilenameUtils.removeExtension(file.getName());
            Attivita attivita = new Attivita(name, 4043, new ArrayList<>());
            Workbook workbook = new XSSFWorkbook(excel);
            Sheet dataSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataSheet.rowIterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (row.getCell(0) != null) {
                    Scuola scuola = scuolaRepository.getScuolaById(row.getCell(2).getStringCellValue());
                    // System.out.println(scuola.toString());
                    String nome = row.getCell(0).getStringCellValue();
                    String cognome = row.getCell(1).getStringCellValue();
                    String id = nome + cognome + scuola.getNome();
                    Studente stud = new Studente(id, nome, cognome, scuola);
                    System.out.println(stud);
                    attivita.getStudPartecipanti().add(stud);
                }
            }
            System.out.println(this.save(attivita));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void uploadOpen(MultipartFile file) {
        Attivita attivita = new Attivita("Open2022", 4043, new ArrayList<>());
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            //System.out.println(row.getCell(3).getStringCellValue());
            Scuola scuola = scuolaRepository.getScuolaByNome(row.getCell(3).getStringCellValue());
            String nome = row.getCell(0).getStringCellValue();
            String cognome = row.getCell(1).getStringCellValue();
            String id = nome + cognome + scuola.getNome();
            Studente stud = new Studente(id, nome, cognome, scuola);
            // System.out.println(stud.toString());
            attivita.getStudPartecipanti().add(stud);

        }
        System.out.println(this.save(attivita));
    }

    @Override
    public void uploadGioco(MultipartFile file) {
        Attivita attivita = new Attivita("InformaticaXGioco", 4043);
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        Scuola s = new Scuola("xxx", "Scuolax", "MARCHE", "FERMO", "PSE", "LICEO");
        while (iterator.hasNext()) {
            Row r = iterator.next();
            if (!r.getCell(0).getStringCellValue().isEmpty()) {
                int finale = r.getCell(0).getStringCellValue().length();
                int inizioNome = (r.getCell(0).getStringCellValue().indexOf(':')) + 2;
                int inizioCognome = (r.getCell(0).getStringCellValue().lastIndexOf(':')) + 2;
                int fineNome = r.getCell(0).getStringCellValue().indexOf('\n');
                String nome = r.getCell(0).getStringCellValue().substring(inizioNome, fineNome);
                String cognome = r.getCell(0).getStringCellValue().substring(inizioCognome, finale);
                String id = nome + cognome + s.getNome();
                Studente stud = new Studente(id, nome, cognome, s);
                attivita.getStudPartecipanti().add(stud);
                System.out.println(stud);
            }
        }

        System.out.println(this.save(attivita));
    }

    /**
     * Find information about students that chose UNICAM and their high school, given an activity.
     *
     * @return list of activity view pairs
     */
    @Override
    public void uploadG(MultipartFile file) {
        Sheet dataSheet = this.fileOpenerHelper(file);
        Iterator<Row> iterator = dataSheet.rowIterator();
        iterator.next();
        while (iterator.hasNext()) {
            Row r = iterator.next();
            if (!r.getCell(0).getStringCellValue().equals("Nome e Cognome")) {
                if (r.getCell(1) != null) {
                    System.out.println("ciao");
                }
            } else {
                System.out.println("pipi");
            }

           /* String s = r.getCell(0).getStringCellValue();
            System.out.println(r.getCell(0).getStringCellValue());
            if (r.getCell(0).getStringCellValue().equals("---")){
                r=iterator.next();
                System.out.println(r.getCell(2).getStringCellValue());
            }*/

        }
    }

    @Override
    public void creaPdf() {
        XWPFDocument document = new XWPFDocument();
        XWPFStyles styles = document.createStyles();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageSz pageSz = sectPr.addNewPgSz();
        pageSz.setW(BigInteger.valueOf(12240));
        pageSz.setH(BigInteger.valueOf(15840));

        XWPFParagraph paragraph = document.createParagraph();

        //create table
        XWPFTable table = document.createTable();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("col one, row one");
        tableRowOne.addNewTableCell().setText("col two, row one");
        tableRowOne.addNewTableCell().setText("col three, row one");

        //create CTTblGrid for this table with widths of the 3 columns.
        //necessary for Libreoffice/Openoffice and PdfConverter to accept the column widths.
        //values are in unit twentieths of a point (1/1440 of an inch)
        //first column = 2 inches width
        table.getCTTbl().addNewTblGrid().addNewGridCol().setW(BigInteger.valueOf(2 * 1440));
        //other columns (2 in this case) also each 2 inches width
        for (int col = 1; col < 3; col++) {
            table.getCTTbl().getTblGrid().addNewGridCol().setW(BigInteger.valueOf(2 * 1440));
        }

        //create second row
        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("col one, row two");
        tableRowTwo.getCell(1).setText("col two, row two");
        tableRowTwo.getCell(2).setText("col three, row two");

        //create third row
        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("col one, row three");
        tableRowThree.getCell(1).setText("col two, row three");
        tableRowThree.getCell(2).setText("col three, row three");

        paragraph = document.createParagraph();

        //trying picture
        XWPFRun run = paragraph.createRun();
        run.setText("The picture in line: ");
        InputStream in = null;
        String file = "src/main/resources/example1.jpg";
        try {
            in = new FileInputStream(new File(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            run.addPicture(in, Document.PICTURE_TYPE_JPEG, "example1.jpg", Units.toEMU(100), Units.toEMU(30));
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        run.setText(" text after the picture.");

        paragraph = document.createParagraph();

        //document must be written so underlaaying objects will be committed
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            document = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PdfOptions options = PdfOptions.create();
        PdfConverter converter = (PdfConverter) PdfConverter.getInstance();
        try {
            String fileout = "src/main/resources/esempio1.pdf";
            FileOutputStream outp = new FileOutputStream(new File(fileout));
            converter.convert(document, outp, options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

       /* XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

       /* run.setText(" text after the picture.");

        try {
            String filePath="src/main/resources/example1.jpg";
            InputStream in = new FileInputStream(new File(filePath));
            run.addPicture(in, Document.PICTURE_TYPE_JPEG, "example1.jpg", Units.toEMU(100), Units.toEMU(30));
            in.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.write(out);
            document.close();

            document = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()));
            PdfOptions options = PdfOptions.create();
            PdfConverter converter = (PdfConverter)PdfConverter.getInstance();
            converter.convert(document, new FileOutputStream(new File("src/main/resources/prova1.jpg")), options);
            document.close();
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
*/
        System.out.println("ciao");

    }


    @Override
    public List<ActivityViewDTOPair> findStudentsFromActivity(String activityName) {
        List<ActivityViewDTOPair> result = new ArrayList<>();
        Attivita activity = this.attivitaRepository.findByNome(activityName);
        activity.getStudPartecipanti().forEach(s -> {
            Universitario u = this.universitarioRepository.findByNomeAndCognomeAndComuneScuola(
                    s.getNome().toUpperCase(), s.getCognome().toUpperCase(), s.getScuola().getCitta().toUpperCase());
            if (u != null)
                result.add(new ActivityViewDTOPair(u, this.scuolaRepository.getScuolaByNomeContainingAndAndCitta(
                        s.getScuola().getNome(), u.getComuneScuola())));
        });
        return result;
    }

    @Override
    public Sheet fileOpenerHelper(MultipartFile file) {
        try {
            Path tempDir = Files.createTempDirectory("");
            File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
            file.transferTo(tempFile);
            Workbook workbook = new XSSFWorkbook(tempFile);
            Sheet dataSheet = workbook.getSheetAt(0);
            return dataSheet;
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }


}
