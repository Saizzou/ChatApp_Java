package Chat.Client;

import java.io.*;
import java.net.*;

public class Client {
    private String hostname;
    private int port;
    private String kullaniciAdi;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void calistir(){
        try{
            Socket socket = new Socket(hostname, port);
            System.out.println("Server'e baglanti saglandi");

            new ThreadOku(socket, this).start();
            new ThreadYaz(socket, this).start();
        } catch (UnknownHostException e) {
            System.out.println("Server bulunamadi: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Hatasi:" + e.getMessage());
        }
    }

    void setKullaniciAdi(String kullaniciAdi){
        this.kullaniciAdi = kullaniciAdi;
    }

    String getKullaniciAdi(){
        return this.kullaniciAdi;
    }

    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(hostname, port);
        client.calistir();
    }
}
