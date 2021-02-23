package common;

import java.util.ArrayList;

public class Vm extends BaseObject{

	private static final long serialVersionUID = 1L;
	private ArrayList<Processo> listaProcesso = new ArrayList<Processo>();
	private Espaco espaco;
	private Nuvem nuvem;
	private Host host;

	public Vm(String nome, Nuvem nuvem, Host host) {
		super(nome);
		this.nuvem = nuvem;
		this.host = host;
	}
	
	public void iniciaEspaco() {
		this.espaco = new Espaco(nuvem,host,this);
		espaco.start();
	}
	
	public int verificaProcessoExiste(String nome) {
		
		int i = 0;
		
		if(!listaProcesso.isEmpty()) {
			while(i<listaProcesso.size()) {
				if(listaProcesso.get(i).getNome().contentEquals(nome)) {
					return i;
				}
				i++;
			}
		}
		
		return -1;
	}
	
	public void criaProcesso() {
		
		while(verificaProcessoExiste("processo"+this.indice)!=-1) {
			indice++;
		}
		listaProcesso.add(new Processo("processo"+this.indice,nuvem,host,this));
		listaProcesso.get(listaProcesso.size()-1).iniciaEspaco();
		indice++;
	}
	
	public void adicionaProcesso(Processo processo) {
				
		if(verificaProcessoExiste(processo.getNome())!=-1) {
			while(verificaProcessoExiste("processo"+this.indice)!=-1) {
				indice++;
			}
			processo.setNome("processo"+this.indice);
			processo.setNuvem(nuvem);
			processo.setHost(host);
			processo.setVm(this);
			processo.reiniciar();
			listaProcesso.add(processo);
			indice++;
		}
		else {
			processo.setNuvem(nuvem);
			processo.setHost(host);
			processo.setVm(this);
			processo.reiniciar();
			listaProcesso.add(processo);
		}
	}
	
	public void removeProcesso(String nome) {
		
		int i = verificaProcessoExiste(nome);
		
		if(i!=-1) {
			listaProcesso.get(i).finalizar();
			listaProcesso.remove(i);
		}
	}
	
	public void finalizar() {
		espaco.finalizar();
	}
	
	public void reiniciar() {
		finalizar();
		iniciaEspaco();
	}
	
	public void moverProcesso(String nome, String destino) {
		
		int i = verificaProcessoExiste(nome);
		
		if(i!=-1&&!destino.contentEquals(espaco.getReferencia())) {
			espaco.enviaMensagem(destino, listaProcesso.remove(i));
		}
	}
	
	public void atualizaReferencia() {
		
		for(int i=0;i<listaProcesso.size();i++) {
			listaProcesso.get(i).setNuvem(nuvem);
			listaProcesso.get(i).setHost(host);
			listaProcesso.get(i).reiniciar();
		}
	}
	
	public ArrayList<Processo> getListaProcesso() {
		return listaProcesso;
	}
	
	public Nuvem getNuvem() {
		return nuvem;
	}
	
	public void setNuvem(Nuvem nuvem) {
		this.nuvem = nuvem;
	}
	
	public void setHost(Host host) {
		this.host = host;
	}
}
