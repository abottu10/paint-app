import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

import javax.swing.JFrame;

public class PaintConnector {

	static String hostName = "127.0.0.1";
	static int portNumber = 4444;
	static Socket clientSocket;
	static PrintWriter out;
	static BufferedReader in;
	static InputStreamReader ir;
	
	public static void main(String[] args) {

		SimplePaint s = new SimplePaint();
		String input;
		int[] cords;
		try{
			clientSocket = new Socket(hostName, portNumber);
			//create our io streams
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			ir = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(ir);
			while(clientSocket.isConnected()){
				input = in.readLine();
				cords = split(input);
				System.out.print(cords[0] + " " + cords[1] + " " + cords[2] + " " + cords[3] + "\n");
				// insert function that can take coordinates to update paint  
			}
			
		}catch(UnknownHostException e){
			System.exit(1);
		}catch(IOException e){
			System.exit(1);
		}
		if(clientSocket.isClosed()){
			closeConnection();
		}
	}
	
	// constructor
	public PaintConnector(){
		
	}
	
	// function used to send data to server
	public void send(int x1, int y1, int x2, int y2){
		String line = x1 + ":" + y1 + ":" + x2 + ":" + y2;
		out.println(line);
	}
	
	// function used to parse incoming data
	public static int[] split (String str){
		int[] cords = new int[4];
		int itt = 0;
		String subStr = "";
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) != ':'){
				subStr += str.charAt(i);
			} else {
				cords[itt] = Integer.parseInt(subStr);
				subStr = "";
				itt++;
			}
		}
		cords[3] = Integer.parseInt(subStr);
		return cords;
	}
	
	// function use to properly close connection with server
	public static void closeConnection(){
		try{
			out.close();
			in.close();
			clientSocket.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}