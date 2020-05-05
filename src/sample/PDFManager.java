package sample;

import javafx.scene.control.Control;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        pdDoc.getNumberOfPages();
        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());
        Text = pdfStripper.getText(pdDoc);
//        Text=Text.replaceAll("[^A-Za-z0-9. ]+", "");
        String appDir= Controller.directoryName;
        Text=Text.replaceAll("test.mp4", "#");
        int occurences=findOccurencesOf("#",Text);
        System.out.println("No of songs added to the library : "+occurences);
        String[] arrOfStr = Text.split("#", occurences+1);
//        Text=Text.replaceAll("\\s+", "@");

        for(int i=0;i<occurences;i++)
        {
            System.out.println("Value at :::"+i+" ::::"+arrOfStr[i]);
        }

        int flag=0;
        File appTempDirectory =new File(appDir+"/Karaoketemp");
        if(!appTempDirectory.exists())
        {
          appTempDirectory.mkdir();
        }

        File file=new File(appDir+"/Karaoketemp/songsLibraryFile.txt");
        FileWriter myWriter = new FileWriter(appDir+"/Karaoketemp/songsLibraryFile.txt");
        if (file.exists())
        {
            System.out.println("Exists");
            fileExistsAddSongsToTheFile(file);
        }
        else
        {
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

    public void fileExistsAddSongsToTheFile( File file)
    {


    }

    public void fileDoenotExistsMakeNewOne()
    {


    }

    public int findOccurencesOf(String str,String main)
    {
        int count =0;
        for(int i=0;i<main.length();i++)
        {
            System.out.println("----->>>"+str.charAt(0));
            if(main.charAt(i)==str.charAt(0))
            {
             count++;
            }


        }

        return count ;


    }


}
