package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import servidor.Cliente;

public class Fase {

	private Image marioImg, luigiImg, background;
	
	public Fase() throws IOException {
		try {
			background = (ImageIO.read(new File("background.jpg")));
			marioImg = (ImageIO.read(new File("background.jpg")));
			luigiImg = (ImageIO.read(new File("background.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Image getMarioImg() {
		return marioImg;
	}

	public Image getLuigiImg() {
		return luigiImg;
	}

	public Image getBackground() {
		return background;
	}

	/**
	 * Atualiza posicao de acordo com a colisao
	 * @param c
	 * @return
	 */
	public Posicao atualizaPos(Cliente c) {
		// TODO Auto-generated method stub
		return c.getPosicao();
	}

}
