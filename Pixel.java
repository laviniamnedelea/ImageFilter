package brightnessFilter;

public abstract class Pixel implements Brightness { // Clasa va implementa interfata Brightness.
	
    protected int x; // Fiecare pixel va avea coordonatele imaginii x si y corespunzatoare.
    protected int y;
    public int bitsPerPixel;

    public abstract void setX(byte b1, byte b2, byte b3, byte b4); // Functie ce seteaza x - setter. 

    public abstract void setY(byte b1, byte b2, byte b3, byte b4); // Functie ce seteaza y - setter.

    public abstract int getX(); // Getter pentru x.

    public abstract int getY(); // Getter pentru y.

    public abstract void setBitsPerPixel(byte b1, byte b2); // Se seteaza numarul de biti pe fiecare pixel. In cazul nostru ar trebui sa fie 256.

    Pixel(int x, int y, int bitsPerPixel) { // Constructor cu parametri pentru clasa Pixel.
        this.x = x;
        this.y = y;
        this.bitsPerPixel = bitsPerPixel;
    }

    Pixel() { // Constructor fara parametri pentru clasa Pixel.
        this.x = 0; // valori default.
        this.y = 0;
        this.bitsPerPixel = 0;
    }
}
