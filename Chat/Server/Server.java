package Chat.Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    private int port;
    private Set<String> kullaniciAdlari = new HashSet<>();
    private Set<KullaniciThread> kullaniciThreads = new HashSet<>();

    public Server(int port) {
        this.port = port;
    }

    public void calistir() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serverin dinledigi port: " + port);

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Yeni kullanici baglandi");

                KullaniciThread yeniKullanici = new KullaniciThread(socket, this);
                kullaniciThreads.add(yeniKullanici);
                yeniKullanici.start();
            }
        } catch (IOException e) {
            System.out.println("Server hatasi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: Java ChatApp Server <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        Server server = new Server(port);
        server.calistir();
    }

    void yayin(String msg, KullaniciThread hariciKullanici) {
        for (KullaniciThread secKullanici : kullaniciThreads) {
            if (secKullanici != hariciKullanici){
                secKullanici.msgGonder(msg);
            }
        }
    }

    void ekleKullaniciAdi(String kullaniciAdi) {
        kullaniciAdlari.add(kullaniciAdi);
    }

    void cikarKullanici(String kullaniciAdi, KullaniciThread secKullanici){
        boolean cikarildi = kullaniciAdlari.remove(kullaniciAdi);
        if (cikarildi) {
            kullaniciThreads.remove(secKullanici);
            System.out.println("Kullanici " + kullaniciAdi + "Serveri terk etti.");

        }
    }

    Set<String> getKullaniciAdlari(){
        return this.kullaniciAdlari;
    }
    boolean varKullanicilar() {
        return !this.kullaniciAdlari.isEmpty();
    }
}