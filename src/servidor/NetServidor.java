package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class NetServidor {

	private ServerSocket serverSocket;
	private Collection<Socket> clientes;
	private Servidor servidor;
	
	private final static Logger LOGGER = Logger.getLogger(NetServidor.class
			.getName());
	
	public NetServidor(Servidor servidor) throws IOException {
		this.clientes = new ArrayList<Socket>(2);
		this.servidor = servidor;
		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			LOGGER.severe("Could not listen on port: " + 8080 + ", " + e);
			throw e;
		}
	}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.finalize();
	}

	public Cliente getCliente() {
		Socket accept = null;
		try {
			System.out.println("Server aguardando");
			accept = serverSocket.accept();
			System.out.println("Server recebendo conexao");
			clientes.add(accept);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Cliente(accept, this);
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

}
