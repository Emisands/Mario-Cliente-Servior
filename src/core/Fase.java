package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import servidor.Cliente;
import servidor.Servidor;

public class Fase {

	private Image background;
	private Image marioDirImg, marioEsqImg, marioAndandoDireitaImg,
			marioAndandoEsquerdaImg;
	private Image luigiDirImg, luigiEsqImg, luigiAndandoDireitaImg,
			luigiAndandoEsquerdaImg;

	static final int CHAO_Y = 427, PAREDE_ESQ = 0, PAREDE_DIR = 800;

	public Fase() throws IOException {
		try {
			background = (ImageIO.read(new File("background.jpg")));

			marioDirImg = (ImageIO.read(new File("Mario_Parado_DIR.png")));

			luigiDirImg = (ImageIO.read(new File("Luigi_Parado_DIR.png")));
			/*
			 * marioEsqImg = (ImageIO.read(new File("Mario_Parado_ESQ.png")));
			 * marioAndandoDireitaImg = (ImageIO.read(new
			 * File("Mario_DIR.pgn"))); marioAndandoEsquerdaImg =
			 * (ImageIO.read(new File("Mario_ESQ.pgn")));
			 * 
			 * 
			 * luigiEsqImg = (ImageIO.read(new File("Luigi_Parado_ESQ.png")));
			 * luigiAndandoDireitaImg = (ImageIO.read(new
			 * File("Luigi_DIR.pgn"))); luigiAndandoEsquerdaImg =
			 * (ImageIO.read(new File("Luigi_ESQ.pgn")));
			 */
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Atualiza posicao de acordo com a colisao
	 * 
	 * @param cliente
	 * @return
	 */
	public Posicao atualizaPos(Cliente cliente, Servidor servidor) {
		this.fisica(cliente, servidor);
		this.colisao(cliente, servidor);
		return cliente.getPosicao();
	}

	private void fisica(Cliente cliente, Servidor servidor) {
		int x, y, dx, dy;
		x = cliente.getPosicao().getX();
		y = cliente.getPosicao().getX();
		dx = cliente.getPosicao().getDx();
		dy = cliente.getPosicao().getDy();

		// de velocidade para posicao
		x += dx;
		y += dy;

		// velocidade
		if (dx != 0){
			dx -= (dx / Math.abs(dx));
		}
		if (dy != 0){
			dy -= (dy / Math.abs(dy));
		}

		// gravidade
		dy += 1;
		if (dy == 0){
			dy = 1;
		}
		
		cliente.getPosicao().setX(x);
		cliente.getPosicao().setY(y);
		cliente.getPosicao().setDx(dx);
		cliente.getPosicao().setDy(dy);
	}

	private void colisao(Cliente cliente, Servidor servidor){
		int x, y, h, l;
		x = cliente.getPosicao().getX();
		y = cliente.getPosicao().getY();
		h = cliente.getPosicao().getH();
		l = cliente.getPosicao().getL();
		
		// nao passa do chao
		if (y + h < CHAO_Y){
			y = CHAO_Y;
		}
		
		// nao passa das paredes
		if (x + l> PAREDE_DIR){
			x = PAREDE_DIR;
		}
		if (x < PAREDE_ESQ){
			x = PAREDE_ESQ;
		}
	
		// dois corpos nao ocupam o mesmo espaco
		
		
		cliente.getPosicao().setX(x);
		cliente.getPosicao().setY(y);
	}

	public void interpretaComando(Comando comando, Cliente cliente) {
		switch (comando) {
		case DIREITA:
			cliente.getPosicao().setX(cliente.getPosicao().getX() + 10);
			break;
		case ESQUERDA:
			cliente.getPosicao().setX(cliente.getPosicao().getX() - 10);
			break;
		case PULA:
			cliente.getPosicao().setY(cliente.getPosicao().getY() + 50);
		default:
			break;
		}
	}

	public Image getBackground() {
		return background;
	}

	public Image getMarioDirImg() {
		return marioDirImg;
	}

	public Image getMarioEsqImg() {
		return marioEsqImg;
	}

	public Image getMarioAndandoDireitaImg() {
		return marioAndandoDireitaImg;
	}

	public Image getMarioAndandoEsquerdaImg() {
		return marioAndandoEsquerdaImg;
	}

	public Image getLuigiDirImg() {
		return luigiDirImg;
	}

	public Image getLuigiEsqImg() {
		return luigiEsqImg;
	}

	public Image getLuigiAndandoDireitaImg() {
		return luigiAndandoDireitaImg;
	}

	public Image getLuigiAndandoEsquerdaImg() {
		return luigiAndandoEsquerdaImg;
	}

}
