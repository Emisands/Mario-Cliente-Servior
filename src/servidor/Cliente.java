package servidor;

import core.Posicao;

public class Cliente {

	private Posicao posicao;
	private NetCliente netClient;
	private Servidor servidor;
	private int id;

	public Cliente(NetCliente netClient, Servidor servidor) {
		this.netClient = netClient;
		this.servidor = servidor;
	}

	public void fim() {
		netClient.envia(-3);
	}

	public Posicao getPosicao() {
		return posicao;
	}

	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}

	public NetCliente getNetClient() {
		return netClient;
	}

	public void setNetClient(NetCliente netClient) {
		this.netClient = netClient;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void enviarPosicao() {
		this.netClient.envia(this.id);
	}

}
