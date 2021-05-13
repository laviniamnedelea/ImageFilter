package brightnessFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Consumer extends Thread { // Ambele threaduri folosesc clasa deja implementata Thread.
    protected Buffer buffer;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public Consumer(Buffer b) { // Constructor ce initializeaza bufferul la care au acces consumatorul si producatorul.
        buffer = b;
    }

    @Override
    public void start() { // Se face override pe functia de start pt a se afisa mesajul corespunzator.
        System.out.println("Starting " + getName());
        super.start();
    }

    public void run() {
        byte[] byteBlock; // Se declara byteBlock-ul primit de la producator prin buffer.
        System.out.println("Running " + getName()); //Afisam mesajul corespunzator.
        for (int i = 1; i <= 4; i++) { // Pentru fiecare sfert de informatie, apelam functia get pe buffer si afisam 
        	// faptul ca am luat acel bloc.
            byteBlock = buffer.get();
            System.out.println("Consumatorul a primit :\t" +
                    byteBlock);
            try {
                outputStream.write(byteBlock); // Facem append pe stream-ul de date final pentru fiecare sfert.
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        
        buffer.input = outputStream.toByteArray(); // Convertim output stream-ul la bytes.
        System.out.println("Going dead " + getName()); // Afisam faptul ca thread-ul a murit.
    }
}
