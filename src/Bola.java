import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Bola {
	public static int standartBallRadius = 10; // Tamanho da bola
	private Arkanoid instance;
	private Dimension vetor = new Dimension(0,0);
	private Point ponto = new Point(0,0);
	private int radius;
	
	public Bola(Arkanoid inst, int x, int y, int radius) {
		instance = inst;
		ponto = new Point(x,y);
		this.radius = radius;
	}
	
	public void setVetor(int XMovement, int YMovement) {
		vetor = new Dimension(XMovement, YMovement);
	}
	
	public Point getPosition() {
		return ponto;
	}
	public void setPosition(int x, int y) {
		ponto = new Point(x,y);
	}
	
	// Impedir que a bola ultrapasse o container
	public void tick() {
		if (ponto.x-radius <=0 && vetor.width<0) vetor.width = -vetor.width;
		if (ponto.x+radius >= instance.getGameDimension().width && vetor.width>0) vetor.width = -vetor.width;
		if (ponto.y-radius <=0 && vetor.height<0) vetor.height = -vetor.height;
		if (ponto.y+radius >= instance.getGameDimension().height && vetor.height>0) instance.perderBola();
		
		// Impedir que a bola ultrapasse a barra depois da colisao
		if (instance.getBarra() != null) {
			if(instance.getBarra().collidesWith(new Rectangle(ponto.x-radius+vetor.width, ponto.y-radius+vetor.height, radius*2, radius*2))) {
				vetor.height = -vetor.height;
				
				
			}
		}
		
		ponto.move(ponto.x+vetor.width, ponto.y+vetor.height);
		
		// Destruir blocos
		for (Blocos[] blocks : instance.getBlocos()) {
			for(Blocos b : blocks) {
				if (b.collidesWith(new Rectangle(ponto.x-radius, ponto.y-radius, radius*2, radius*2))) {				
					b.destruir();			
					instance.addScore(b.getValor());
					vetor.height = -vetor.height;
					
					boolean won = true;
					for (Blocos[] blocks2 : instance.getBlocos()) {
						for(Blocos b2 : blocks) {
							if (!b2.isDestroyed()) won = false;
						}
					}
					if (won) instance.playerWon();
				}
			}
		}
	}
	
	// Formato da bola
	public void render(Graphics graficos) {
		graficos.setColor(new Color(0,0,0)); // Cor da bola
		graficos.fillOval(ponto.x-radius, ponto.y-radius, radius*2, radius*2);
	}
}
