package brightnessFilter;

public interface Brightness { 
    int getX(); // Functie ce va returna latimea pozei - width. 
    int getY(); // Functie ce va returna inaltimea pozei - height. 
    byte[] getFileData(); // Functie ce va returna data corespunzatoare bitilor pozei.
    byte[] getHeaderData(); // Functie ce va returna data corespunzatoare bitilor header-ului.
    int getBrightness(); // Functie ce va returna brightness-ul setat.
    String getPath(); // Functie ce va returna path-ul pozei.
}
