package group1.langlearning.utils;

import java.util.HashMap;
import java.util.Map;

public class SawhaliVerbConjugation_Modified {

	
		public static void main(String[] args) {
			String verb = "kuongea"; 
			String tense = "PRES"; 
			String subject = "2s"; 
			String feature = "NEjG";
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
			System.out.println(Verbchange);
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
		// split the verb into syllables based on Swahili phonotactics
		// this regex matches after every vowel, before every vowel, and around certain consonant clusters
	
		int syllableCount = syllables.length;
	
		// adjust syllable count for certain suffixes and vowel sequences
		if (verb.endsWith("ni") || verb.endsWith("si") || verb.matches(".*[aeiou]{2}.*")) {
			syllableCount--;
		}
	
		return syllableCount;
	}
	
		/*
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
		}*/
		
	
	
		public static String Verbchange(String verb, String tense, String subject) {
			if( Syllablecount(verb)== 2)
			{
				verb = verb;
			}
			else{
			//System.out.println(Syllablecount(verb));
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
	




// import java.util.HashMap;
// import java.util.Map;

// public class SawhaliVerbConjugation_Modified {

//     public static void main(String[] args) {
//         String verb = "kuweza"; 
//         String tense = "PRES"; 
//         String subject = "1p"; 
//         String feature = "NEG";
//         String Impcheck = "IlMP";
//         if(tense.equals("PERF"))
//         {
// 			String PERFVerbchange = PERFVerbchange(verb, subject, feature);
// 			System.out.println(PERFVerbchange);
// 		}
//         else if(Impcheck.equals("IMP"))
//         {
// 			String IMPVerbchange = IMPVerbchange(verb, subject);
// 			System.out.println(IMPVerbchange);
// 		}
// 		else if(feature.equals("NEG")){
// 		String NegVerbchange = NegVerbchange(verb, tense, subject);
//         System.out.println(NegVerbchange);
// 		}
// 		else
// 		{
//         String Verbchange = Verbchange(verb, tense, subject);
//         System.out.println(Verbchange);
// 		}
    
// 	}
// 	public static String PERFVerbchange(String verb, String subject,String feature){
		
// 		if(feature.equals("NEG"))
// 		{
// 			Map<String, String> Suffixes = new HashMap<String, String>();
// 			Suffixes.put("1s", "si");
// 			Suffixes.put("2s", "hu");
// 			Suffixes.put("3s", "ha");
// 			Suffixes.put("1p", "hatu");
// 			Suffixes.put("2p", "ham");
// 			Suffixes.put("3p", "hava");
// 			String suffix = "";
// 			suffix = Suffixes.get(subject);
// 			suffix = suffix + "ja";
// 			verb = verb.substring(2);
// 			return suffix + verb;
// 		}
// 		else
// 		{
// 	     Map<String, String> Suffixes = new HashMap<String, String>();
//          Suffixes.put("1s", "ni");
//          Suffixes.put("2s", "u");
//          Suffixes.put("3s", "a");
//          Suffixes.put("1p", "tu");
//          Suffixes.put("2p", "m");
//          Suffixes.put("3p", "wa");
//          String suffix = "";
//          suffix = Suffixes.get(subject);
// 		 suffix = suffix + "me";
// 		 verb = verb.substring(2);
// 	     return suffix + verb;
// 		}
		
	
		
// 	}
//     public static String IMPVerbchange(String verb, String subject){
		
// 		Map<String, String> Suffixes = new HashMap<String, String>();
// 		if(subject.equals("2s"))
// 		{
// 			if(verb.equals("kuja"))
// 			{
// 				return "njoo";
// 			}
// 			else if (verb.equals("kuleta"))
// 			{
// 				return "leta";
// 			}
// 			else{
// 			verb = verb.substring(2);
// 			verb = verb.substring(0, verb.length() - 1);
// 			return verb;
// 			}
// 		}
// 		else if(subject.equals("2p"))
// 		{
// 			if(verb.equals("kuja"))
// 			{
// 				return "njooni";
// 			}
// 			else
// 			{
// 			verb = verb.substring(2);
// 			verb = verb.substring(0, verb.length() - 1) + "eni";
// 			return verb;
// 			}
// 		}
// 		return verb;	
		
// 	}
//     public static String NegVerbchange(String verb, String tense, String subject){
		
// 		Map<String, String> Suffixes = new HashMap<String, String>();
//         Suffixes.put("1s", "si");
//         Suffixes.put("2s", "hu");
//         Suffixes.put("3s", "ha");
//         Suffixes.put("1p", "hatu");
//         Suffixes.put("2p", "ham");
//         Suffixes.put("3p", "hava");

//         String suffix = "";
//         if (tense.equals("PRES")) {
// 			verb = verb.substring(2);
// 			verb = verb.substring(0, verb.length() - 1) + "i";
//             suffix = Suffixes.get(subject);
//         } else if (tense.equals("PAST")) {
//             suffix = Suffixes.get(subject);
//         } else if (tense.equals("FUT")) {
// 			verb = verb.substring(2);
//             suffix = Suffixes.get(subject);
//             suffix = suffix + "ta";
//         }

//         return suffix + verb;
// 	}
//     public static int Syllablecount(String word) {
//         int s = 0;
//         boolean prevVowel = false;
//         for (int i = 0; i < word.length(); i++) {
//             boolean isVowel = isVowel(word.charAt(i));
//             if (isVowel && !prevVowel) {
//                 s++;
//             }
//             prevVowel = isVowel;
//         }
//         if (word.endsWith("e")) {
//             s--;
//         }
//         if (s == 0) {
//             s = 1;
//         }
//         return s;
//     }
    
//     private static boolean isVowel(char c) {
//         return "aeiouAEIOU".indexOf(c) != -1;
//     }
    


//     public static String Verbchange(String verb, String tense, String subject) {
// 		if( Syllablecount(verb)== 2)
// 		{
// 			verb = verb;
// 		}
// 		else{
// 		System.out.println(Syllablecount(verb));
// 		verb = verb.substring(2);
// 		}
//         Map<String, String> Suffixes = new HashMap<String, String>();
//         Suffixes.put("1s", "ni");
//         Suffixes.put("2s", "u");
//         Suffixes.put("3s", "a");
//         Suffixes.put("1p", "tu");
//         Suffixes.put("2p", "m");
//         Suffixes.put("3p", "wa");

//         String suffix = "";
//         if (tense.equals("PRES")) {
//             suffix = Suffixes.get(subject);
//             suffix = suffix + "na";
//         } else if (tense.equals("PAST")) {
//             suffix = Suffixes.get(subject);
//             suffix = suffix + "li";
//         } else if (tense.equals("FUT")) {
//             suffix = Suffixes.get(subject);
//             suffix = suffix + "ta";
//         }

//         return suffix + verb;
    
// }
// }
