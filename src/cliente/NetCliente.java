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

	private final static Logger LOGGER = Logger.getLogger(NetCliente.class
			.getName());

	@Override
	public void run() {
		boolean fim = false;
		
		LOGGER.info("Cliente Aguardando dados");
		while (!fim) {
			System.out.println("Cliente Aguardando dados");
			System.out.flush();
			int read = is.nextInt();
			LOGGER.info("Recebido " + read + " do servidor");

			switch (read) {
			case -1: // mario
				cliente.getMario().getPosicao().setX(is.nextInt());
				cliente.getMario().getPosicao().setY(is.nextInt());
				break;
			case -2: // luigi
				cliente.getLuigi().getPosicao().setX(is.nextInt());
				cliente.getLuigi().getPosicao().setY(is.nextInt());
				break;
			case -3: // fim
				cliente.fim();
				fim = true;
				break;
			default:
				LOGGER.info("Valor inesperado, ignorando");
			}
			LOGGER.info("Cliente Autlaizado:\t" + cliente.toString() );
			cliente.update();
		}
	}

	public NetCliente(String ip, Cliente cliente) throws IOException {
		// conecta com o servidor
		this.cliente = cliente;
		try {
			socket = new Socket(ip, 8080);
			os = new PrintStream(socket.getOutputStream(), true);
			is = new Scanner(socket.getInputStream());
		} catch (UnknownHostException e) {
			LOGGER.severe("Don't know about host.");
			throw e;
		} catch (IOException e) {
			LOGGER.severe("Couldn't get I/O for the connection to host");
			throw e;
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
		os.println(c.ordinal());
	}

}
