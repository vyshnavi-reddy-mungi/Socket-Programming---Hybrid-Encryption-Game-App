package group1.langlearning.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group1.langlearning.entity.McqQuizSwahili;
import group1.langlearning.models.SwahiliSentenceRequestModel;
import group1.langlearning.repositories.McqQuizSwahiliRepository;

@RestController
public class SwahiliTestCases {

    @Autowired
    private McqQuizSwahiliRepository mcqQuizSwahiliRepository;
    @GetMapping("/swahili/testcases")
    public void testcases(){
    // public static void main(String[] args) {

     Gson gson = new GsonBuilder().create();
        
     Map<String,ArrayList<String>> verbList = new  HashMap<String,ArrayList<String>>();
     try{
        InputStream inputStreamVerbs = SwahiliTestCases.class.getResourceAsStream("/Verb forms PSD.txt");

        // Create a reader to read the contents of the file
        BufferedReader readerVerbs = new BufferedReader(new InputStreamReader(inputStreamVerbs));
    
        // Read each line of the file
        String line;
       
        while ((line = readerVerbs.readLine()) != null) {
            // Split the line into an array of values
            String[] values = line.split("-");
           
            String[] verbForms = values[1].split(",");
          
            ArrayList<String> verbs = new ArrayList<>();
            for(String v:verbForms)
                verbs.add(v);
            verbList.put(values[0],verbs);
    
        }
        // for(Map.Entry<String, ArrayList<String>> hm:verbList.entrySet())
        // {
        //    System.out.println(hm.getKey()+":"+hm.getValue());
        // }
    
            // Get the file as a stream from the resources folder
            InputStream inputStream = SwahiliTestCases.class.getResourceAsStream("/testdist.tsv");

            // Create a reader to read the contents of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Create a temporary file for writing the updated values
           BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Swahili & English TestCases sheet.tsv")));
             
                      // Read each line of the file
            
            while ((line = reader.readLine()) != null) {

                // Split the line into an array of values
                String[] values = line.split("\t");

                SwahiliSentenceRequestModel swahiliSentenceRequestModel = new SwahiliSentenceRequestModel();
                swahiliSentenceRequestModel = generateRequestModel(swahiliSentenceRequestModel,values[0]);
               
                String englishString = getEnglishSentence(swahiliSentenceRequestModel,verbList);
               
                String answer =   getSwahiliSentence(swahiliSentenceRequestModel);

                // writer.write(String.join("\t", values));
                writer.write(values[0]+"\t"+englishString+"\t"+answer);

                writer.newLine();
                if(!values[2].equals(answer))
                System.out.println(values[0]+"\t"+englishString+"\t"+values[2]+"\t"+ answer);
            }

            // Close the stream and reader
            inputStream.close();
            reader.close();

            // Close the writer
            writer.close();
            inputStreamVerbs.close();
            readerVerbs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private static String getEnglishSentence(SwahiliSentenceRequestModel swahiliSentenceRequestModel, Map<String, ArrayList<String>> verbList) {
        
    String englishString = "";
   
    
    if(swahiliSentenceRequestModel.getFeature()!=null && swahiliSentenceRequestModel.getFeature().equals("NEG"))
    {
       englishString = getNegativeString(verbList,swahiliSentenceRequestModel.getEnglishVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
    }
    else if(swahiliSentenceRequestModel.getImpcheck()!=null && swahiliSentenceRequestModel.getImpcheck().equals("IMP"))
    {
       englishString = swahiliSentenceRequestModel.getEnglishVerb();
    }
    else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PRES"))
    {
       englishString = getPresentString(verbList,swahiliSentenceRequestModel.getEnglishVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
    }
    else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("FUT"))
    {
       englishString = getFutureString(verbList,swahiliSentenceRequestModel.getEnglishVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
    }
    else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PERF"))
    {
       englishString = getPerfectString(verbList,swahiliSentenceRequestModel.getEnglishVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
    }
    else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PAST"))
    {
       englishString = getPastString(verbList,swahiliSentenceRequestModel.getEnglishVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
    }
   
    return englishString;
    }

    private static String getPastString(Map<String, ArrayList<String>> verbList, String verb, String tense,
            String subject) {
        String pastString = "";

        if(subject.equals("1s"))
            pastString = pastString + "I ";
        else if(subject.equals("2s"))
            pastString = pastString + "you ";
        else if(subject.equals("3s"))
            pastString = pastString + "(s)he ";
        else if(subject.equals("1p"))
            pastString = pastString + "we ";
        else if(subject.equals("2p"))
            pastString = pastString + "ye ";
        else if(subject.equals("3p"))
            pastString = pastString + "they ";
                
    
        if(verb.startsWith("be "))
        {
            if(subject.equals("1s") || subject.equals("3s"))
                pastString = pastString + "was " + verb.substring(3);
            else if(subject.equals("2s") || subject.equals("1p") || subject.equals("2p") || subject.equals("3p"))
                pastString = pastString + "were " + verb.substring(3);   
        }
        else
        {
            ArrayList<String> verbForms = verbList.get(verb);
            pastString = pastString + verbForms.get(0);
        }
    
        return pastString;
    }


private static String getPerfectString(Map<String, ArrayList<String>> verbList, String verb, String tense,
            String subject) {

        String perfectString = "";

        if(subject.equals("1s"))
            perfectString = perfectString + "I ";
        else if(subject.equals("2s"))
            perfectString = perfectString + "you ";
        else if(subject.equals("3s"))
            perfectString = perfectString + "(s)he ";
        else if(subject.equals("1p"))
            perfectString = perfectString + "we ";
        else if(subject.equals("2p"))
            perfectString = perfectString + "ye ";
        else if(subject.equals("3p"))
            perfectString = perfectString + "they ";
        
        
        if(verb.startsWith("be "))
        {
            if(subject.equals("3s"))
                perfectString = perfectString + "has been " + verb.substring(3); 
            else
                perfectString = perfectString + "have been " + verb.substring(3); 
        }
            
        else
        {
            ArrayList<String> verbForms = verbList.get(verb);
            if(subject.equals("3s"))
                perfectString = perfectString + "has " + verbForms.get(3); 
            else
                perfectString = perfectString + "have " + verbForms.get(3);
        }
        return perfectString;
    }


private static String getFutureString(Map<String, ArrayList<String>> verbList, String verb, String tense,
            String subject) {
    String futureString = "";
    if(subject.equals("1s"))
        futureString = futureString + "I ";
    else if(subject.equals("2s"))
        futureString = futureString + "you ";
    else if(subject.equals("3s"))
        futureString = futureString + "(s)he ";
    else if(subject.equals("1p"))
        futureString = futureString + "we ";
    else if(subject.equals("2p"))
        futureString = futureString + "ye ";
    else if(subject.equals("3p"))
        futureString = futureString + "they ";

    futureString = futureString + "will " + verb;

    return futureString;
    }


private static String getPresentString(Map<String, ArrayList<String>> verbList, String verb, String tense,
            String subject) {
    String presentString = "";
    if(subject.equals("1s"))
        presentString = presentString + "I ";
    else if(subject.equals("2s"))
        presentString = presentString + "you ";
    else if(subject.equals("3s"))
        presentString = presentString + "(s)he ";
    else if(subject.equals("1p"))
        presentString = presentString + "we ";
    else if(subject.equals("2p"))
        presentString = presentString + "ye ";
    else if(subject.equals("3p"))
        presentString = presentString + "they ";

    if(verb.startsWith("be "))
    {
        if(subject.equals("1s"))
            presentString = presentString + "am " + verb.substring(3);
        else if(subject.equals("3s"))
            presentString = presentString + "is " + verb.substring(3);
        else if(subject.equals("2s") || subject.equals("1p") || subject.equals("2p") || subject.equals("3p"))
            presentString = presentString + "are " + verb.substring(3);   
    }
    else
    {
        if(subject.equals("3s"))
            {
                try{
                InputStream inputStream = SwahiliTestCases.class.getResourceAsStream("/3s-present verbs.txt");

            // Create a reader to read the contents of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read each line of the file
            String line;
            Map<String,String> present_3s = new  HashMap<String,String>();

            while ((line = reader.readLine()) != null) {

                // Split the line into an array of values
                String[] values = line.split("-");
               
                present_3s.put(values[0],values[1]);

            }
            
            //  for(Map.Entry<String, String> hm:present_3s.entrySet())
            //  {
            //     System.out.println(hm.getKey()+":"+hm.getValue());
            //  }

             presentString = presentString + present_3s.get(verb);
            }
            catch (Exception e) {
               e.printStackTrace();
            }

            }
        else
            presentString = presentString + verb;
    }

        return presentString;
    }


private static String getNegativeString(Map<String, ArrayList<String>> verbList,String verb, String tense, String subject) {
    String negativeString = "";

    if(subject.equals("1s"))
        negativeString = negativeString + "I ";
    else if(subject.equals("2s"))
        negativeString = negativeString + "you ";
    else if(subject.equals("3s"))
        negativeString = negativeString + "(s)he ";
    else if(subject.equals("1p"))
        negativeString = negativeString + "we ";
    else if(subject.equals("2p"))
        negativeString = negativeString + "ye ";
    else if(subject.equals("3p"))
        negativeString = negativeString + "they ";

    ArrayList<String> verbForms = verbList.get(verb);
    if(tense != null && tense.equals("PAST"))
    {
        if(verb.startsWith("be "))
        {
            if(subject.equals("1s") || subject.equals("3s"))
                negativeString = negativeString + "was not " + verb.substring(3);
            else if(subject.equals("2s") || subject.equals("1p") || subject.equals("2p") || subject.equals("3p"))
                negativeString = negativeString + "were not " + verb.substring(3);   
        }
        else
        negativeString = negativeString + "did not " + verb;

    }
    else if(tense != null && tense.equals("PRES"))
    {
        if(verb.startsWith("be "))
        {
            if(subject.equals("1s"))
                negativeString = negativeString + "am not " + verb.substring(3);
            else if(subject.equals("3s"))
                negativeString = negativeString + "is not " + verb.substring(3);
            else if(subject.equals("2s") || subject.equals("1p") || subject.equals("2p") || subject.equals("3p"))
                negativeString = negativeString + "are not " + verb.substring(3);   
        }
        else
        negativeString = negativeString + "do not " + verbForms.get(1);
    }
    else if(tense != null && tense.equals("FUT"))
    {
        negativeString = negativeString + "will not " + verbForms.get(2);
    }
    else if(tense != null && tense.equals("PERF"))
    {
        if(verb.startsWith("be "))
        {
            if(subject.equals("3s"))
                negativeString = negativeString + "has not been " + verb.substring(3); 
            else
                negativeString = negativeString + "have not been " + verb.substring(3); 
        }
           
        else
        {
            if(subject.equals("3s"))
                negativeString = negativeString + "has not " + verbForms.get(3); 
            else
                negativeString = negativeString + "have not " + verbForms.get(3);
        }
    }
    
        return negativeString;
    }


    public  SwahiliSentenceRequestModel generateRequestModel(SwahiliSentenceRequestModel requestModel, String srcString)
    {
        String grammarrule[] = srcString.split("\\+");
       
        if(grammarrule[0] != null && grammarrule[0].equals("NEG"))
        {
            requestModel.setFeature(grammarrule[0]);
            requestModel.setSubject(grammarrule[1]);
            requestModel.setTense(grammarrule[2]);
            requestModel.setVerb(grammarrule[3]);
        }
        else if(grammarrule[1] != null && grammarrule[1].equals("IMP"))
        {
            requestModel.setSubject(grammarrule[0]);
            requestModel.setImpcheck(grammarrule[1]);
            requestModel.setVerb(grammarrule[2]);
        }
        else{
            requestModel.setSubject(grammarrule[0]);
            requestModel.setTense(grammarrule[1]);
            requestModel.setVerb(grammarrule[2]);
        }
        McqQuizSwahili mcqQuizSwahili= mcqQuizSwahiliRepository.findByCorrectAnswer(requestModel.getVerb());
        requestModel.setEnglishVerb(mcqQuizSwahili.getInput());
        return requestModel;
        
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
