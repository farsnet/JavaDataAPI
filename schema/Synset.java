package farsnet.schema;

import java.util.*;
import farsnet.service.*;

public class Synset {

	private int id;
	
	private String pos;
	
	private String semanticCategory;
	
	//private String example;
	
	//private String gloss;
	
	private String nofather;
	
	private String noMapping;
	
	public Synset(){}
	
	public Synset(int id, String pos, String semanticCategory, String example, String gloss, String nofather, String noMapping) {
		
		this.id = id;
		this.semanticCategory = semanticCategory;
		//this.example = example;
		//this.gloss = gloss;
		this.nofather = nofather;
		this.noMapping = noMapping;
		this.pos = pos;
	}
	
	public int getId() {
		return id;
	}

	public String getSemanticCategory() {
		return semanticCategory;
	}
	
	public String getNoMapping() {
		return noMapping;
	}

	public String getNofather() {
		return nofather;
	}

	public String getPos() {
		return pos;
	}
	
	public List<SynsetExample> getExamples() {
		return SynsetService.getSynsetExamples(this.id);
	}

	public List<SynsetGloss> getGlosses() {
		return SynsetService.getSynsetGlosses(this.id);
	}
	
	public List<Sense> getSenses(){
		
		return SenseService.getSensesBySynset(this.id);
	}
	
	public List<WordNetSynset> getWordNetSynsets(){
		
		return SynsetService.getWordNetSynsets(this.id);
	}

	public List<SynsetRelation> getSynsetRelations(){
		
		return SynsetService.getSynsetRelationsById(this.id);
	}

	public List<SynsetRelation> getSynsetRelations(SynsetRelationType relationType){
		
		SynsetRelationType[] types=new SynsetRelationType[1];
		types[0]=relationType;
				
		return SynsetService.getSynsetRelationsByType(this.id, types);
	}

	public List<SynsetRelation> getSynsetRelations(SynsetRelationType[] relationTypes){
			
		return SynsetService.getSynsetRelationsByType(this.id, relationTypes);
	}
}
