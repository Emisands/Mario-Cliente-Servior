package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import servidor.Cliente;

public class Fase {

	private Image background;
	private Image marioDirImg, marioEsqImg, marioAndandoDireitaImg,
			marioAndandoEsquerdaImg;
	private Image luigiDirImg, luigiEsqImg, luigiAndandoDireitaImg,
			luigiAndandoEsquerdaImg;

	public Fase() throws IOException {
		try {
			background = (ImageIO.read(new File("background.jpg")));

			marioDirImg = (ImageIO.read(new File("Mario_Parado_DIR.png")));
			/*
			 * marioEsqImg = (ImageIO.read(new File("Mario_Parado_ESQ.png")));
			 * marioAndandoDireitaImg = (ImageIO.read(new
			 * File("Mario_DIR.pgn"))); marioAndandoEsquerdaImg =
			 * (ImageIO.read(new File("Mario_ESQ.pgn")));
			 * 
			 * luigiDirImg = (ImageIO.read(new File("Luigi_Parado_DIR.png")));
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
	 * @param c
	 * @return
	 */
	public Posicao atualizaPos(Cliente c) {
		// TODO Auto-generated method stub
		return new Posicao(c.getPosicao().getX() + 1, c.getPosicao().getY() + 1);
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
