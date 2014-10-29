package servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

public class NetCliente implements Runnable {

	private Socket socket;
	private PrintStream os;
	private Scanner is;
	private Thread thread;

	private final static Logger LOGGER = Logger.getLogger(NetCliente.class.getName());
	
	@Override
	public void run() {
		while (true){
			int recived;
			recived = is.nextInt();
			LOGGER.info("Recebido " + recived);
		}
	}

	public NetCliente(Socket socket) {
		this.socket = socket;
		try {
			os = new PrintStream(socket.getOutputStream(), true);
			is = new Scanner(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
		
		thread = new Thread(this);
		thread.setDaemon(true);
		}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			os.close();
			is.close();
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		super.finalize();
	}

	/**
	 * Envia comandos para o servidor
	 * 
	 * @param c
	 */
	public void envia(Integer c) {
		LOGGER.info("Server enviando " + c);
		os.print(c);
	}
	
	public void start() {
		thread.start();
	}

}
