package servidor;

import java.io.IOException;

import core.Fase;
import core.Posicao;

public class Servidor implements Runnable{

	private Cliente mario, luigi;
	private Fase fase;
	private NetServidor netServidor;
	private boolean fim = false;
	
	public Servidor() throws IOException {		
		this.fase = new Fase();
		this.netServidor = new NetServidor(this);
		
		// incia a conexao
		this.mario = this.netServidor.getCliente();
		this.luigi = this.netServidor.getCliente();
		
		this.mario.getPosicao().setL(16);
		this.mario.getPosicao().setH(27);
		
		this.luigi.getPosicao().setL(14);
		this.luigi.getPosicao().setH(22);
				
		// envia dizendo qual e qual
		this.mario.getNetClient().envia(-1);
		this.mario.setId(-1);
		
		this.luigi.getNetClient().envia(-2);
		this.luigi.setId(-2);
		
		// definine uma posicao inicial
		this.mario.setPosicao(new Posicao(20, 20));
		this.luigi.setPosicao(new Posicao(30, 20));
		
		new Thread(this).start();
	}
	
	public void run(){
		while (!fim){
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// valida a posicao inicial
			this.mover(this.mario);
			this.mover(this.luigi);
			
			// entrega a posicao para os clientes
			this.mario.enviarPosicao();
			this.mario.enviarPosicao(this.luigi);
			
			this.luigi.enviarPosicao();
			this.luigi.enviarPosicao(this.mario);
		}
	}
	
	public void mover(Cliente cliente){
		cliente.setPosicao(fase.atualizaPos(cliente, this));
	}
	
	public void fim(){
		this.mario.fim();
		this.luigi.fim();
		fim  = true;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Servidor finalizado");
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}
	
}
