package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

	private ObjectOutputStream mOutputWriter;
	private ObjectInputStream mInputReader;
	private MainServer ms;
	boolean echo = false;

	public ServerThread(Socket s, MainServer mainServer) {
		ms = mainServer;
		try{
			mOutputWriter = new ObjectOutputStream(s.getOutputStream());
			mInputReader = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(String line) {
		try {
			mOutputWriter.writeObject(line);
			mOutputWriter.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void run() {
		try {

			//In the chat client this is where the thread constantly reads in objects.
			while(true){
				Object o = mInputReader.readObject();
				if(o instanceof String){
					String s = (String) o;
					if(s.startsWith("req: ")){
						if(s.endsWith("echo")){
							echo = !echo;
						}
					}
					ms.gui.writeToLog("Message from Server Thread: " + this.getName() + "Message: " + s);
					if(echo){
						sendMessage("Server Echo: " + s);
					}
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}
}


