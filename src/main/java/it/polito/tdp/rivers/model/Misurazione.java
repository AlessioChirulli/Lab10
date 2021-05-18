package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Misurazione {
	
	River fiume;
	LocalDate primaMisurazione;
	LocalDate ultimaMisurazione;
	int numMisurazioni;
	double flussoMedio;
	
	
	
	public Misurazione(River fiume, LocalDate primaMisurazione, LocalDate ultimaMisurazione, int numMisurazioni,
			double flussoMedio) {
		super();
		this.fiume = fiume;
		this.primaMisurazione = primaMisurazione;
		this.ultimaMisurazione = ultimaMisurazione;
		this.numMisurazioni = numMisurazioni;
		this.flussoMedio = flussoMedio;
	}
	
	public River getFiume() {
		return fiume;
	}
	public void setFiume(River fiume) {
		this.fiume = fiume;
	}
	public LocalDate getPrimaMisurazione() {
		return primaMisurazione;
	}
	public void setPrimaMisurazione(LocalDate primaMisurazione) {
		this.primaMisurazione = primaMisurazione;
	}
	public LocalDate getUltimaMisurazione() {
		return ultimaMisurazione;
	}
	public void setUltimaMisurazione(LocalDate ultimaMisurazione) {
		this.ultimaMisurazione = ultimaMisurazione;
	}
	public int getNumMisurazioni() {
		return numMisurazioni;
	}
	public void setNumMisurazioni(int numMisurazioni) {
		this.numMisurazioni = numMisurazioni;
	}
	public double getFlussoMedio() {
		return flussoMedio;
	}
	public void setFlussoMedio(double flussoMedio) {
		this.flussoMedio = flussoMedio;
	}
	
	

}
