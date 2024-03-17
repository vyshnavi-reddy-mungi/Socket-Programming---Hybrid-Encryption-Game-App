package group1.langlearning.utils;

import java.util.HashMap;
import java.util.Map;

import group1.langlearning.models.SwahiliSentenceRequestModel;

public class GenerateSwahiliSentence {
    
    public static void main(String args[])
    {
        SwahiliSentenceRequestModel swahiliSentenceRequestModel = new SwahiliSentenceRequestModel();
        swahiliSentenceRequestModel.setSubject("1p");
        swahiliSentenceRequestModel.setTense("PRES");
        swahiliSentenceRequestModel.setFeature(null);
        swahiliSentenceRequestModel.setVerb("kujibu");
        swahiliSentenceRequestModel.setImpcheck(null);
        swahiliSentenceRequestModel.setFeature("NEG");
        String answer = getSwahiliSentence(swahiliSentenceRequestModel);
        System.out.println("swahili sentence : "+answer);
    }
    
    public static String getSwahiliSentence(SwahiliSentenceRequestModel requestModel)
    {
        String finalAnswer = "";
        if(requestModel.getTense()!=null && requestModel.getTense().equals("PERF"))
        {
            finalAnswer = PERFVerbchange(requestModel.getVerb(), requestModel.getSubject(), requestModel.getFeature());
        }
        else if(requestModel.getImpcheck()!=null && requestModel.getImpcheck().equals("IMP"))
        {
            finalAnswer = IMPVerbchange(requestModel.getVerb(), requestModel.getSubject());
        }
        else if(requestModel.getFeature()!=null &&  requestModel.getFeature().equals("NEG")){
            finalAnswer = NegVerbchange(requestModel.getVerb(), requestModel.getTense(), requestModel.getSubject());
        }
        else
        {
            finalAnswer = Verbchange(requestModel.getVerb(), requestModel.getTense(), requestModel.getSubject());
        }
        return finalAnswer;
    }
   
    public static String PERFVerbchange(String verb, String subject,String feature){
			
        if(feature != null && feature.equals("NEG"))
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
            if(verb.equals("kuja"))
            {
                return "njoo";
            }
            else if (verb.equals("kuleta"))
            {
                return "leta";
            }
            else{
            verb = verb.substring(2);
            verb = verb.substring(0, verb.length() - 1);
            return verb;
            }
        }
        else if(subject.equals("2p"))
        {
            if(verb.equals("kuja"))
            {
                return "njooni";
            }
            else
            {
            verb = verb.substring(2);
            verb = verb.substring(0, verb.length() - 1) + "eni";
            return verb;
            }
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
    public static int Syllablecount(String verb) {
    String[] syllables = verb.split("(?<=[aeiou])|(?=[aeiou])|(?<=m|n|ny|ng|nd|mb|nz|kw|ch|sh|ph|th)|(?=m|n|ny|ng|nd|mb|nz|kw|ch|sh|ph|th)");
   
    int syllableCount = syllables.length;

    // adjust syllable count for certain suffixes and vowel sequences
    if (verb.endsWith("ni") || verb.endsWith("si") || verb.matches(".*[aeiou]{2}.*")) {
        syllableCount--;
    }

    return syllableCount;
}
public static String Verbchange(String verb, String tense, String subject) {
    if( Syllablecount(verb)== 2)
    {
        verb = verb;
    }
    else{
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
