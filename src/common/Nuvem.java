package common;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Nuvem extends BaseObject{

	private static final long serialVersionUID = 1L;
	private ArrayList<Host> listaHost = new ArrayList<Host>();
	private Espaco espaco;
	
	public Nuvem(String nome) {
		super(nome);
		//iniciaEspaco();
	}
	
	public void iniciaEspaco() {
		this.espaco = new Espaco(this);
		espaco.start();
	}
	
	public int verificaHostExiste(String nome) {
		
		int i = 0;
		
		if(!listaHost.isEmpty()) {
			while(i<listaHost.size()) {
				if(listaHost.get(i).getNome().contentEquals(nome)) {
					return i;
				}
				i++;
			}
		}
		
		return -1;
	}
	
	public void criaHost() {
		
		while(verificaHostExiste("host"+this.indice)!=-1) {
			indice++;
		}
		listaHost.add(new Host("host"+this.indice,this));
		listaHost.get(listaHost.size()-1).iniciaEspaco();
		indice++;
	}
	
	public void adicionaHost(Host host) {
		
		if(verificaHostExiste(host.getNome())!=-1) {
			while(verificaHostExiste("host"+this.indice)!=-1) {
				indice++;
			}
			host.setNome("host"+this.indice);
			host.setNuvem(this);
			host.atualizaReferencia();
			host.reiniciar();
			listaHost.add(host);
			indice++;
		}
		else {
			host.setNuvem(this);
			host.atualizaReferencia();
			host.reiniciar();
			listaHost.add(host);
		}
	}
	
	public void removeHost(String nome) {
		
		int i = verificaHostExiste(nome);
		
		if(i!=-1) {
			if(listaHost.get(i).getListaVm().isEmpty()) {
				listaHost.get(i).finalizar();
				listaHost.remove(i);
			}
			else {
				System.out.println("Só é possivel remover se estiver vazio");
			}
		}
	}
	
	public void removeHost(int index) {
		
		if(listaHost.get(index).getListaVm().isEmpty()) {
			listaHost.get(index).finalizar();
			listaHost.remove(index);
		}
		else {
			JOptionPane.showMessageDialog(null, "Certifique-se de esvaziar o objeto antes de delet�-lo");		}
	}
	
	public void finalizar() {
		espaco.finalizar();
	}
	
	public void reiniciar() {
		finalizar();
		iniciaEspaco();
	}
	
	public void moverHost(String nome, String destino) {
		
		int i = verificaHostExiste(nome);
		
		if(i!=-1&&!destino.contentEquals(espaco.getReferencia())) {
			espaco.enviaMensagem(destino, listaHost.remove(i));
		}
	}
	
	public ArrayList<Host> getListaHost() {
		return listaHost;
	}
}
