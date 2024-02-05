package com.management.app.managemenetapp;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.management.app.types.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Singleton class for managing files, providing functionality to create PDFs and import data from CSV.
 */
public class FilesManager {
    private static FilesManager instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private FilesManager() {
    }

    /**
     * Returns the singleton instance of FilesManager. If the instance is null, a new instance is created.
     *
     * @return The singleton instance of FilesManager.
     */
    public static FilesManager getInstance() {
        if (instance == null) {
            instance = new FilesManager();
        }
        return instance;
    }

    /**
     * Creates a PDF document for announcing grades.
     *
     * @param exam the exam
     */
    public void createPdfGradesAnnouncement(Exam exam) {
        ArrayList<Grades> grades = DatabaseManager.getInstance().getGradesForPdf(exam);
        String fileName = Translator.getInstance().translate("GradesNoticeFileName");

        try {
            File file = choosePdfFileLocation(fileName, false);
            if (file == null) {
                return;
            }

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            // creating title
            document.add(createHeaderParagraph(exam.getName()));
            document.add(createHeaderParagraph(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(exam.getDate())));

            // creating students table
            float[] columnWidth3 = {80, 120, 70};
            Table table = new Table(columnWidth3).setTextAlignment(TextAlignment.CENTER);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // adding header
            table.addCell(createTableHeaderCell("Matrikelnr."));
            table.addCell(createTableHeaderCell("Punkte"));
            table.addCell(createTableHeaderCell("Note"));

            // adding Students
            for (Grades grade : grades) {
                table.addCell(grade.getStudent().getMatriculationNumber());
                table.addCell(String.valueOf(grade.getPoints()));
                table.addCell(grade.getGrade());
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            SceneManager.getInstance().showError(e.getMessage());
        }
    }

    /**
     * Creates a PDF document for grades suitable for the Exam Office.
     *
     * @param exam the Exam cla
     */
    public void createPdfGradesForExamOffice(Exam exam, Major major) {
        Lecturer owner = exam.getAssociatedCourse().getOwner();
        ExamOfficeEmployee employee = major.getEmployee();
        ArrayList<Grades> grades = DatabaseManager.getInstance().getGradesForExamOffice(exam, major);
        String fileName = Translator.getInstance().translate("GradesListFileName") + "_" + major.getName();

        try {
            // Choose pdf Save Location
            File file = choosePdfFileLocation(fileName, false);
            if (file == null) {
                return;
            }

            // Creating pdf
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            // add logo to PDF
            URL imagePath = ExamManagementApp.class.getResource("images/logo-black.png");
            if (imagePath != null) {
                ImageData imageData = ImageDataFactory.create(imagePath);
                Image logo = new Image(imageData);
                logo.setHeight(52);
                logo.setRelativePosition(205f, -3.9f, 110f, 0f);
                document.add(logo);
            }

            float col = 400f;
            float[] columnWidth = {col + 230, col};
            float defaultFontSize = 10.5f;
            float smallFontSize = 8.5f;

            Table table = new Table(columnWidth);
            table.setBorder(Border.NO_BORDER);

            table.addCell(createBorderlessCell("", defaultFontSize));
            table.addCell(createBorderlessCell(owner.getFullName(), defaultFontSize));

            String sender = "\n\nBergische Universität Wuppertal, " + owner.getFullName() + "\n" +
                    "Gaußstraße 20, 42119 Wuppertal";
            table.addCell(createBorderlessCell(sender, 7f));

            // owner working group
            table.addCell(createBorderlessCell(owner.getResearchGroup(), defaultFontSize));

            // recipient
            String recipient = "Zentrales Prüfungsamt\n" +
                    "Dezernat 3.4\n" +
                    "Herr " +
                    employee.getFullName() + "\n" +
                    employee.getRoom();
            table.addCell(createBorderlessCell(recipient, defaultFontSize));

            float[] columnWidth2 = {80, 150};
            Table ownerDataTable = new Table(columnWidth2);
            ownerDataTable.setBorder(Border.NO_BORDER);

            // Owner information
            ownerDataTable.addCell(createBorderlessCell("Gaußstraße 20, ", defaultFontSize));
            ownerDataTable.addCell(createBorderlessCell("42119 Wuppertal", defaultFontSize));

            ownerDataTable.addCell(createBorderlessCell("RAUM", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell(owner.getRoom(), smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("TELEFON", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell(owner.getPhoneNumber(), smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("MOBIL", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell("", smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("FAX", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell("", smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("MAIL", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell(owner.getEmailAddress(), smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("AKTENZEICHEN", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell("", smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("DATUM", smallFontSize));
            ownerDataTable.addCell(createBorderlessCell(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now()), smallFontSize));

            ownerDataTable.addCell(createBorderlessCell("", smallFontSize));

            Cell cell = new Cell().add(ownerDataTable);
            cell.setBorder(Border.NO_BORDER);
            table.addCell(cell);
            document.add(table);

            // brief text
            document.add(new Paragraph("Sehr geehrter Herr " + employee.getFullName() + ",\n").setFontSize(defaultFontSize));
            document.add(new Paragraph("hiermit übersende ich Ihnen die Ergebnisse der Klausur zur Veranstaltung " + exam.getName() + " vom \n" +
                    DateTimeFormatter.ofPattern("dd.MM.yyyy").format(exam.getDate()) + ":\n").setFontSize(defaultFontSize));
            document.add(new Paragraph("\n"));

            // grades Table
            float[] columnWidth3 = {80, 120, 50};
            Table gradesTable = new Table(columnWidth3);

            gradesTable.addCell(createTableHeaderCell("Matr.-Nr"));
            gradesTable.addCell(createTableHeaderCell("Name"));
            gradesTable.addCell(createTableHeaderCell("Note"));

            for (Grades grade : grades) {
                gradesTable.addCell(grade.getStudent().getMatriculationNumber());
                gradesTable.addCell(grade.getStudent().getFullName());
                gradesTable.addCell(String.valueOf(grade.getGrade()));
            }

            document.add(gradesTable);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Mit freundlichen Grüßen,\n").setFontSize(defaultFontSize));
            document.add(new Paragraph(owner.getFullName()));

            document.close();
        } catch (Exception e) {
            SceneManager.getInstance().showError(e.getMessage());
        }
    }

    /**
     * Imports data from a CSV file.
     */
    public void importFromCSV(String examId) {
        File file = choosePdfFileLocation("ChooseSaveLocation", true);

        if (file == null)
            return;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skip the header line

            Stream<String> inputStream = reader.lines();
            inputStream.forEachOrdered(line -> {
                String[] data = line.split(",");
                Student student = new Student(data[1], data[0], data[2], new Major(data[3]));
                DatabaseManager.getInstance().saveStudent(student, examId);
            });
        } catch (Exception e) {
            SceneManager.getInstance().showError(e.getMessage());
            return;
        }
        SceneManager.getInstance().showError(Translator.getInstance().translate("ImportSuccessful"), true);
    }

    private Cell createBorderlessCell(String text, float fontSize) {
        Paragraph paragraph = new Paragraph(text).setFontSize(fontSize);
        return new Cell().add(paragraph).setBorder(Border.NO_BORDER);
    }

    private Cell createTableHeaderCell(String text) throws IOException {
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Paragraph paragraph = new Paragraph(text).setFont(fontBold);
        paragraph.setUnderline();

        Cell cell = new Cell().add(paragraph);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    private Paragraph createHeaderParagraph(String text) throws IOException {
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Paragraph paragraph = new Paragraph(text);
        paragraph.setFontSize(14f);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setFont(fontBold);

        return paragraph;
    }

    /**
     * opens a stage with native FileChooser to choose the Location where to save/load a file
     *
     * @param initialFileName the inital name of the file
     * @return the chosen File
     */
    private File choosePdfFileLocation(String initialFileName, boolean isCsvFile) {
        FileChooser fileChooser = new FileChooser();

        if (isCsvFile) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        } else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("pdf", "*.pdf"));
            fileChooser.setInitialFileName(initialFileName);
        }

        fileChooser.setTitle(Translator.getInstance().translate("ChooseSaveLocation"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"));

        try {
            if (isCsvFile)
                return fileChooser.showOpenDialog(SceneManager.getInstance().getWindow());
            else
                return fileChooser.showSaveDialog(SceneManager.getInstance().getWindow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
