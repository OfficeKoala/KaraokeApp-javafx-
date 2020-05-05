package sample;

import javafx.scene.control.Control;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class PDFManager {

    private PDFParser parser;
    private PDFTextStripper pdfStripper;
    private PDDocument pdDoc;
    private COSDocument cosDoc;

    private String Text;
    private String filePath;
    private File file;

    public PDFManager() {

    }

    public String toText() throws IOException {
        this.pdfStripper = null;
        this.pdDoc = null;
        this.cosDoc = null;

        file = new File(filePath);
        parser = new PDFParser(new RandomAccessFile(file, "r"));
        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        pdDoc.getNumberOfPages();
        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());
        Text = pdfStripper.getText(pdDoc);
//        Text=Text.replaceAll("[^A-Za-z0-9. ]+", "");
        String appTempDir = Controller.directoryName;
        String appMainDir = Controller.appDirectory;
        Text = Text.replaceAll("test.mp4", "#");
        int occurences = findOccurencesOf("#", Text);
        Text= Text.replaceAll("[0-9]*$", "");
        System.out.println("No of songs added to the library : " + occurences);
        String[] arrOfStr = Text.split("#", occurences + 1);
//        Text=Text.replaceAll("\\s+", "@");



        for (int i = 0; i < occurences; i++) {

            String songName=arrOfStr[i].substring(0,arrOfStr[i].length()-4);

            System.out.println("___SONG__: "+songName);
            File songDirectory = new File(Controller.directoryName + "/karaokeApp/song" + i);
            File song = new File(Controller.directoryName + "/karaokeApp/song" + i + "/songDetails.txt");

            if (!songDirectory.exists()) {
                songDirectory.mkdir();
                if (!song.exists()) {
                    song.createNewFile();
                }


            }

            Song songDetails = new Song(arrOfStr[i], "SomeArtist");
            FileOutputStream fileOut = new FileOutputStream(Controller.directoryName + "/karaokeApp/song" + i + "/songDetails.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(songDetails);
            objectOut.close();

//            FileWriter songWriter = new FileWriter();
//            songWriter.write(arrOfStr[i]);

        }


        int flag = 0;
        File appTempDirectory = new File(appTempDir + "/Karaoketemp");
        if (!appTempDirectory.exists()) {
            appTempDirectory.mkdir();
        }

        File file = new File(appTempDir + "/Karaoketemp/songsLibraryFile.txt");
        FileWriter myWriter = new FileWriter(appTempDir + "/Karaoketemp/songsLibraryFile.txt");
        if (file.exists()) {
            System.out.println("Exists");
            fileExistsAddSongsToTheFile(file);
        } else {
            System.out.println("Does not Exists");
            file.createNewFile();
            myWriter.write(Text);
            myWriter.close();
        }


        return Text;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public PDDocument getPdDoc() {
        return pdDoc;
    }

    public void fileExistsAddSongsToTheFile(File file) {


    }

    public void fileDoenotExistsMakeNewOne() {


    }

    public int findOccurencesOf(String str, String main) {
        int count = 0;
        for (int i = 0; i < main.length(); i++) {
            if (main.charAt(i) == str.charAt(0)) {
                count++;
            }

        }
        return count;


    }


}
