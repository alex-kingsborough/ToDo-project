package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer {

	static ServerGUI gui = null;
	Vector<ClientConnection> clientConnections;
	boolean started = false;
	private ServerSocket ss = null;
	private static MainServer mServ;
	private int mServerPort;
	Thread serverThread;
	
	MainServer(){
		clientConnections = new Vector<ClientConnection>();
		mServerPort = 6789;
	}

	public void startServer() {
		gui.writeToLog("Server started.");
		serverThread = new Thread(()->{run();});
		serverThread.start();
	}

	public void stopServer() {
		serverThread.interrupt();
		try { ss.close(); } catch (IOException e) { }
	}
	
	public void run(){
		/*
		 * TODO: Implement the server configuration from file.
		 */
		try {
			ss = new ServerSocket(mServerPort);
			gui.writeToLog("Server Started On Port:" + mServerPort);
			while(true) {
				Socket socket = ss.accept();
				clientConnections.add(new ClientConnection(socket,this));
			}
		} catch (Exception e) {
			for(ClientConnection st : clientConnections)
				try { st.getSocket().close(); } catch (IOException e1) { }
			gui.writeToLog("Server stopped.");
		} finally {
			try {
				if(ss != null) ss.close();
			} catch (IOException e) {
			}
		}
	}

	public static void main(String [] args){
		mServ = new MainServer();
		
		gui = new ServerGUI(mServ);
	}
}
