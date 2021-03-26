package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class Server
{

        static void Log(String msg) {
            System.out.println(msg);
        }

        public static void main(String[] args) throws IOException {

            System.in.read();


            ServerSocket listener = null;
            boolean isGuessed = false;

            String[] secretsWords = new String[]{"dog","cat","slave","gays","dolphin"};
            int indexOfSecret;
            String secret = null;
            String question = null;
            String[] questions  = new String[]{"gavkaet i shluha","myavkaet i toje shluha","fucking ...","skorohod and others","jotaros love of all life"};
            String guessedWord = null;

            String letter = null;

            try {
                listener = new ServerSocket(37152, 1, InetAddress.getByName("127.0.0.1"));
                Log("server is started");
            } catch (Exception e) {
                Log("failed to start server: " + e.getMessage());
                return;
            }

            while (true) {


                Log("server is listening");

                Socket talking = null;

                boolean isRun = false;

                DataInputStream in = null;
                DataOutputStream out = null;

                try {
                    talking = listener.accept();
                    Log("client is connected");

                    in = new DataInputStream(talking.getInputStream());
                    out = new DataOutputStream(talking.getOutputStream());
                    isRun = true;
                } catch (Exception e) {
                    Log("client error: " + e.getMessage());
                    continue;
                }



                Random rand = new Random();

                while (isRun) {
                    try {
                        String request = in.readUTF();
                        Log("from client: " + request);

                        String response = "";

                        switch (request) {
                            case "/hello":
                                response = "hi";
                                break;
                            case "/start":
                                indexOfSecret = rand.nextInt(5);
                                secret = secretsWords[indexOfSecret-1];
                                question = questions[indexOfSecret-1];

                                for (int i = 0; i < secret.length(); i++) {
                                    guessedWord+="*";
                                }

                                response = question+"\n==========\n"+guessedWord;
                                break;

                            case "/question":
                                response = question+"\n==========\n"+guessedWord;
                                break;

                            default:
                                if (request.length()==9){
                                    for (int i = 0; i < secret.length(); i++) {
                                        if (request.charAt(request.length()-1)==secret.charAt(i)){
                                            isGuessed = true;

                                            char[] temp = guessedWord.toCharArray();

                                            temp[i] = request.charAt(request.length()-1);

                                            guessedWord=String.valueOf(temp);
                                        }else {isGuessed=false;}

                                        if (isGuessed){
                                            response = "yes\n"+guessedWord;
                                        }else {response="no\n"+guessedWord;}
                                    }
                                }else if (request.length()>=9){

                                    if (request.substring(7)==secret){
                                        response = "congratulation, you win. Guesses word is "+secret;
                                    }else {
                                        response = "no"+"\n"+guessedWord;
                                    }
                                }
                                break;
                        }

                        out.writeUTF(response);
                        Log("to client: " + response);
                    }
                    catch (Exception e){
                        Log("client  error: "+e.getMessage());
                        isRun = false;
                    }
                }

                talking.close();

            }

        }
    }


