package it.polito.tdp.metroparis.model;

import java.util.Objects;

// oggetto di appoggio per segnare le coppie fermata P e fermata A
public class coppieF {
	
	Fermata partenza;
	Fermata arrivo;
	public Fermata getPartenza() {
		return partenza;
	}
	public Fermata getArrivo() {
		return arrivo;
	}
	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}
	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}
	public coppieF(Fermata partenza, Fermata arrivo) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(arrivo, partenza);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		coppieF other = (coppieF) obj;
		return Objects.equals(arrivo, other.arrivo) && Objects.equals(partenza, other.partenza);
	}
	
	
	

}
