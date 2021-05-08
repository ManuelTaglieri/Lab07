package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> soluzione;
	private int anni;
	private long oreMax;
	private int worst;
	private List<PowerOutage> tutti;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> worstCase(int id, int anni, long oreMax) {
		
		this.tutti = podao.getPowerOutages(id);
		
		this.soluzione = new ArrayList<PowerOutage>();
		this.anni = anni;
		this.oreMax = oreMax;
		this.worst = 0;
		cercaPeggiore(new ArrayList<PowerOutage>(), 0);
		
		return soluzione;
	}

	private void cercaPeggiore(List<PowerOutage> parziale, int utentiColpiti) {
		
		if (utentiColpiti>worst) {
				this.soluzione = new ArrayList<PowerOutage>(parziale);
				this.worst = utentiColpiti;
			}
		for (PowerOutage p : this.tutti) {
				if (parziale.size()==0) {
					parziale.add(p);
					if (isValida(parziale))
						cercaPeggiore(parziale, utentiColpiti + p.getClientiColpiti());
					parziale.remove(p);
				}
				else if (!parziale.contains(p) && !p.getInizio().isAfter(parziale.get(parziale.size()-1).getInizio())) {
					parziale.add(p);
					if (isValida(parziale))
						cercaPeggiore(parziale, utentiColpiti + p.getClientiColpiti());
					parziale.remove(p);
				}
			}
	}

	public int getWorst() {
		return worst;
	}
	
	public long getHours(List<PowerOutage> parziale) {
		long oreTot = 0;
		for (PowerOutage p : parziale) {
			oreTot += p.getDurata();
		}
		return oreTot;
	}

	private boolean isValida(List<PowerOutage> parziale) {
		long oreTot = this.getHours(parziale);
		if (oreTot>this.oreMax) {
			return false;
		}
		if (parziale.get(parziale.size()-1).getInizio().until(parziale.get(0).getFine(), ChronoUnit.YEARS)>this.anni) {
			return false;
		}
		return true;
	}

}
