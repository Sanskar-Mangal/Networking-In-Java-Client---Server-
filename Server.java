import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server {
    ServerSocket server;
    Socket socket;

    BufferedReader br; //read data
    PrintWriter out;   

    public Server(){

        try{
            server=new ServerSocket(3306);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting....");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch(Exception e){
            //e.printStackTrace();
            System.out.println("Connection is closed");
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
                    System.out.println("Client terminated the chat");
                    break;
                }
                System.out.println("Client :" +msg);
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
            }catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection is closed");
                }
            
        };

        //STARTING THE THREAD
        new Thread(r2).start();
    }

    public static void main(String[] arr){
        System.out.println("start server");
        new Server();
        
    }
    
    
}
