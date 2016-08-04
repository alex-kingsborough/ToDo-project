package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer {

	static ServerGUI gui = null;
	Vector<ServerThread> serverThreads;
	boolean started = false;
	private ServerSocket ss = null;
	private static MainServer mServ;
	private int mServerPort;
	Thread serverThread;
	
	MainServer(){
		serverThreads = new Vector<ServerThread>();
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
				serverThreads.add(new ServerThread(socket,this));
			}
		} catch (Exception e) {
			for(ServerThread st : serverThreads)
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
