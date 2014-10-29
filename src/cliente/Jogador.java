package cliente;

import core.Posicao;

public class Jogador {

	private Posicao posicao;

	public Jogador() {
		posicao =  new Posicao();
	}
	
	public Posicao getPosicao() {
		return posicao;
	}

	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}
	
}
