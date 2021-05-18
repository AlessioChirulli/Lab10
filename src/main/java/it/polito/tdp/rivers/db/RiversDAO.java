package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.Misurazione;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Misurazione getMisurazione(River fiume) {
		
		String sql="SELECT r.name,f.river,f.day,f.flow "
				+ "FROM flow f, river r "
				+ "WHERE (f.river=r.id) and f.river = ? "
				+ "ORDER BY f.day";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();
			Misurazione misurazione= new Misurazione(fiume,null,null,0,0);
			int count=0;
			float flusso=0;
			if(res.last())
				misurazione.setUltimaMisurazione(res.getDate("f.day").toLocalDate());
			if(res.first()) {
				misurazione.setPrimaMisurazione(res.getDate("f.day").toLocalDate());
				count++;
				flusso+=res.getFloat("f.flow");
			}
			while (res.next()) {
				count++;
				flusso+=res.getFloat("f.flow");
			}
			float flussoMedio= flusso/count;
			misurazione.setFlussoMedio(flussoMedio);
			misurazione.setNumMisurazioni(count);

			conn.close();
			
			return misurazione;
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<Flow> getFlows(River fiume){
		
		String sql="SELECT r.name,f.river,f.day,f.flow "
				+ "FROM flow f, river r "
				+ "WHERE (f.river=r.id) and f.river = ? "
				+ "ORDER BY f.day";
		
		List<Flow> flows= new LinkedList<Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				flows.add(new Flow(res.getDate("f.day").toLocalDate(),res.getFloat("f.flow"),fiume));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
		
	}
}
