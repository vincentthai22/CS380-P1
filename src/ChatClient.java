/**
 * VINCENT THAI
 * CS 380
 * Project 1
 * ChatClient.JAVA
 * PROFESSOR DAVARPANAH
 * SPRING 2017
 */
import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Vincent on 4/11/2017.
 */
public class ChatClient implements  Runnable{
    String host;
    int port;

    public ChatClient(String host, int port){
        this.host = host;
        this.port=port;
    }

    public static void main(String[] args) {
        new Thread (new ChatClient("codebank.xyz", 38001)).start();
        new Thread(new ReceiverClient("codebank.xyz", 38001)).start();
    }


    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            System.out.println("Enter Username");
            while(true) {
                out.println(stdIn.readLine());
            }
        } catch (NullPointerException e){
            System.out.println("Failed to connect.. try again.");
        } catch (IOException e){

        }
    }
}
class ReceiverClient implements Runnable {
    String host;
    int port;
    public ReceiverClient(String host, int port){
        this.host = host;
        this.port=port;
    }

    @Override
    public void run() {
        String temp="";
        try (Socket socket = new Socket(host, port)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            out.println("Receiver" + new Random().nextInt());
            while(true) {
                temp = br.readLine();
                if (temp.equals("null"))
                    throw new Exception("Session expired. Please reconnect");
                System.out.println(temp);
            }
        } catch (NullPointerException e){
            System.out.println("Connection Lost");
        } catch (IOException e){

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
