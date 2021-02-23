import javax.swing.JOptionPane;

public class Notificacao {
	
	public static void moverUniverso() {
		JOptionPane.showMessageDialog(null, "Não é possivel mover isso");
	}
	
	public static void moverNuvem() {
		JOptionPane.showMessageDialog(null, "Não é possivel mover uma nuvem");
	}
	
	public static void deletarNaoVazio() {
		JOptionPane.showMessageDialog(null, "Só é possivel deletar objetos vazios");
	}
	
	public static void naoExisteTopico() {
		JOptionPane.showMessageDialog(null, "Topico nao existe");
	}
	
	public static void naoExisteDestino() {
		JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o destino");
	}
}
