package wags.logical.RadixProblems;

import java.util.ArrayList;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Line;

import wags.Proxy;
import wags.logical.DSTConstants;
import wags.logical.DisplayManager;
import wags.logical.EdgeCollection;
import wags.logical.EdgeParent;
import wags.logical.Node;
import wags.logical.NodeClickable;
import wags.logical.NodeCollection;
import wags.logical.NodeDragController;
import wags.logical.TraversalContainer;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;


public class SearchDisplayManager extends DisplayManager implements IsSerializable
{
	private EdgeCollection edgeCollection;
	private SearchProblem problem;

	//permanent widgets
	private TextArea counterPanel;
	private AbsolutePanel labelPanel;


	public SearchDisplayManager(DrawingArea canvas, AbsolutePanel panel,
			NodeCollection nc, SearchProblem problem)
	{
		System.out.println(problem.getName());
		this.panel = panel;
		this.canvas = canvas;
		this.nodeCollection = nc;
		this.problem = problem;
		super.problem = problem;
		this.itemsInPanel = new ArrayList<Widget>();
		drawLines();
	}

	@Override
	public void displayProblem()
	{
		cont = new TraversalContainer(this);
		addProblemTextArea();
		addCounterPanel();
		addLeftButtonPanel();
		addMiddlePanel();
		addRightButtonPanel();
		addBackButton();
		addResetButton();
		addEvaluateButton();
		addBucketLabels();
				
		insertNodesAndEdges();
	}
	
	@Override
	protected void addProblemTextArea() {
        TextArea t = new TextArea();
        t.setStyleName("problem_statement");
        t.setPixelSize(400, 90);   // was 598,90
        t.setReadOnly(true);
        t.setText(problem.getProblemText());
        Proxy.getDST().add(t, 2, 5);
    }
	
	private void addCounterPanel(){
		counterPanel = new TextArea();
		counterPanel.setStyleName("problem_statement");
		counterPanel.setPixelSize(195, 90);
		counterPanel.setReadOnly(true);
		counterPanel.setText("Current Position: Ones");
		Proxy.getDST().add(counterPanel, 407, 5);
		
	}
	
	private void insertNodesAndEdges()
	{
		cont = new TraversalContainer(this); //for reset of traversal problems
		if(problem.getInsertMethod().equals(DSTConstants.INSERT_METHOD_VALUE))
		{
			insertNodesByValue(problem.getNodes());
		}
		else
		{
			insertNodesByValueAndLocation(problem.getNodes(), problem.getXPositions(),
					problem.getYPositions(), problem.getNodesDraggable(), problem.getNodeType());
		}
		
	}
	
	private boolean showingSubMess;
	
	private void addBucketLabels() {
		AbsolutePanel bucketHolder = new AbsolutePanel();
		bucketHolder.setPixelSize(600, 30);
		bucketHolder.setStyleName("bucket_holding_panel");
		
		for (int i = 0; i < 10; i++) {
			labelPanel = new AbsolutePanel();
			Label l = new Label("" + i);
			labelPanel.add(l);
			labelPanel.setPixelSize(60, 30);
			labelPanel.setStyleName("bucket_panel");
			bucketHolder.add(labelPanel, (60 * i) + 1, 0);
		}
		
		Proxy.getDST().add(bucketHolder, 3, 174);
	}

