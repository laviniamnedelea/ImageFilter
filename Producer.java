package brightnessFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class Producer extends Thread { // Ambele threaduri folosesc clasa deja implementata Thread.
    private Buffer buffer;
    private byte result[];

    public Producer(Buffer buffer) { // Constructor ce initializeaza bufferul la care au acces consumatorul si producatorul.
        this.buffer = buffer;
    }

    @Override
    public void start() { // Se face override pe functia de start pt a se afisa mesajul corespunzator.
        System.out.println("Starting " + getName());
        super.start();
    }

    public void run() {
        System.out.println("Running " + getName()); // Afisam mesaj corespunzator.
        long size = 4096; // Lungimea unui "chunk" de date citit este de 4096, si va fi incrementat 
        // pana ce citim tot "sfertul" de imagine.
        for (int i = 1; i <= 4; i++) { // Crestem indexul cu cate un bloc de fiecare data, avand in total 4 blocuri de date. 
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
            while (size < i * buffer.length / 4) { // Size va ajunge sa fie egal cu lungimea fiecarui sfert, numarul de ordine
            	// al "sfertului" fiin dat de i.
                byte[] block = new byte[4096]; // Cream un nou array  de 4096 de bytes.
                try {
                    buffer.myInputFile.read(block); // Citim respectivul bloc de la cursorul dat de input stream.
                } catch (IOException e) {
                    e.printStackTrace();
                }
                size += 4096; // Incrementam mereu size-ul cu cati bytes am citit (deci cate 4096).

                try {
                    outputStream.write(block); // Se face append in outputstream la blocul citit.
                } catch (IOException e) {
                    e.printStackTrace();
                }

                result = outputStream.toByteArray(); // Se converteste outputstream-ul in bytes.
            }

            // cand se termina citirea a blocului de bytes
            buffer.put(result); // Se pune blocul/sfertul citit in buffer.
            System.out.println("Producatorul a pus :\t" + result); // Se afiseaza mesajul corespunzator.
            try {
                sleep((int) (Math.random() * 100)); // Se asteapta.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Going dead " + getName()); // Afisam mesajul in momentul in care thread-ul moare.
    }
}