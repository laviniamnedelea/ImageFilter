package brightnessFilter;

// Clasa este abstracta. Implementam functiile getBrightness() si getPath() in clasa derivata din aceasta, ImageForFilter. 
public abstract class Image extends Pixel { // Clasa abstracta Image extinde clasa abstracta Pixel.

    protected byte[] headerData; // Avem data pentru header (bitii).
    protected byte[] fileData; // Data pentru fisier.

    
    public abstract void setBrightness(int brightness);
    public abstract int getBrightness();
    

    Image(int x, int y, int bitsPerPixel, byte[] fileData, byte[] headerData) { // Constructor cu parametrii.
        super(x, y, bitsPerPixel); // X si y sunt specifici clasei parinte, Pixel. Ele totusi sunt atribute mostenite si de clasa Image.
        this.fileData = fileData; // Setam data pentru fisier.
        this.headerData = headerData; // Setam data pentru header.
    }

    Image() { // Constructor fara parametri.

        super(); // Folosim constructorul fara parametri al clasei parinte.
        this.fileData = null; // valori default.
        this.headerData = null;
    }

    public void setFileData(byte... data) { // Folosim varargs pentru data.
        int k = 0;
        byte[] auxData = new byte[data.length]; // auxData este o variabila auxiliara pentru datele noastre.
        if (data != null) { // Daca data nu este null.
            for (byte b : data) { // Pentru fiecare byte vom pune acest byte in auxData, pe pozitii incrementate prin indexul k.
                auxData[k] = b;
                k++;
            }
            this.fileData = auxData; // Setam atributul de date, specific clasei Image.
        }
    }

    public void setHeaderData(byte[] headerData) {
        this.headerData = headerData; // Setam atributul de header, specific clasei Image.
    }

    public static int fourByteReaderTool(byte b1, byte b2, byte b3, byte b4) { // Metoda ce citeste valoarea binara avand 4 bytes b1..b4 in aceasta ordine.
        return (b4 << 24) & 0xFF000000 | (b3 << 16) & 0x00FF0000 | (b2 << 8) & 0x0000FF00 | (b1 << 0) & 0x000000FF;
    }

    public static int twoByteReaderTool(byte b1, byte b2) { // Metoda ce citeste valoarea binara avand 2 bytes b1, b2 in aceasta ordine.
        return ((b2 & 0xFF) << 8) | (b1 & 0xFF);
    }

    @Override
    public void setX(byte b1, byte b2, byte b3, byte b4) {  // Setter pentru width, folosind functia ce citeste de pe 4 bytes.
        this.x = fourByteReaderTool(b1, b2, b3, b4);
    }

    @Override
    public void setY(byte b1, byte b2, byte b3, byte b4) { // Setter pentru height, folosind functia ce citeste de pe 4 bytes.
        this.y = fourByteReaderTool(b1, b2, b3, b4);
    }

    @Override
    public void setBitsPerPixel(byte b1, byte b2) { // Setter pentru bitsPerPixel, folosind functia ce citeste de pe 2 bytes.
        this.bitsPerPixel = twoByteReaderTool(b1, b2);
    }

    @Override
    public int getX() { // Getter pentru width.
        return x;
    }

    @Override
    public int getY() { // Getter pentru height.
        return y;
    }

    public byte[] getFileData() { // Getter pentru data fisierului efectiv.

        return fileData;
    }

    public byte[] getHeaderData() { // Getter pentru header-ul fisierului.
        return headerData;
    }

}
