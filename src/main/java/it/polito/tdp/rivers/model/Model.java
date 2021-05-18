package it.polito.tdp.rivers.model;

import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	RiversDAO dao;
	
	public Model() {
		dao = new RiversDAO();
	}
	
	public List<River>  getRivers() {
		return dao.getAllRivers();
	}
	
	public Misurazione getMisurazione(River fiume) {
		return dao.getMisurazione(fiume);
	}
	public List<Flow> getFlows(River fiume){
		return dao.getFlows(fiume);
	}
}
