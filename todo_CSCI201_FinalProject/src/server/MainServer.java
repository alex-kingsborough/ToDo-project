package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer implements Runnable{

	static ServerGUI gui = null;
	Vector<ServerThread> serverThreads;
	boolean started = false;
	private ServerSocket ss = null;
	private static MainServer mServ;
	private int mServerPort;
	Thread serverThread;

	public MainServer(){

	}

	public void startServer() {
		gui.writeToLog("Server started.");
		started = true;
	}

	public void stopServer() {
		gui.writeToLog("Server stopped.");
		started = false;
	}
	
	public void run(){
		/*
		 * IMPLEMENT THE GET PORT FROM FILE PART
		 */
		mServerPort = 6790;
		serverThreads = new Vector<ServerThread>();
		serverThread = new Thread();
		try {
			ss = new ServerSocket(mServerPort);
			while(true){
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					stopServer();
					e.printStackTrace();
				}
				while (started) {
					System.out.println("Here 3");
					gui.writeToLog("waiting for connection...");
					Socket s = ss.accept();
					gui.writeToLog("connection from " + s.getInetAddress());
					ServerThread st = new ServerThread(s, this);
					serverThreads.add(st);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("ioe closing ss: " + ioe.getMessage());
					stopServer();
				}
			}
		}
	}

	public static void main(String [] args){
		mServ = new MainServer();
		
		(new Thread(mServ)).start();
		
		gui = new ServerGUI(mServ);
	}
}
