package paint_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class paint_server{
	// store client output streams
	ArrayList outputStreams;

	public static void main(String[] args){
		paint_server s = new paint_server();
	}
	
	public paint_server(){
		Thread t = new Thread(new server());
		t.start();
	}
	
	// function creates thread for each client
	public class client implements Runnable {
		BufferedReader reader;
		Socket sock;
		PrintWriter write;
		
		public client(Socket clientSock, PrintWriter user){
			write = user;
            try 
            {
                sock = clientSock;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
            	ex.printStackTrace();
            }
		}
		
		@Override
		public void run(){
			String message = " ";
			try {
				while(message != null){
					message = reader.readLine();
					pushMessage(message);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
	
	// function used to wait on clients
	public class server implements Runnable{
		@Override
		public void run(){
			outputStreams = new ArrayList();
			System.out.println("hi");
			try{
				ServerSocket serverSocket = new ServerSocket(4444);				
				while(true){
					Socket clientSock = serverSocket.accept();
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
					outputStreams.add(writer);
					
					Thread listener = new Thread(new client(clientSock, writer));
					listener.start();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// function used to update all clients on changes that were made
	public void pushMessage(String message){
		System.out.println(message);
		Iterator it = outputStreams.iterator();
		
		while(it.hasNext()){
			try{
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
