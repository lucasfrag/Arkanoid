
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Arkanoid extends JPanel {
	private Dimension gameField = new Dimension (750,640);
	private Dimension sideBar = new Dimension(100, 300);
	private boolean isRunning = false;
	private boolean isPaused = false;
	private boolean won = false;
	private boolean lost = false;
	
	private int bolasContador;
	private int score;
	
	private Blocos[][] blocos;
	private Barra barra;
	private Bola bola;
	
	
	
	public Arkanoid(Frame container, int blocosOnX, int blocosOnY) {
		// Vai reconhecer as teclas precionadas e executar certa acao
		container.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent teclado) {
				if (won || lost) {
					if (teclado.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0); // Pressione ENTER para iniciar
				} else if (!isRunning || isPaused) {
					if (teclado.getKeyCode() == KeyEvent.VK_ENTER) start(); // Pressione ENTER para iniciar
				} else {
					if (teclado.getKeyCode() == KeyEvent.VK_RIGHT) barra.moveOnXAxis(20); // Mover barra para direita
					if (teclado.getKeyCode() == KeyEvent.VK_LEFT) barra.moveOnXAxis(-20); // Mover barra para esquerda
				}
			}
		});
		
		blocos = new Blocos[blocosOnX][blocosOnY];
		for (int x = 0; x != blocos.length; x++) {
			for (int y = 0; y != blocos[0].length; y++) {
				int blocoWidth = gameField.width/blocosOnX;
				int blocoHeight = (gameField.height/4)/blocosOnX;
				blocos[x][y] = new Blocos(100, x*blocoWidth, y*blocoHeight, blocoWidth, blocoHeight);
			}
		}		
		
		barra = new Barra(this, (int) ((gameField.getWidth()-Barra.standartPlayerWidth)/2), gameField.height-Barra.standartPlayerHeight, Barra.standartPlayerWidth, Barra.standartPlayerHeight);
		bola = new Bola(this, gameField.width/2, gameField.height/2, Bola.standartBallRadius);
		bolasContador = 3; // Número de vidas
		score = 0; // Recorde
	}
	
	public void addScore(int score) {
		this.score +=score;
	}
	
	public void perderBola() {
		pause();
		bolasContador -=1;
		
		if (bolasContador <=0) lost=true; // Se vida acabar, você perde...
		
		bola.setVetor(10, 10);
		bola.setPosition(gameField.width/2, gameField.height/2);	// Vai centralizar a bola quando jogo nao estiver rodando
		barra.setX((int) ((gameField.getWidth()-Barra.standartPlayerWidth)/2)); // Vai centralizar horizontalmente a barra
		barra.setY(gameField.height-Barra.standartPlayerHeight); // Vai centralizar verticalmente a barra
		repaint();
	}
	
	// Se o jogador vencer...
	public void playerWon() {
		won = true;
	}
	
	// Iniciar jogo
	public void start() {
		isPaused = false;
		if (!isRunning) {
			gameThread.start();
		}
	}
	
	// Pausar jogo
	public void pause() {
		isPaused = true;
	}
	
	public Dimension getGameDimension() {
		return gameField;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	// Player 1
	public void setBarra(Barra barra) {
		this.barra = barra;
	}
	
	public Barra getBarra() {
		return this.barra;
	}
	
	public Blocos[][] getBlocos() {
		return this.blocos;
	}
	
	// Container do Jogo
	public void setSize(Dimension size) {
		super.setSize(size);
		
		if (!isRunning) {
			gameField = new Dimension((size.width*3)/4, size.height); // Vai ajustar a distancias da borda do container até a janela
			sideBar = new Dimension (size.width/4, size.height);
			
			// Vetor de blocos
			for (int x = 0; x != blocos.length; x++) {
				for (int y = 0; y != blocos[0].length; y++) {
					int blocoWidth = gameField.width/blocos.length;
					int blocoHeight = (gameField.height/4)/blocos[0].length;
					blocos[x][y] = new Blocos(100, x*blocoWidth, y*blocoHeight, blocoWidth, blocoHeight);
				}
			}
			
			bola.setPosition(gameField.width/2, gameField.height/2);	// Vai centralizar a bola quando jogo nao estiver rodando
			barra.setX((int) ((gameField.getWidth()-Barra.standartPlayerWidth)/2)); // Vai centralizar horizontalmente a barra
			barra.setY(gameField.height-Barra.standartPlayerHeight); // Vai centralizar verticalmente a barra
		}
	}
	
	private Thread gameThread = new Thread(new Runnable() {
		public void run() {
			isRunning = true;
			bola.setVetor(10, 10);
			while (isRunning) {
				if (!isPaused) {
					bola.tick();
				}
				
				won = true;
				for (Blocos[] blocks : blocos) {
					for(Blocos b : blocks) {
						if (!b.isDestroyed()) won = false;
					}
				}
				
				repaint();
				try {
					Thread.sleep(60); // Velocidade da bola
				} catch (Exception exception) {}
			}
		}
	});
	
	public void paint(Graphics graficos) {
		super.paint(graficos);
		
		if (!isRunning) {
			setSize(getSize());
		}
		
		graficos.translate((getWidth()-(gameField.width+sideBar.width))/2, (getHeight()-(gameField.height))/2); // Vai centralizar o container
		
		graficos.setColor(new Color(0,0,0));
		int radius = 4;
		for (int i=0; i != bolasContador; i++) {
			graficos.fillOval(i*radius*2, -(radius*2+3), radius*2, radius*2);
		}
		
		graficos.setColor(new Color (255,255,255));	// Cor de fundo do jogo
		graficos.fillRect(0, 0, gameField.width, gameField.height); // Container
		
		bola.render(graficos); // Vai inserir a bola no jogo
		barra.render(graficos); // Vai inserir a barra no jogo
		
		for (Blocos[] blocks : blocos) {
			for(Blocos b : blocks) {
				b.render(graficos);
			}
		}

		graficos.setColor(new Color(0,0,0)); // Cor da barra lateral
		graficos.drawRect(gameField.width, 0, sideBar.width,sideBar.height);
		
		graficos.setColor(new Color (255,255,255));
		graficos.setFont(new Font("Arial", Font.BOLD, 30));
		graficos.drawString("Score: "+score, gameField.width+20, 50);
		graficos.drawString("Lifes: " + bolasContador, gameField.width+20,100);
		
		graficos.setColor(new Color(0,0,0)); // Cor da borda
		graficos.drawRect(0, 0, gameField.width, gameField.height);	// Borda
		
		String msg = "    Pressione ENTER para começar!";
		if (won) {
			msg = "Pressione ENTER novamente para sair.";
			graficos.drawString("Você venceu!", gameField.width+20,500);
			stop();
		}
		
		if (lost) {
			msg = "Pressione ENTER novamente para sair";
			graficos.drawString("Você perdeu!", gameField.width+20,500);
			stop();
		}
		
		if (!isRunning) {
			graficos.drawString(msg, gameField.width-580, gameField.height-350);
		}
		
	}
}
