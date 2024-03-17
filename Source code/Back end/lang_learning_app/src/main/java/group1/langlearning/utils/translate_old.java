// package group1.langlearning.utils;

// import java.util.*;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.InputStream;
// import java.nio.charset.StandardCharsets;
// import java.io.InputStreamReader;
// import java.io.InputStream;

// import com.google.gson.Gson;
// import com.google.gson.*;

// // Rule class : Blue print for language translation rules
// class Rule {
//     String srcLang;
//     String srcString;
//     String tarLang;
//     String tarString;

//     Rule(String srcLang, String srcString, String tarLang, String tarString){
//         this.srcLang = srcLang;
//         this.srcString = srcString;
//         this.tarLang = tarLang;
//         this.tarString = tarString;
//     }

//     // Returns reverse rule, ex: direct rule a -> b , indirect rule b -> a
//     Rule getIndirect(){
//         return new Rule(tarLang, tarString, srcLang, srcString);
//     }

//     void printRule(Rule rule, String src) {
//         if(src.equals(srcLang)) {
//             System.out.println(srcString + " - > " + tarString);
//         }

//     }
// };

// /*  Language class used to add grammer like: pronouns, verbs, adjectives, conjunctions
//     currently have only pronouns and verbs, we can add remaining in the same way
// */
// class Language {
//     Map<Character, ArrayList<String>> pronouns;
//     Map<String, Map<Character, ArrayList<String>>> verbs;

//     Language(){
//         // initalizing pronouns with 1st, 2nd and 3rd persons as keys
//         this.pronouns = new HashMap<Character, ArrayList<String>>();
//         this.pronouns.put('1', new ArrayList<String>());
//         this.pronouns.put('2', new ArrayList<String>());
//         this.pronouns.put('3', new ArrayList<String>());

//         // inializing verbs with past, present and future maps
//         this.verbs = new HashMap<String, Map<Character, ArrayList<String>>>();

//         this.verbs.put("past", new HashMap<Character, ArrayList<String>>());
//         this.verbs.put("present", new HashMap<Character,ArrayList<String>>());
//         this.verbs.put("future", new HashMap<Character, ArrayList<String>>());

//         // initalizing each past, present and future items with 1st, 2nd and 3rd person maps
//         this.verbs.get("past").put('1', new ArrayList<String>());
//         this.verbs.get("past").put('2', new ArrayList<String>());
//         this.verbs.get("past").put('3', new ArrayList<String>());
//         this.verbs.get("present").put('1', new ArrayList<String>());
//         this.verbs.get("present").put('2', new ArrayList<String>());
//         this.verbs.get("present").put('3', new ArrayList<String>());
//         this.verbs.get("future").put('1', new ArrayList<String>());
//         this.verbs.get("future").put('2', new ArrayList<String>());
//         this.verbs.get("future").put('3', new ArrayList<String>());
//     }

//     /*  returns pronoun details based on the value, like 1st, 2nd, 3rd  
//       and index of pronoun etc */
//     String getPronounDetails(String value){
//         String details = "";
//         for(Map.Entry<Character, ArrayList<String>> entry: this.pronouns.entrySet()){
//             for(int i = 0; i < entry.getValue().size(); i++){
//                 if(entry.getValue().get(i).equals(value)){
//                     details = details + entry.getKey() + " " + i;
//                     return details;
//                 }
//             }
//         }
        
//         return details;
//     }

//     /* returns verb details based on the value, like past, present or future 
//     and index of verb */
//     String getVerbDetails(String value, char person){
//         String details = "";
//         for(Map.Entry<String, Map<Character, ArrayList<String>>> entry1: this.verbs.entrySet()){

//                 for(int i=0; i < entry1.getValue().get(person).size(); i++){
//                     if(entry1.getValue().get(person).get(i).equals(value)){
//                         details = details + entry1.getKey() + " " + person + " " + i;
//                         return details;
//                     }
//                 }

//         }
//         return details;
//     }
// };

// class Translate {
//     static Map<String, Language> Library = new HashMap<String, Language>();
//     static Set<String> pronouns = new HashSet<String>();
//     static Set<String> verbs = new HashSet<String>();
//     static ArrayList<Rule> Rules = new ArrayList<Rule>();

//     // Retuns arraylist of rules for a give source language
//     static ArrayList<Rule> getRules(String srcLang){
//         ArrayList<Rule> srcRules = new ArrayList<Rule>();

//         for(Rule rule: Rules){
//             if(rule.srcLang.equals(srcLang)){
//                 srcRules.add(rule);
//             }
//         }
//         return srcRules;
//     }

//     // returns a randomly string in source language based on randomly selected rule
//     static String generate_source_string(String srcLang) {
//         String srcString="";
//         ArrayList<Rule> srcRules = getRules(srcLang);
//         int selectedRuleIdx = (new Random()).nextInt(srcRules.size());
//         Rule selectedRule = srcRules.get(selectedRuleIdx);

//         String [] sentence = selectedRule.srcString.split("\\++"); 
//         char person = '\0';
//         for(String grammer : sentence) {
//             String [] grammerType = grammer.split("\\s+");

//             if(grammerType[0].equals("pronoun")){
//                 person = grammerType[1].charAt(0);
//                 ArrayList<String> selectedPronouns = Library.get(srcLang).pronouns.get(person);
//                 srcString = srcString + selectedPronouns.get((new Random()).nextInt(selectedPronouns.size())) + " ";
//             } else if(grammerType[0].equals("verb")){
//                 ArrayList<String> selectedVerbs = Library.get(srcLang).verbs.get(grammerType[1]).get(person);
//                 srcString = srcString + selectedVerbs.get((new Random()).nextInt(selectedVerbs.size())) + " ";
//             } else {
//                 srcString = srcString + grammerType[0] + " ";
//             }
//         }
//         return srcString;
//     }

