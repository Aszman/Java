import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadExample {

    static AtomicInteger count = new AtomicInteger(0);
    static Semaphore sem = new Semaphore(0);

    // lista plików do pobrania
    static String [] toDownload = {
            "https://home.agh.edu.pl/~pszwed/wyklad-c/01-jezyk-c-intro.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf",
    };

    static class Downloader implements Runnable{

        private final String url;

        Downloader(String url){
            this.url = url;
        }

        @Override
        public void run() {
            String fileName = url.substring(url.lastIndexOf('/') + 1);

            try(InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream(fileName) ){
                for(;;){
                    int b = in.read();
                    if(b<0){
                        break;
                    }

                    out.write(b);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Done:"+fileName);

            count.incrementAndGet();
            sem.release();
        }
    }

    static void sequentialDownload(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Downloader(url).run();
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
    }

    static void concurrentDownload(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
    }

    static void concurrentDownload2(){
        count= new AtomicInteger(0);

        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }

        while(!count.compareAndSet(toDownload.length,0)){
            // wait...
            Thread.yield();
        }

        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
    }

    static void concurrentDownload3(){
        sem = new Semaphore(0);
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }

        try {
            sem.acquire(toDownload.length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
    }

    public static void main(String[] args){
        System.out.println("Pobieranie sekwencyjne:");
        sequentialDownload();

        System.out.println("\nPobieranie współbieżne ze zliczaniem:");
        concurrentDownload2();

        System.out.println("\nPobieranie współbieżne z semaforem:");
        concurrentDownload3();
    }
}
