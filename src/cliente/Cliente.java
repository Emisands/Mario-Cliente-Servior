package cliente;

import java.io.IOException;

import core.Comando;
import core.Fase;

public class Cliente {
	private Jogador mario, luigi;
	private String ipServidor;
	private Fase fase;
	private NetCliente concecta;
	private Janela janela;
	
	// constrututor
	
	public Cliente() throws IOException {
		this.mario = new Jogador();
		this.luigi = new Jogador();
		this.ipServidor = "localhost";
		this.concecta = new NetCliente(ipServidor, this);
		this.fase = new Fase();
		
		this.janela = new Janela(this, fase);
		this.janela.setVisible(true);
		
		this.concecta.start();
	}
	
	// geter e setter
	
	public Jogador getMario() {
		return mario;
	}

	public void setMario(Jogador mario) {
		this.mario = mario;
	}

	public Jogador getLuigi() {
		return luigi;
	}

	public void setLuigi(Jogador luigi) {
		this.luigi = luigi;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public NetCliente getConcecta() {
		return concecta;
	}

	public void setConcecta(NetCliente concecta) {
		this.concecta = concecta;
	}
	
	//metodos
	
	public synchronized void teclado (Comando  c) {
		concecta.envia(c);
	}

	public synchronized void fim() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Cliente finalizado");
	}
}

