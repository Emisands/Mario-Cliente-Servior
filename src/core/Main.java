package core;

import java.io.IOException;

import servidor.Servidor;
import cliente.Cliente;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
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
				Cliente c1 = new Cliente();
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
				Cliente c2 = new Cliente();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
