package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class NetServidor {

	private ServerSocket serverSocket;
	private Servidor servidor;
	private Collection<Socket> clientes;

	public NetServidor(Servidor servidor) {
		this.clientes = new ArrayList<Socket>(2);
		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + 8080 + ", " + e);
			System.exit(1);
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
		return new Cliente(new NetCliente(accept), this.servidor);
	}

}
