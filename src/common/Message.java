package common;
import net.jini.core.entry.Entry;

public class Message implements Entry {
	
	private static final long serialVersionUID = 1L;
	public String destino;
	public Object mensagem;
	
	public Message() {
		
	}
	
	public Message(String destino) {
		this();
		this.destino = destino;
	}
	
	public Message(String destino, Object mensagem) {
		this(destino);
		this.mensagem = mensagem;
	}
}
