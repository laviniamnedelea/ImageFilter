package brightnessFilter;

import java.io.*;

public class Filter extends ImageForFilter {

    public static ImageForFilter applyFilter(ImageForFilter image) {

        byte[] newData = image.getFileData(); // Vom stoca aici noul array de pixeli, initializand cu valorile pe care le are imaginea initiala.

        for (int j = 0; j < newData.length; j++) { // Parcurgem o singura data array-ul.
            if (image.brightness > 0) { // Verificam daca brightness-ul dat de utilizator este pozitiv.
                int val = (((int) newData[j] & 0xFF) + ((int) image.brightness & 0xFF)); // se adauga brightness-ul respectiv la valoarea initiala.
                if (val > 255) { // Daca valoarea obtinuta depaseste maximul unui byte(8 biti) de 255 (overflow), se trunchiaza la 255. 
                    newData[j] = (byte) 255;
                } else newData[j] = (byte) val; // In caz contrar, se seteaza valoarea noua.
            } else {
                int val = (((int) newData[j] & 0xFF) + ((int) image.brightness)); // Se adauga valoarea negativa a brightness-ului.
                if (val < 0) { // Daca rezula o valoare sub minimul unui byte, se produce underflow, deci vom aproxima la 0. 
                    newData[j] = (byte) 0;
                } else {
                    newData[j] = (byte) val; // In caz contrar, se seteaza valoarea noua.
                }
            }

        }
        image.setFileData(newData); // Setam bitii noii imagini, acum ea fiind modificata (avem adaugat offset-ul la biti).
        return image;

    }

    public static void writeNewImage(String newPath, ImageForFilter newImage) throws IOException { // Se scrie noua imagine.

        ByteArrayOutputStream dest = new ByteArrayOutputStream(); // Folosim un OutputStream de bytes pentru a concatena datele
        // din header cu cele din fisier.
        dest.write(newImage.getHeaderData());// Scriem datele din header.
        dest.write(newImage.getFileData()); // Scriem datele din fisier.
        byte[] dataToWrite = dest.toByteArray(); // Scriem in dataToWrite stream-ul citit anterior si il convertim la bytes.

        OutputStream outData = new BufferedOutputStream(new FileOutputStream(newPath)); // Cream un outputstream nou pentru 
        // fisierul ce va fi scris la path-ul newPath. 
        outData.write(dataToWrite); // Scriem datele citite la acel path.


    }

}
