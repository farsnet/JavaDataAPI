package farsnet.schema;

public class SynsetGloss {

	private int id;
	
	private String content;
	
	private String lexicon;
	
	public SynsetGloss(){}
	
	public SynsetGloss(int id, String content, String lexicon) {
		
		this.id = id;
		this.content = content;
		this.lexicon = lexicon;
	}
	
	public int getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}

	public String getLexicon() {
		return lexicon;
	}
}
