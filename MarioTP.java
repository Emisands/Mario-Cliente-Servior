
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Collection;
import java.util.ArrayList;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.imageio.ImageIO;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Funciona apenas para localhost por falta de tempo.
 * 
 * Quando executa abre 2 clientes e um servidor, separar e passar o 
 * endereco dos servidor por paramaretro resolveria.
 * 
 */

class ClienteCliente {
	private ClienteJogador mario, luigi;
	private String ipServidor;
	private CoreFase fase;
	private ClienteNetCliente concecta;
	private ClienteJanela janela;
	private int oldHash;
	
	private final static Logger LOGGER = Logger.getLogger(ClienteCliente.class.getName());
	
	// constrututor
	
	public ClienteCliente() throws IOException {
		this.mario = new ClienteJogador();
		this.luigi = new ClienteJogador();
		this.ipServidor = "localhost";
		this.concecta = new ClienteNetCliente(ipServidor, this);
		this.fase = new CoreFase();
		
		this.janela = new ClienteJanela(this, fase);
		this.janela.setVisible(true);
		
		this.oldHash = this.hashCode();
	}
	
	// geter e setter
	
	public ClienteJogador getMario() {
		return mario;
	}

	public void setMario(ClienteJogador mario) {
		this.mario = mario;
	}

	public ClienteJogador getLuigi() {
		return luigi;
	}

	public void setLuigi(ClienteJogador luigi) {
		this.luigi = luigi;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public CoreFase getFase() {
		return fase;
	}

	public void setFase(CoreFase fase) {
		this.fase = fase;
	}

	public ClienteNetCliente getConcecta() {
		return concecta;
	}

	public void setConcecta(ClienteNetCliente concecta) {
		this.concecta = concecta;
	}
	
	//metodos
	
	public synchronized void teclado (CoreComando  c) {
		concecta.envia(c);
	}

	public synchronized void fim() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("ClienteCliente finalizado");
	}

	@Override
	public String toString() {
		return "ClienteCliente [mario=" + mario + ", luigi=" + luigi + ", ipServidor="
				+ ipServidor + "]";
	}

	public void update() {
		if (this.oldHash != this.hashCode()){
			LOGGER.info("ClienteCliente desatualizado, invalidando janela");
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
		ClienteCliente other = (ClienteCliente) obj;
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




class ClienteJanela extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3348188083060554786L;

	private ClienteCliente cliente;
	private ClientePainel painel;
	private final static Logger LOGGER = Logger.getLogger(ClienteJanela.class.getName()); 

	@Override
	public void keyPressed(KeyEvent e) {
		LOGGER.info("KeyEvent " + e.toString());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			cliente.teclado(CoreComando.DIREITA);
			break;
		case KeyEvent.VK_LEFT:
			cliente.teclado(CoreComando.ESQUERDA);
			break;
		case KeyEvent.VK_DOWN:
			cliente.teclado(CoreComando.ABAIXA);
			break;
		case KeyEvent.VK_SPACE:
			cliente.teclado(CoreComando.PULA);
			break;
		case KeyEvent.VK_Z:
			cliente.teclado(CoreComando.ATACA);
		}
	}

	public ClienteJanela(ClienteCliente cliente, CoreFase fase) throws IOException {
		super("Super Mega Mario");

		this.cliente = cliente;
		this.painel = new ClientePainel(cliente, fase);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		add(this.painel);
		setResizable(false);
		
		pack();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}



class ClienteJogador {

	private CorePosicao posicao;

	public ClienteJogador() {
		posicao =  new CorePosicao();
	}
	
	public CorePosicao getPosicao() {
		return posicao;
	}

	public void setPosicao(CorePosicao posicao) {
		this.posicao = posicao;
	}

	@Override
	public String toString() {
		return "ClienteJogador [posicao=" + posicao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
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
		ClienteJogador other = (ClienteJogador) obj;
		if (posicao == null) {
			if (other.posicao != null)
				return false;
		} else if (!posicao.equals(other.posicao))
			return false;
		return true;
	}
	
}



class ClienteNetCliente implements Runnable {

	private Socket socket;

	private PrintStream os;
	private Scanner is;

	private ClienteCliente cliente;
	private Thread thread;

	private final static Logger LOGGER = Logger.getLogger(ClienteNetCliente.class
			.getName());

	@Override
	public void run() {
		boolean fim = false;
		
		LOGGER.info("ClienteCliente Aguardando dados");
		while (!fim) {
			System.out.println("ClienteCliente Aguardando dados");
			System.out.flush();
			int read = is.nextInt();
			LOGGER.info("Recebido " + read + " do servidor");

			switch (read) {
			case -1: // mario
				cliente.getMario().getPosicao().setX(is.nextInt());
				cliente.getMario().getPosicao().setY(is.nextInt());
				break;
			case -2: // luigi
				cliente.getLuigi().getPosicao().setX(is.nextInt());
				cliente.getLuigi().getPosicao().setY(is.nextInt());
				break;
			case -3: // fim
				cliente.fim();
				fim = true;
				break;
			default:
				LOGGER.info("Valor inesperado, ignorando");
			}
			LOGGER.info("ClienteCliente Autlaizado:\t" + cliente.toString() );
			cliente.update();
		}
	}

	public ClienteNetCliente(String ip, ClienteCliente cliente) throws IOException {
		// conecta com o servidor
		this.cliente = cliente;
		try {
			socket = new Socket(ip, 8080);
			os = new PrintStream(socket.getOutputStream(), true);
			is = new Scanner(socket.getInputStream());
		} catch (UnknownHostException e) {
			LOGGER.severe("Don't know about host.");
			throw e;
		} catch (IOException e) {
			LOGGER.severe("Couldn't get I/O for the connection to host");
			throw e;
		}
		LOGGER.info("Conectado com o host");

		thread = new Thread(this);
		thread.setDaemon(true);

		thread.start();
	}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		LOGGER.info("ClienteNetCliente finalizando");
		try {
			os.close();
			is.close();
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		super.finalize();
	}

	/**
	 * Envia comandos para o servidor
	 * 
	 * @param c
	 */
	public void envia(CoreComando c) {
		LOGGER.info("Eviando " + c.toString());
		os.println(c.ordinal());
	}

}




class ClientePainel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7638259897070943178L;

	private static final int imgx = 800;
	private static final int imgy = 500;
	
	private ClienteCliente cliente;
	private CoreFase fase;
	
	private final static Logger LOGGER = Logger.getLogger(ClientePainel.class.getName());
	
	public ClientePainel( ClienteCliente cliente, CoreFase fase) throws IOException {
		
		this.setPreferredSize(new Dimension(imgx, imgy));
		
		this.cliente = cliente;
		this.fase = fase;
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		LOGGER.info("Desenhando ClienteCliente:\t" + cliente.toString() );
		if (fase != null && cliente != null && cliente.getMario() != null && cliente.getLuigi() != null) {
			g.drawImage(fase.getBackground(), 0,0, this);
			
			g.drawImage(fase.getMarioDirImg(), cliente.getMario().getPosicao().getX(), cliente
					.getMario().getPosicao().getY(), this);
			
			g.drawImage(fase.getLuigiDirImg(), cliente.getLuigi().getPosicao().getX(), cliente
					.getLuigi().getPosicao().getY(), this);
			g.drawImage(fase.getEstrelaFinal(), 690, 336, this);
			
		}
	}
}

class CorePosicao {

