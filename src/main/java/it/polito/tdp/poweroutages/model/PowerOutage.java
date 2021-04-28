package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	
	int nerc_id;
	int clientiColpiti;
	LocalDateTime fine;
	LocalDateTime inizio;
	long durata;
	
	
	public PowerOutage(int nerc_id, int clientiColpiti, LocalDateTime fine, LocalDateTime inizio) {
		super();
		this.nerc_id = nerc_id;
		this.clientiColpiti = clientiColpiti;
		this.fine = fine;
		this.inizio = inizio;
		this.durata = inizio.until(fine, ChronoUnit.HOURS);
	}

	public long getDurata() {
		return durata;
	}

	public int getNerc_id() {
		return nerc_id;
	}

	public int getClientiColpiti() {
		return clientiColpiti;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	@Override
	public String toString() {
		return "Inizio=" + inizio + " Fine=" + fine + " Durata=" + durata + " ClientiColpiti="
				+ clientiColpiti + "\n";
	}
	
	

}
