package cliente;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import core.Comando;
import core.Fase;

public class Janela extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3348188083060554786L;

	private Cliente cliente;
	private Painel painel;
	private final static Logger LOGGER = Logger.getLogger(Janela.class.getName()); 

	@Override
	public void keyPressed(KeyEvent e) {
		LOGGER.info("KeyEvent " + e.toString());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			cliente.teclado(Comando.DIREITA);
			break;
		case KeyEvent.VK_LEFT:
			cliente.teclado(Comando.ESQUERDA);
			break;
		case KeyEvent.VK_DOWN:
			cliente.teclado(Comando.ABAIXA);
			break;
		case KeyEvent.VK_SPACE:
			cliente.teclado(Comando.PULA);
			break;
		case KeyEvent.VK_Z:
			cliente.teclado(Comando.ATACA);
		}
	}

	public Janela(Cliente cliente, Fase fase) throws IOException {
		super("Super Mega Mario");

		this.cliente = cliente;
		this.painel = new Painel(cliente, fase);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		add(this.painel);
		
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
