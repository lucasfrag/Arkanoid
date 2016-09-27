import java.awt.*;

public class Blocos {
	private boolean isDestroyed = false;
	private Rectangle hitBox;
	private int valor = 0;
	
	
	public Blocos(int valor, int x, int y, int width, int height) {
		this.valor=valor;
		hitBox = new Rectangle(x, y, width, height);
	}
	
	public boolean collidesWith(Rectangle object) {
		return (isDestroyed)? false : hitBox.intersects(object);
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public void destruir() {
			isDestroyed = true;	
	}
	
	public void render(Graphics graficos) {
		if (!isDestroyed) {
			// Cor dos blocos
			graficos.setColor(new Color(255,0,0));
			graficos.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			
			// Borda dos blocos
			graficos.setColor(new Color(0,0,0));
			graficos.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}

	}
}
