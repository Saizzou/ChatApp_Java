package Chat.Client;

import java.io.*;
import java.net.*;

public class ThreadYaz extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public ThreadYaz(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Cikti Yayininda Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void calistir() {
        Console console = System.console();

        String kullaniciAdi = console.readLine("\nKullanici Adinizi girin: ");
        client.setKullaniciAdi(kullaniciAdi);
        writer.println(kullaniciAdi);

        String text;

        do {
            text = console.readLine("[" + kullaniciAdi + "]: ");
            writer.println(text);
        } while (!text.equals("{cikis}"));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Server yazdirma Hatasi: " + e.getMessage());
        }
    }
}
