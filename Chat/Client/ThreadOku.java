package Chat.Client;

import java.io.*;
import java.net.*;

public class ThreadOku extends Thread {
    private BufferedReader reader;
    private Client client;
    private Socket socket;

    public ThreadOku(Socket socket, Client client) {
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Girdi Yayininda Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void calistir() {
        while (true) {
            try {
                String cevap = reader.readLine();
                System.out.println("\n" + cevap);

                if (client.getKullaniciAdi() != null) {
                    System.out.println("[" + client.getKullaniciAdi() + "]: ");
                }
            } catch (IOException e) {
                    System.out.println("Server okuma hatasi: " + e.getMessage());
                    e.printStackTrace();
                    break;
            }
        }
    }
}
