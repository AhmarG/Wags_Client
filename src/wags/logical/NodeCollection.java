package wags.logical;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;

public class NodeCollection implements IsSerializable
{	
	private ArrayList<Node> nodes;
	
	public NodeCollection()
	{
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	
	public void addNodesToPanel(AbsolutePanel panel) {
		for (int i = 0; i < nodes.size(); i++) {
			panel.add(nodes.get(i).getLabel(), 5, 5 + (50 * i));
		}
	}
	
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
	
	public Node getNode(int index)
	{
		if(index >= 0 && index < nodes.size())
		{
			return nodes.get(index);
		}
		else
			throw new IndexOutOfBoundsException();
	}
	
	public Node getNodeByLabel(Label l)
	{
		for(int i = 0; i < nodes.size(); i++)
		{
			if(nodes.get(i).getLabel().getText() == l.getText())
			{
				return nodes.get(i);
			}
		}
		throw new NoSuchElementException();
	}
	
	public Node getNodeByLabelText(String labelText) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getLabel().getText() == labelText) {
				return nodes.get(i);
			}
		}
		throw new NoSuchElementException();
	}
	
	public void resetNodes(AbsolutePanel panel) {
		for (int i = 0; i < nodes.size(); i++) {
			panel.remove(nodes.get(i).getLabel());
		}
		addNodesToPanel(panel);
	}
	
	public void resetNodeStyles(String nodeType)
	{
		for(int i = 0; i < nodes.size(); i++)
		{
			if(nodeType.equals(DSTConstants.NODE_STRING_DRAGGABLE)){
				nodes.get(i).getLabel().setStyleName("string_node");
			}
			else{
				nodes.get(i).getLabel().setStyleName("node");
			}
		}
	}
	
	public void removeSelectedState() {
		for(int i = 0; i < nodes.size(); i++){
			nodes.get(i).getLabel().removeStyleName("selected_node");
		}
		
	}
			
	public void makeNodesDraggable(DragController dc)
	{
		for(int i = 0; i < nodes.size(); i++)
		{
			dc.makeDraggable(nodes.get(i).getLabel());
		}
	}
	
	public void makeNodesNotDraggable(DragController dc)
	{
		try
		{
			for(int i = 0; i < nodes.size(); i++)
			{
				dc.makeNotDraggable(nodes.get(i).getLabel());
			}
		}
		catch(Exception e)
		{
			System.out.println("Still ok");
		}
	}
	
	public void emptyNodes()
	{
		nodes.clear();
	}
	
	public int size() {
		return nodes.size();
	}

}
