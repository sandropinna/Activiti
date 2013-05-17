package org.activiti.engine.impl.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.impl.db.HasRevision;
import org.activiti.engine.impl.db.PersistentObject;

public class ContrattoEntity  implements Contratto, Serializable, PersistentObject, HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected int revision;
	protected Date dataFirma;
	protected Integer numProtocolloIngresso;
	protected Integer numProtocolloUscita;
	protected Date dataInizioPrestazione;
	protected Date dataFineContratto;
	protected String cig;
	protected String cup;
	protected Double importo;
	protected Double imponibile;
	protected Integer percentualeIva;
	
	
	public ContrattoEntity(){
		
	}
	
	public ContrattoEntity(String id){
		this.id = id;
	}

	
	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public int getRevisionNext() {
		return revision + 1;
	}

	@Override
	public Object getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		
		persistentState.put("dataFirma", dataFirma);
	    persistentState.put("numProtocolloIngresso", numProtocolloIngresso);
	    persistentState.put("numProtocolloUscita", numProtocolloUscita);
	    persistentState.put("dataInizioPrestazione", dataInizioPrestazione);
	    persistentState.put("dataFineContratto", dataFineContratto);	    
	    persistentState.put("cig", cig);
	    persistentState.put("cup", cup);
	    persistentState.put("importo", importo);
	    persistentState.put("imponibile", imponibile);
	    persistentState.put("percentualeIva", percentualeIva);
	    return persistentState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public Integer getNumProtocolloIngresso() {
		return numProtocolloIngresso;
	}

	public void setNumProtocolloIngresso(Integer numProtocolloIngresso) {
		this.numProtocolloIngresso = numProtocolloIngresso;
	}

	public Integer getNumProtocolloUscita() {
		return numProtocolloUscita;
	}

	public void setNumProtocolloUscita(Integer numProtocolloUscita) {
		this.numProtocolloUscita = numProtocolloUscita;
	}

	public Date getDataInizioPrestazione() {
		return dataInizioPrestazione;
	}

	public void setDataInizioPrestazione(Date dataInizioPrestazione) {
		this.dataInizioPrestazione = dataInizioPrestazione;
	}

	public Date getDataFineContratto() {
		return dataFineContratto;
	}

	public void setDataFineContratto(Date dataFineContratto) {
		this.dataFineContratto = dataFineContratto;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Double getImponibile() {
		return imponibile;
	}

	public void setImponibile(Double imponibile) {
		this.imponibile = imponibile;
	}

	public Integer getPercentualeIva() {
		return percentualeIva;
	}

	public void setPercentualeIva(Integer percentualeIva) {
		this.percentualeIva = percentualeIva;
	}
	

}
