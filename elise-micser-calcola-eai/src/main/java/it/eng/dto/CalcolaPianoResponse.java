package it.eng.dto;

public class CalcolaPianoResponse {
	
	private Double importoRata;

	public Double getImportoRata() {
		return importoRata;
	}

	public void setImportoRata(Double importoRata) {
		this.importoRata = importoRata;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalcolaPianoResponse [importoRata=");
		builder.append(importoRata);
		builder.append("]");
		return builder.toString();
	}
}
