package it.eng.dto;

import java.io.Serializable;

public class CalcolaPianoRequest implements Serializable{


	private static final long serialVersionUID = 1L;

	private Double importoRichiesto;

	public Double getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoRichiesto(Double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalcolaPianoRequest [importoRichiesto=");
		builder.append(importoRichiesto);
		builder.append("]");
		return builder.toString();
	}

}
