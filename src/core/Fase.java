package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import servidor.Cliente;
import servidor.Servidor;

public class Fase {

	private String StateImgM;
	private String StateImgL;
	private Image background;
	private Image EstrelaFinal;
	private Image marioDirImg;
	private Image luigiDirImg;

	static final int CHAO_Y = 408, PAREDE_ESQ = 0, PAREDE_DIR = 778;

	public Fase() throws IOException {
		try {
			setStateImgL("Luigi_Parado_ESQ.png");
			setStateImgM("Mario_Parado_DIR.png");
			background = (ImageIO.read(new File("background.jpg")));
			EstrelaFinal = (ImageIO.read(new File("Up.png")));
			
			marioDirImg = (ImageIO.read(new File(getStateImgM())));

			luigiDirImg = (ImageIO.read(new File(getStateImgL())));
			/*
			marioEsqImg = (ImageIO.read(new File("Mario_Parado_ESQ.png")));
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
		y = cliente.getPosicao().getY();
		dx = cliente.getPosicao().getDx();
		dy = cliente.getPosicao().getDy();

		// de velocidade para posicao
		x += dx;
		//y += dy;

		// velocidade
		if (dx != 0){
			dx -= (dx / Math.abs(dx));
		}
		if (dy != 0){
			dy -= (dy / Math.abs(dy));
		}

		// gravidade
		dy += 20;
		if (dy <= 0 ){
			dy = 1;
		}
		
		
		cliente.getPosicao().setX(x);
		cliente.getPosicao().setY(y);
		cliente.getPosicao().setDx(dx);
		cliente.getPosicao().setDy(dy);
	}

	private void colisao(Cliente cliente, Servidor servidor){
		int x, y, h = 40, l;
		x = cliente.getPosicao().getX();
		y = cliente.getPosicao().getY();
		h = cliente.getPosicao().getH();
		l = cliente.getPosicao().getL();
		
		// nao passa do chao
		
		if (y > CHAO_Y) {
			y = 408;
		}
		
		if (y < 408) {
			y+=10;
		}
		if (y < 340) {
			y = 340;
		}
		// nao passa das paredes
		if (x > PAREDE_DIR){
			x = PAREDE_DIR;
		}
		if (x < PAREDE_ESQ){
			x = PAREDE_ESQ;
		}    
		
		//------------- OBJETIVO -----------------
		if((x > 675 && x < 710) && y < 370 ) {
			JOptionPane.showMessageDialog(null,"Parabens! Você venceu! Clique em OK para finalizar.","FIM DE JOGO!",JOptionPane.INFORMATION_MESSAGE); 
			System.exit(0); 
		}
		//	-------------------------------------
	
		// dois corpos nao ocupam o mesmo espaco
		
		
		cliente.getPosicao().setX(x);
		cliente.getPosicao().setY(y);
		
	}

	public void interpretaComando(Comando comando, Cliente cliente) {
		switch (comando) {
		case DIREITA:
			cliente.getPosicao().setX(cliente.getPosicao().getX() + 5);
			//setStateImg("");
			break;
		case ESQUERDA:
			cliente.getPosicao().setX(cliente.getPosicao().getX() - 5);
			//setStateImg("");
			break;
		case PULA:
		//	for (int i = 0; i < 5; i++) {
			cliente.getPosicao().setY(cliente.getPosicao().getY() - 40);
		/*	try {  
				   Thread.sleep(25);  
				} catch (Exception e) {  
				   e.printStackTrace();  
				}  
			//setStateImg("");
			} */
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

	public Image getLuigiDirImg() {
		return luigiDirImg;
	}


	public String getStateImgM() {
		return StateImgM;
	}

	public void setStateImgM(String stateImgM) {
		StateImgM = stateImgM;
	}

	public String getStateImgL() {
		return StateImgL;
	}

	public void setStateImgL(String stateImgL) {
		StateImgL = stateImgL;
	}

	public Image getEstrelaFinal() {
		return EstrelaFinal;
	}

}
