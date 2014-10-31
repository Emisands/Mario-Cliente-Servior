package servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

import core.Comando;

public class NetCliente implements Runnable {

	private Socket socket;
	private PrintStream os;
	private Scanner is;
	private Thread thread;
	private Cliente cliente;

	private final static Logger LOGGER = Logger.getLogger(NetCliente.class.getName());
	
	@Override
	public void run() {
		while (true){
			int recived;
			recived = is.nextInt();
			Comando[] comandos = Comando.values();
			Comando c = comandos[recived];
			LOGGER.info("Recebido " + c.toString());
			cliente.getNetServidor().getServidor().getFase().interpretaComando(c, cliente);
		}
	}

	public NetCliente(Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
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
		thread.start();
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
		os.println(c.intValue());
	}

}
