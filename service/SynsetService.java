package farsnet.service;

import java.sql.*;
import java.util.*;
import farsnet.schema.*;
import farsnet.database.SqlLiteDbUtility;

public class SynsetService {

	public static List<Synset> getSynsetsByWord(String searchStyle, String searchKeyword){
		
		List<Synset> results = new ArrayList<Synset>();
		
		String sql = "SELECT id, pos, semanticCategory, example, gloss, nofather, noMapping FROM synset "+
				 	"WHERE synset.id IN (SELECT "+
					"synset.id as synset_id "+
					"FROM "+
					"word INNER JOIN sense ON sense.word = word.id "+
					"INNER JOIN synset ON sense.synset = synset.id "+
					"LEFT OUTER JOIN value ON value.word = word.id "+
					"WHERE word.search_value @SearchStyle '@SearchValue' OR (value.search_value) @SearchStyle '@SearchValue') "+
					" OR synset.id IN (SELECT sense.synset AS synset_id FROM sense INNER JOIN sense_relation ON sense.id = sense_relation.sense INNER JOIN sense AS sense_2 ON sense_2.id = sense_relation.sense2 INNER JOIN word ON sense_2.word = word.id WHERE sense_relation.type =  'Refer-to' AND word.search_value LIKE  '@SearchValue')"+
					" OR synset.id IN (SELECT sense_2.synset AS synset_id FROM sense INNER JOIN sense_relation ON sense.id = sense_relation.sense INNER JOIN sense AS sense_2 ON sense_2.id = sense_relation.sense2 INNER JOIN word ON sense.word = word.id WHERE sense_relation.type =  'Refer-to' AND word.search_value LIKE  '@SearchValue')";
			 	
		searchKeyword=SecureValue(NormalValue(searchKeyword));
		 
		if(searchStyle.equals("LIKE") || searchStyle.equals("START") || searchStyle.equals("END")){
			
			sql = sql.replace("@SearchStyle", "LIKE");
							
			if(searchStyle.equals("LIKE")){
				
				searchKeyword="%"+searchKeyword+"%";
			}
			
			if(searchStyle.equals("START")){
				
				searchKeyword=""+searchKeyword+"%";
			}
			
			if(searchStyle.equals("END")){
				
				searchKeyword="%"+searchKeyword+"";
			}
		}
		if(searchStyle.equals("EXACT")){
			
			sql = sql.replace("@SearchStyle", "=");
		}
		
		sql = sql.replace("@SearchValue", searchKeyword);
		
		try {
			 Connection conn = SqlLiteDbUtility.getConnection();			 
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new Synset(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}
	
	public static List<Synset> getAllSynsets(){
		
		List<Synset> results = new ArrayList<Synset>();
		
		String sql = "SELECT id, pos, semanticCategory, example, gloss, nofather, noMapping FROM synset ";

		try {
			 Connection conn = SqlLiteDbUtility.getConnection();			 
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new Synset(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}
	
	public static Synset getSynsetById(int synsetId){
		
		Synset result = null;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 String sql = "SELECT id, pos, semanticCategory, example, gloss, nofather, noMapping FROM synset WHERE id="+synsetId;
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 result=new Synset(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return result;
	}
	
	public static List<SynsetRelation> getSynsetRelationsById(int synsetId){
		
		 List<SynsetRelation> results = new ArrayList<SynsetRelation>();
		
		 String sql = "SELECT id, type, synsetWords1, synsetWords2, synset, synset2, reverse_type FROM synset_relation WHERE synset="+synsetId+" OR synset2="+synsetId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SynsetRelation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		List<SynsetRelation> resultsArr = new ArrayList<SynsetRelation>();
	 	 
		SynsetRelation temp;
		
		String type;
		
		int synsetId2;
		
		int synsetId1;
		
		String synsetWords2;
		
		String synsetWords1;
		
		String reverseType;
		
		 for(int i=0;i<results.size();i++ ){
			
			 temp=results.get(i);
			 
			 if(temp.getSynsetId1()!=synsetId){
				 	 
				 type=temp.getType();
				
				 synsetId2=temp.getSynsetId2();
				
				 synsetId1=temp.getSynsetId1();
				
				 synsetWords2=temp.getSynsetWords2();
				
				 synsetWords1=temp.getSynsetWords1();
				
				 reverseType=temp.getReverseType();
				 
				 temp.setReverseType(type);
				 
				 temp.setSynsetId1(synsetId2);
				 
				 temp.setSynsetId2(synsetId1);
				 
				 temp.setSynsetWords1(synsetWords2);
				 
				 temp.setSynsetWords2(synsetWords1);
				 
				 temp.setType(reverseType);
			 }
			 
			 resultsArr.add(temp);
		 }
		 
		 return resultsArr;
	}
	
	public static List<SynsetRelation> getSynsetRelationsByType(int synsetId, SynsetRelationType[] types){
		
		List<SynsetRelation> results = new ArrayList<SynsetRelation>();
			
		String _types="";
		
		String _revTypes="";
		
		for (SynsetRelationType type : types) {
			_types=_types+"'"+RelationValue(type)+"',";
			
			_revTypes=_revTypes+"'"+RelationValue(ReverseRelationType(type))+"',";
		}
		
		_types=_types+"'not_type'";
		
		_revTypes=_revTypes+"'not_type'";
		
		String sql = "SELECT id, type, synsetWords1, synsetWords2, synset, synset2, reverse_type FROM synset_relation WHERE (synset = " + synsetId + " AND type in ("+_types+")) OR (synset2 = " + synsetId + " AND type in ("+_revTypes+"))"+ " ORDER BY synset";
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SynsetRelation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		 
		List<SynsetRelation> resultsArr = new ArrayList<SynsetRelation>();
	 	 
		SynsetRelation temp;
		
		String type;
		
		int synsetId2;
		
		int synsetId1;
		
		String synsetWords2;
		
		String synsetWords1;
		
		String reverseType;
		
		 for(int i=0;i<results.size();i++ ){
			
			 temp=results.get(i);
			 
			 if(temp.getSynsetId1()!=synsetId){
				 	 
				 type=temp.getType();
				
				 synsetId2=temp.getSynsetId2();
				
				 synsetId1=temp.getSynsetId1();
				
				 synsetWords2=temp.getSynsetWords2();
				
				 synsetWords1=temp.getSynsetWords1();
				
				 reverseType=temp.getReverseType();
				 
				 temp.setReverseType(type);
				 
				 temp.setSynsetId1(synsetId2);
				 
				 temp.setSynsetId2(synsetId1);
				 
				 temp.setSynsetWords1(synsetWords2);
				 
				 temp.setSynsetWords2(synsetWords1);
				 
				 temp.setType(reverseType);
			 }
			 
			 resultsArr.add(temp);
		 }
		 
		 return results;
	}

	public static List<WordNetSynset> getWordNetSynsets(int synsetId){
		
		List<WordNetSynset> results = new ArrayList<WordNetSynset>();
		
		 String sql = "SELECT id, wnPos, wnOffset, example, gloss, synset, type FROM wordnetsynset WHERE synset="+synsetId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new WordNetSynset(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}

	public static List<SynsetExample> getSynsetExamples(int synsetId){
		
		List<SynsetExample> results = new ArrayList<SynsetExample>();
		
		 String sql = "SELECT gloss_and_example.id, content, lexicon.title FROM gloss_and_example INNER JOIN lexicon ON gloss_and_example.lexicon=lexicon.id WHERE type='EXAMPLE' and synset="+synsetId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SynsetExample(rs.getInt(1), rs.getString(2), rs.getString(3)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}

	public static List<SynsetGloss> getSynsetGlosses(int synsetId){
		
		List<SynsetGloss> results = new ArrayList<SynsetGloss>();
		
		 String sql = "SELECT gloss_and_example.id, content, lexicon.title FROM gloss_and_example INNER JOIN lexicon ON gloss_and_example.lexicon=lexicon.id WHERE type='GLOSS' and synset="+synsetId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SynsetGloss(rs.getInt(1), rs.getString(2), rs.getString(3)));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}

	private static String NormalValue(String Value){
		   
		  String NormalValue = Value;

		  NormalValue = NormalValue.replace("ی", "ي");

		  NormalValue = NormalValue.replace("ى", "ي");
		  
		  NormalValue = NormalValue.replace("ك", "ک");
		  
		  NormalValue = NormalValue.replace( "'", "");

		  NormalValue = NormalValue.replace( "\"", "");

		  NormalValue = NormalValue.replace( " ", "");
		  
		  NormalValue = NormalValue.replace( "‌", "");
		  
		  //همزه

		  NormalValue = NormalValue.replace( "‌‌ء", "");
		  
		  NormalValue = NormalValue.replace( "‌‌ٔ", "");

		  NormalValue = NormalValue.replace( "‌‌ؤ", "و");

		  NormalValue = NormalValue.replace( "‌‌ئ", "ي");

		  NormalValue = NormalValue.replace( "آ", "ا");

		  NormalValue = NormalValue.replace( "‌‌أ", "ا");

		  NormalValue = NormalValue.replace( "إ", "ا");

		  NormalValue = NormalValue.replace( "ۀ", "ه");

		  NormalValue = NormalValue.replace( "ة", "ه");
		  
		  //اعراب و تنوین

		  NormalValue = NormalValue.replace("َ", "");

		  NormalValue = NormalValue.replace("ُ", "");

		  NormalValue = NormalValue.replace("ِ", "");

		  NormalValue = NormalValue.replace("ً", "");

		  NormalValue = NormalValue.replace("ٌ", "");

		  NormalValue = NormalValue.replace("ٍ", "");
		  
		  //تشدید و سكون
		  
		  NormalValue = NormalValue.replace("ّ", "");

		  NormalValue = NormalValue.replace("ْ", "");

		  NormalValue = NormalValue.replace("ِّ", "");
		  
		  NormalValue = NormalValue.replace("ٍّ", "");
		  
		  NormalValue = NormalValue.replace("َّ", "");
		  
		  NormalValue = NormalValue.replace("ًّ", "");
		  
		  NormalValue = NormalValue.replace("ُّ", "");
		  
		  NormalValue = NormalValue.replace("ٌّ", "");
		  
		  NormalValue = NormalValue.replace("u200D", "%");
		  
		  NormalValue = NormalValue.replace("ء", "");
		  
		  NormalValue = NormalValue.replace("أ", "ا");
			
		  NormalValue = NormalValue.replace("ئ", "ي");
		   
		  return NormalValue;
	   }
	
	private static String SecureValue(String Value){
		   
	       if(Value==null)
	    	   return "";
		   
		   Value = Value.replace("\0", "");
		   
		   Value = Value.replace("\'", "");
		   
		   Value = Value.replace("\"", "");
		   
		   Value = Value.replace("\b", "");
		   
		   Value = Value.replace("\n", "");
		   
		   Value = Value.replace("\r", "");
		   
		   Value = Value.replace("\t", "");
		   
		   Value = Value.replace("\\", "");
		   
		   Value = Value.replace("/", "");
		   
		   Value = Value.replace("%", "");
		   
		   Value = Value.replace("_", "");
		   
		   Value = Value.replace("ـ", "");
		   
		   //Value = Value.replace("-", "");
		   
		   Value = Value.replace("!", "");
		   
		   Value = Value.replace(";", "");
		   
		   Value = Value.replace("?", "");
		   
		   Value = Value.replace("=", "");
		   
		   Value = Value.replace("<", "");
		   
		   Value = Value.replace(">", "");
		   
		   Value = Value.replace("&", "");
		   
		   Value = Value.replace("#", "");
		   
		   Value = Value.replace("@", "");
		   
		   Value = Value.replace("$", "");
		   
		   Value = Value.replace("^", "");
		   
		   Value = Value.replace("*", "");
		   
		   Value = Value.replace("+", "");
		   
		   return Value;
	   }
	
	private static String RelationValue(SynsetRelationType type) {

		if(type.toString()=="Related_to" || 
		   type.toString()=="Has-Unit" ||	
		   type.toString().substring(3)	=="Is_"
				){
			return type.toString().replace("_", "-");
		}

		if(type.toString()=="Has_Salient_defining_feature"){
			return "Has-Salient defining feature";
		}
		
		return type.toString().replace("_", " ");
	}

	private static SynsetRelationType ReverseRelationType(SynsetRelationType type) {
		
		//Agent...Is_Agent_of
		if(SynsetRelationType.Agent==type){
			
			return SynsetRelationType.Is_Agent_of;
		}
		
		if(SynsetRelationType.Is_Agent_of==type){
			
			return SynsetRelationType.Agent;
		}
		
		//Hypernym..Hyponym
		if(SynsetRelationType.Hypernym==type){
			
			return SynsetRelationType.Hyponym;
		}
		
		if(SynsetRelationType.Hyponym==type){
			
			return SynsetRelationType.Hypernym;
		}
		
		//Instance_hypernym..Instance_hyponym
		if(SynsetRelationType.Instance_hyponym==type){
			
			return SynsetRelationType.Instance_hypernym;
		}
		
		if(SynsetRelationType.Instance_hypernym==type){
			
			return SynsetRelationType.Instance_hyponym;
		}
		
		//Part_holonym..Part_meronym
		if(SynsetRelationType.Part_holonym==type){
			
			return SynsetRelationType.Part_meronym;
		}
		
		if(SynsetRelationType.Part_meronym==type){
			
			return SynsetRelationType.Part_holonym;
		}
		
		//Member_holonym..Member_meronym
		if(SynsetRelationType.Member_holonym==type){
			
			return SynsetRelationType.Member_meronym;
		}
		
		if(SynsetRelationType.Member_meronym==type){
			
			return SynsetRelationType.Member_holonym;
		}
		
		//Substance_holonym..Substance_meronym
		if(SynsetRelationType.Substance_holonym==type){
			
			return SynsetRelationType.Substance_meronym;
		}
		
		if(SynsetRelationType.Substance_meronym==type){
			
			return SynsetRelationType.Substance_holonym;
		}
		
		//Portion_meronym..Portion_holonym
		if(SynsetRelationType.Portion_holonym==type){
			
			return SynsetRelationType.Portion_meronym;
		}
		
		if(SynsetRelationType.Portion_meronym==type){
			
			return SynsetRelationType.Portion_holonym;
		}
		
		//Is_Domain_of..Domain
		if(SynsetRelationType.Domain==type){
			
			return SynsetRelationType.Is_Domain_of;
		}
		
		if(SynsetRelationType.Is_Domain_of==type){
			
			return SynsetRelationType.Domain;
		}
		
		//Is_Caused_by..Cause
		if(SynsetRelationType.Cause==type){
			
			return SynsetRelationType.Is_Caused_by;
		}
		
		if(SynsetRelationType.Is_Caused_by==type){
			
			return SynsetRelationType.Cause;
		}
				
		//Is_Instrument_of..Instrument
		if(SynsetRelationType.Is_Instrument_of==type){
			
			return SynsetRelationType.Instrument;
		}
		
		if(SynsetRelationType.Instrument==type){
			
			return SynsetRelationType.Is_Instrument_of;
		}	
		
		//Is_Entailed_by...Entailment
		if(SynsetRelationType.Is_Entailed_by==type){
			
			return SynsetRelationType.Entailment;
		}
		
		if(SynsetRelationType.Entailment==type){
			
			return SynsetRelationType.Is_Entailed_by;
		}
			
		//Location...Is_Location_of
		if(SynsetRelationType.Location==type){
			
			return SynsetRelationType.Is_Location_of;
		}
		
		if(SynsetRelationType.Is_Location_of==type){
			
			return SynsetRelationType.Location;
		}
				
		//Has_Salient_defining_feature..Salient_defining_feature
		if(SynsetRelationType.Has_Salient_defining_feature==type){
			
			return SynsetRelationType.Salient_defining_feature;
		}
		
		if(SynsetRelationType.Salient_defining_feature==type){
			
			return SynsetRelationType.Has_Salient_defining_feature;
		}
				
		//Is_Attribute_of..Attribute
		if(SynsetRelationType.Is_Attribute_of==type){
			
			return SynsetRelationType.Attribute;
		}
		
		if(SynsetRelationType.Attribute==type){
			
			return SynsetRelationType.Is_Attribute_of;
		}
		
		//Unit..Attribute
		if(SynsetRelationType.Unit==type){
			
			return SynsetRelationType.Has_Unit;
		}
		
		if(SynsetRelationType.Has_Unit==type){
			
			return SynsetRelationType.Unit;
		}
			
		//Is_Patient_of..Patient
		if(SynsetRelationType.Is_Patient_of==type){
			
			return SynsetRelationType.Patient;
		}
		
		if(SynsetRelationType.Patient==type){
			
			return SynsetRelationType.Is_Patient_of;
		}
		
		return type;
	}

}
