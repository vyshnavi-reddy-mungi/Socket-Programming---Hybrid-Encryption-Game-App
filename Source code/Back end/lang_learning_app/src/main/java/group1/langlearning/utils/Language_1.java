package group1.langlearning.utils;

import java.util.ArrayList;
import java.util.Map;

public class Language_1 {
   
    public Map<Character, Map<String, Map<String, ArrayList<String>>>> pronouns;
    public Map<String, Map<String, Map<Character, Map<String, Map<String, ArrayList<String>>>>>> verbs;
    public Map<String, Map<String, Map<Character, Map<String, ArrayList<String>>>>> auxilaryVerbs;

    /*  returns pronoun details based on the value, like 1st, 2nd, 3rd  
      and index of pronoun etc */
   public String getPronounDetails(String value){
        String details = "";
        for(Map.Entry<Character, Map<String, Map<String, ArrayList<String>>>> entry1: this.pronouns.entrySet()){
            for(Map.Entry<String, Map<String, ArrayList<String>>> entry2: entry1.getValue().entrySet()){
                for(Map.Entry<String, ArrayList<String>> entry3: entry2.getValue().entrySet()){
                    for(int i = 0; i < entry3.getValue().size(); i++){
                        if(entry3.getValue().get(i).equals(value)){
                            details = details + entry1.getKey() + " " + entry2.getKey() + " " + entry3.getKey() + " " + i;
                            return details;
                        }
                    }
                }
            }
        }
        
        return details;
    }

    /* returns verb details based on the value, like past, present or future 
    and index of verb */
    public String getVerbDetails(String value, String person, String kind, String gender, String tense, String type){
        String details = "";
        System.out.println("tense = " +  tense + " type = " + type);
        if(tense.equals("")){
            for(Map.Entry<String, Map<String, Map<Character, Map<String, Map<String, ArrayList<String>>>>>> entry1: this.verbs.entrySet()){
                for(Map.Entry<String, Map<Character, Map<String, Map<String, ArrayList<String>>>>> entry2: entry1.getValue().entrySet()){
                   ArrayList<String> entry3 = this.verbs.get(entry1.getKey()).get(entry2.getKey()).get(person.charAt(0)).get(kind).get(gender);
                            for(int i=0; i<entry3.size(); i++){
                                if(entry3.get(i).equals(value)){
                                    details = details + entry1.getKey() + " " + entry2.getKey() + " " + person + " " + kind + " " + gender + " " + i;
                                    return details;
                                }
                            }
                }
            }
        } else {
            ArrayList<String> entry = this.verbs.get(tense).get(type).get(person.charAt(0)).get(kind).get(gender);
            
                    for(int i=0; i<entry.size(); i++){
                        if(entry.get(i).equals(value)){
                            details = details + tense + " " + type + " " + person + " " + kind + " " + gender + " " + i;
                            return details;
                        }
                    }
        }
            
        
        return details;
    }
    
    /* returns auxilaryVerbs details */
   public String getAuxilaryVerbDetails(String value, String person, String kind){
        String details = "";
        for(Map.Entry<String, Map<String, Map<Character, Map<String, ArrayList<String>>>>> entry1: this.auxilaryVerbs.entrySet()){
            for(Map.Entry<String, Map<Character, Map<String, ArrayList<String>>>> entry2: entry1.getValue().entrySet()){
               ArrayList<String> entry3 = entry2.getValue().get(person.charAt(0)).get(kind);
                        for(int i=0; i<entry3.size(); i++){
                            if(entry3.get(i).equals(value)){
                                details = details + entry1.getKey() + " " + entry2.getKey() + " " + person + " " + kind + " " + i;
                                return details;
                            }
                        }
            }
        }
        return details;
    }
}