package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {

	private Model model;
	
	
	//Eventi
	private PriorityQueue<Event> queue;
	
	//Parametri di input
	private float k;
	private List<Flow> fIn;
	private float fMed;
	
	//Modello del mondo
	private float Q;
	private float C;
	private float fOut;
	private float fOutMin;
	
	//misurazioni in output
	private int nGiorniDisservizio;
	private float Cmed;
	
	//Impostazione parametri iniziali
	public Simulator(float k,float fMed,River fiume) {
		this.model=new Model();
		this.k = k;
		this.fMed=fMed*3600*24;
		fIn=this.model.getFlows(fiume);
		
	}
	
	
	
	//simulazione
	public void run() {
		this.queue=new PriorityQueue<>();
		
		//Stato iniziale
		this.Q = this.k*this.fMed*30;
		this.C = this.Q/2;
		this.nGiorniDisservizio = 0;
		this.Cmed = 0;
		fOutMin=(float)(0.8*this.fMed);
		
		for(Flow f: fIn) {
			this.queue.add(new Event(f.getDay(),f,EventType.INGRESSO));
		}
		
		//Ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			System.out.println(e);
			this.processEvent(e);
		}

	}
	public void processEvent(Event e) {
		switch(e.getType()) {
		case INGRESSO:
			//CAPIENZA CORRENTE
			C+=e.getFlow().getFlow();
			if(this.C>this.Q) {
				this.queue.add(new Event(e.getDate(),e.getFlow(),EventType.TRACIMAZIONE));
			}
			int p=(int)(Math.random()*100); //PROBABILITA' IRRIGAZIONE
			//IRRIGAZIONE
			if(p<5) {
				this.queue.add(new Event(e.getDate(),e.getFlow(),EventType.IRRIGAZIONE));
				}	
			else {
				//USCITA NORMALE
				this.queue.add(new Event(e.getDate(),e.getFlow(),EventType.USCITA));
			}
			break;
			
		case USCITA:
			//IRRIGAZIONE
			if(this.fOutMin>C) {
				this.nGiorniDisservizio++;
				this.C=0;
				Cmed+=C;
			}//IRRIGAZIONE POSSIBILE
			else {
				this.C-=this.fOutMin;
				Cmed+=C;
			}
			break;
			
		case TRACIMAZIONE:
			float diff=this.C-this.Q;
			this.C-=diff;
			break;
			
		case IRRIGAZIONE:
			this.fOut=this.fOutMin*10;
			//DISSERVIZIO IRRIGAZIONE
			if(this.fOut>C) {
				this.nGiorniDisservizio++;
				this.fOut=C;
				C=0;
				Cmed+=C;
			}//IRRIGAZIONE POSSIBILE
			else {
				this.C-=this.fOut;
				Cmed+=C;
			}
			break;
		}
	}
	
	public int getDisservizio() {
		return this.nGiorniDisservizio;
	}
	public float getCmed() {
		return (this.Cmed)/this.fIn.size();
	}

}
