package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getCromosomi(){
		String sql = "SELECT DISTINCT g.Chromosome as c "
				+ "FROM genes g "
				+ "WHERE g.Chromosome != 0";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				result.add(res.getInt("c"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Arco> getArchi() {
		String sql = "SELECT g1.Chromosome as c1, g2.Chromosome as c2,  SUM( distinct i.Expression_Corr) AS peso "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.Chromosome != g2.Chromosome AND i.GeneID1 = g1.GeneID AND i.GeneID2 = g2.GeneID AND g1.Chromosome != 0 AND g2.Chromosome !=0 "
				+ "GROUP BY g1.Chromosome, g2.Chromosome";
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Arco a = new Arco(res.getInt("c1"), res.getInt("c2"), res.getDouble("peso"));
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public List<Integer> getCromosomi(){
//		String sql = "SELECT distinct g.Chromosome "
//				+ "FROM genes g "
//				+ "WHERE g.Chromosome >0";
//		
//		List<Integer> result = new ArrayList<>();
//		Connection conn = DBConnect.getConnection();
//
//		try {
//			PreparedStatement st = conn.prepareStatement(sql);
//			ResultSet res = st.executeQuery();
//			while (res.next()) {
//				result.add(res.getInt("Chromosome"));
//			}
//			res.close();
//			st.close();
//			conn.close();
//			return result;
//			
//		} catch (SQLException e) {
//			throw new RuntimeException("Database error", e) ;
//		}
//	}
//	public List<Arco> getArchi(){
//		String sql = "SELECT g1.Chromosome AS c1, g2.Chromosome AS c2, SUM(distinct i.Expression_Corr) AS peso "
//				+ "FROM interactions i, genes g1, genes g2 "
//				+ "WHERE g1.Chromosome != g2.Chromosome AND g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2 AND g1.Chromosome !=0 AND g2.Chromosome !=0 "
//				+ "GROUP BY g1.Chromosome, g2.Chromosome";
//		List<Arco> result = new ArrayList<>();
//		Connection conn = DBConnect.getConnection();
//
//		try {
//			PreparedStatement st = conn.prepareStatement(sql);
//			ResultSet res = st.executeQuery();
//			while (res.next()) {
//				Arco a = new Arco(res.getInt("c1"), res.getInt("c2"), res.getDouble("peso"));
//				result.add(a);
//			}
//			res.close();
//			st.close();
//			conn.close();
//			return result;
//			
//		} catch (SQLException e) {
//			throw new RuntimeException("Database error", e) ;
//		}
//	}
	


	
}
