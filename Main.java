package brightnessFilter; // Folosim package-ul acesta pentru intreg proiectul, pentru a evita conflictele de denumiri.

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) { // Folosim clasa predefinita Scanner pentru captarea inputului de la user.
            do {
                ImageForFilter myImage = new ImageForFilter(); // Definim obiect nou de tipul ImageForFilter pentru poza initiala.
                ImageForFilter newImage; // Definim obiect nou de tipul ImageForFilter pentru poza finala.
                String path; // Declaram path-ul imaginii initiale.
                String answer; // In answer vom stoca optiunea cu sau fara command line. 
                boolean running = true;
                do {
                    try {
                        answer = args[3]; // Daca acesta este N, se vor ignora primele 3 argumente, intrucat citirea se va face de la tastatura.
                    } catch (Exception e) { // In cazul in care argumentele nu sunt date corect, se va afisa un mesaj pentru exceptie.
                        System.out.println("Varianta de rulare din command line este setata ca default. "
                                + "Asigurati-va ca argumentele sunt introduse corect: "
                                + "\n [FILE INPUT PATH] [BRIGHTNESS VALUE] [FILE OUTPUT PATH] "
                                + "[Cmd option: D/N] \n Alegeti N pentru introducerea de la tastatura."
                                + " Exemplu: \"./src/a.bmp\" 100 \"./src/a_modified_lighter.bmp\" D ");
                        return;

                    }
                    if (answer.equals("D")) { // Daca este selectata optiunea de command line, citim argumentul dat pentru path.
                        path = args[0];

                    } else if (answer.equals("N")) { // Daca este selectata optiunea fara command line, citim de la tastatura path.
                        System.out.println("Dati calea fisierului sau tastati 'q' pentru a inchide programul.");
                        path = scanner.nextLine();
                    } else { // In cazul in care argumentele nu sunt date corect, se va afisa un mesaj pentru exceptie.
                        System.out.println("Varianta de rulare din command line este setata ca default. "
                                + "Asigurati-va ca argumentele sunt introduse corect: "
                                + "\n [FILE INPUT PATH] [BRIGHTNESS VALUE] [FILE OUTPUT PATH] "
                                + "[Cmd option: D/N]");
                        return;

                    }

                    if ("q".equals(path)) { // Daca se introduce q de la tastatura (in modul fara cmd), se va intrerupe executia.
                        System.out.println("Programul se va inchide.");
                        return;
                    } else {
                        long beginning = System.currentTimeMillis(); // Calculam timpul citirii din fisier.
                        myImage = myImage.readImage(path); // Citim imaginea cu metoda readImage specifica clasei ImageForFilter, folosind 
                        // obiectul myImage.
                        long end = System.currentTimeMillis() - beginning;  // Oprim calcularea timpului.
                        System.out.println(" Timp citire din fisier: " + end + " ms"); // Afisam timpul de citire.
                        if (myImage != null) { // In cazul in care imaginea a fost citita, running devine false, deoarece pe baza lui 
                        	// se face prelucrarea efectiva, care se va opri la finalul procesarii.
                            running = false;
                            if (answer.equals("D")) { // Daca este setata optiunea de command line, citim valoarea de offset din argmente.
                                myImage.setBrightness(Integer.parseInt(args[1]));
                            } else { // In caz contrar, se citeste de la tastatura.
                                System.out.println("Introduceti factorul de luminozitate pe care doriti sa-l adaugati prin filtru." +
                                        "\nATENTIE! In cazul depasirii " +
                                        "limitelor admise, culoarea se va adapta automat.");
                                myImage.setBrightness(scanner.nextInt()); // Aceasta valoare este un int astfel incat limitele pixelului
                                // sa nu depaseasca 0 sau 255.
                            }

                            beginning = System.currentTimeMillis(); // Calculam timpul de procesare. 
                            newImage = Filter.applyFilter(myImage); // Aplicam functia applyFilter specifica clasei Filter.
                            end = System.currentTimeMillis() - beginning;
                            System.out.println(" Timp procesare: " + end + " ms"); // Afisam timpul de procesare. 
                            String myNewPath; // Declaram calea imaginii modificate. Aceasta poate fi calea veche, rezultand in inlocuirea imaginii.
                            if (answer.equals("D")) { // Daca se folosesc argumente, se va citi acesta.
                                myNewPath = args[2];

                            } else { // In caz contrar, se va citi de la tastatura.
                                System.out.println("Introduceti calea catre fisierul destinatie:");
                                scanner.nextLine(); // Scanner-ul trebuie avansat cu o linie, deoarece ramane in spate.
                                // Se citeste calea catre fisierul destinatie
                                myNewPath = scanner.nextLine(); 
                            }

                            beginning = System.currentTimeMillis(); // Incepem scrierea si calculam timpul necesar acesteia.
                            Filter.writeNewImage(myNewPath, newImage); // Folosim metoda de scriere din clasa Filter.
                            end = System.currentTimeMillis() - beginning; // Oprim calculul timpului.
                            System.out.println(" Timp scriere: " + end + " ms"); // Afisam timpul necesar scrierii.
                        }
                        if (answer.equals("D")) // In cazul folosirii argumentelor de la cmd line oprim executia. In caz contrar, putem 
                        	// continua si incepe o noua prelucrare.
                            return;
                    }
                } while (running);
            } while (true);
        } catch (InterruptedException e) { // In cazul unei erori, o vom capta si afisa.
            e.printStackTrace();
        }
    }
}

