package farsnet.service;

import java.sql.*;
import java.util.*;
import farsnet.schema.*;
import farsnet.database.SqlLiteDbUtility;

public class SenseService {

	public static List<Sense> getSensesByWord(String searchStyle, String searchKeyword){
		
		List<Sense> results = new ArrayList<Sense>();
		
		 String sql = "SELECT sense.id, seqId, vtansivity, vactivity, vtype, synset, vpastStem, vpresentStem, category, goupOrMokassar, esmeZamir, adad, adverb_type_1, adverb_type_2, adj_pishin_vijegi, adj_type, noe_khas, nounType, adj_type_sademorakkab, vIssababi, vIsIdiom, vGozaraType, kootah_nevesht, mohavere, word.id as wordId, word.defaultValue, word.avaInfo, word.pos FROM sense INNER JOIN word ON sense.word = word.id WHERE sense.id IN (SELECT sense.id FROM word INNER JOIN sense ON sense.word = word.id LEFT OUTER JOIN value ON value.word = word.id WHERE word.search_value @SearchStyle '@SearchValue' OR value.search_value @SearchStyle '@SearchValue') "+
			"OR sense.id IN (SELECT sense.id FROM sense INNER JOIN sense_relation ON sense.id = sense_relation.sense INNER JOIN sense AS sense_2 ON sense_2.id = sense_relation.sense2 INNER JOIN word ON sense_2.word = word.id WHERE sense_relation.type =  'Refer-to' AND word.search_value LIKE  '@SearchValue') "+
			"OR sense.id IN (SELECT sense_2.id FROM sense INNER JOIN sense_relation ON sense.id = sense_relation.sense INNER JOIN sense AS sense_2 ON sense_2.id = sense_relation.sense2 INNER JOIN word ON sense.word = word.id WHERE sense_relation.type =  'Refer-to' AND word.search_value LIKE  '@SearchValue') ";
		 	
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
				 results.add(new Sense(
						 rs.getInt("id"),
						 rs.getString("seqId"),
						 rs.getString("pos"),
						 rs.getString("defaultValue"),
						 rs.getInt("wordId"),
						 rs.getString("avaInfo"),
						 getVtansivity(rs.getString("vtansivity")),
						 getVactivity(rs.getString("vactivity")),
						 getVtype(rs.getString("vtype")),
						 getNormalValue(rs.getString("synset")),
						 getNormalValue(rs.getString("vpastStem")),
						 getNormalValue(rs.getString("vpresentStem")),
						 getCategory(rs.getString("category")),
						 getGoupOrMokassar(rs.getString("goupOrMokassar")),
						 getEsmeZamir(rs.getString("esmeZamir")),
						 getAdad(rs.getString("adad")),
						 getAdverbType1(rs.getString("adverb_type_1")),
						 getAdverbType2(rs.getString("adverb_type_2")),
						 getAdjPishinVijegi(rs.getString("adj_pishin_vijegi")),
						 getAdjType(rs.getString("adj_type")),
						 getNoeKhas(rs.getString("noe_khas")),
						 getNounType(rs.getString("nounType")),
						 getAdjTypeSademorakkab(rs.getString("adj_type_sademorakkab")),
						 rs.getBoolean("vIssababi"),
						 rs.getBoolean("vIsIdiom"),
						 getVGozaraType(rs.getString("vGozaraType")),
						 rs.getBoolean("kootah_nevesht"),
						 rs.getBoolean("mohavere")				 
						 ));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}
	
