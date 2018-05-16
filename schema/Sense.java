package farsnet.schema;

import java.util.*;
import farsnet.service.*;

public class Sense {
	
	int id;
	
	String seqId;
	
	String value;
	
	Word word;
	
	//vtansivity
	String verbTransitivity;
	
	//vactivity
	String verbActivePassive;
	
	//vtype
	String verbType;
	
	String synset;
	
	//vpastStem
	String verbPastStem;
	
	//vpresentStem
	String verbPresentStem;
	
	//category
	String nounCategory;
	
	//goupOrMokassar
	String nounPluralType;
	
	//esmeZamir
	String pronoun;
	
	//adad
	String nounNumeralType;
	
	//adverb_type_1
	String adverbType1;
	
	//adverb_type_2
	String adverbType2;
	
	//adj_pishin_vijegi
	String preNounAdjectiveType;
	
	//adj_type
	String adjectiveType2;
	
	//noe_khas
	String nounSpecifityType;
	
	String nounType;
	
	//adj_type_sademorakkab
	String adjectiveType1;
	
	//vIssababi
	Boolean isCausative;
	
	//vIsIdiom
	Boolean isIdiomatic;
	
	//vGozaraType;
	String transitiveType;
	
	//kootah_nevesht
	Boolean isAbbreviation;
	
	//mohaverh
	Boolean isColloquial;
	
	public Sense(){}
	
	public Sense(int id, String seqId, String pos, String defaultValue, int wordId, String defaultPhonetic, String verbTransitivity,String verbActivePassive,String verbType,String synset,String verbPastStem,String verbPresentStem,String nounCategory,String nounPluralType,String pronoun,String nounNumeralType,String adverbType1,String adverbType2,String preNounAdjectiveType,String adjectiveType2,String nounSpecifityType,String nounType,String adjectiveType1,Boolean isCausative,Boolean isIdiomatic,String transitiveType,Boolean isAbbreviation, Boolean isColloquial) {
		
		this.id = id;
		this.isColloquial = isColloquial;
		this.isAbbreviation = isAbbreviation;
		this.transitiveType = transitiveType;
		this.isIdiomatic = isIdiomatic;
		this.isCausative = isCausative;
		this.adjectiveType1 = adjectiveType1;
		this.nounType = nounType;
		this.nounSpecifityType = nounSpecifityType;
		this.adjectiveType2 = adjectiveType2;
		this.preNounAdjectiveType = preNounAdjectiveType;
		this.adverbType1 = adverbType1;
		this.adverbType2 = adverbType2;
		this.nounNumeralType = nounNumeralType;
		this.pronoun = pronoun;
		this.nounPluralType = nounPluralType;
		this.nounCategory = nounCategory;
		this.verbPastStem = verbPastStem;
		this.verbPresentStem = verbPresentStem;
		this.synset = synset;
		this.verbType = verbType;
		this.verbActivePassive = verbActivePassive;
		this.verbTransitivity = verbTransitivity;
		this.id = id;
		this.seqId = seqId;
		this.value = defaultValue;
		this.word = new Word(wordId, pos, defaultPhonetic, defaultValue);
	}

	public int getId(){
      return id;
    }

	public String getSeqId(){
      return seqId;
    }

	public String getValue(){
      return value;
    }
	
	public String getVerbActivePassive(){
      return verbActivePassive;
    }
	
	public String getVerbTransitivity(){
	      return verbTransitivity;
	    }
	
	public String getVerbType(){
      return verbType;
    }
	
	public String getVerbPresentStem(){
      return verbPresentStem;
    }
	
	public String getVerbPastStem(){
      return verbPastStem;
    }
	
	public String getNounCategory(){
      return nounCategory;
    }
	
	public String getNounPluralType(){
      return nounPluralType;
    }
	
	public String getPronoun(){
      return pronoun;
    }
	
	public String getNounNumeralType(){
      return nounNumeralType;
    }

	public String getAdverbType1(){
      return adverbType1;
    }

	public String getAdverbType2(){
      return adverbType2;
    }

	public String getPreNounAdjectiveType(){
      return preNounAdjectiveType;
    }

	public String getAdjectiveType2(){
      return adjectiveType2;
    }
	
	public String getNounSpecifityType(){
      return nounSpecifityType;
    }
	
	public String getNounType(){
      return nounType;
    }
	
	public String getAdjectiveType1(){
      return adjectiveType1;
    }
	
	public Boolean getIsCausative(){
      return isCausative;
    }
		
	public Boolean getIsIdiomatic(){
      return isIdiomatic;
    }
	
	public String getTransitiveType(){
      return transitiveType;
    }
		
	public Boolean getIsAbbreviation(){
      return isAbbreviation;
    }
		
	public Boolean getIsColloquial(){
      return isColloquial;
    }
	
	public Word getWord(){
      return word;
    }
	
	public Synset getSynset(){
		
		if(this.synset!=null && !this.synset.equals(""))
		{
			return SynsetService.getSynsetById(Integer.parseInt(this.synset));
		}
		else
		{		
			return null;	
		}
    }

	public List<SenseRelation> getSenseRelations(){
		
		return SenseService.getSenseRelationsById(this.id);
	}

	public List<SenseRelation> getSenseRelations(SenseRelationType relationType){
		
		SenseRelationType[] types=new SenseRelationType[1];
		types[0]=relationType;
				
		return SenseService.getSenseRelationsByType(this.id, types);
	}

	public List<SenseRelation> getSenseRelations(SenseRelationType[] relationTypes){
			
		return SenseService.getSenseRelationsByType(this.id, relationTypes);
	}
}
