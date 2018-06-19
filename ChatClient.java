import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ChatClient{
    static FromStd fromStd;
    static Thread fStd;

    static Echo echo;
    static Thread e;

    static BufferedReader stdInput;
    static PrintWriter output;
    static String source = "temp";
    public static void main(String[] args){

        runAsClient(args);

    }

    public static void runAsClient(String[] args){

        try {
            int port_number= Integer.valueOf( args[0] );
            
            Socket client_socket= new Socket( "localhost", port_number );

            echo = new Echo(client_socket);
            e = new Thread(echo);
            e.start();

            fromStd = new FromStd(client_socket);
            fStd = new Thread(fromStd);
            fStd.start();
            
        }
        catch ( Exception e ){
        }

    }

}

class FromStd implements Runnable{

    BufferedReader stdInput;
    PrintWriter output;
    String source = "temp";
    Socket client_socket;
    public FromStd(Socket client_socket){
        this.client_socket = client_socket;
        try{
            //handles stdInput
            stdInput= new BufferedReader(new InputStreamReader(System.in));
            //outputs to other
            output = new PrintWriter( client_socket.getOutputStream(), true );
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    @Override 
    public void run(){
        try{
            // source = stdInput.readLine();
            while(!source.equalsIgnoreCase("NULL")){
                source = stdInput.readLine();
                output.println(source);
                
            }
            output.println(source);

        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

class Echo implements Runnable{

    BufferedReader input;
    String source = "temp";
    Socket client_socket;
    public Echo(Socket client_socket){
        this.client_socket = client_socket;
        try{
            //handles input from other
            input= new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        }
        catch (Exception e){
        }
        
    }

    @Override 
    public void run(){
        try{
            source = input.readLine();
            // System.out.println(input);
            while(!source.equalsIgnoreCase("NULL")){
                //source = input.readLine();
                System.out.println(source);
                source = input.readLine();
            }
            System.exit(0);
        }
        catch(Exception e){}
        
    }
}