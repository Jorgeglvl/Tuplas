package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import common.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class Gerenciador {

	//---------------------------------------------/Variaveis/----------------------------------------------//
	private JFrame frame;
	private Gerenciador window;
	private ArrayList<Nuvem> listaNuvem = new ArrayList<Nuvem>();
	private NuvemModel raiz;
	private JTree tree;
	private ActionListener ato;
	private JDialog jdialog ;
	private Espaco espaco;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//----------------------------------------------/Strings/-----------------------------------------------//
	private String dialogoResposta;
	private String mensagemProcesso;
	private String nome = "Cliente";
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//--------------------------------------------/Labels/Botoes/--------------------------------------------//
	private JButton adicionar;
	private JButton remover;
	private JButton enviarMsg;
	private JLabel jl_title;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//-------------------------------------------/ComboBox/-------------------------------------------//
	private JComboBox<String> nuvemBox;
	private JComboBox<String> hostBox;
	private JComboBox<String> vmBox;
	private JComboBox<String> processoBox;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	//-------------------------------------------/Paineis/Chats/-------------------------------------------//
	private JScrollPane scrollTree;
	private JScrollPane scrollChat;
	private JPanel jp_buttons;
	private JPanel jp_tittle;
	private JTextArea painelChat;
	//--------------------------------------------/-------------/--------------------------------------------//
	
	public static void main(String[] args) {
		new Gerenciador();
	}

	public Gerenciador() {
		window = this;
		initialize();
		espaco = new Espaco(this);
		espaco.start();
		espaco.enviaMensagem("Servidor", "Cliente");
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(110, 110, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jp_tittle = new JPanel();
		jp_tittle.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(jp_tittle, BorderLayout.NORTH);
		
		jl_title = new JLabel("Gerenciador de Ambientes Multinuvens");
		jp_tittle.add(jl_title);
		
		iniciaBotoes();
		iniciaArvore();
		createRunnable();
	}
	
	public void esreveChat(String mensagem) {
		painelChat.append(mensagem+"\n");
		painelChat.setCaretPosition(painelChat.getText().length());
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
		//espaco.finalizar();
		//espaco = new Espaco(this);
		//espaco.conecta();
	}
	
	public void setListaNuvem(ArrayList<Nuvem> listaNuvem) {
		int selected = tree.getLeadSelectionRow();		
		this.listaNuvem = listaNuvem;
		atualizaArvore();
		tree.setSelectionRow(selected);
	}
	
	private void varreBotao() {
		
		ato = new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//int selected = tree.getLeadSelectionRow();
				String entradaDialogo = ""+tree.getLastSelectedPathComponent();
				if(arg0.getSource() == adicionar) {
					if(tree.getSelectionPath().getPathCount()==1) {
						espaco.enviaMensagem("Servidor", "criaNuvem");
						//listaNuvem.add(new Nuvem("nuvem"+index));
						//index++;
					}
					else if(tree.getSelectionPath().getPathCount()==2) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						//listaNuvem.get(indexNuvem).criaHost();
						espaco.enviaMensagem("Servidor", "criaHost-"+indexNuvem);
					}
					else if(tree.getSelectionPath().getPathCount()==3) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).criaVm();
						espaco.enviaMensagem("Servidor", "criaVm-"+indexNuvem+"-"+indexHost);
					}
					else if(tree.getSelectionPath().getPathCount()==4) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).criaProcesso(window);
						espaco.enviaMensagem("Servidor", "criaProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm);
					}
					else if(tree.getSelectionPath().getPathCount()==5) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
						dialogoResposta = "";
				        criaDialogo(indexNuvem,indexHost,indexVm);
				        System.out.println(dialogoResposta);
						//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(indexProcesso).enviarMensagem(mensagemProcesso, dialogoResposta);
				        if(dialogoResposta.contains("nuvem"+indexNuvem+"/host"+indexHost+"/vm"+indexVm+"/processo"+indexProcesso)) {
				        	System.out.println("TÃ¡ mandando pro mesmo canto");
				        }
				        else {
				        	espaco.enviaMensagem("Servidor", "enviarMensagemP-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+indexProcesso+"-"+mensagemProcesso+"//"+dialogoResposta);
				        }			        
					}
					//atualizaArvore();
				}
				else if(arg0.getSource() == remover) {
					if(tree.getSelectionPath().getPathCount()==2) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						if(listaNuvem.get(indexNuvem).getListaHost().isEmpty()) {
							//listaNuvem.get(indexNuvem).finalizar();
							//listaNuvem.remove(indexNuvem);
							espaco.enviaMensagem("Servidor", "removeNuvem-"+indexNuvem);
						}
						else {
							System.out.println("Num dÃ¡ man, n insiste");
							JOptionPane.showMessageDialog(null, "Certifique-se de esvaziar o objeto antes de deletá-lo");
						}
					}
					else if(tree.getSelectionPath().getPathCount()==3) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						//listaNuvem.get(indexNuvem).removeHost(indexHost);
						espaco.enviaMensagem("Servidor", "removeHost-"+indexNuvem+"-"+indexHost);
					}
					else if(tree.getSelectionPath().getPathCount()==4) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).removeVm(indexVm);
						espaco.enviaMensagem("Servidor", "removeVm-"+indexNuvem+"-"+indexHost+"-"+indexVm);
					}
					else if(tree.getSelectionPath().getPathCount()==5) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
						//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().remove(indexProcesso);
						espaco.enviaMensagem("Servidor", "removeProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+indexProcesso);
					}
					//atualizaArvore();
				}
				else if(arg0.getSource() == enviarMsg) {
					if(entradaDialogo.contains("Universo")) {
						System.out.println("NÃ£o toque no universo");
						JOptionPane.showMessageDialog(null, "Não foi possivel mover esse objeto");
					}
					else if(entradaDialogo.contains("nuvem")) {
						System.out.println("NÃ£o pode enviar uma nuvem como dado");
						JOptionPane.showMessageDialog(null, "Núvens não podem ser movidas");
					}
					else {
						dialogoResposta = "";
				        criaDialogo(0);
				        System.out.println(entradaDialogo);
				        System.out.println(dialogoResposta);
				        if(dialogoResposta.contains("null")) {
				        	//System.out.println("NÃ£o foi possivel encontrar o destino");
				        	JOptionPane.showMessageDialog(null, "NÃ£o foi possivel encontrar o destino");
				        }
				        else if(dialogoResposta.contains("vm")&&entradaDialogo.contains("processo")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
							int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
							System.out.println(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso());
							//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).moverProcesso(entradaDialogo, dialogoResposta);
							espaco.enviaMensagem("Servidor", "moverProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
				        else if(dialogoResposta.contains("host")&&entradaDialogo.contains("vm")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
							System.out.println(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm());
							//listaNuvem.get(indexNuvem).getListaHost().get(indexHost).moverVm(entradaDialogo, dialogoResposta);
							espaco.enviaMensagem("Servidor", "moverVm-"+indexNuvem+"-"+indexHost+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
				        else if(dialogoResposta.contains("nuvem")&&entradaDialogo.contains("host")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							System.out.println(listaNuvem.get(indexNuvem).getListaHost());
							//listaNuvem.get(indexNuvem).moverHost(entradaDialogo, dialogoResposta);
							espaco.enviaMensagem("Servidor", "moverHost-"+indexNuvem+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
					}
					/*try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					//atualizaArvore();
				}

				else if(arg0.getSource() == nuvemBox) {
					if(!entradaDialogo.contains("host")) {
						criaDialogo(nuvemBox.getSelectedIndex());
					}
					else {
						jdialog.dispose();
					}
				}
				else if(arg0.getSource() == hostBox) {
					if(!entradaDialogo.contains("vm")) {
						criaDialogo(hostBox.getSelectedIndex());
					}
					else {
						jdialog.dispose();
					}
				}
				else if(arg0.getSource() == vmBox) {
					if(!entradaDialogo.contains("processo")) {
						criaDialogo(vmBox.getSelectedIndex());
					}
					else {
						jdialog.dispose();
					}
				}
//				atualizaInterface();
				//tree.setSelectionRow(selected);
			}
		};
		
		adicionar.addActionListener(ato);
		remover.addActionListener(ato);
		enviarMsg.addActionListener(ato);
	}
	
	private void criaDialogo(int index) {
		
		if(jdialog == null) {
			jdialog = new JDialog(frame, true);
	        jdialog.setSize(200, 200);
	        jdialog.getContentPane().setLayout(new FlowLayout());
	        jdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        
	        nuvemBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.size();i++) {
				nuvemBox.addItem(listaNuvem.get(i).getNome());
			}
			nuvemBox.addActionListener(ato);
			jdialog.getContentPane().add(nuvemBox);
			jdialog.setTitle("Escolha o destino");
			jdialog.setVisible(true);
			dialogoResposta += nuvemBox.getSelectedItem();
			if(hostBox!=null) {
				dialogoResposta += "/"+hostBox.getSelectedItem();
				if(vmBox!=null) {
					dialogoResposta += "/"+vmBox.getSelectedItem();
				}
			}
			jdialog = null;
			hostBox = null;
			vmBox = null;
		}
		else if(hostBox == null) {
			hostBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.get(index).getListaHost().size();i++) {
				hostBox.addItem(listaNuvem.get(index).getListaHost().get(i).getNome());
			}
			hostBox.addActionListener(ato);
			jdialog.getContentPane().add(hostBox);
			jdialog.setVisible(true);
		}
		else if(vmBox == null) {
			vmBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.get(nuvemBox.getSelectedIndex()).getListaHost().get(index).getListaVm().size();i++) {
				vmBox.addItem(listaNuvem.get(nuvemBox.getSelectedIndex()).getListaHost().get(index).getListaVm().get(i).getNome());
			}
			vmBox.addActionListener(ato);
			jdialog.getContentPane().add(vmBox);
			jdialog.setVisible(true);
		}
	}
	
	private void criaDialogo(int indexNuvem,int indexHost, int indexVm) {
		
		jdialog = new JDialog(frame, true);
        jdialog.setSize(280, 150);
        jdialog.getContentPane().setLayout(null);
        jdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		processoBox = new JComboBox<String>();
		processoBox.setBounds(73, 20, 124, 24);
		for(int i=0;i<listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().size();i++) {
			processoBox.addItem(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(i).getNome());
		}
		processoBox.addActionListener(ato);
		jdialog.getContentPane().add(processoBox);
		JTextField chat = new JTextField();
		chat.setBounds(12, 65, 257, 50);
		jdialog.getContentPane().add(chat);
		chat.setColumns(10);
		chat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mensagemProcesso = arg0.getActionCommand();
				chat.setText("");
				jdialog.dispose();
			}
		});
		jdialog.setTitle("Escrever mensagem");
		jdialog.setVisible(true);
		jdialog = null;
		dialogoResposta = listaNuvem.get(indexNuvem).getNome();
		dialogoResposta += "/"+listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getNome();
		dialogoResposta += "/"+listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getNome();
		dialogoResposta += "/"+processoBox.getSelectedItem();
	}
	
	private void iniciaArvore() {
		raiz = new NuvemModel(listaNuvem);
		tree = new JTree(raiz);
		scrollTree = new JScrollPane(tree);
		tree.setSelectionRow(0);
		frame.getContentPane().add(scrollTree);
	}
	
	private void atualizaArvore() {
		raiz = new NuvemModel(listaNuvem);
		tree.setModel(raiz);
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
	}
	
	private void iniciaBotoes() {
		jp_buttons = new JPanel();
		jp_buttons.setBackground(Color.WHITE);
		frame.getContentPane().add(jp_buttons, BorderLayout.SOUTH);
		
		adicionar = new JButton("Novo");
		jp_buttons.add(adicionar);
		
		remover = new JButton("Remover");
		jp_buttons.add(remover);
		
		enviarMsg = new JButton("Mover Objeto");
		jp_buttons.add(enviarMsg);
		
		painelChat = new JTextArea();
		painelChat.setEditable(false);
		painelChat.setText("Mensagens entre processos aparecerÃ£o aqui  \n");
		scrollChat = new JScrollPane(painelChat);
		frame.getContentPane().add(scrollChat, BorderLayout.EAST);
		
	}
	
	private void createRunnable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
					window.varreBotao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
