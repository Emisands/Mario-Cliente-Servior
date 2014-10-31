package servidor;

import java.net.Socket;

import core.Posicao;

public class Cliente {

	private Posicao posicao;
	private NetCliente netClient;
	private NetServidor netServidor;
	private int id;

	public Cliente(Socket socket, NetServidor netServidor) {
		this.netClient = new NetCliente(socket, this);
		this.netServidor = netServidor;
		this.posicao = new Posicao();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void enviarPosicao() {
		this.netClient.envia(this.id);
		this.netClient.envia(this.posicao.getX());
		this.netClient.envia(this.posicao.getY());
	}

	public void enviarPosicao(Cliente c) {
		this.netClient.envia(c.id);
		this.netClient.envia(c.posicao.getX());
		this.netClient.envia(c.posicao.getY());
	}

	public NetServidor getNetServidor() {
		return netServidor;
	}

	public void setNetServidor(NetServidor netServidor) {
		this.netServidor = netServidor;
	}

}
