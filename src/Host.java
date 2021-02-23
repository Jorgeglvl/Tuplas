import java.util.ArrayList;

public class Host extends ObjetoBase {

	private static final long serialVersionUID = 1L;
	private ArrayList<Vm> listaVm = new ArrayList<Vm>();
	private Espaco espaco;
	private Nuvem nuvem;

	public Host(String nome, Nuvem nuvem) {
		super(nome);
		this.nuvem = nuvem;
		//iniciaEspaco();
	}
	
	public void iniciaEspaco() {
		this.espaco = new Espaco(nuvem,this);
		espaco.start();
	}
	
	public int verificaVmExiste(String nome) {
		
		int i = 0;
		
		if(!listaVm.isEmpty()) {
			while(i<listaVm.size()) {
				if(listaVm.get(i).getNome().contentEquals(nome)) {
					return i;
				}
				i++;
			}
		}
		
		return -1;
	}
	
	public void criaVm() {
		
		while(verificaVmExiste("vm"+this.indice)!=-1) {
			indice++;
		}
		listaVm.add(new Vm("vm"+this.indice,nuvem,this));
		listaVm.get(listaVm.size()-1).iniciaEspaco();
		indice++;
	}
	
	public void adicionaVm(Vm vm) {
		
		if(verificaVmExiste(vm.getNome())!=-1) {
			while(verificaVmExiste("vm"+this.indice)!=-1) {
				indice++;
			}
			vm.setNome("vm"+this.indice);
			vm.setNuvem(nuvem);
			vm.setHost(this);
			vm.atualizaReferencia();
			vm.reiniciar();
			listaVm.add(vm);
			indice++;
		}
		else {
			vm.setNuvem(nuvem);
			vm.setHost(this);
			vm.atualizaReferencia();
			vm.reiniciar();
			listaVm.add(vm);
		}
	}
	
	public void removeVm(String nome) {
		
		int i = verificaVmExiste(nome);
		
		if(i!=-1) {
			listaVm.get(i).finalizar();
			listaVm.remove(i);
		}
	}

	public void removeVm(int index) {
		
		if(listaVm.get(index).getListaProcesso().isEmpty()) {
			listaVm.get(index).finalizar();
			listaVm.remove(index);
		}
		else {
			Notificacao.deletarNaoVazio();
		}
	}
	
	public void finalizar() {
		espaco.finalizar();
	}
	
	public void reiniciar() {
		finalizar();
		iniciaEspaco();
	}
	
	public void moverVm(String nome, String destino) {
		
		int i = verificaVmExiste(nome);
		
		if(i!=-1&&!destino.contentEquals(espaco.getReferencia())) {
			espaco.enviaMensagem(destino, listaVm.remove(i));
		}
	}
	
	public void atualizaReferencia() {
		
		for(int i=0;i<listaVm.size();i++) {
			listaVm.get(i).setNuvem(nuvem);
			listaVm.get(i).atualizaReferencia();
			listaVm.get(i).reiniciar();
		}
	}
	
	public ArrayList<Vm> getListaVm() {
		return listaVm;
	}
	
	public Nuvem getNuvem() {
		return nuvem;
	}
	
	public void setNuvem(Nuvem nuvem) {
		this.nuvem = nuvem;
	}
}
