package farsnet.schema;

public class WordNetSynset {

	private int id;
	
	private String wnPos;
	
	private String wnOffset;
	
	private String example;
	
	private String gloss;
	
	private int synset;
	
	private String type;
	
	public WordNetSynset(){}
	
	public WordNetSynset(int id, String wnPos, String wnOffset, String example, String gloss, int synset, String type) {
		
		this.id = id;
		this.wnPos = wnPos;
		this.wnOffset=wnOffset;
		this.example=example;
		this.gloss=gloss;
		this.synset=synset;
		this.type=type;
		
	}
	
	public int getId() {
		return id;
	}

	public String getWnPos() {
		return wnPos;
	}

	public String getWnOffset() {
		return wnOffset;
	}

	public String getExample() {
		return example;
	}
	
	public String getGloss() {
		return gloss;
	}
	
	public int getSynset() {
		return synset;
	}
	
	public String getType() {
		return type;
	}
}
