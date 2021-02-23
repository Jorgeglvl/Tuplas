import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Teste extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public Teste() {
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(75, 12, 124, 24);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nuvem", "Teste", "Eai", "Ulrix", "Pisao"}));
		
		textField = new JTextField();
		textField.setBounds(12, 65, 257, 24);
		textField.setColumns(10);
		setLayout(null);
		add(comboBox);
		add(textField);

	}
	
	public void arvore() {
		/*new DefaultTreeModel(new DefaultMutableTreeNode("Universo") {
		
		private static final long serialVersionUID = 1L;

		{
			for(int i=0;i<listaNuvem.size();i++) {
				DefaultMutableTreeNode noNuvem = new DefaultMutableTreeNode(listaNuvem.get(i));
				for(int j=0;j<listaNuvem.get(i).getListaHost().size();j++) {
					DefaultMutableTreeNode noHost = new DefaultMutableTreeNode(listaNuvem.get(i).getListaHost().get(j));
					for(int k=0;j<listaNuvem.get(i).getListaHost().get(j).getListaVm().size();k++) {
						DefaultMutableTreeNode noVm = new DefaultMutableTreeNode(listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k));
						noHost.add(noVm);
					}
					noNuvem.add(noHost);
				}
				add(noNuvem);
			}
		}}));*/
	}
	
	public void arvore2() {
	/*new DefaultTreeModel(new DefaultMutableTreeNode("Universo") {

		private static final long serialVersionUID = 1L;

		{
			for(int i=0;i<listaNuvem.size();i++) {
				DefaultMutableTreeNode noNuvem = new DefaultMutableTreeNode(listaNuvem.get(i));
				for(int j=0;j<listaNuvem.get(i).getListaHost().size();j++) {
					DefaultMutableTreeNode noHost = new DefaultMutableTreeNode(listaNuvem.get(i).getListaHost().get(j));
					for(int k=0;j<listaNuvem.get(i).getListaHost().get(j).getListaVm().size();k++) {
						DefaultMutableTreeNode noVm = new DefaultMutableTreeNode(listaNuvem.get(i).getListaHost().get(j).getListaVm().get(k));
						noHost.add(noVm);
					}
					noNuvem.add(noHost);
				}
				add(noNuvem);
			}
		}
	}
	);*/
	/*for(int i=0;i<listaNuvem.size();i++) {
		raiz.add(new DefaultMutableTreeNode(listaNuvem.get(i)));
	}*/
	}
	
	public void botao() {
		//else if(arg0.getSource() == processoBox) {
			//if(!entradaDialogo.contains("mensagem")) {
				//criaDialogo(processoBox.getSelectedIndex());
			//}
			//else {
				//jdialog.dispose();
			//}
		//}
	}
	
	public void enviar() {
		/*
		else if(entradaDialogo.contains("processo")) {
			//chama a função criaDialogo(-1) só com a opção de escolher o processo da mesma vm
			int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
			int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
			int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
			int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
			System.out.println(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexProcesso));
			dialogoResposta = "";
	        criaDialogo(indexNuvem,indexHost,indexVm);
			listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(indexProcesso);
		}*/
	}
	public void enviar2() {
        /*if(dialogoResposta.contains("processo")) {
			int indexNuvem = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(0), tree.getSelectionPath().getPathComponent(1));
			int indexHost = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(1), tree.getSelectionPath().getPathComponent(2));
			int indexVm = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(2), tree.getSelectionPath().getPathComponent(3));
			int indexProcesso = raiz.getIndexOfChild(tree.getSelectionPath().getPathComponent(3), tree.getSelectionPath().getPathComponent(4));
			System.out.println(listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexProcesso));
			listaNuvem.get(indexNuvem).getListaHost().get(indexHost).getListaVm().get(indexVm).getListaProcesso().get(indexProcesso);
        }
        else*/ 
	}
}
