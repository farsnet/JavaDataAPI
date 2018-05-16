package farsnet.schema;

import java.util.*;
import farsnet.service.*;

public class Word {

	private int id;
	
	private String pos;
	
	private String defaultPhonetic;
	
	private String defaultValue;
	
	public Word(){}
	
	public Word(int id, String pos, String defaultPhonetic, String defaultValue) {
		
		this.id = id;
		this.defaultPhonetic = defaultPhonetic;
		this.defaultValue = defaultValue;
		this.pos = pos;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPos() {
		return pos;
	}

	public String getDefaultPhonetic() {
		return defaultPhonetic;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public List<WrittenForm> getWrittenForms() {
		return SenseService.getWrittenFormsByWord(this.id);
	}
	
	public List<PhoneticForm> getPhoneticForms() {
		return SenseService.getPhoneticFormsByWord(this.id);
	}
}
