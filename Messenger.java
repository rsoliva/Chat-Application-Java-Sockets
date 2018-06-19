//Server
//thread for accepting
//build list of clients
//create buffer of messages

//thread for listening
//thread for echoing

public class ChatClient {

	Thread acceptC;
	Thread listener;
	Thread relayM;

	AcceptClient acceptClient;

	public ChatClient(){
		runServer();
	}

	public void runServer(){
		try	{
			int port_number= Integer.valueOf( args[1] );
			ServerSocket server_socket= new ServerSocket( port_number );

			acceptClient = new AcceptClient(server_socket);
			acceptC = new Thread(acceptClient);
			acceptC.start();

			Socket client_socket= server_socket.accept();

			server_socket.close();

			fromStd = new FromStd(client_socket);
			fStd = new Thread(fromStd);

			echo = new Echo(client_socket);
			e = new Thread(echo);

			fStd.start();
			e.start();

			fStd.join();
			e.join();
			
		}
		catch ( Exception e ){
			System.out.println( e.getMessage() );
		}
	}
}

class AcceptClient implements Runnable {
	ArrayList<Socket> sockets = new ArrayList<Socket>();
	ServerSocket server;

	public AcceptClient(Socket serverSocket){
		server = serverSocket;
	}

	@Override
	public void run(){
		while(true){
			try{
				sockets.add(server.accept());
			}
			catch{}
		}
	}

}

class Listener implements Runnable {

	ArrayList<String> messages = new ArrayList<String>();
	public Listener(){

	}

	public void run(){

	}


}