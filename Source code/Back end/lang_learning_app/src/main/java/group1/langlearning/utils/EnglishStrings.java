package group1.langlearning.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.record.chart.CatLabRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group1.langlearning.models.SwahiliSentenceRequestModel;

public class EnglishStrings {
    
    public static void main(String[] args) {

        Gson gson = new GsonBuilder().create();
        try {
            // Get the file as a stream from the resources folder
            InputStream inputStream = EnglishStrings.class.getResourceAsStream("/Verb forms PSD.txt");

            // Create a reader to read the contents of the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read each line of the file
            String line;
            Map<String,ArrayList<String>> verbList = new  HashMap<String,ArrayList<String>>();

            while ((line = reader.readLine()) != null) {

                // Split the line into an array of values
                String[] values = line.split("-");
                String[] verbForms = values[1].split(",");
                ArrayList<String> verbs = new ArrayList<>();
                for(String v:verbForms)
                    verbs.add(v);
                verbList.put(values[0],verbs);

            }
            
            //  for(Map.Entry<String, ArrayList<String>> hm:verbList.entrySet())
            //  {
            //     System.out.println(hm.getKey()+":"+hm.getValue());
            //  }


             BufferedWriter writer = new BufferedWriter(new FileWriter(new File("TestCases.tsv")));
             InputStream inputStreamNEG = EnglishStrings.class.getResourceAsStream("/ALL testcases.txt");

             // Create a reader to read the contents of the file
             BufferedReader readerNEG = new BufferedReader(new InputStreamReader(inputStreamNEG));
             while ((line = readerNEG.readLine()) != null) {
                 // Split the line into an array of values
                //  String[] values = line.split("\\+");
                 SwahiliSentenceRequestModel swahiliSentenceRequestModel = new SwahiliSentenceRequestModel();
                //  System.out.println("line : "+line);
                swahiliSentenceRequestModel = generateRequestModel(swahiliSentenceRequestModel,line);
                System.out.println("request model : "+gson.toJson(swahiliSentenceRequestModel));
               String englishString = "";
                 if(swahiliSentenceRequestModel.getFeature()!=null && swahiliSentenceRequestModel.getFeature().equals("NEG"))
                 {
                    englishString = getNegativeString(verbList,swahiliSentenceRequestModel.getVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
                    System.out.println(line+"\t"+englishString);
                 }
                 else if(swahiliSentenceRequestModel.getImpcheck()!=null && swahiliSentenceRequestModel.getImpcheck().equals("IMP"))
                 {
                    englishString = swahiliSentenceRequestModel.getVerb();
                    System.out.println("model : "+gson.toJson(swahiliSentenceRequestModel));
                    System.out.println("string : "+englishString);
                    // System.out.println(line+"\t"+englishString);
                 }
                 else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PRES"))
                 {
                    englishString = getPresentString(verbList,swahiliSentenceRequestModel.getVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
                    System.out.println(line+"\t"+englishString);
                 }
                 else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("FUT"))
                 {
                    englishString = getFutureString(verbList,swahiliSentenceRequestModel.getVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
                    System.out.println(line+"\t"+englishString);
                 }
                 else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PERF"))
                 {
                    englishString = getPerfectString(verbList,swahiliSentenceRequestModel.getVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
                    System.out.println(line+"\t"+englishString);
                 }
                 else if(swahiliSentenceRequestModel.getTense()!=null && swahiliSentenceRequestModel.getTense().equals("PAST"))
                 {
                    englishString = getPastString(verbList,swahiliSentenceRequestModel.getVerb(), swahiliSentenceRequestModel.getTense(), swahiliSentenceRequestModel.getSubject());
                    System.out.println(line+"\t"+englishString);
                 }
                 writer.write(line+"\t"+englishString);
                 writer.newLine();
             }
            // Close the stream and reader
             inputStream.close();
             reader.close();
             inputStreamNEG.close();
             readerNEG.close();
             writer.close();
 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
                InputStream inputStream = EnglishStrings.class.getResourceAsStream("/3s-present verbs.txt");

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
            
             for(Map.Entry<String, String> hm:present_3s.entrySet())
             {
                System.out.println(hm.getKey()+":"+hm.getValue());
             }

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

public static SwahiliSentenceRequestModel generateRequestModel(SwahiliSentenceRequestModel requestModel, String srcString)
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
    // System.out.println("Request model : "+new GsonBuilder().create().toJson(requestModel));
    return requestModel;
    
}

}