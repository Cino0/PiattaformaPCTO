package com.example.PiattaformaPCTO_v2.service;

import com.example.PiattaformaPCTO_v2.collection.*;
import com.example.PiattaformaPCTO_v2.dto.ActivityViewDTOPair;
import com.example.PiattaformaPCTO_v2.repository.AttivitaRepository;
import com.example.PiattaformaPCTO_v2.repository.UniversitarioRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class SimpleRisultatiService implements RisultatiService {


    @Autowired
    private AttivitaRepository attivitaRepository;

    @Autowired
    private AttivitaService attivitaService;

    @Autowired
    private UniversitarioRepository universitarioRepository;

    @Override
    public void crea() {
        //Ottengo la lista di tutte le attività, da migliorare filtrando con l'anno accademico di svolgimento
        List<Attivita> attivita = attivitaRepository.findAll();

        //Creo una lista vuota per contenere tutti i risultati
        List<Risultati> r = new ArrayList<>();

        //Scorro la lista delle attività presenti, da miglirare anche con un for each
        for (int i = 0; i < attivita.size(); i++) {
            //ottengo il nome dell'attività
            String nome = attivita.get(i).getNome();
            System.out.println(nome);

            // dalla singola attività ottengo la lista degli studenti partecipanti
            List<Studente> stud = attivita.get(i).getStudPartecipanti();

            // scorro tutti gli studenti
            for (int x = 0; x < stud.size(); x++) {

                //dal singolo studente ottengo la scuola che frequenta
                Scuola s = stud.get(x).getScuola();
                // Prendo l'id, si puo collassare in una dichiarazione sola
                String scuola = s.getIdScuola();

                //Utilizzo  il metodo per ottenere l'indice in cui inserire la scuola
                int index = this.scuoleHelperd(r, scuola);
                // nel caso la scuola sia presente
                if (index != -1) {

                    //Utilizzo il metodo per ottenere l'indice in cui inserire l'attivita
                    int a = this.attivitaHelper(r.get(index).getAttivita(), nome);
                    //nel caso in cui l'attivta sia gia presente aggiungo la presenza di quello studente
                    if (a != -1) {
                        r.get(index).getAttivita().get(a).getPartecipanti().add(stud.get(x));
                    } else {
                        //mentre se non è presente creo la presenza
                        Presenza p = new Presenza(nome);
                        p.getPartecipanti().add(stud.get(x));
                        r.get(index).getAttivita().add(p);
                    }
                } else {
                    //nel caso in cui non sia presente la scuola creo una nuova presenza e un risultato per la scuola
                    Presenza p = new Presenza(nome);
                    Risultati res = new Risultati("21/22", s);
                    p.getPartecipanti().add(stud.get(x));
                    res.getAttivita().add(p);
                    r.add(res);
                }
            }
        }
        //Scorro di nuovo la lista dei risultati per controllare il numero di studenti che si sono iscritti all'università
        for (int y = 0; y < r.size(); y++) {
            for (int b = 0; b < r.get(y).getAttivita().size(); b++) {
                for (int c = 0; c < r.get(y).getAttivita().get(b).getPartecipanti().size(); c++) {
                    Studente studi = r.get(y).getAttivita().get(b).getPartecipanti().get(c);
                    String nome = studi.getNome().toUpperCase();
                    String cognome = studi.getCognome().toUpperCase();
                    String citta = studi.getScuola().getCitta().toUpperCase();
                    Universitario uni = universitarioRepository.findByNomeAndCognomeAndComuneScuola(nome, cognome, citta);
                    if (uni != null) {
                        System.out.println(uni.getCognome() + "   " + uni.getScuolaProv());
                        r.get(y).getAttivita().get(b).nuovoIscritto(studi);
                        if (this.studenteHelper(r, studi) == 0) {
                            r.get(y).nuovoIscritto(studi);

                        }

                    }
                }
            }
        }
        for (Risultati risultati : r) {
            for (Studente s : risultati.getIscritti()) {
                System.out.println(s.toString());
            }
        }
        //this.creaRisultato(r);
        //this.createexcl(r);
        this.createword(r);
    }

    @Override
    public void createStudentsFromActivities() {
        Map<Attivita, List<ActivityViewDTOPair>> result = new HashMap<>();
        List<Attivita> activities = this.attivitaRepository.findAll();
        activities.forEach(a -> result.put(a, attivitaService.findStudentsFromActivity(a.getNome())));
        XWPFDocument xwpfDocument = new XWPFDocument();
        String filepath = "src/main/resources/StudentsFromActivities.docx";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            result.entrySet().forEach(e -> {
                XWPFParagraph paragraph = xwpfDocument.createParagraph();
                XWPFRun run = paragraph.createRun();
                Attivita activity = e.getKey();
                run.setText(String.format("Nome: %s | Anno: %s", activity.getNome(), activity.getAnnoAcc()));
                run.addBreak();
                long universityStudentsCount = e.getValue().stream().count();
                run.setText(String.format("Totale iscritti dall'attività: %d", universityStudentsCount));
                run.addBreak();
                if (universityStudentsCount > 0){run.setText("Elenco universitari:");
                    run.addBreak();
                    for (ActivityViewDTOPair p : e.getValue()) {
                        run.setText(String.format("%s (%s), %s %s, %s | %s | %s, %s, %s",
                                p.universityStudent().getMatricola(),
                                p.universityStudent().getAnnoImm(),
                                p.universityStudent().getNome(),
                                p.universityStudent().getCognome(),
                                p.highSchool().getNome(),
                                p.highSchool().getTipo(),
                                p.highSchool().getCitta(),
                                p.highSchool().getProvincia(),
                                p.highSchool().getRegione()));
                        run.addBreak();
                    }
                }
                run.addBreak();
            });
            xwpfDocument.write(fileOutputStream);
            xwpfDocument.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Metodo che controlla se una scuola è presente all'interno della lista dei risultati
     *
     * @param res la lista dei risultati
     * @param id  l'id della scuola
     * @return l'indice a cui si trova nel caso sia presente, -1 nel caso non sia presente
     */
    private int scuoleHelperd(List<Risultati> res, String id) {
        if (res.size() == 0) {
            return -1;
        } else {
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i).getScuola().getIdScuola().equals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * Metodo che controlla se una scuola è presente all'interno della lista dei risultati
     *
     * @param pres
     * @param nome
     * @return
     */
    private int attivitaHelper(List<Presenza> pres, String nome) {
        for (int i = 0; i < pres.size(); i++) {
            if (Objects.equals(pres.get(i).getNomeAttivita(), nome)) {
                // System.out.println("sta qua"+i);
                return i;
            }
        }
        return -1;
    }


   /* private void createexcl(List<Risultati> r){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Risultati PCTO 21-22");
        sheet.createRow(0);
        sheet.getRow(0).createCell(0).setCellValue("Scuola");
        sheet.getRow(0).createCell(1).setCellValue("Città");
        sheet.getRow(0).createCell(2).setCellValue("Regione");
        sheet.getRow(0).createCell(3).setCellValue("Iscritti tot.");
        for (int i=0;i<r.size();i++){
            sheet.createRow(i+1);
            sheet.getRow(i+1).createCell(0).setCellValue(r.get(i).getScuola().getNome());
            sheet.getRow(i+1).createCell(1).setCellValue(r.get(i).getScuola().getCitta());
            sheet.getRow(i+1).createCell(2).setCellValue(r.get(i).getScuola().getRegione());
            sheet.getRow(i+1).createCell(3).setCellValue(r.get(i).getIscritti().size());
            int index=4;
            int y=0;
            for (int x=0;x<r.get(i).getAttivita().size();x++){
                sheet.getRow(0).createCell(index).setCellValue("Attività");
                sheet.getRow(i+1).createCell(index).setCellValue(r.get(i).getAttivita().get(x).getNomeAttivita());
                index++;
                sheet.getRow(0).createCell(index).setCellValue("Anno Accademico Svolgimento");
                sheet.getRow(i+1).createCell(index).setCellValue("2021-2022");
                index++;
                sheet.getRow(0).createCell(index).setCellValue("Studenti partecipanti");
                sheet.getRow(i+1).createCell(index).setCellValue(r.get(i).getAttivita().get(x).getPartecipanti().size());
                index++;
                sheet.getRow(0).createCell(index).setCellValue("Iscritti ottenuti");
                sheet.getRow(i+1).createCell(index).setCellValue(r.get(i).getAttivita().get(x).getIscritti());
                index++;
                y=x;

            }
            int off=0;
            if(r.get(i).getIscritti().size()>0){
                for (int c=0;c<r.get(i).getAttivita().get(y).getPartecipanti().size();c++){
                    Studente studi=r.get(i).getAttivita().get(y).getPartecipanti().get(c);
                    String nome= studi.getNome().toUpperCase();
                    String cognome= studi.getCognome().toUpperCase();
                    String citta= studi.getScuola().getCitta().toUpperCase();
                    Universitario uni =universitariRepository.findByNomeAndCognomeAndComuneScuola(nome,cognome,citta);

                    if(uni!=null){
                        sheet.getRow(0).createCell(12+off).setCellValue("Nome");
                        String stud= uni.getNome()+" "+uni.getCognome();
                        sheet.getRow(i+1).createCell(12+off).setCellValue(stud);
                        off++;
                    }
                }
            }
        }
        FileOutputStream file = null;
        try {
            file = new FileOutputStream("src/main/resources/Risultati3.xlsx");
            workbook.write(file);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/


    private void creaRisultato(List<Risultati> r) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Risultati PCTO 21-22");
        sheet.createRow(0);
        sheet.getRow(0).createCell(0).setCellValue("Scuola");
        sheet.getRow(0).createCell(1).setCellValue("Iscritti tot.");

        for (int i = 0; i < r.size(); i++) {
            int index = 2;
            sheet.createRow(i + 1);
            sheet.getRow(i + 1).createCell(0).setCellValue(r.get(i).getScuola().getNome());
            sheet.getRow(i + 1).createCell(1).setCellValue(r.get(i).getIscritti().size());
            /*for (int x = 0;x<r.get(i).getIscritti().size();x++){
                String stud = r.get(i).getIscritti().get(x).getNome()+" "+r.get(i).getIscritti().get(x).getCognome();
                sheet.getRow(i+1).createCell(x+2).setCellValue(stud);
            }*/
            for (int x = 0; x < r.get(i).getAttivita().size(); x++) {
                sheet.getRow(i + 1).createCell(index).setCellValue(r.get(i).getAttivita().get(x).getNomeAttivita());
                sheet.getRow(i + 1).createCell(index + 1).setCellValue(r.get(i).getAttivita().get(x).getIscritti().size());
                index += 2;
            }
        }
        FileOutputStream file = null;
        try {
            file = new FileOutputStream("src/main/resources/Risultati4.xlsx");
            workbook.write(file);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private int studenteHelper(List<Risultati> risultati, Studente studente) {
        for (Risultati r : risultati) {
            if (r.getIscritti().contains(studente)) {
                return -1;
            }
        }
        return 0;
    }


    private void createword(List<Risultati> r) {
        XWPFDocument xwpfDocument = new XWPFDocument();
        String filepath = "src/main/resources/Risultati.docx";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            for (Risultati risultati : r) {
                XWPFParagraph paragraph = xwpfDocument.createParagraph();
                XWPFRun run = paragraph.createRun();
                Scuola sc = risultati.getScuola();
                run.setText(sc.getNome() + " " + sc.getRegione() + " " + sc.getCitta());
                run.addBreak();
                run.setText(" Totale iscritti all'università: ");
                run.setText(String.valueOf(risultati.getIscritti().size()));
                run.addBreak();
                for (Presenza p : risultati.getAttivita()) {
                    run.setText(p.getNomeAttivita());
                    run.setText(" Partecipanti : ");
                    run.setText(String.valueOf(p.getPartecipanti().size()));
                    run.setText(" Iscritti: ");
                    run.setText(String.valueOf(p.getIscritti().size()));
                    run.addBreak();
                }
                run.setText("Elenco iscritti: ");
                run.addBreak();
                for (Studente s : risultati.getIscritti()) {
                    run.setText(s.getNome() + " " + s.getCognome());
                    run.addBreak();
                }
                run.addBreak();
            }
            xwpfDocument.write(fileOutputStream);
            xwpfDocument.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
