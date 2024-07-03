package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> cromosomi;
	private List<Integer> best;
	private double nMax;
	
	
	public Model() {
		this.dao = new GenesDao();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.cromosomi = dao.getCromosomi();
	}
	

	
	public void creaGrafo() {
		Graphs.addAllVertices(this.grafo, this.cromosomi);
		List<Arco> archi = dao.getArchi();
		for (Arco a: archi) {
				Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getPeso());
		}
	}
	
	public int getV() {
		return this.grafo.vertexSet().size();
		
	}
	public int getA() {
		Set<DefaultWeightedEdge> archi = this.grafo.edgeSet();
		return archi.size();
	}
	
	public double getMax() {
		double max =0;
		for (Arco a : dao.getArchi()) {
			if (a.getPeso()>max) {
				max = a.getPeso();
			}
		}
		return max;
		
	}
	
	public double getMin() {
		double min = 10000000;
		for (Arco a : dao.getArchi()) {
			if (a.getPeso()<min) {
				min = a.getPeso();
			}
		}
		return min;
	}
	
	public int contaMaggiori(double s) {
		int n =0;
		for (Arco a : dao.getArchi()) {
			if (a.getPeso()>s) {
				n++;
			}
		}
		return n;
	}
	public int contaMinori(double s) {
		int n =0;
		for (Arco a : dao.getArchi()) {
			if (a.getPeso()<s) {
				n++;
			}
		}
		return n;
	}
	
	
	public List<Integer> cercaPercorso(double s){
		this.best = new ArrayList<>();
		this.nMax = 0;
		List<Integer> parziale = new ArrayList<>();
		
		for (Integer c: this.grafo.vertexSet()) {
			parziale.add(c);
			ricorsione(c, parziale, s);
			parziale.remove(parziale.size()-1);
		}
		
		return this.best;
		
	}

	private void ricorsione(Integer v1, List<Integer> parziale, double s) {
		//condizione di uscita 
		Integer corrente = v1;
		List<Integer> successori  = Graphs.successorListOf(this.grafo, corrente);
			if (calcolaPeso(parziale) >= this.nMax) {
				this.best = new ArrayList<>(parziale);
				this.nMax = calcolaPeso(parziale);
			}
			
		for (Integer v2: successori) {
			double peso =this.grafo.getEdgeWeight(this.grafo.getEdge(corrente, v2));
			if (peso>s &&!parziale.contains(v2)) {
				parziale.add(v2);
				ricorsione(v2, parziale, s);
				parziale.remove(parziale.size()-1);
			}
	}
	
	
	
}

	public  double calcolaPeso(List<Integer> parziale) {
		double peso = 0;
		if (parziale.size()<=1) {
			return peso;
		}
		for (int i =1; i<parziale.size(); i++) {
			DefaultWeightedEdge d = this.grafo.getEdge(parziale.get(i-1), parziale.get(i));
			peso += this.grafo.getEdgeWeight(d);
		}
		return peso;
	}
	
	
	
}