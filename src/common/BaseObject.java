package common;
import java.io.Serializable;

public class BaseObject implements Serializable {

	protected static final long serialVersionUID = 1L;
	protected int indice = 0;
	protected String nome;

	public BaseObject(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
