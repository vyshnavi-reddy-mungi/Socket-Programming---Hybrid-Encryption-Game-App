package group1.langlearning.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TEST_CID {

    
    public static void main(String[] args) {
        // Replace "localhost" with the appropriate IPFS API address if needed
        String url = "http://127.0.0.1:8081/ipfs/QmdpfFjdcDZHSJm8a36uWhkFg9uJE5kdYgKdgJE27He9nX";
        try {
            postData(url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void  postData(String postUrl) throws IOException
    {
        URL url = new URL(postUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json"); // Set content type
        connection.setConnectTimeout(60000);
        
        // Prepare your data as a String
        // String data = "{\"name\": \"John Doe\", \"age\": 30}";
        
        connection.setDoOutput(true); // Allow writing to output stream
       // OutputStream os = connection.getOutputStream();
        //os.write(encryptedData.getBytes()); // Write data to output stream
       // os.flush();
        
        
        int responseCode = connection.getResponseCode();
        String content = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
          // Read response
          InputStream is = connection.getInputStream();
          
          StringBuilder sb = new StringBuilder();
          String line;
          BufferedReader reader = new BufferedReader(new InputStreamReader(is));
          
          while ((line = reader.readLine()) != null) {
            sb.append(line); // Add newline character after each line
          }
          
           content = sb.toString();
          
          reader.close();
         
          is.close();
        } else {
          System.out.println("Error:"+responseCode);
        }
        
        connection.disconnect();
        
        System.out.println("content-----------:"+content);
        // String decryptedData= "";
        
      
    }

}
