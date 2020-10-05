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
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph.setAttribute("ui.stylesheet", "url('file:///C:\\Users\\pedro\\Desktop\\ProjetoLMD\\src\\main\\java\\com\\ufu\\ProjetoLMD\\style.css')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");             

		graph.setAutoCreate(true);
		graph.setStrict(false);		
		
		Viewer viewer = graph.display();

		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

		preBuiltGraph();

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
		panel.add(new JLabel("Escolha uma das opções:"));

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
	
	public void preBuiltGraph() {
		graph.addEdge("12", "1", "2");
		graph.addEdge("14", "1", "4");

		graph.addEdge("23", "2", "3");
		graph.addEdge("25", "2", "5");

		graph.addEdge("34", "3", "4");
		graph.addEdge("36", "3", "6");

		graph.addEdge("47", "4", "7");

		graph.addEdge("58", "5", "8");

		graph.addEdge("68", "6", "8");
		graph.addEdge("69", "6", "9");
		
		graph.addEdge("79", "7", "9");
	}
}
