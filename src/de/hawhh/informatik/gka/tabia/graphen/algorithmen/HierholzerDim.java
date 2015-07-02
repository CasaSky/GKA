package de.hawhh.informatik.gka.tabia.graphen.algorithmen;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import de.hawhh.informatik.gka.tabia.graphen.material.*;
import de.hawhh.*;

public class HierholzerDim
{
		private JungGraph graph;
		private MyOwnVertex startNode;
		
		private List<MyOwnEdge> visitedEdges;
		private Set<MyOwnVertex> visitedVertices;
		private List<List<MyOwnVertex>> disjointCircles;
		private Queue<MyOwnVertex> workingQueue;
		
		HierholzerDim(JungGraph graph, MyOwnVertex start)
		{
			this.graph 		= graph;
			this.startNode 	= start;			
			this.visitedEdges = new LinkedList<>();
			this.visitedVertices = new HashSet<>();
			this.disjointCircles = new LinkedList<>();
			this.workingQueue = new ArrayDeque<>();
		}
		
		void startHierholzer(){
			
			int counter = 0;
			//if(!precondition()) return;
			
			while(visitedEdges.size() < graph.getMygraph().getEdgeCount()){

				System.out.println("Counter"+counter++);
				// initialize temporary-startnode and add to queue
				MyOwnVertex tmpstartNode = chooseNextVertex(); 			
				workingQueue.offer(tmpstartNode);
				
				List<MyOwnVertex> oneCircle = new LinkedList<MyOwnVertex>();
				while(!workingQueue.isEmpty()){
					
					// get next Vertex from Queue
					MyOwnVertex vertex = workingQueue.poll();
					
					// add Vertex to Circle
					oneCircle.add(vertex);
					visitedVertices.add(vertex);
					
					// Outgoing Edge-Set
					Collection<MyOwnEdge> outgouingEdges = new LinkedHashSet<>(graph.getMygraph().getOutEdges(vertex));
					// Unvisited Edge-Set = {Outgoing Edge-Set} \ {Deleted Edge-Set}
					outgouingEdges.removeAll(visitedEdges);
					
					MyOwnEdge nextEdge = null;
					// Select Edge from Unvisited Edge-Set
					for(MyOwnEdge anEdge : outgouingEdges){
						nextEdge = anEdge;
						break;
					}
					
					// Note the Edge as visited
					visitedEdges.add(nextEdge);

					// Get the Vertex on the opposite 
					MyOwnVertex target;
					if (!nextEdge.target().equals(vertex))
						target = nextEdge.target();
					else
							target = nextEdge.source();
					
					// if circle close
					if(tmpstartNode.equals(target))
					{
						// add Vertex no the opposite to circle and save circle
						oneCircle.add(target);
						disjointCircles.add(oneCircle);
					}
					else{
						// add next Vertex to queue
						workingQueue.offer(target);
					}
				}
			}
			
			// build EulerianPath Recursive
			List<MyOwnVertex> eulerianPath = new ArrayList<>();
			buildEulerianCircuit(eulerianPath,0);

		}
		
		
		private MyOwnVertex chooseNextVertex(){
			
			if(visitedEdges.isEmpty()) return startNode;
			
			for(MyOwnVertex vertex : visitedVertices){
				
				Set<MyOwnEdge> outgouingEdges = new HashSet<>(graph.getMygraph().getOutEdges(vertex));
				outgouingEdges.removeAll(visitedEdges);
				
				if(!outgouingEdges.isEmpty()) return vertex;
				
			}
			return null;
		}
		
		private List<MyOwnVertex> buildEulerianCircuit(List<MyOwnVertex> eulerianPath, int i){
			
			// if index i >= totalCircles
			if(i >= disjointCircles.size()) return eulerianPath;
			
			// if last Circle
			if(i+1 >= disjointCircles.size())
			{
				List<MyOwnVertex> oneCircle = disjointCircles.get(i);
				for(MyOwnVertex vertex: oneCircle)
				{
					eulerianPath.add(vertex);
				}
				return eulerianPath;
			}
			
			MyOwnVertex nextCircleCut = disjointCircles.get(i+1).get(0);
			
			List<MyOwnVertex>     oneCircle = disjointCircles.get(i);
			Iterator<MyOwnVertex> oneCircleIterator = oneCircle.iterator();
			eulerianPath.add(oneCircleIterator.next());
			
			while(oneCircleIterator.hasNext())
			{
				// get Next Vertex
				MyOwnVertex vertex = oneCircleIterator.next();
				
				// if Circle cutting with next Circle
				if(vertex.equals(nextCircleCut))
				{
					buildEulerianCircuit(eulerianPath, i+1);
				}
				else
				{
					eulerianPath.add(vertex);
				}
			}
			
			return eulerianPath;
		}
		
		private boolean precondition(){
			boolean c1 = (graph.getMygraph().getEdgeCount() > graph.getMygraph().getVertexCount()-1);
			return c1;
		}
		
		public static void main(String[] args)
		{
			RandomEulerGraph graph = new RandomEulerGraph("#undirected");
			graph.generateGraph();
			List<MyOwnVertex> vertices = new ArrayList<>(graph.graph().vertexSet());
			MyOwnVertex start = vertices.get(0);
			HierholzerDim dim = new HierholzerDim(graph.graph(), start);
			dim.startHierholzer();
			System.out.println("Graph Edge Size "+graph.graph().edgeSet().size());
			System.out.println("Vist Edge Size "+dim.visitedEdges.size());
			boolean test = EulerkreisProperties.isEulerKreis(dim.visitedEdges, graph.graph());
			System.out.println(test);
		}
}
