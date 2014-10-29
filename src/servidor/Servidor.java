package servidor;

import java.io.IOException;

import core.Fase;

public class Servidor {

	private Cliente mario, luigi;
	private Fase fase;
	private NetServidor netServidor;
	
	public Servidor() throws IOException {
		this.netServidor = new NetServidor(this);
		
		this.fase = new Fase();
		
		// incia a conexao
		this.mario = this.netServidor.getCliente();
		this.luigi = this.netServidor.getCliente();
		
		this.mario.getNetClient().start();
		this.luigi.getNetClient().start();
		
		// envia dizendo qual e qual
		this.mario.getNetClient().envia(-1);
		this.mario.setId(-1);
		
		this.luigi.getNetClient().envia(-2);
		this.luigi.setId(-2);
		
		// valida a posicao inicial
		this.mover(this.mario);
		this.mover(this.luigi);
		
		this.mario.enviarPosicao();
	}
	
	public void mover(Cliente c){
		c.setPosicao(fase.atualizaPos(c));
	}
	
	public void fim(){
		this.mario.fim();
		this.luigi.fim();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Servidor finalizado");
	}
	
}
