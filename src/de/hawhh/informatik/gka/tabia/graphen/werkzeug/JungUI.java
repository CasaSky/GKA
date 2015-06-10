package de.hawhh.informatik.gka.tabia.graphen.werkzeug;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.collections15.Transformer;

import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnEdge;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class JungUI
{
	private Layout<MyOwnVertex, MyOwnEdge> layout; 
	private VisualizationViewer<MyOwnVertex, MyOwnEdge> vv;
	private DefaultModalGraphMouse<MyOwnVertex, MyOwnEdge> gm;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu modeMenu;
	private JMenu optionMenu;
	private JMenu openMenu;
	private JMenuItem chooseFileItem;
	private JMenuItem disjktraItem;
	private JMenuItem bigGraphItem;
	private JMenuItem createFiveGraphsItem;
	private JMenuItem kruskalItem;
	private JMenuItem primNormalItem;
	private JMenuItem primFibItem;
	private String[] bspStrings = { "bsp1", "bsp2", "bsp3", "bsp4", "bsp5", "bsp6" };
	private JComboBox<String> graphFiles = new JComboBox<>(bspStrings);
	
	public JungUI(JungGraph sgv)
	{
		erstelleLayout(sgv);
		erzeugeFrame();
	}

	public void erstelleLayout(JungGraph sgv)
	{
	    // Layout erstellen und den Graphen �bergeben
        layout = new CircleLayout<MyOwnVertex, MyOwnEdge>(sgv.getMygraph());
        layout.setSize(new Dimension(500,500));
        vv = 
              new VisualizationViewer<MyOwnVertex, MyOwnEdge>(layout);
        vv.setPreferredSize(new Dimension(550,550));       
        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        // Create a graph mouse and add it to the visualization component
        gm = new DefaultModalGraphMouse<>();
        gm.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(gm); 
        // Setup up a new vertex to paint transformer...
        Transformer<MyOwnVertex,Paint> vertexPaint = new Transformer<MyOwnVertex,Paint>() {
            public Paint transform(MyOwnVertex i) {
                return Color.RED;
            }
        };  
        
        
        // Set up a new stroke Transformer for the edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<MyOwnEdge, Stroke> edgeStrokeTransformer = 
              new Transformer<MyOwnEdge, Stroke>() {
            public Stroke transform(MyOwnEdge s) {
                return edgeStroke;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyOwnVertex>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<MyOwnEdge>());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	}
	public void erzeugeFrame()
    {
		frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
        // Let's add a menu for changing mouse modes
        menuBar = new JMenuBar();
        modeMenu = gm.getModeMenu(); // Obtain mode menu from the mouse
        modeMenu.setText("Mouse Mode");
        modeMenu.setPreferredSize(new Dimension(80,20)); // Change the size 
        menuBar.add(modeMenu);
        openMenu = new JMenu("Open");
        chooseFileItem = new JMenuItem("Choose File");
        openMenu.add(chooseFileItem);
        menuBar.add(openMenu);
        optionMenu = new JMenu("Option"); // Obtain search menu 
        optionMenu.setPreferredSize(new Dimension(80,20)); // Change the size 
        disjktraItem = new JMenuItem("Dijktra Starten");
        optionMenu.add(disjktraItem);
        bigGraphItem = new JMenuItem("Big Graph");
        optionMenu.add(bigGraphItem);
        createFiveGraphsItem = new JMenuItem("Create Five Graphs");
        optionMenu.add(createFiveGraphsItem);       
        kruskalItem = new JMenuItem("Kruskal Starten");
        optionMenu.add(kruskalItem);
        primNormalItem = new JMenuItem("Prim ohne Fib Starten");
        optionMenu.add(primNormalItem);      
        primFibItem = new JMenuItem("Prim mit Fib Starten");    
        optionMenu.add(primFibItem);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
        gm.setMode(ModalGraphMouse.Mode.PICKING); // Start off in picking mode
		frame.pack();
		frame.setVisible(true);  
		frame.setSize(600, 600);
    }
	public VisualizationViewer<MyOwnVertex, MyOwnEdge> getVisualViewer()
	{
		return vv;
	}
	
	public JMenuItem getDisktraItem()
	{
		return disjktraItem;
	}
	
	public JMenuItem getBigGraphItem()
	{
		return bigGraphItem;
	}
	
	public JMenuItem getKruskalItem()
    {
        return kruskalItem;
    }
	
	public JMenuItem getPrimNormalItem()
    {
        return primNormalItem;
    }
	
	public JMenuItem getPrimFibItem()
    {
        return primFibItem;
    }
	
	public JComboBox<String> getGraphFiles()
	{
		return graphFiles;
	}
	
	public JMenuItem getCreateFiveGraphsItem()
	{
		return createFiveGraphsItem;
	}
	
	public JMenuItem getChooseFileItem()
	{
		return chooseFileItem;
	}
}

