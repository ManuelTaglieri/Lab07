package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<PowerOutage> soluzione;
	int anni;
	long oreMax;
	long worst;
	List<PowerOutage> tutti;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> worstCase(int id, int anni, long oreMax) {
		
		this.tutti = podao.getPowerOutages(id);
		
		this.soluzione = new ArrayList<>();
		this.anni = anni;
		this.oreMax = oreMax;
		this.worst = 0;
		cercaPeggiore(new ArrayList<PowerOutage>(), 0);
		
		return soluzione;
	}

	private void cercaPeggiore(List<PowerOutage> parziale, long oreGuaste) {
		
		if (oreGuaste>worst) {
			this.soluzione = parziale;
		} else {
			for (PowerOutage p : this.tutti) {
				parziale.add(p);
				if (isValida(parziale))
					cercaPeggiore(parziale,oreGuaste + p.getDurata());
				parziale.remove(p);
			}
		}
		
	}

	private boolean isValida(List<PowerOutage> parziale) {
		long oreTot = 0;
		for (PowerOutage p : parziale) {
			oreTot += p.getDurata();
		}
		if (oreTot>this.oreMax) {
			return false;
		}
		if (parziale.get(0).getFine().until(parziale.get(parziale.size()-1).getInizio(), ChronoUnit.YEARS)>this.anni) {
			return false;
		}
		return true;
	}

}
