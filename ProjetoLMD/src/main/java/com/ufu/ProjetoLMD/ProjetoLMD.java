package com.ufu.ProjetoLMD;

import java.net.URL;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class ProjetoLMD implements ViewerListener {
	protected boolean loop = true;
	private Graph graph = new SingleGraph("Busca");

	public static void main(String args[]) {
		new ProjetoLMD();
	}
	
	public ProjetoLMD() {			
		URL styleURL = getClass().getResource("style.css");
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph.setAttribute("ui.stylesheet", "url('" + styleURL + "')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");             

		graph.setAutoCreate(true);
		graph.setStrict(false);		
		
		Viewer viewer = graph.display();

		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

		preBuiltGraph();
//		randomGraph(4, 15);

		for (Node node : graph) {
			node.addAttribute("ui.label", node.getId());
		}

		ViewerPipe fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(graph);				

		while(loop) {
			fromViewer.pump(); 
		}
	}

	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
		clearGraph();
	}

	public void buttonReleased(String id) {		
		String ordemBusca;

		Object[] options = { "Busca em Largura", "Busca em Profundidade", "Cancelar" };

		JPanel panel = new JPanel();
		panel.add(new JLabel("Escolha o tipo de busca desejada"));

		int result = JOptionPane.showOptionDialog(
				null, panel, "Busca no grafo",
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null, options, null);

		switch (result) {
		case 0:
			ordemBusca = explore(graph.getNode(id), 'B');
			break;

		case 1:
			ordemBusca = explore(graph.getNode(id), 'D');
			break;

		default:
			return;
		}

		JOptionPane.showMessageDialog(
				null, "A ordem percorrida no grafo foi:\n" + ordemBusca,
				"Resultado", JOptionPane.PLAIN_MESSAGE);
		clearGraph();
	}

	public String explore(Node source, char operation) {	

		String ordemBusca = "";

		Iterator<? extends Node> nodeIterator;

		switch (operation) {
		case 'B':
			nodeIterator = source.getBreadthFirstIterator();
			break;

		case 'D': 
			nodeIterator = source.getDepthFirstIterator();
			break;

		default:
			return "";			
		}

		while (nodeIterator.hasNext()) {
			Node nextNode = nodeIterator.next();
			nextNode.setAttribute("ui.class", "marked");
			ordemBusca += nextNode.getId() + " ";
			sleep();
		}

		return ordemBusca;
	}

	protected void sleep() {
		try { Thread.sleep(1000); } catch (Exception e) {}
	}

	public void clearGraph() {
		for(Node node : graph) {
			node.setAttribute("ui.class", "");
		}
	}

	public void randomGraph(int averageDegree, int amountOfNodes) {
		Generator gen = new RandomGenerator(averageDegree);
		gen.addSink(graph);
		gen.begin();
		for(int i=0; i<amountOfNodes; i++)
			gen.nextEvents();
		gen.end();
	}
	
	public void preBuiltGraph() {
		graph.addEdge("12", "A", "B");
		//graph.addEdge("14", "A", "D");

		graph.addEdge("23", "B", "C");
		graph.addEdge("25", "B", "E");

		//graph.addEdge("34", "C", "D");
		//graph.addEdge("36", "C", "F");

		graph.addEdge("47", "D", "G");

		graph.addEdge("58", "E", "H");

		//graph.addEdge("68", "F", "H");
		graph.addEdge("69", "F", "I");
		graph.addEdge("37", "H", "J");
		graph.addEdge("20", "J", "A");
		graph.addEdge("19", "B", "J");
		graph.addEdge("15", "A", "I");
		graph.addEdge("13", "E", "J");
		graph.addEdge("60", "K", "L");
		graph.addEdge("14", "K", "C");
		graph.addEdge("35", "B", "X");
		graph.addEdge("40", "L", "D");
		//graph.addEdge("79", "G", "I");
		
	}
}
//mvn install  	
//mvn exec:java -Dexec.mainClass="com.ufu.ProjetoLMD.ProjetoLMD" 