//     // returns a target string for a given source language and source string
//     static ArrayList<String> generate_target_string(ArrayList<String> srcStrings, String srcLang, String tarLang) {
//         ArrayList<String> targetStrings = new ArrayList<String>();

//         for(String srcString: srcStrings){
//             String tarString = "";
//             String [] words = srcString.split("\\s+");
//             char person = '\0';
//             for(String word: words){
                
//                 if(pronouns.contains(word)){
//                     String [] wordDetails = Library.get(srcLang).getPronounDetails(word).split("\\s+");
                    
//                     person = wordDetails[0].charAt(0);
//                     tarString = tarString + Library.get(tarLang)
//                                             .pronouns.get(person)
//                                             .get(Integer.parseInt(wordDetails[1])) + " ";
//                 }else if(verbs.contains(word)){
//                     String [] wordDetails = Library.get(srcLang).getVerbDetails(word, person).split("\\s+");
                    
//                     if(wordDetails[0].equals("future") && tarLang.equals("en"))
//                         tarString = tarString + "will ";

//                     tarString = tarString + Library.get(tarLang)
//                                             .verbs.get(wordDetails[0])
//                                             .get(person)
//                                             .get(Integer.parseInt(wordDetails[2])) + " ";
//                 }
//             }
//             targetStrings.add(tarString);
//         }
//         return targetStrings;
//     }

//     // reads data from json file and creates Grammer like, library, pronouns and verbs
//     static void createGrammer(){
//         Gson gson = new Gson();

//         try {
//             // BufferedReader br = new BufferedReader(new FileReader("language.json"));

//             InputStream inputStream = Translate.class.getResourceAsStream("/language.json");
//             // BOMInputStream bomInputStream = new BOMInputStream(inputStream);
//             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

//             JsonObject data = gson.fromJson(br, JsonObject.class);

//             Set<Map.Entry<String, JsonElement>> languages = data.get("lang").getAsJsonObject().entrySet();//will return members of your object
//             for (Map.Entry<String, JsonElement> language: languages) {
//                 // adding languages to library
//                 Library.put(language.getKey(), new Language());
//                 for(Map.Entry<String, JsonElement> grammer: language.getValue().getAsJsonObject().entrySet()){
//                     // adding pronouns
//                     if(grammer.getKey().equals("pronouns")){
//                         for(Map.Entry<String, JsonElement> person : grammer.getValue().getAsJsonObject().entrySet()) {
//                             ArrayList<String> words = new ArrayList<String>();
//                             System.out.println(person.getKey() + " " + person.getValue().getAsJsonArray());
//                             for (int i=0; i < person.getValue().getAsJsonArray().size(); i++){   
//                                 words.add(person.getValue().getAsJsonArray().get(i).getAsString());
//                             }   
//                             Library.get(language.getKey()).pronouns.put(person.getKey().charAt(0), words);
//                             pronouns.addAll(words);
//                         }
//                     } else if(grammer.getKey().equals("verbs")){
//                         for(Map.Entry<String, JsonElement> type : grammer.getValue().getAsJsonObject().entrySet()){
//                             for(Map.Entry<String, JsonElement> person : type.getValue().getAsJsonObject().entrySet()) {
//                                 ArrayList<String> words = new ArrayList<String>();
//                                 // System.out.println(person.getKey() + " " + person.getValue().getAsJsonArray());
//                                 for (int i=0; i < person.getValue().getAsJsonArray().size(); i++){   
//                                     words.add(person.getValue().getAsJsonArray().get(i).getAsString());
//                                 }   
//                                 Library.get(language.getKey()).verbs.get(type.getKey()).put(person.getKey().charAt(0), words);
//                                 verbs.addAll(words);
//                             }
//                         }
//                     }
//                 }
//             }
            
//         } catch(Exception e){
//             e.printStackTrace();
//         }
//     }

//     static void createRules() {
//         Rule rule = new Rule("en", "pronoun 1st+verb past", "te", "pronoun 1st+verb past");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 2nd+verb past", "te", "pronoun 2nd+verb past");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 3rd+verb past", "te", "pronoun 3rd+verb past");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());
//         // simple present
//         rule = new Rule("en", "pronoun 1st+verb present", "te", "pronoun 1st+verb present");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 2nd+verb present", "te", "pronoun 2nd+verb present");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 3rd+verb present", "te", "pronoun 3rd+verb present");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());
//         // simple future
//         rule = new Rule("en", "pronoun 1st+will+verb future", "te", "pronoun 1st+verb future");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 2nd+will+verb future", "te", "pronoun 2nd+verb future");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());

//         rule = new Rule("en", "pronoun 3rd+will+verb future", "te", "pronoun 3rd+verb future");
//         Rules.add(rule);
//         Rules.add(rule.getIndirect());
//     }

//     public static void main(String args[]) {
//         // create grammer
//         createGrammer();

//         // Creating Rules
//         createRules();
        
//         // #############################################################//#endregion
//         Scanner sc = new Scanner(System.in);

//         System.out.println("Enter source language : ");
//         String src = sc.nextLine();

//         System.out.println("Enter target languate : ");
//         String target = sc.nextLine();
        
//         ArrayList<String> sourceStrings = new ArrayList<String>();

//         for(int i=0; i<10; i++){
//             sourceStrings.add(generate_source_string(src));
//         }

//         ArrayList<String> targetStrings = generate_target_string(sourceStrings, src, target);

//         for(int i=0; i<sourceStrings.size(); i++){
//             System.out.println(sourceStrings.get(i) + " -> " + targetStrings.get(i));
//         }
//     }
// }
