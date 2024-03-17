package group1.langlearning.utils;

import java.util.HashMap;
import java.util.Map;

import group1.langlearning.models.SwahiliSentenceRequestModel;

public class SawhaliVerbConjugation {

    public void getSwahiliSentence(SwahiliSentenceRequestModel requestModel)
    {
        if(requestModel.getTense().equals("PERF"))
        {
			String PERFVerbchange = PERFVerbchange(requestModel.getVerb(),requestModel.getSubject(),requestModel.getFeature());
			System.out.println(PERFVerbchange);
		}
        else if(requestModel.getImpcheck().equals("IMP"))
        {
			String IMPVerbchange = IMPVerbchange(requestModel.getVerb(),requestModel.getSubject());
			System.out.println(IMPVerbchange);
		}
		else if(requestModel.getFeature().equals("NEG")){
		String NegVerbchange = NegVerbchange(requestModel.getVerb(),requestModel.getTense(),requestModel.getSubject());
        System.out.println(NegVerbchange);
		}
		else
		{
        String Verbchange = Verbchange(requestModel.getVerb(),requestModel.getTense(),requestModel.getSubject());
        System.out.println(Verbchange);
		}
    
	
    }
    public static void main(String[] args) {
        String verb = "kuchanganya"; 
        String tense = "present"; 
        String subject = "2s"; 
        String feature = "jj";
        String Impcheck = "IlMP";
        if(tense.equals("PERF"))
        {
			String PERFVerbchange = PERFVerbchange(verb, subject, feature);
			System.out.println(PERFVerbchange);
		}
        else if(Impcheck.equals("IMP"))
        {
			String IMPVerbchange = IMPVerbchange(verb, subject);
			System.out.println(IMPVerbchange);
		}
		else if(feature.equals("NEG")){
		String NegVerbchange = NegVerbchange(verb, tense, subject);
        System.out.println(NegVerbchange);
		}
		else
		{
        String Verbchange = Verbchange(verb, tense, subject);
        System.out.println("sentence "+Verbchange);
		}
    
	}
	public static String PERFVerbchange(String verb, String subject,String feature){
		
		if(feature.equals("NEG"))
		{
			Map<String, String> Suffixes = new HashMap<String, String>();
			Suffixes.put("1s", "si");
			Suffixes.put("2s", "hu");
			Suffixes.put("3s", "ha");
			Suffixes.put("1p", "hatu");
			Suffixes.put("2p", "ham");
			Suffixes.put("3p", "hava");
			String suffix = "";
			suffix = Suffixes.get(subject);
			suffix = suffix + "ja";
			verb = verb.substring(2);
			return suffix + verb;
		}
		else
		{
	     Map<String, String> Suffixes = new HashMap<String, String>();
         Suffixes.put("1s", "ni");
         Suffixes.put("2s", "u");
         Suffixes.put("3s", "a");
         Suffixes.put("1p", "tu");
         Suffixes.put("2p", "m");
         Suffixes.put("3p", "wa");
         String suffix = "";
         suffix = Suffixes.get(subject);
		 suffix = suffix + "me";
		 verb = verb.substring(2);
	     return suffix + verb;
		}
		
	
		
	}
    public static String IMPVerbchange(String verb, String subject){
		
		Map<String, String> Suffixes = new HashMap<String, String>();
		
		if(subject.equals("2s"))
		{
			verb = verb.substring(2);
			return verb;
		}
		else if(subject.equals("2p"))
		{
			verb = verb.substring(2);
			verb = verb.substring(0, verb.length() - 1) + "eni";
			return verb;
		}
		return verb;	
		
	}
    public static String NegVerbchange(String verb, String tense, String subject){
		
		Map<String, String> Suffixes = new HashMap<String, String>();
        Suffixes.put("1s", "si");
        Suffixes.put("2s", "hu");
        Suffixes.put("3s", "ha");
        Suffixes.put("1p", "hatu");
        Suffixes.put("2p", "ham");
        Suffixes.put("3p", "hava");

        String suffix = "";
        if (tense.equals("PRES")) {
			verb = verb.substring(2);
			verb = verb.substring(0, verb.length() - 1) + "i";
            suffix = Suffixes.get(subject);
        } else if (tense.equals("PAST")) {
            suffix = Suffixes.get(subject);
        } else if (tense.equals("FUT")) {
			verb = verb.substring(2);
            suffix = Suffixes.get(subject);
            suffix = suffix + "ta";
        }

        return suffix + verb;
	}
    public static int Syllablecount(String word) {
        int s = 0;
        boolean prevVowel = false;
        for (int i = 0; i < word.length(); i++) {
            boolean isVowel = isVowel(word.charAt(i));
            if (isVowel && !prevVowel) {
                s++;
            }
            prevVowel = isVowel;
        }
        if (word.endsWith("e")) {
            s--;
        }
        if (s == 0) {
            s = 1;
        }
        return s;
    }
    
    private static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }
    


    public static String Verbchange(String verb, String tense, String subject) {
		if( Syllablecount(verb)== 2)
		{
			verb = verb;
		}
		else{
		System.out.println(Syllablecount(verb));
		verb = verb.substring(2);
		}
        Map<String, String> Suffixes = new HashMap<String, String>();
        Suffixes.put("1s", "ni");
        Suffixes.put("2s", "u");
        Suffixes.put("3s", "a");
        Suffixes.put("1p", "tu");
        Suffixes.put("2p", "m");
        Suffixes.put("3p", "wa");

        String suffix = "";
        if (tense.equals("PRES")) {
            suffix = Suffixes.get(subject);
            suffix = suffix + "na";
        } else if (tense.equals("PAST")) {
            suffix = Suffixes.get(subject);
            suffix = suffix + "li";
        } else if (tense.equals("FUT")) {
            suffix = Suffixes.get(subject);
            suffix = suffix + "ta";
        }

        return suffix + verb;
    
}


}
