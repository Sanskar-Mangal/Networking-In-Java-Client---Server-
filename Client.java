import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;

    BufferedReader br; //read data
    PrintWriter out;   //write data

    public Client(){
        try{
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",3306);
            System.out.println("connection done");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void startReading() throws IOException{
        //thread read karke deta rahega

        Runnable r1=()->{
            System.out.print("reader started..");
            try{
             while(true){

                
                String msg=br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat");
                    socket.close();
                    break;
                }
                System.out.println("Server :" +msg);
            }
        }catch(Exception e){
                //e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };
        //STARTING THE THREAD
        new Thread(r1).start();
    }

    public void startWriting(){
        //thread - data user lega and the send karega client tak

        Runnable r2=()->{
            System.out.println("Writer Started");
            try{
            while(true && !socket.isClosed()){
                
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();

                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                            socket.close();
                            break;
                    }
                }
                System.out.println("Connection is closed");
            }catch(Exception e){
                    e.printStackTrace();
                    //System.out.println("Connection is closed");
                }
            
        };

        //STARTING THE THREAD
        new Thread(r2).start();
    }
    public static void main(String[] arr){
        System.out.println("Starting client");
        new Client();
    }
    
}
