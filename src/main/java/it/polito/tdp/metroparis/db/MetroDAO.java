package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;
import it.polito.tdp.metroparis.model.coppieF;

public class MetroDAO {

	public List<Fermata> readFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> readLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	public boolean isConnesse(Fermata partenza, Fermata arrivo) {
		String sql = "SELECT COUNT(*) as c "
				+ "FROM connessione "
				+ "WHERE id_stazP = ? "
				+ "AND id_stazA = ? ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, partenza.getIdFermata());
			st.setInt(2, arrivo.getIdFermata());
			
			ResultSet rs = st.executeQuery();
			
			rs.first();
			
			int c = rs.getInt("c");
			

			st.close();
			conn.close();
			
			return c != 0;

		} catch (SQLException e) {
			e.printStackTrace();
//			throw new RuntimeException("Errore di connessione al Database.");
			return false;
		}
		
	}

	public List<Fermata> trovaCollegate(Fermata partenza) {
		
		// uso isAdjacent
		
		String sql = "SELECT * "
				+ "FROM fermata "
				+ "WHERE id_fermata IN ( "
				+ "SELECT id_StazA "
				+ "FROM connessione "
				+ "WHERE id_stazP = ? "
				+ "GROUP BY id_stazA "
				+ ") "
				+ "ORDER BY nome ASC";

		List<Fermata> fermate = new ArrayList<Fermata>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, partenza.getIdFermata());
			
			ResultSet rs = st.executeQuery();
			
			
			
			while(rs.next()) {
				
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
			
				fermate.add(f);
			}
			
				
			

			st.close();
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
//			throw new RuntimeException("Errore di connessione al Database.");
			return null;
		}
		
		
		

		
		
		return fermate;
	}

	
public List<Fermata> trovaIdCollegate(Fermata partenza, Map<Integer, Fermata> fermateIdMap) {
		
		// uso isAdjacent
		
		String sql = "SELECT id_StazA "
				+ "FROM connessione "
				+ "WHERE id_stazP = ? "
				+ "GROUP BY id_stazA";

		List<Fermata> fermate = new ArrayList<Fermata>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, partenza.getIdFermata());
			
			ResultSet rs = st.executeQuery();
			
			
			
			while(rs.next()) {
			
				Integer idFermata = rs.getInt("id_stazA");
//				riprendo dalla mappa la mia fermata
				fermate.add(fermateIdMap.get(idFermata));
			}
			
				
			

			st.close();
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
//			throw new RuntimeException("Errore di connessione al Database.");
			return null;
		}
		
		
		

		
		
		return fermate;
	}
	


public List<coppieF> getAllCoppie (Map<Integer, Fermata> fermateIdMap) {
	
	String sql = "SELECT DISTINCT id_stazA, id_stazP " +
			"FROM connessione";
	
	
	List<coppieF> allCoppie = new ArrayList<>();
	
	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		
		while(rs.next()) {
		
			coppieF coppia = new coppieF(fermateIdMap.get(rs.getInt("id_stazA")),
					fermateIdMap.get(rs.getInt("id_stazP")));
			allCoppie.add(coppia);
		}
		

		st.close();
		conn.close();
		

	} catch (SQLException e) {
		e.printStackTrace();
//		throw new RuntimeException("Errore di connessione al Database.");
		return null;
	}
	
	
	return allCoppie;
	
	
}



}