	@Override
	protected void addResetButton()
	{
		resetButton = new Button("Reset");
		resetButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event)
			{
				removeWidgetsFromPanel();

				for(int i = 0; i < getNodes().size(); i++)
				{
					problem.getGridPanel().remove(getNodes().get(i).getLabel());
				}

				nodeCollection.emptyNodes();
				insertNodesAndEdges();
			}
		});
		resetButton.setStyleName("control_button");
		leftButtonPanel.add(resetButton, 62, 2);
	}

	private void addEvaluateButton()
	{
		evaluateButton = new Button("Evaluate");
		evaluateButton.setWidth("124px");
		evaluateButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
			//	setEdgeParentAndChildren();
				String evalResult = problem.getEval().evaluate(problem.getName(), problem.getArguments(), getNodes(), null);

				if(showingSubMess == true)
				{
					Proxy.getDST().remove(submitText);
					Proxy.getDST().remove(submitOkButton);
				}
				
				if(evalResult.equals("")) return;  //used with the traversal problems with help on, if it was empty string
												   //then the user made a correct click and we don't need to save or display
												   //anything
				submitText.setText(evalResult);				
				addToPanel(submitText, DSTConstants.SUBMIT_X, DSTConstants.SUBMIT_MESS_Y);
				int yOffset = DSTConstants.SUBMIT_MESS_Y + submitText.getOffsetHeight()+2;
				addToPanel(submitOkButton, DSTConstants.SUBMIT_X, yOffset);
				showingSubMess = true;

			}
		});
		showingSubMess = false;
		evaluateButton.setStyleName("control_button");
		rightButtonPanel.add(evaluateButton, 257, 2);
		
		submitText = new TextArea();
		submitText.setCharacterWidth(30);
		submitText.setReadOnly(true);
		submitText.setVisibleLines(5);
		submitOkButton = new Button("Ok");
		submitOkButton.setStyleName("control_button");
		submitOkButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Proxy.getDST().remove(submitText);
				Proxy.getDST().remove(submitOkButton);
				showingSubMess = false;
			}	
		});
	}
	
	public void drawLines(){
		for(int i=1;i<=9;i++){
			Line line = new Line(i*60,50,i*60,700);
			drawEdge(line);
		}
	}

	public void insertNodesByNumber(int numNodes)
	{
		for(int i = 0; i < numNodes; i++)
		{
			Label label = new Label(((char)('A'+i))+"");
			label.setStyleName("node");
			panel.add(label, 5, 150+(50 *i));
			NodeDragController.getInstance().makeDraggable(label);
			nodeCollection.addNode(new Node(((""+(char)('A'+i))), label));
		}
	}

	public void insertNodesByValue(String nodes)
	{
		String[] splitNodes = nodes.split(" ");
		for(int i = 0; i < nodes.length(); i++)
		{
			Label label = new Label(splitNodes[i]);
			label.setStyleName("node");
			label.getElement().getStyle().setTop(10+(45*i), Style.Unit.PX);
			panel.add(label);
			NodeDragController.getInstance().makeDraggable(label);
			nodeCollection.addNode(new Node(splitNodes[i], label));
		}
	}
	
	public void insertNodesByValueAndLocation(String nodes, int[][] xPositions, int[][] yPositions, boolean draggable,
			String nodeType)
	{
		int spaces = 0;
		int current = ((Evaluation_RadixSortWithHelp)problem.getEval()).getCurrent();
		for (int i = 0; i < nodes.length(); i++) {
			if (nodes.charAt(i) == ' ')
				spaces++;
		}
		
		spaces++;
		String[] splitNodes = nodes.split(" ");
		if (spaces != xPositions[0].length || spaces != yPositions[0].length)
			throw new NullPointerException(); //need to find right exception
		else if(nodeType.equals(DSTConstants.NODE_STRING_DRAGGABLE)) {
			
			for (int i = 0; i < splitNodes.length; i++) {
				Label label = new Label(splitNodes[i]);
				label.setStyleName("string_node");
                problem.getGridPanel().add(label, xPositions[current][i], yPositions[current][i]);
                NodeDragController.getInstance().makeDraggable(label);
                nodeCollection.addNode(new Node(splitNodes[i], label));
			}
		}	
		else
		{
			for(int i = 0; i <splitNodes.length; i++)
			{
				Label label = new Label(splitNodes[i]);
				label.setStyleName("node");
				problem.getGridPanel().add(label, xPositions[current][i], yPositions[current][i]);
				if(draggable) NodeDragController.getInstance().makeDraggable(label);
				
				if(nodeType.equals(DSTConstants.NODE_DRAGGABLE))
					nodeCollection.addNode(new Node(splitNodes[i], label));
				else if(nodeType.equals(DSTConstants.NODE_CLICKABLE_FORCE_EVAL))
					nodeCollection.addNode(new NodeClickable(splitNodes[i], label, cont, true));
				else
					nodeCollection.addNode(new NodeClickable(splitNodes[i], label, cont, false));
			}
		}
	}
	
	public TextArea getEvalText(){
		return this.submitText;
	}
	
	public TextArea getCounterPanel(){
		return this.counterPanel;
	}

	@Override
	public ArrayList<Node> getNodes()
	{
		return nodeCollection.getNodes();
	}

	@Override
	public ArrayList<EdgeParent> getEdges()
	{
		return edgeCollection.getEdges();
	}

	public void makeNodesDraggable()
	{
		nodeCollection.makeNodesDraggable(NodeDragController.getInstance());
	}

	public void makeNodesNotDraggable()
	{
		nodeCollection.makeNodesNotDraggable(NodeDragController.getInstance());
	}

	public void resetNodeStyles()
	{
		nodeCollection.resetNodeStyles(problem.getNodeType());
	}

	public void resetEdgeStyles()
	{
		edgeCollection.resetEdgeColor();
	}

	public void setEdgeParentAndChildren()
	{
		edgeCollection.setParentAndChildNodes();
	}
}
