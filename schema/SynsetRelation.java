package farsnet.schema;

import farsnet.service.*;

public class SynsetRelation {
	
	private int id;
	
	private String type;
	
	private String synsetWords1;
	
	private String synsetWords2;
	
	private int synsetId1;
	
	private int synsetId2;
	
	private String reverseType;
	
	public SynsetRelation(int id, String type, String synsetWords1, String synsetWords2, int synsetId1, int synsetId2, String reverseType) {
		
		this.id=id;
		this.type=type;
		this.synsetWords1=synsetWords1;
		this.synsetWords2=synsetWords2;
		this.synsetId1=synsetId1;
		this.synsetId2=synsetId2;
		this.reverseType=reverseType;
	}
	
	public SynsetRelation() {
		// TODO Auto-generated constructor stub
	}

	public int getId(){
      return id;
    }
	
	public String getType(){
      return type;
    }
	
	public String getSynsetWords1()
    {
      return synsetWords1;
    }
	
	public String getSynsetWords2()
    {
      return synsetWords2;
    }
	
	public int getSynsetId1()
    {
      return synsetId1;
    }
	
	public int getSynsetId2()
    {
      return synsetId2;
    }
	
	public String getReverseType()
    {
      return reverseType;
    }
	
	public Synset getSynset1() {
		return SynsetService.getSynsetById(this.synsetId1);
	}

	public Synset getSynset2() {
		return SynsetService.getSynsetById(this.synsetId2);
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setSynsetWords1(String synsetWords1) {
		this.synsetWords1 = synsetWords1;
	}

	public void setSynsetWords2(String synsetWords2) {
		this.synsetWords2 = synsetWords2;
	}

	public void setSynsetId1(int synsetId1) {
		this.synsetId1 = synsetId1;
	}

	public void setSynsetId2(int synsetId2) {
		this.synsetId2 = synsetId2;
	}

	public void setReverseType(String reverseType) {
		this.reverseType = reverseType;
	}
}
