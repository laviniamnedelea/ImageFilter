package brightnessFilter;

import java.io.*;
import java.util.Arrays;

public class ImageForFilter extends Image { // Clasa ImageForFilter implementeaza clasa abstracta Image.

    protected String path; // Path-ul imaginii. 
    protected int brightness; // Offset-ul adaugat.
    protected long length; // Lungimea imaginii.
    protected static InputStream myInputFile; // Locatia de unde vom citi imaginea se va seta sub forma unui cursor la inceputul acesteia, urmand
    // ca ea sa fie citita prin intermediul thread-urilor.
    protected byte[] input; // In input vom avea imaginea ce a fost citita in cate un sfert, pe rand, folosind thread-uri.

    ImageForFilter(int x, int y, int bitsPerPixel, byte[] fileData, byte[] headerData, String path, int brightness) { 
    	// Constructor cu parametri pentru ImageForFilter.
        super(x, y, bitsPerPixel, fileData, headerData); // Se apeleaza constructorul cu parametri din clasa parinte.
        this.brightness = brightness;
        this.path = path;
    }

    ImageForFilter() { // Constructor fara parametri.
        super(); // Se apeleaza constructorul fara parametri din clasa parinte.
        this.path = null;
        this.brightness = 0;
    }

    public void setPath(String path) { // Setter pentru path.
        this.path = path;
    }

    public String getPath() { // Getter pentru path.
        return path;
    }
    
    @Override
    public void setBrightness(int brightness) { // Setter pentru brightness.
        this.brightness = brightness;
    }
    
    @Override
    public int getBrightness() { // Getter pentru brightness.
        return brightness;
    }


    protected ImageForFilter readImage(String path) throws IOException, InterruptedException {

        File myFile = new File(path); // Imaginea citita de la calea data de path.
        myInputFile = new FileInputStream(myFile); // Imaginea sub forma de FileInputStream - mai exact, un cursor spre startul imaginii.
        ImageForFilter myImage = new ImageForFilter(); // imaginea care va fi returnata(ce am citit)

        length = myFile.length(); // Aflu lungimea fisierului inainte de citire.

        Buffer b = new Buffer(length); // Bufferul la care vor avea acces Producer si Consumer.
        Producer p1 = new Producer(b); // Obiect nou Producer.
        Consumer c1 = new Consumer(b); // Obiect nou Consumer.
        p1.start(); // Pornim simultan executia celor doua threaduri.
        c1.start();
        p1.join(); // Asteptam sa se "aduca" rezultatul de la thread-uri pentru a continua firul executiei.
        c1.join();

        input = c1.buffer.input; // In input vom avea imaginea citita.

        myImage.setBitsPerPixel(input[28], input[29]); // Se seteaza numarul de biti pe fiecare pixel. In cazul nostru ar trebui sa fie 24.
        if ((input[0] == 'B' && input[1] == 'M') && (myImage.bitsPerPixel == 24)) { // Se verifica conditiile: imagine de tip bmp
        	// pe 24 de biti.
            int fileLength = fourByteReaderTool(input[2], input[3], input[4], input[5]); // Citim lungimea fisierului stocata pe 4 bytes folosind functia creata anterior.
            int headerLength = fourByteReaderTool(input[14], input[15], input[16], input[17]); // Citim lungimea headerului stocata pe 4 bytes folosind functia creata anterior.
            myImage.setX(input[18], input[19], input[20], input[21]); // Latimea pozei- width. 
            myImage.setY(input[22], input[23], input[24], input[25]); // Inaltimea pozei- height.

            byte[] fileHeader = new byte[headerLength]; // Cream un nou array de bytes pentru datele din header.
            byte[] fileData = new byte[fileLength]; // Cream un nou array de bytes pentru datele din fisier.

            myImage.setHeaderData(Arrays.copyOfRange(input, 0, headerLength)); // Setam data din header, de la inceputul bitilor cititi, pana
            // la lungimea header-ului.
            myImage.setFileData(Arrays.copyOfRange(input, headerLength, fileLength)); // Setam data din fisier, de la inceputul bitilor cititi, pana
            // la lungimea file-ului.
            myImage.setPath(path); // Setam path-ul citit.
            
        } else {
            System.out.println("Fisierul trebuie sa aiba extensia bmp si sa fie pe 24 de biti!"); // In cazul in care nu se verifica conditia, se
            // afiseaza un mesaj de eroare.
        }
        return myImage; // Se va returna imaginea citita, de tipul ImageForFilter, care va avea atributele necesare setate.
    }
}
