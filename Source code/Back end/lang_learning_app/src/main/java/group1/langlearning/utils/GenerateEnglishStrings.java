// package group1.langlearning.utils;

// import java.io.BufferedReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;

// public class GenerateEnglishStrings {
    
//     public static void main(String args[])
//     {
//         InputStream inputStream = EnglishStrings.class.getResourceAsStream("/ALL testcases.txt");

//         // Create a reader to read the contents of the file
//         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

//         // Read each line of the file
//         String line;
//         while ((line = reader.readLine()) != null) {

//             // Split the line into an array of values
//             String[] values = line.split("//+");
           
//             ArrayList<String> verbs = new ArrayList<>();
//             for(String v:verbForms)
//                 verbs.add(v);
//             verbList.put(values[0],verbs);

//         }
        
//         //  for(Map.Entry<String, ArrayList<String>> hm:verbList.entrySet())
//         //  {
//         //     System.out.println(hm.getKey()+":"+hm.getValue());
//         //  }


//          BufferedWriter writer = new BufferedWriter(new FileWriter(new File("TestCases.tsv")));
//          InputStream inputStreamNEG = EnglishStrings.class.getResourceAsStream("/ALL testcases.txt");


//     }
    
// }