	private int x, y, dx, dy, h, l;

	public CorePosicao() {
		this.x = 0;
		this.y = 408;
	}

	public CorePosicao(int x, int y) {
		this(x, y, 0, 0);
	}

	public CorePosicao(int x, int y, int dx, int dy) {
		this(x, y, dx, dy, 5, 5);
	}

	public CorePosicao(int x, int y, int dx, int dy, int h, int l) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.h = h;
		this.l = l;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	@Override
	public String toString() {
		return "CorePosicao [x=" + x + ", y=" + y + ", dx=" + dx + ", dy=" + dy + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + dx;
		result = prime * result + dy;
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
		CorePosicao other = (CorePosicao) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (dx != other.dx)
			return false;
		if (dy != other.dy)
			return false;
		return true;
	}

}
class CoreFase {

	private String StateImgM;
	private String StateImgL;
	private Image background;
	private Image EstrelaFinal;
	private Image marioDirImg;
	private Image luigiDirImg;

	static final int CHAO_Y = 408, PAREDE_ESQ = 0, PAREDE_DIR = 778;

	public CoreFase() throws IOException {
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
	public CorePosicao atualizaPos(ServidorCliente cliente, Servidor servidor) {
		this.fisica(cliente, servidor);
		this.colisao(cliente, servidor);
		return cliente.getPosicao();
	}

	private void fisica(ServidorCliente cliente, Servidor servidor) {
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

	private void colisao(ServidorCliente cliente, Servidor servidor){
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

	public void interpretaComando(CoreComando comando, ServidorCliente cliente) {
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


enum CoreComando {
	DIREITA, ESQUERDA, PULA, ABAIXA, ATACA;
}


class ServidorCliente {

	private CorePosicao posicao;
	private ServidorNetCliente netClient;
	private ServidorNetServidor netServidor;
	private int id;

	public ServidorCliente(Socket socket, ServidorNetServidor netServidor) {
		this.netClient = new ServidorNetCliente(socket, this);
		this.netServidor = netServidor;
		this.posicao = new CorePosicao();
	}

	public void fim() {
		netClient.envia(-3);
	}

	public CorePosicao getPosicao() {
		return posicao;
	}

	public void setPosicao(CorePosicao posicao) {
		this.posicao = posicao;
	}

	public ServidorNetCliente getNetClient() {
		return netClient;
	}

	public void setNetClient(ServidorNetCliente netClient) {
		this.netClient = netClient;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void enviarPosicao() {
		this.netClient.envia(this.id);
		this.netClient.envia(this.posicao.getX());
		this.netClient.envia(this.posicao.getY());
	}

	public void enviarPosicao(ServidorCliente c) {
		this.netClient.envia(c.id);
		this.netClient.envia(c.posicao.getX());
		this.netClient.envia(c.posicao.getY());
	}

	public ServidorNetServidor getNetServidor() {
		return netServidor;
	}

	public void setNetServidor(ServidorNetServidor netServidor) {
		this.netServidor = netServidor;
	}

}

class ServidorNetCliente implements Runnable {

	private Socket socket;
	private PrintStream os;
	private Scanner is;
	private Thread thread;
	private ServidorCliente cliente;

	private final static Logger LOGGER = Logger.getLogger(ServidorNetCliente.class.getName());
	
	@Override
	public void run() {
		while (true){
			int recived;
			recived = is.nextInt();
			CoreComando[] comandos = CoreComando.values();
			CoreComando c = comandos[recived];
			LOGGER.info("Recebido " + c.toString());
			cliente.getNetServidor().getServidor().getFase().interpretaComando(c, cliente);
		}
	}

	public ServidorNetCliente(Socket socket, ServidorCliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
		try {
			os = new PrintStream(socket.getOutputStream(), true);
			is = new Scanner(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
		
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			os.close();
			is.close();
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Trying to connect to unknown host: " + e);
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		super.finalize();
	}

	/**
	 * Envia comandos para o servidor
	 * 
	 * @param c
	 */
	public void envia(Integer c) {
		LOGGER.info("Server enviando " + c);
		os.println(c.intValue());
	}

}

 class ServidorNetServidor {

	private ServerSocket serverSocket;
	private Collection<Socket> clientes;
	private Servidor servidor;
	
	private final static Logger LOGGER = Logger.getLogger(ServidorNetServidor.class
			.getName());
	
	public ServidorNetServidor(Servidor servidor) throws IOException {
		this.clientes = new ArrayList<Socket>(2);
		this.servidor = servidor;
		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			LOGGER.severe("Could not listen on port: " + 8080 + ", " + e);
			throw e;
		}
	}

	/**
	 * caso o objeto seja destruido, finaliza a conecxao
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.finalize();
	}

	public ServidorCliente getCliente() {
		Socket accept = null;
		try {
			System.out.println("Server aguardando");
			accept = serverSocket.accept();
			System.out.println("Server recebendo conexao");
			clientes.add(accept);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ServidorCliente(accept, this);
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

}

class Servidor implements Runnable{

	private ServidorCliente mario, luigi;
	private CoreFase fase;
	private ServidorNetServidor netServidor;
	private boolean fim = false;
	
	public Servidor() throws IOException {		
		this.fase = new CoreFase();
		this.netServidor = new ServidorNetServidor(this);
		
		// incia a conexao
		this.mario = this.netServidor.getCliente();
		this.luigi = this.netServidor.getCliente();
		
		this.mario.getPosicao().setL(24);
		this.mario.getPosicao().setH(40);
		
		this.luigi.getPosicao().setL(27);
		this.luigi.getPosicao().setH(40);
				
		// envia dizendo qual e qual
		this.mario.getNetClient().envia(-1);
		this.mario.setId(-1);
		
		this.luigi.getNetClient().envia(-2);
		this.luigi.setId(-2);
		
		// definine uma posicao inicial
		this.mario.setPosicao(new CorePosicao(20, 408));
		this.luigi.setPosicao(new CorePosicao(200, 408));
		
		new Thread(this).start();
	}
	
	public void run(){
		while (!fim){
			try {
				Thread.sleep(100);
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
	
	public void mover(ServidorCliente cliente){
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

	public CoreFase getFase() {
		return fase;
	}

	public void setFase(CoreFase fase) {
		this.fase = fase;
	}
	
}


public class MarioTP {

	public static void main(String[] args) {
		new MarioTP();
	}
	
	public MarioTP() {
		new Thread(new s()).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		new Thread(new c1()).start();
		new Thread(new c2()).start();
	}
	
	private class s implements Runnable{
		@Override
		public void run() {
			try {
				Servidor s = new Servidor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class c1 implements Runnable{
		@Override
		public void run() {
			try {
				ClienteCliente c1 = new ClienteCliente();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class c2 implements Runnable{
		@Override
		public void run() {
			try {
				ClienteCliente c2 = new ClienteCliente();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