	public static List<Sense> getSensesBySynset(int synsetId){
		
		List<Sense> results = new ArrayList<Sense>();
		
		String sql = "SELECT sense.id, seqId, vtansivity, vactivity, vtype, synset, vpastStem, vpresentStem, category, goupOrMokassar, esmeZamir, adad, adverb_type_1, adverb_type_2, adj_pishin_vijegi, adj_type, noe_khas, nounType, adj_type_sademorakkab, vIssababi, vIsIdiom, vGozaraType, kootah_nevesht, mohavere, word.id as wordId, word.defaultValue, word.avaInfo, word.pos FROM sense INNER JOIN word ON sense.word = word.id WHERE sense.synset = "+synsetId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new Sense(
						 rs.getInt("id"),
						 rs.getString("seqId"),
						 rs.getString("pos"),
						 rs.getString("defaultValue"),
						 rs.getInt("wordId"),
						 rs.getString("avaInfo"),
						 getVtansivity(rs.getString("vtansivity")),
						 getVactivity(rs.getString("vactivity")),
						 getVtype(rs.getString("vtype")),
						 getNormalValue(rs.getString("synset")),
						 getNormalValue(rs.getString("vpastStem")),
						 getNormalValue(rs.getString("vpresentStem")),
						 getCategory(rs.getString("category")),
						 getGoupOrMokassar(rs.getString("goupOrMokassar")),
						 getEsmeZamir(rs.getString("esmeZamir")),
						 getAdad(rs.getString("adad")),
						 getAdverbType1(rs.getString("adverb_type_1")),
						 getAdverbType2(rs.getString("adverb_type_2")),
						 getAdjPishinVijegi(rs.getString("adj_pishin_vijegi")),
						 getAdjType(rs.getString("adj_type")),
						 getNoeKhas(rs.getString("noe_khas")),
						 getNounType(rs.getString("nounType")),
						 getAdjTypeSademorakkab(rs.getString("adj_type_sademorakkab")),
						 rs.getBoolean("vIssababi"),
						 rs.getBoolean("vIsIdiom"),
						 getVGozaraType(rs.getString("vGozaraType")),
						 rs.getBoolean("kootah_nevesht"),
						 rs.getBoolean("mohavere")			 
						 ));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}
	
	public static Sense getSenseById(int senseId){
		
		Sense result=null;
		
		String sql = "SELECT sense.id, seqId, vtansivity, vactivity, vtype, synset, vpastStem, vpresentStem, category, goupOrMokassar, esmeZamir, adad, adverb_type_1, adverb_type_2, adj_pishin_vijegi, adj_type, noe_khas, nounType, adj_type_sademorakkab, vIssababi, vIsIdiom, vGozaraType, kootah_nevesht, mohavere, word.id as wordId, word.defaultValue, word.avaInfo, word.pos FROM sense INNER JOIN word ON sense.word = word.id WHERE sense.id = "+senseId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 result=new Sense(
						 rs.getInt("id"),
						 rs.getString("seqId"),
						 rs.getString("pos"),
						 rs.getString("defaultValue"),
						 rs.getInt("wordId"),
						 rs.getString("avaInfo"),
						 getVtansivity(rs.getString("vtansivity")),
						 getVactivity(rs.getString("vactivity")),
						 getVtype(rs.getString("vtype")),
						 getNormalValue(rs.getString("synset")),
						 getNormalValue(rs.getString("vpastStem")),
						 getNormalValue(rs.getString("vpresentStem")),
						 getCategory(rs.getString("category")),
						 getGoupOrMokassar(rs.getString("goupOrMokassar")),
						 getEsmeZamir(rs.getString("esmeZamir")),
						 getAdad(rs.getString("adad")),
						 getAdverbType1(rs.getString("adverb_type_1")),
						 getAdverbType2(rs.getString("adverb_type_2")),
						 getAdjPishinVijegi(rs.getString("adj_pishin_vijegi")),
						 getAdjType(rs.getString("adj_type")),
						 getNoeKhas(rs.getString("noe_khas")),
						 getNounType(rs.getString("nounType")),
						 getAdjTypeSademorakkab(rs.getString("adj_type_sademorakkab")),
						 rs.getBoolean("vIssababi"),
						 rs.getBoolean("vIsIdiom"),
						 getVGozaraType(rs.getString("vGozaraType")),
						 rs.getBoolean("kootah_nevesht"),
						 rs.getBoolean("mohavere")			 
						 );
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return result;
	}
	
	public static List<Sense> getAllSenses(){
		
		List<Sense> results = new ArrayList<Sense>();
		
		String sql = "SELECT sense.id, seqId, vtansivity, vactivity, vtype, synset, vpastStem, vpresentStem, category, goupOrMokassar, esmeZamir, adad, adverb_type_1, adverb_type_2, adj_pishin_vijegi, adj_type, noe_khas, nounType, adj_type_sademorakkab, vIssababi, vIsIdiom, vGozaraType, kootah_nevesht, mohavere, word.id as wordId, word.defaultValue, word.avaInfo, word.pos FROM sense INNER JOIN word ON sense.word = word.id";
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new Sense(
						 rs.getInt("id"),
						 rs.getString("seqId"),
						 rs.getString("pos"),
						 rs.getString("defaultValue"),
						 rs.getInt("wordId"),
						 rs.getString("avaInfo"),
						 getVtansivity(rs.getString("vtansivity")),
						 getVactivity(rs.getString("vactivity")),
						 getVtype(rs.getString("vtype")),
						 getNormalValue(rs.getString("synset")),
						 getNormalValue(rs.getString("vpastStem")),
						 getNormalValue(rs.getString("vpresentStem")),
						 getCategory(rs.getString("category")),
						 getGoupOrMokassar(rs.getString("goupOrMokassar")),
						 getEsmeZamir(rs.getString("esmeZamir")),
						 getAdad(rs.getString("adad")),
						 getAdverbType1(rs.getString("adverb_type_1")),
						 getAdverbType2(rs.getString("adverb_type_2")),
						 getAdjPishinVijegi(rs.getString("adj_pishin_vijegi")),
						 getAdjType(rs.getString("adj_type")),
						 getNoeKhas(rs.getString("noe_khas")),
						 getNounType(rs.getString("nounType")),
						 getAdjTypeSademorakkab(rs.getString("adj_type_sademorakkab")),
						 rs.getBoolean("vIssababi"),
						 rs.getBoolean("vIsIdiom"),
						 getVGozaraType(rs.getString("vGozaraType")),
						 rs.getBoolean("kootah_nevesht"),
						 rs.getBoolean("mohavere")			 
						 ));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return results;
	}
	
	public static List<SenseRelation> getSenseRelationsById(int senseId){
		
		List<SenseRelation> results = new ArrayList<SenseRelation>();
		
		String sql = "SELECT id, type, sense, sense2, senseWord1, senseWord2 FROM sense_relation WHERE sense = " + senseId + " OR sense2 = " + senseId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SenseRelation(
						 rs.getInt("id"),
						 rs.getInt("sense"),
						 rs.getInt("sense2"),
						 rs.getString("senseWord1"),
						 rs.getString("senseWord2"),
						 rs.getString("type")
						 ));
			 }
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 List<SenseRelation> resultsArr = new ArrayList<SenseRelation>();
		 
		 SenseRelation temp;
			
		 String type;
		
		 int senseId2;
		
		 int senseId1;
		
		 String senseWord2;
		 
		 String senseWord1;
			
		 for(int i=0;i<results.size();i++ ){
				
			 temp=results.get(i);
			 
			 if(temp.getSenseId1()!=senseId){
				 	 
				 type=temp.getType();
				
				 senseId2=temp.getSenseId2();
				
				 senseId1=temp.getSenseId1();
				
				 senseWord2=temp.getSenseWord2();
				
				 senseWord1=temp.getSenseWord1();
				 
				 temp.setType(ReverseSRelationType(type));
				 
				 temp.setSenseId1(senseId2);
				 
				 temp.setSenseId2(senseId1);
				 
				 temp.setSenseWord1(senseWord2);
				 
				 temp.setSenseWord2(senseWord1);
			 }
			 
			 resultsArr.add(temp);
		 }
		 
		 return resultsArr;
	}
	
	public static List<SenseRelation> getSenseRelationsByType(int senseId, SenseRelationType[] types){
				
		List<SenseRelation> results = new ArrayList<SenseRelation>();
		
		String _types="";
		
		String _revTypes="";
		
		for (SenseRelationType type : types) {
			_types=_types+"'"+RelationValue(type)+"',";
			
			_revTypes=_revTypes+"'"+RelationValue(ReverseRelationType(type))+"',";
		}
		
		_types=_types+"'not_type'";
		
		_revTypes=_revTypes+"'not_type'";
		
		String sql = "SELECT id, type, sense, sense2, senseWord1, senseWord2 FROM sense_relation WHERE (sense = " + senseId + " AND type in ("+_types+")) OR (sense2 = " + senseId + " AND type in ("+_revTypes+"))"+ " ORDER BY sense";
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new SenseRelation(
						 rs.getInt("id"),
						 rs.getInt("sense"),
						 rs.getInt("sense2"),
						 rs.getString("senseWord1"),
						 rs.getString("senseWord2"),
						 rs.getString("type")
						 ));
			 }
   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 List<SenseRelation> resultsArr = new ArrayList<SenseRelation>();
		 
		 SenseRelation temp;
			
		 String type;
		
		 int senseId2;
		
		 int senseId1;
		
		 String senseWord2;
		 
		 String senseWord1;
			
		 for(int i=0;i<results.size();i++ ){
				
			 temp=results.get(i);
			 
			 if(temp.getSenseId1()!=senseId){
				 	 
				 type=temp.getType();
				
				 senseId2=temp.getSenseId2();
				
				 senseId1=temp.getSenseId1();
				
				 senseWord2=temp.getSenseWord2();
				
				 senseWord1=temp.getSenseWord1();
				 
				 temp.setType(ReverseSRelationType(type));
				 
				 temp.setSenseId1(senseId2);
				 
				 temp.setSenseId2(senseId1);
				 
				 temp.setSenseWord1(senseWord2);
				 
				 temp.setSenseWord2(senseWord1);
			 }
			 
			 resultsArr.add(temp);
		 }
		 
		 return resultsArr;
	}
	
	public static List<PhoneticForm> getPhoneticFormsByWord(int wordId){
		
		List<PhoneticForm> results = new ArrayList<PhoneticForm>();
		
		String sql = "SELECT id, value FROM speech WHERE word = " + wordId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new PhoneticForm(
						 rs.getInt("id"),
						 rs.getString("value")
						 ));
			 }
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return results;
	}
	
	public static List<WrittenForm> getWrittenFormsByWord(int wordId){
		
		List<WrittenForm> results = new ArrayList<WrittenForm>();
		
		String sql = "SELECT id, value FROM value WHERE word = " + wordId;
		
		 try {
			 Connection conn = SqlLiteDbUtility.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql); 
			 ResultSet rs = stmt.executeQuery();
			 while(rs.next()){
				 results.add(new WrittenForm(
						 rs.getInt("id"),
						 rs.getString("value")
						 ));
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

	private static String getVtansivity(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "dovajhi": return "Causative/Anticausative"; 

        case "inTransitive": return "Intransitive"; 

        case "transitive": return "Transitive"; 
		
		}
		
		return value;
	}

	private static String getVactivity(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "active": return "Active"; 

        case "passive": return "Passive"; 
		
		}
		
		return value;
	}

	private static String getVtype(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "auxiliaryVerb": return "Auxiliary"; 

        case "compoundVerb": return "Complex"; 

        case "copulaVerb": return "Copula";

        case "pishvandiVerb": return "Phrasal";

        case "simpleVerb": return "Simple";
		
		}
		
		return value;
	}

	private static String getCategory(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "category_adad": return "Numeral"; 

        case "category_Am": return "General"; 

        case "category_khAs": return "Specific";

        case "category_masdari": return "Infinitival";

        case "category_esmZamir": return "Pronoun";

		}
		
		return value;
	}

	private static String getGoupOrMokassar(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "am_khas_esmejam": return "MassNoun"; 

        case "am_khas_jam": return "Regular"; 

        case "am_khas_mokassar": return "Irregular";

		}
		
		return value;
	}

	private static String getEsmeZamir(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "moakkad": return "Emphatic"; 

        case "gheir_moshakhas": return "Indefinite"; 

        case "motaghabel": return "Reciprocal";
        
        case "noun_type_morakab": return "";

		}
		
		return value;
	}

	private static String getAdad(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "asli": return "Cardinal"; 

        case "tartibi": return "Ordinal"; 

		}
		
		return value;
	}

	private static String getAdverbType1(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		switch(value){
			
        case "morakkab": return "Compound";

        case "moshtagh":  return "Derivative";

        case "moshtagh_morakab": return "DerivationalCompound";

        case "saade":  return "Simple";

		}
		
		return value;
	}
	
	private static String getNormalValue(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		return value;
	}

	private static String getAdverbType2(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		String res = " ";

	    switch (value.charAt(0)) {

	        case '1': res += "AdjectiveModifying" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(0) + ","; break;
	    }

	    switch (value.charAt(1)) {

	        case '1': res += "AdverbModifying" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(1) + ","; break;
	    }

	    switch (value.charAt(2)) {

	        case '1': res += "VerbModifying" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(2) + ","; break;
	    }

	    switch (value.charAt(3)) {

	        case '1': res += "SentenceModifying" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(3) + ","; break;
	    }

	    return res.substring(0, res.length() - 1);
	}

	private static String getAdjPishinVijegi(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing") || value.equals("No"))
			return "";
		
		switch(value){
		
        case "Yes_mobham": return "Indefinite"; 

        case "Yes_taajobi": return "Exclamatory";

        case "Yes_eshare": return "Demonstrative";

        case "Yes_Nothing": return "Simple";

		}
		
		return value;
	}

	private static String getAdjType(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing") || value=="No")
			return "";
		
		switch(value){
		
        case "bartarin": return "Superlative"; 

        case "motlagh": return "Absolute";

        case "bartar": return "Comparative"; 
        
		}
		
		return value;
	}

	private static String getNoeKhas(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing") || value=="No")
			return "";
		
		switch(value){
		
        case "noe_khas_ensan": return "Human"; 

        case "noe_khas_heyvan": return "Animal";

        case "noe_khas_makan": return "Place"; 

        case "noe_khas_zaman": return "Time";
        
		}
		
		return value;
	}

	private static String getAdjTypeSademorakkab(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing") || value=="No")
			return "";
		
		switch(value){
		
        case "adj_type_morakab": return "Compound"; 

        case "adj_type_moshtagh": return "Derivative"; 

        case "adj_type_moshtagh_morakab": return "DerivatinalCompound"; 

        case "adj_type_saade": return "Simple"; 
        
		}
		
		return value;	
	}

	private static String getVGozaraType(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing"))
			return "";
		
		String res = " ";

	    switch (value.charAt(0)) {

	        case '1': res += "WithComplement" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(0) + ","; break;
	    }

	    switch (value.charAt(1)) {

	        case '1': res += "WithObject" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(1) + ","; break;
	    }

	    switch (value.charAt(2)) {

	        case '1': res += "WithPredicate" + ","; break;

	        case '0': res += ""; break;

	        default: res += value.charAt(2) + ","; break;
	    }

	    return res.substring(0, res.length() - 1);
	}

	private static String getNounType(String value){
		
		if(value==null || value.equals("") || value.equals("Nothing") || value=="No")
			return "";
		
		switch(value){
		
        case "noun_type_morakab": return "Compound"; 

        case "noun_type_moshtagh": return "Derivative"; 

        case "noun_type_moshtagh_morakab": return "DerivatinalCompound"; 

        case "noun_type_saade": return "Simple"; 
        
        case "noun_type_ebarat": return "Phrasal";
        
		}
		
		return value;		
	}

	private static String RelationValue(SenseRelationType type) {

		if(type.toString()=="Derivationally_related_form"){
			
			return "Derivationally related form";
		}
		
		return type.toString().replace("_", "-");
	}

	private static SenseRelationType ReverseRelationType(SenseRelationType type) {
		
		//Refer_to...Is_Referred_by
		if(SenseRelationType.Refer_to==type){
			
			return SenseRelationType.Is_Referred_by;
		}
		
		if(SenseRelationType.Is_Referred_by==type){
			
			return SenseRelationType.Refer_to;
		}
		
		//Verbal_Part..Is_Verbal_Part_of
		if(SenseRelationType.Verbal_Part==type){
			
			return SenseRelationType.Is_Verbal_Part_of;
		}
		
		if(SenseRelationType.Is_Verbal_Part_of==type){
			
			return SenseRelationType.Verbal_Part;
		}
		
		//Is_Non_Verbal_Part_of..Non_Verbal_Part
		if(SenseRelationType.Is_Non_Verbal_Part_of==type){
			
			return SenseRelationType.Non_Verbal_Part;
		}
		
		if(SenseRelationType.Non_Verbal_Part==type){
			
			return SenseRelationType.Is_Non_Verbal_Part_of;
		}
		
		return type;
	}

	private static String ReverseSRelationType(String type) {
		
		//Refer_to...Is_Referred_by
		if(type.equals("Refer-to")){
			
			return "Is-Referred-by";
		}
		
		if(type.equals("Is-Referred-by")){
			
			return "Refer-to";
		}
		
		//Verbal_Part..Is_Verbal_Part_of
		if(type.equals("Verbal-Part")){
			
			return "Is-Verbal-Part-of";
		}
		
		if(type.equals("Is-Verbal-Part-of")){
			
			return "Verbal-Part";
		}
		
		//Is_Non_Verbal_Part_of..Non_Verbal_Part
		if(type.equals("Non-Verbal-Part")){
			
			return "Is-Non-Verbal-Part-of";
		}
		
		if(type.equals("Is-Non-Verbal-Part-of")){
			
			return "Non-Verbal-Part";
		}
		
		return type;
	}

}
