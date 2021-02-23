package server;
import java.util.ArrayList;

import common.*;

public class Server {
	
	private String nome = "Servidor";
	private ArrayList<Nuvem> listaNuvem = new ArrayList<Nuvem>();
	private ArrayList<String> listaClientes = new ArrayList<String>();
	private Espaco espaco;
	private int index = 0;
	private int indexCliente = 0;
	private ServerFrame serverFrame;
	
	public Server() {
		espaco = new Espaco(this);
		espaco.start();
		serverFrame = new ServerFrame();
		serverFrame.escreveChat("Servidor Iniciado");
		while(true) {
			
		}
	}
	
	public static void main(String[] args) {
		
		new Server();
	}
	
	public void criaNuvem() {
		listaNuvem.add(new Nuvem("nuvem"+index));
		listaNuvem.get(listaNuvem.size()-1).iniciaEspaco();
		index++;
	}
	
	public void criaHost(int indexNuvem) {
		
		listaNuvem.get(indexNuvem).criaHost();
	}
	
	public void criaVm(int indexNuvem, int indexHost) {
		
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).criaVm();
	}
	
	public void criaProcesso(int indexNuvem, int indexHost, int indexVm) {
		
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).criaProcesso();
	}
	
	public void enviarMensagemP(int indexNuvem, int indexHost, int indexVm, int indexProcesso,String mensagem, String destino) {
		
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(indexProcesso).enviarMensagem(mensagem, destino);
	}
	
	public void enviarMensagemC(String mensagem) {
		
		for(int i=0;i<listaClientes.size();i++) {
			espaco.enviaMensagem(listaClientes.get(i), mensagem);
		}
	}
	
	public void removeNuvem(int indexNuvem) {
		listaNuvem.get(indexNuvem).finalizar();
		listaNuvem.remove(indexNuvem);
	}
	
	public void removeHost(int indexNuvem, int indexHost) {
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).finalizar();
		listaNuvem.get(indexNuvem).removeHost(indexHost);
	}
	
	public void removeVm(int indexNuvem, int indexHost, int indexVm) {
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).finalizar();
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).removeVm(indexVm);
	}
	
	public void removeProcesso(int indexNuvem, int indexHost, int indexVm, int indexProcesso) {
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(indexProcesso).finalizar();
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().remove(indexProcesso);
	}
	
	public void moverProcesso(int indexNuvem, int indexHost, int indexVm, String mensagem, String destino) {
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).moverProcesso(mensagem, destino);
	}
	
	public void moverVm(int indexNuvem, int indexHost, String mensagem, String destino) {
		listaNuvem.get(indexNuvem).getListaHost().get(indexHost).moverVm(mensagem, destino);
	}
	
	public void moverHost(int indexNuvem,String mensagem, String destino) {
		listaNuvem.get(indexNuvem).moverHost(mensagem, destino);
	}
	
	public void receberMensagem(String mensagem) {
		
		if(mensagem.contentEquals("Cliente")) {
			String nomeCliente = "Cliente"+indexCliente;
			listaClientes.add(nomeCliente);
			espaco.enviaMensagem("Cliente", nomeCliente);
			indexCliente++;
			serverFrame.escreveChat("Iniciando cliente: "+nomeCliente);
		}
		else if(mensagem.contentEquals("criaNuvem")) {
			criaNuvem();
			serverFrame.escreveChat("Criando nuvem"+index);
		}
		else if(mensagem.contains("criaHost-")) {
			criaHost(Integer.parseInt(mensagem.replaceFirst("criaHost-", "")));
			serverFrame.escreveChat("Criando host");
		}
		else if(mensagem.contains("criaVm-")) {
			mensagem = mensagem.replaceFirst("criaVm-", "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			criaVm(indexes.get(0),indexes.get(1));
			serverFrame.escreveChat("Criando vm");
		}
		else if(mensagem.contains("criaProcesso-")) {
			mensagem = mensagem.replaceFirst("criaProcesso-", "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			criaProcesso(indexes.get(0),indexes.get(1),indexes.get(2));
			serverFrame.escreveChat("Criando processo");
		}
		else if(mensagem.contains("enviarMensagemP-")) {
			mensagem = mensagem.replaceFirst("enviarMensagemP-", "");
			String[] conteudo = mensagem.substring(mensagem.lastIndexOf("-")+1).split("//");
			mensagem = mensagem.replaceFirst("-"+conteudo[0]+"//"+conteudo[1], "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			enviarMensagemP(indexes.get(0),indexes.get(1),indexes.get(2),indexes.get(3),conteudo[0],conteudo[1]);
			serverFrame.escreveChat("Enviando mensagem para processo");
		}
		else if(mensagem.contains("enviarMensagemC-")) {
			mensagem = mensagem.replaceFirst("enviarMensagemC-", "");
			enviarMensagemC(mensagem);
			serverFrame.escreveChat("Enviando mensagem para clientes");
		}
		else if(mensagem.contains("removeNuvem-")) {		
			removeNuvem(Integer.parseInt(mensagem.replaceFirst("removeNuvem-", "")));
			serverFrame.escreveChat("Removendo nuvem");
		}
		else if(mensagem.contains("removeHost-")) {		
			mensagem = mensagem.replaceFirst("removeHost-", "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			removeHost(indexes.get(0), indexes.get(1));
			serverFrame.escreveChat("Removendo host");
		}
		else if(mensagem.contains("removeVm-")) {		
			mensagem = mensagem.replaceFirst("removeVm-", "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			removeVm(indexes.get(0), indexes.get(1),indexes.get(2));
			serverFrame.escreveChat("Removendo vm");
		}
		else if(mensagem.contains("removeProcesso-")) {		
			mensagem = mensagem.replaceFirst("removeProcesso-", "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			removeProcesso(indexes.get(0), indexes.get(1),indexes.get(2),indexes.get(3));
			serverFrame.escreveChat("Removendo processo");
		}
		else if(mensagem.contains("moverHost-")) {
			mensagem = mensagem.replaceFirst("moverHost-", "");
			String[] conteudo = mensagem.substring(mensagem.lastIndexOf("-")+1).split("//");
			mensagem = mensagem.replaceFirst("-"+conteudo[0]+"//"+conteudo[1], "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			moverHost(indexes.get(0),conteudo[0],conteudo[1]);
			serverFrame.escreveChat("Movendo host");
		}
		else if(mensagem.contains("moverVm-")) {
			mensagem = mensagem.replaceFirst("moverVm-", "");
			String[] conteudo = mensagem.substring(mensagem.lastIndexOf("-")+1).split("//");
			mensagem = mensagem.replaceFirst("-"+conteudo[0]+"//"+conteudo[1], "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			moverVm(indexes.get(0),indexes.get(1),conteudo[0],conteudo[1]);
			serverFrame.escreveChat("Movendo vm");
		}
		else if(mensagem.contains("moverProcesso-")) {
			mensagem = mensagem.replaceFirst("moverProcesso-", "");
			String[] conteudo = mensagem.substring(mensagem.lastIndexOf("-")+1).split("//");
			mensagem = mensagem.replaceFirst("-"+conteudo[0]+"//"+conteudo[1], "");
			ArrayList<Integer> indexes = extraiIndex(mensagem);
			moverProcesso(indexes.get(0),indexes.get(1),indexes.get(2),conteudo[0],conteudo[1]);
			serverFrame.escreveChat("Movendo processo");
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<Nuvem> copiaLista = copiaListaNuvem(listaNuvem);
		for(int i=0;i<listaClientes.size();i++) {
			espaco.enviaMensagem(listaClientes.get(i), copiaLista);
		}
	}
	
	public String getNome() {
		return nome;
	}
	
	private ArrayList<Nuvem> copiaListaNuvem(ArrayList<Nuvem> listaNuvem) {
		
		ArrayList<Nuvem> novaListaNuvem = new ArrayList<Nuvem>();
		
		for(int i=0;i<listaNuvem.size();i++) {
			novaListaNuvem.add(new Nuvem(listaNuvem.get(i).getNome()));
			for(int j=0;j<listaNuvem.get(i).getListaHost().size();j++) {
				novaListaNuvem.get(i).getListaHost().add(new Host(listaNuvem.get(i).getListaHost().get(j).getNome(),novaListaNuvem.get(i)));
				for(int k=0;k<listaNuvem.get(i).getListaHost().get(j).getListaVm().size();k++) {
					novaListaNuvem.get(i).getListaHost().get(j).getListaVm().add(new Vm(listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k).getNome(),novaListaNuvem.get(i),novaListaNuvem.get(i).getListaHost().get(j)));
					for(int l=0;l<listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k).getListaProcesso().size();l++) {
						novaListaNuvem.get(i).getListaHost().get(j).getListaVm().get(k).getListaProcesso().add(new Processo(listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k).getListaProcesso().get(l).getNome(),novaListaNuvem.get(i),listaNuvem.get(i).getListaHost().get(j),listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k)));
					}
				}
			}
		}
		
		return novaListaNuvem;
	}
	
	private ArrayList<Integer> extraiIndex(String mensagem) {
		
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		String auxiliar = "";
		
		for(int p=0;p<mensagem.length();p++) {
			if(mensagem.charAt(p)!='-') {
				auxiliar += mensagem.charAt(p);
			}
			else {
				indexes.add(Integer.parseInt(auxiliar));
				auxiliar = "";
			}
		}
		indexes.add(Integer.parseInt(auxiliar));
		
		return indexes;
	}
}
