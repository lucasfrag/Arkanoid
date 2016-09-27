import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Barra {
	public static int standartPlayerWidth = 80; // Largura da barra
	public static int standartPlayerHeight = 12; // Altura da barra
	private Rectangle hitBox;
	private Arkanoid instance;
	
	public Barra(Arkanoid inst, int x, int y, int width, int height) {
		instance = inst;
		hitBox = new Rectangle(x, y, width, height);
	}
	
	public void moveOnXAxis(int speed) {
		hitBox.x += speed;
		
		// Impedir que a barra saia do container no lado esquerdo
		if (hitBox.x < 0) hitBox.x = 0;
		if (hitBox.x > instance.getGameDimension().width-instance.getBarra().hitBox.width) hitBox.x = instance.getGameDimension().width-instance.getBarra().hitBox.width;	
	}
	
	public boolean collidesWith(Rectangle object) {
		return hitBox.intersects(object);
	}
	
	public void setX(int x) {
		hitBox.x = x;
	}
	
	public void setY(int y) {
		hitBox.y = y;
	}
	
	public int getWidth() {
		return this.hitBox.width;
	}
	
	public int getHeight() {
		return this.hitBox.height;
	}
	
	public void render(Graphics graficos) {
		graficos.setColor(new Color(200,200,255)); // Cor da barra do jogo
		graficos.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
}
