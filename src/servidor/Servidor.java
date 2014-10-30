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
		this.netServidor = new NetServidor(this);
		
		this.fase = new Fase();
		
		// incia a conexao
		this.mario = this.netServidor.getCliente();
		this.luigi = this.netServidor.getCliente();
				
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
	
	public void mover(Cliente c){
		c.setPosicao(fase.atualizaPos(c));
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
	
}
