import javax.swing.JFrame;

public class Principal {
	public static JFrame frame;
	public static Arkanoid arkanoid;
	
	public static void main(String[] args) {
		
		// Janela do jogo
		frame = new JFrame("Arkanoid"); 	// Titulo que aparece na janela
		frame.setSize(900, 600);			// Altura e largura da janela
		frame.setLocationRelativeTo(null); 	// Força a janela ser iniciada no centro da tela 
		
		arkanoid = new Arkanoid (frame, 10, 3);
		arkanoid.setSize(frame.getSize());
		
		frame.add(arkanoid);				// Adiciona o jogo na janela
		
		frame.setVisible(true);				// Torna a janela visivel
	}
	
}
