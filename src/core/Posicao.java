package core;

public class Posicao {

	private int x, y, dx, dy, h, l;

	public Posicao() {
		this.x = 0;
		this.y = 408;
	}

	public Posicao(int x, int y) {
		this(x, y, 0, 0);
	}

	public Posicao(int x, int y, int dx, int dy) {
		this(x, y, dx, dy, 5, 5);
	}

	public Posicao(int x, int y, int dx, int dy, int h, int l) {
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
		return "Posicao [x=" + x + ", y=" + y + ", dx=" + dx + ", dy=" + dy + "]";
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
		Posicao other = (Posicao) obj;
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
