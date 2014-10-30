package cliente;

import java.io.IOException;
import java.util.logging.Logger;

import core.Comando;
import core.Fase;

public class Cliente {
	private Jogador mario, luigi;
	private String ipServidor;
	private Fase fase;
	private NetCliente concecta;
	private Janela janela;
	private int oldHash;
	
	private final static Logger LOGGER = Logger.getLogger(Cliente.class.getName());
	
	// constrututor
	
	public Cliente() throws IOException {
		this.mario = new Jogador();
		this.luigi = new Jogador();
		this.ipServidor = "localhost";
		this.concecta = new NetCliente(ipServidor, this);
		this.fase = new Fase();
		
		this.janela = new Janela(this, fase);
		this.janela.setVisible(true);
		
		this.oldHash = this.hashCode();
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

	@Override
	public String toString() {
		return "Cliente [mario=" + mario + ", luigi=" + luigi + ", ipServidor="
				+ ipServidor + "]";
	}

	public void update() {
		if (this.oldHash != this.hashCode()){
			LOGGER.info("Cliente desatualizado, invalidando janela");
			if (this.janela != null){
				this.janela.repaint();
			}
			this.oldHash = this.hashCode();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((janela == null) ? 0 : janela.hashCode());
		result = prime * result + ((luigi == null) ? 0 : luigi.hashCode());
		result = prime * result + ((mario == null) ? 0 : mario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (janela == null) {
			if (other.janela != null)
				return false;
		} else if (!janela.equals(other.janela))
			return false;
		if (luigi == null) {
			if (other.luigi != null)
				return false;
		} else if (!luigi.equals(other.luigi))
			return false;
		if (mario == null) {
			if (other.mario != null)
				return false;
		} else if (!mario.equals(other.mario))
			return false;
		return true;
	}
}

