import net.jini.core.entry.Entry;

public class Mensagem implements Entry {
	
	private static final long serialVersionUID = 1L;
	public String destino;
	public Object mensagem;
	
	public Mensagem() {
		
	}
	
	public Mensagem(String destino) {
		this();
		this.destino = destino;
	}
	
	public Mensagem(String destino, Object mensagem) {
		this(destino);
		this.mensagem = mensagem;
	}
}
