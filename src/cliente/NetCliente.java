package cliente;

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
	private Cliente cliente;
	private Thread thread;

	private final static Logger LOGGER = Logger.getLogger(NetCliente.class.getName());

	@Override
	public void run() {
		boolean fim = false;
		LOGGER.info("Cliente Aguardando dados");
		while (true) {
			System.out.println("Cliente Aguardando dados");
			System.out.flush();
			try {
				int read = is.nextInt();
				LOGGER.info("Recebido " + read + " do servidor");
				
				switch (read) {
				case -1: // mario
					cliente.getMario().getPosicao()
							.setX(socket.getInputStream().read());
					cliente.getMario().getPosicao()
							.setY(socket.getInputStream().read());
					break;
				case -2: // luigi
					cliente.getLuigi().getPosicao()
							.setX(socket.getInputStream().read());
					cliente.getLuigi().getPosicao()
							.setY(socket.getInputStream().read());
					break;
				case -3: // fim
					cliente.fim();
					fim = true;
					break;
				}
			} catch (IOException e) {
				System.exit(1);
			}
		}
	}

	public NetCliente(String ip, Cliente cliente) {
		// conecta com o servidor
		this.cliente = cliente;
		try {
			socket = new Socket(ip, 8080);
			os = new PrintStream(socket.getOutputStream(), true);
			is = new Scanner(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
		LOGGER.info("Conectado com o host");

		thread = new Thread(this);
		thread.setDaemon(true);
		
		thread.start();
	}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		LOGGER.info("NetCliente finalizando");
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
	public void envia(Comando c) {
		LOGGER.info("Eviando " + c.toString());
		os.print(c);
	}

}
