package de.hawhh.informatik.gka.tabia.graphen.werkzeug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import de.hawhh.informatik.gka.tabia.graphen.algorithmen.Dijkstra;
import de.hawhh.informatik.gka.tabia.graphen.algorithmen.Kruskal;
import de.hawhh.informatik.gka.tabia.graphen.algorithmen.Prim;
import de.hawhh.informatik.gka.tabia.graphen.algorithmen.PrimFibo;
import de.hawhh.informatik.gka.tabia.graphen.daten.DateiManager;
import de.hawhh.informatik.gka.tabia.graphen.material.BigGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.JungGraph;
import de.hawhh.informatik.gka.tabia.graphen.material.MyOwnVertex;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class JungWerkzeug
{
	private JungGraph sgv;
	private String pfad;
	private ArrayList<String> listVE;
	private ArrayList<MyOwnVertex> selektedVertex = new ArrayList<>();
	private DateiManager dateimanager;
	private PickedState<MyOwnVertex> pickedState;
	private JungUI ui;
	private Dijkstra di;
	private Kruskal kruskal;
	private Prim prim;
	private PrimFibo primFibo;
	
	public JungWerkzeug(String pfad)
	{
		this.pfad = pfad;
		erzeugeGraph();
		erstelleUI();
		erstelleListener();
		
	}
	
	public JungWerkzeug(JungGraph graph)
	{
		sgv = graph;
		erstelleUI();
		erstelleListener();
	}

	public void erstelleUI()
	{
		ui = new JungUI(sgv);		
	}
	public void erzeugeGraph()
	{
		dateimanager = new DateiManager();
	//datei einlesen
	try {
		dateimanager.dateiLesen(pfad);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// �bergabe der erzeugten Liste
	listVE=dateimanager.getList();
	
	// Erstellung des Graphen nach der gegebenen Referenz
    sgv = new JungGraph(dateimanager.getGraphReference());
    // F�ge die Knoten und Kanten aus der listVE in den Graphen
    sgv.listToGraph(listVE);
	}
	
	 public void erstelleListener()
	 {
		 pickedState= ui.getVisualViewer().getPickedVertexState();

		    // Attach the listener that will print when the vertices selection changes.
		    pickedState.addItemListener(new ItemListener() {

		        @Override
		        public void itemStateChanged(ItemEvent e) {
		            Object subject = e.getItem();
		            // The graph uses Integers for vertices.
		            if (subject instanceof MyOwnVertex) {
		                MyOwnVertex vertex = (MyOwnVertex) subject;
		                if (pickedState.isPicked(vertex)) {
		                    System.out.println("Vertex " + vertex.toString()
		                        + " is now selected");
		                    if (selektedVertex.size()<2)
		                   	 selektedVertex.add(vertex);
		                    else
		                    {
		                   	 selektedVertex.remove(0);
		                   	 selektedVertex.add(vertex);
		                    }
		                } else {
		                    System.out.println("Vertex " + vertex.toString()
		                        + " no longer selected");
		                }
		            }
		        }
		    });
		    ui.getDisktraItem().addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(dijktra())
						JOptionPane.showMessageDialog(null, "Ok! Der k�rzeste Weg ist: "+di.shortPath());
					else
						JOptionPane.showMessageDialog(null, "Sorry");
				}
			});
		    ui.getBigGraphItem().addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(bigGraph())
						JOptionPane.showMessageDialog(null, "Ok! Der k�rzeste Weg ist: "+di.shortPath());
					else
						JOptionPane.showMessageDialog(null, "Sorry, Es gibt keinen Weg !");
				}
			});
		    
		    ui.getCreateFiveGraphsItem().addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					BigGraph bigGraph = new BigGraph("#undirected");
					bigGraph.createFiveGraphs();
				}
			});
		    
		    ui.getKruskalItem().addActionListener( new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    kruskal = new Kruskal(sgv);
                    kruskal.start();
                    new JungWerkzeug(kruskal.minimalgeruest());
                    JOptionPane.showMessageDialog(null, "Laufzeit: "+kruskal.laufzeit()+"\nKantengewicht Summe: "+kruskal.kantenGewichtSumme());
                }
            });
		    
		    ui.getPrimNormalItem().addActionListener( new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    prim = new Prim(sgv);
                    prim.start();
                    new JungWerkzeug(prim.spannbaum());
                    JOptionPane.showMessageDialog(null, "Laufzeit: "+prim.laufzeit()+"\nKantengewicht Summe: "+prim.kantenGewichtSumme());
                }
            });
 
             ui.getPrimFibItem().addActionListener( new ActionListener() {
                 
                 @Override
                 public void actionPerformed(ActionEvent e)
                 {
                     primFibo = new PrimFibo(sgv);
                     primFibo.start();
                     new JungWerkzeug(primFibo.spannbaum());
                     JOptionPane.showMessageDialog(null, "Laufzeit: "+primFibo.laufzeit()+"\nKantengewicht Summe: "+primFibo.kantenGewichtSumme());
                 }
             });
		    
		    ui.getChooseFileItem().addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String graphLink = JOptionPane.showInputDialog(null, "Geben Sie den Graphennamen ein:");
					@SuppressWarnings("unused")
					JungWerkzeug werkzeug = new JungWerkzeug("bspGraphen/"+graphLink+".graph");
				}
			});
//		    ui.getGraphFiles().addActionListener( new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e)
//				{
////					JComboBox<String> selectedChoice = (JComboBox<String>) e.getSource();
////					System.out.println(selectedChoice.getSelectedItem());
//					
//				}
//			});
	 }
	 
	 public boolean dijktra()
	 {
		 //System.out.println("Source: "+selektedVertex.get(0)+" Target: "+selektedVertex.get(1));
		// MyBFS bfs = new MyBFS(sgv, selektedVertex.get(0), selektedVertex.get(1));
		// return (bfs.sucheTarget(selektedVertex.get(0)));
		 di = new Dijkstra(sgv, selektedVertex.get(0), selektedVertex.get(1));
		 return di.start();
	 }
	 
	 public boolean bigGraph()
	 {
			int maxKnoten = Integer.parseInt(JOptionPane.showInputDialog(null, "Geben Sie die Knoten Anzahl ein: "));
			int maxKanten = Integer.parseInt(JOptionPane.showInputDialog(null, "Geben Sie die Kanten Anzahl ein: "));
			BigGraph big = new BigGraph("#undirected", maxKnoten, maxKanten);
			big.generateGraph();
			big.show();
			MyOwnVertex target = new MyOwnVertex("0",0);
			Collection<MyOwnVertex> next = big.graph().getMygraph().getNeighbors(target); 
			Object[] array = next.toArray();
			if (array.length!=0)
			{
			MyOwnVertex source = ((MyOwnVertex) array[0]);
			di = new Dijkstra(big.graph(), source, target);
			boolean found = di.start();
			return found;
			}
			else
				return false;
	 }
}
