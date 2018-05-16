package farsnet.schema;

import farsnet.service.*;

public class SenseRelation {

	private int id;
	
	private int senseId1;
	
	private int senseId2;
	
	private String senseWord1;
	
	private String senseWord2;
	
	private String type;
	
	public SenseRelation(){}
	
	public SenseRelation(int id, int senseId1, int senseId2, String senseWord1, String senseWord2, String type) {
		
		this.id = id;
		this.senseId1 = senseId1;
		this.senseId2 = senseId2;
		this.senseWord1 = senseWord1;
		this.senseWord2 = senseWord2;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public int getSenseId1() {
		return senseId1;
	}

	public int getSenseId2() {
		return senseId2;
	}
	
	public String getSenseWord1() {
		return senseWord1;
	}

	public String getSenseWord2() {
		return senseWord2;
	}

	public String getType() {
		return type;
	}
		
	public Sense getSense1() {
		return SenseService.getSenseById(this.senseId1);
	}

	public Sense getSense2() {
		return SenseService.getSenseById(this.senseId2);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setSenseId1(int senseId1) {
		this.senseId1 = senseId1;
	}

	public void setSenseId2(int senseId2) {
		this.senseId2 = senseId2;
	}

	public void setSenseWord1(String senseWord1) {
		this.senseWord1 = senseWord1;
	}

	public void setSenseWord2(String senseWord2) {
		this.senseWord2 = senseWord2;
	}

	public void setType(String type) {
		this.type = type;
	}

}
