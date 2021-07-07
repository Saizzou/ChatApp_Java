package Chat.Server;

import java.io.*;
import java.net.*;


public class KullaniciThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public KullaniciThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void calistir(){
        try{
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            ciktiKullanicilar();

            String kullaniciAdi = reader.readLine();
            server.ekleKullaniciAdi(kullaniciAdi);

            String serverMsg = "Yeni kullanici baglandi: " + kullaniciAdi;
            server.yayin(serverMsg, this);

            String clientMsg;
            do {
                clientMsg = reader.readLine();
                serverMsg = "[" + kullaniciAdi + "]" + clientMsg;
                server.yayin(serverMsg, this);
            } while (!clientMsg.equals("{cikis}"));

            server.cikarKullanici(kullaniciAdi, this);
            socket.close();

            serverMsg = kullaniciAdi + " cikis yapti.";
            server.yayin(serverMsg, this);
        } catch (IOException e) {
            System.out.println("Kullanici Thread hatasi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    void ciktiKullanicilar() {
        if (server.varKullanicilar()) {
            writer.println("Baglanan kullanicilar: " + server.getKullaniciAdlari());
        } else {
            writer.println("Baska kullanici baglanmadi.");
        }
    }
    void msgGonder(String msg){
        writer.println(msg);
    }
}
