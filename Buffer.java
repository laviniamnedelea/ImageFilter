package brightnessFilter;

//Clasa Buffer cu sincronizare
class Buffer extends ImageForFilter {

    public Buffer(long length) { // Bufferul va fi initializat cu lungimea imaginii pe care o citim
        // chiar din functia de readImage.
        this.length = length;
    }

    protected long length; // Lungimea pozei.
    private boolean available = false; // Initial producatorul nu a pus nicio valoare in buffer, deci available este false.
    byte[] byteBlock = new byte[0]; // Vom stoca sferturile de informatie (de poza) aici.

    public synchronized byte[] get() {
        while (!available) { // Cat timp nu se pune valoare in buffer, se asteapta.
            try {
                wait();// Se va astepta producatorul sa puna o valoare.
            } catch (InterruptedException e) { // Se capteaza exceptia, cand aceasta apare.
                e.printStackTrace();
            }
        }
        available = false;// Available devine fals deoarece s-a luat valoarea din buffer, deci acesta poate fi considerat "gol".
        notifyAll(); // Consumatorul va "anunta" ca a luat o valoare.
        return byteBlock; // Se returneaza valoarea luata din buffer.
    }


    public synchronized void put(byte[] byteBlock) {
        while (available) { // Cat timp s-a pus deja o valoare in buffer, se asteapta.
            try {
                wait(); // Asteapta consumatorul sa preia valoarea
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.byteBlock = byteBlock; // In byteblock se stocheaza valoarea/data pusa de producator.
        available = true; // S-a pus o valoare in buffer.
        notifyAll(); // Producatorul va "anunta" ca a pus o valoare.
    }
}
