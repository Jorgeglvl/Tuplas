package common;
import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class NuvemModel implements TreeModel {

	private String raiz = "Raiz";
	private ArrayList<Nuvem> listaNuvem = new ArrayList<Nuvem>();
	private ArrayList<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

	public NuvemModel() {
		
	}
	
	public NuvemModel(ArrayList<Nuvem> listaNuvem) {
		this.listaNuvem = listaNuvem;
	}

	public Object getChild(Object parent, int index) {
		if(parent == raiz) {
			return listaNuvem.get(index);
		}
		if(parent instanceof Nuvem) {
			return ((Nuvem) parent).getListaHost().get(index);
		}
		if(parent instanceof Host) {
			return ((Host) parent).getListaVm().get(index);
		}
		if(parent instanceof Vm) {
			return ((Vm) parent).getListaProcesso().get(index);
		}
		
		throw new IllegalArgumentException("Invalid parent class"+ parent.getClass().getSimpleName());
	}

	public int getChildCount(Object parent) {
		if(parent == raiz) {
			return listaNuvem.size();
		}
		if(parent instanceof Nuvem) {
			return ((Nuvem) parent).getListaHost().size();
		}
		if(parent instanceof Host) {
			return ((Host) parent).getListaVm().size();
		}
		if(parent instanceof Vm) {
			return ((Vm) parent).getListaProcesso().size();
		}

		throw new IllegalArgumentException("Invalid parent class"+ parent.getClass().getSimpleName());
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(parent == raiz) {
			return listaNuvem.indexOf(child);
		}
		if(parent instanceof Nuvem) {
			return ((Nuvem) parent).getListaHost().indexOf(child);
		}
		if(parent instanceof Host) {
			return ((Host) parent).getListaVm().indexOf(child);
		}
		if(parent instanceof Vm) {
			return ((Vm) parent).getListaProcesso().indexOf(child);
		}

		return 0;
	}

	public Object getRoot() {
		return raiz;
	}

	public boolean isLeaf(Object node) {
		return node instanceof Processo;
	}

	public void valueForPathChanged(TreePath path, Object newValue) {

	}

	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}

	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}
	
	public ArrayList<Nuvem> getListaNuvem() {
		return listaNuvem;
	}
}
