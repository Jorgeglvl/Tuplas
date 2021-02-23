package common;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.Gerenciador;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import server.Server;

public class Espaco extends Thread implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private JavaSpace space;
	private Nuvem nuvem = null;
	private Host host = null;
	private Vm vm = null;
	private Processo processo = null;
	private Server servidor = null;
	private Gerenciador cliente = null;

	private boolean running = true;	
	
	public Espaco() {
		connect();
	}
	
	public Espaco(Server servidor) {
		this();
		this.servidor = servidor;
	}
	
	public Espaco(Gerenciador cliente) {
		this();
		this.cliente = cliente;
	}
	
	public Espaco(Nuvem nuvem) {
		this();
		this.nuvem = nuvem;
	}
	
	public Espaco(Nuvem nuvem, Host host) {
		this(nuvem);
		this.host = host;
	}
	
	public Espaco(Nuvem nuvem, Host host, Vm vm) {
		this(nuvem,host);
		this.vm = vm;
	}
	
	public Espaco(Nuvem nuvem, Host host, Vm vm, Processo processo) {
		this(nuvem,host,vm);
		this.processo = processo;
	}
	
	public boolean connect() {
		System.out.println("Procurando pelo servico JavaSpace...");
		Lookup finder = new Lookup(JavaSpace.class);
        space = (JavaSpace) finder.getService();
        if (space == null) {
                return false;
        } 
        return true;
	}
	
	public void enviaMensagem(String destino, Object mensagem) {
		try {
			space.write(new Message(destino,mensagem), null, 60 * 10000);
		} catch (RemoteException | TransactionException e) {
			e.printStackTrace();
		}
	}
	

	public String getReferencia() {
		
		String referencia;
		
		if(servidor!=null) {
			referencia = servidor.getNome();
		}
		else if(cliente!=null) {
			referencia = cliente.getNome();
		}
		else {
			referencia = nuvem.getNome();
			
			if(host!=null) {
				referencia += "/"+host.getNome();
				if(vm!=null) {
					referencia += "/"+vm.getNome();
					if(processo!=null) {
						referencia += "/"+processo.getNome();
					}
				}
			}
		}
		
		return referencia;
	}
	
	@SuppressWarnings("unchecked")
	public void adicionaObjeto(Object mensagem) {
		
		if(servidor!=null) {
			servidor.receberMensagem((String)mensagem);
		}
		else if(cliente!=null) {
			if(mensagem instanceof ArrayList<?>) {
				cliente.setListaNuvem((ArrayList<Nuvem>)mensagem);
			}
			else if(mensagem instanceof String) {
				if(((String) mensagem).contains("Cliente")) {
					cliente.setNome((String)mensagem);
				}
				else {
					cliente.esreveChat((String)mensagem);
				}
			}
		}
		else if(processo!=null) {
			processo.receberMensagem((String)mensagem);
		}
		else if(vm!=null) {
			vm.adicionaProcesso((Processo)mensagem);
		}
		else if(host!=null) {
			host.adicionaVm((Vm)mensagem);
		}
		else if(nuvem!=null) {
			nuvem.adicionaHost((Host)mensagem);
		}
	}
	
	public void finalizar() {
		running = false;
	}
	
	@Override
	public void run() {
		super.run();
		while(running) {
			Message msg;
			try {
				msg = (Message) space.take(new Message(getReferencia()), null, 10 * 1000);
				if(msg != null) {
					adicionaObjeto(msg.mensagem);
				}
			} catch (RemoteException | UnusableEntryException | TransactionException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
