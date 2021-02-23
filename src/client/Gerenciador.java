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

	private JFrame frame;
	private Gerenciador window;
	private ArrayList<Nuvem> listaNuvem = new ArrayList<Nuvem>();
	private NuvemModel raiz;
	private JTree tree;
	private ActionListener ato;
	private JDialog jd_dialog ;
	private Espaco espaco;

	private String dialogoResposta;
	private String msgProcesso;
	private String nome = "Cliente";

	private JButton jb_new;
	private JButton jb_remove;
	private JButton jb_move;
	private JLabel jl_title;

	private JComboBox<String> jcb_nuvemBox;
	private JComboBox<String> jcb_hostBox;
	private JComboBox<String> jcb_vmBox;
	private JComboBox<String> jcb_processoBox;

	private JScrollPane scrollTree;
	private JScrollPane scrollChat;
	private JPanel jp_buttons;
	private JPanel jp_tittle;
	private JTextArea painelChat;
	
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
				String entradaDialogo = ""+tree.getLastSelectedPathComponent();
				if(arg0.getSource() == jb_new) {
					if(tree.getSelectionPath().getPathCount()==1) {
						espaco.enviaMensagem("Servidor", "criaNuvem");
					}
					else if(tree.getSelectionPath().getPathCount()==2) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						espaco.enviaMensagem("Servidor", "criaHost-"+indexNuvem);
					}
					else if(tree.getSelectionPath().getPathCount()==3) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						espaco.enviaMensagem("Servidor", "criaVm-"+indexNuvem+"-"+indexHost);
					}
					else if(tree.getSelectionPath().getPathCount()==4) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						espaco.enviaMensagem("Servidor", "criaProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm);
					}
					else if(tree.getSelectionPath().getPathCount()==5) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
						dialogoResposta = "";
				        criaDialogo(indexNuvem,indexHost,indexVm);
				        if(dialogoResposta.contains("nuvem"+indexNuvem+"/host"+indexHost+"/vm"+indexVm+"/processo"+indexProcesso)) {
				        }
				        else {
				        	espaco.enviaMensagem("Servidor", "enviarMensagemP-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+indexProcesso+"-"+msgProcesso+"//"+dialogoResposta);
				        }			        
					}
				}
				else if(arg0.getSource() == jb_remove) {
					if(tree.getSelectionPath().getPathCount()==2) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						if(listaNuvem.get(indexNuvem).getListaHost().isEmpty()) {
							espaco.enviaMensagem("Servidor", "removeNuvem-"+indexNuvem);
						}
						else {
							JOptionPane.showMessageDialog(null, "Certifique-se de esvaziar o objeto antes de deletá-lo");
						}
					}
					else if(tree.getSelectionPath().getPathCount()==3) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						espaco.enviaMensagem("Servidor", "removeHost-"+indexNuvem+"-"+indexHost);
					}
					else if(tree.getSelectionPath().getPathCount()==4) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						espaco.enviaMensagem("Servidor", "removeVm-"+indexNuvem+"-"+indexHost+"-"+indexVm);
					}
					else if(tree.getSelectionPath().getPathCount()==5) {
						int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
						int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
						int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
						int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
						espaco.enviaMensagem("Servidor", "removeProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+indexProcesso);
					}
				}
				else if(arg0.getSource() == jb_move) {
					if(entradaDialogo.contains("Universo")) {
						JOptionPane.showMessageDialog(null, "Não foi possivel mover esse objeto");
					}
					else if(entradaDialogo.contains("nuvem")) {
						JOptionPane.showMessageDialog(null, "Núvens não podem ser movidas");
					}
					else {
						dialogoResposta = "";
				        criaDialogo(0);
				        if(dialogoResposta.contains("null")) {
				        	JOptionPane.showMessageDialog(null, "NÃ£o foi possivel encontrar o destino");
				        }
				        else if(dialogoResposta.contains("vm")&&entradaDialogo.contains("processo")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
							int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
							espaco.enviaMensagem("Servidor", "moverProcesso-"+indexNuvem+"-"+indexHost+"-"+indexVm+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
				        else if(dialogoResposta.contains("host")&&entradaDialogo.contains("vm")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
							espaco.enviaMensagem("Servidor", "moverVm-"+indexNuvem+"-"+indexHost+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
				        else if(dialogoResposta.contains("nuvem")&&entradaDialogo.contains("host")) {
							int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
							espaco.enviaMensagem("Servidor", "moverHost-"+indexNuvem+"-"+entradaDialogo+"//"+dialogoResposta);
				        }
					}
				}

				else if(arg0.getSource() == jcb_nuvemBox) {
					if(!entradaDialogo.contains("host")) {
						criaDialogo(jcb_nuvemBox.getSelectedIndex());
					}
					else {
						jd_dialog.dispose();
					}
				}
				else if(arg0.getSource() == jcb_hostBox) {
					if(!entradaDialogo.contains("vm")) {
						criaDialogo(jcb_hostBox.getSelectedIndex());
					}
					else {
						jd_dialog.dispose();
					}
				}
				else if(arg0.getSource() == jcb_vmBox) {
					if(!entradaDialogo.contains("processo")) {
						criaDialogo(jcb_vmBox.getSelectedIndex());
					}
					else {
						jd_dialog.dispose();
					}
				}
			}
		};
		
		jb_new.addActionListener(ato);
		jb_remove.addActionListener(ato);
		jb_move.addActionListener(ato);
	}
	
	private void criaDialogo(int index) {
		
		if(jd_dialog == null) {
			jd_dialog = new JDialog(frame, true);
	        jd_dialog.setSize(200, 200);
	        jd_dialog.getContentPane().setLayout(new FlowLayout());
	        jd_dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        
	        jcb_nuvemBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.size();i++) {
				jcb_nuvemBox.addItem(listaNuvem.get(i).getNome());
			}
			jcb_nuvemBox.addActionListener(ato);
			jd_dialog.getContentPane().add(jcb_nuvemBox);
			jd_dialog.setTitle("Escolha o destino");
			jd_dialog.setVisible(true);
			dialogoResposta += jcb_nuvemBox.getSelectedItem();
			if(jcb_hostBox!=null) {
				dialogoResposta += "/"+jcb_hostBox.getSelectedItem();
				if(jcb_vmBox!=null) {
					dialogoResposta += "/"+jcb_vmBox.getSelectedItem();
				}
			}
			jd_dialog = null;
			jcb_hostBox = null;
			jcb_vmBox = null;
		}
		else if(jcb_hostBox == null) {
			jcb_hostBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.get(index).getListaHost().size();i++) {
				jcb_hostBox.addItem(listaNuvem.get(index).getListaHost().get(i).getNome());
			}
			jcb_hostBox.addActionListener(ato);
			jd_dialog.getContentPane().add(jcb_hostBox);
			jd_dialog.setVisible(true);
		}
		else if(jcb_vmBox == null) {
			jcb_vmBox = new JComboBox<String>();
			for(int i=0;i<listaNuvem.get(jcb_nuvemBox.getSelectedIndex()).getListaHost().get(index).getListaVm().size();i++) {
				jcb_vmBox.addItem(listaNuvem.get(jcb_nuvemBox.getSelectedIndex()).getListaHost().get(index).getListaVm().get(i).getNome());
			}
			jcb_vmBox.addActionListener(ato);
			jd_dialog.getContentPane().add(jcb_vmBox);
			jd_dialog.setVisible(true);
		}
	}
	
	private void criaDialogo(int indexNuvem,int indexHost, int indexVm) {
		
		jd_dialog = new JDialog(frame, true);
        jd_dialog.setSize(280, 150);
        jd_dialog.getContentPane().setLayout(null);
        jd_dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		jcb_processoBox = new JComboBox<String>();
		jcb_processoBox.setBounds(73, 20, 124, 24);
		for(int i=0;i<listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().size();i++) {
			jcb_processoBox.addItem(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(i).getNome());
		}
		jcb_processoBox.addActionListener(ato);
		jd_dialog.getContentPane().add(jcb_processoBox);
		JTextField chat = new JTextField();
		chat.setBounds(12, 65, 257, 50);
		jd_dialog.getContentPane().add(chat);
		chat.setColumns(10);
		chat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				msgProcesso = arg0.getActionCommand();
				chat.setText("");
				jd_dialog.dispose();
			}
		});
		jd_dialog.setTitle("Escrever mensagem");
		jd_dialog.setVisible(true);
		jd_dialog = null;
		dialogoResposta = listaNuvem.get(indexNuvem).getNome();
		dialogoResposta += "/"+listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getNome();
		dialogoResposta += "/"+listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getNome();
		dialogoResposta += "/"+jcb_processoBox.getSelectedItem();
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
		
		jb_new = new JButton("Novo");
		jp_buttons.add(jb_new);
		
		jb_remove = new JButton("Remover");
		jp_buttons.add(jb_remove);
		
		jb_move = new JButton("Mover Objeto");
		jp_buttons.add(jb_move);
		
		painelChat = new JTextArea();
		painelChat.setEditable(false);
		painelChat.setText("---------- Log de Mensagens: ----------\n");
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
