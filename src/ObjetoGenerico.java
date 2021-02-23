import java.io.Serializable;

public class ObjetoGenerico implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected String nome;

	public ObjetoGenerico () {
		
	}
	
	public ObjetoGenerico(String nome) {
		this.nome = nome;
	}
}
