package group1.langlearning.utils;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import java.util.regex.*;

// import com.apptmyz.fpcorporate.upi.data.UPIMandateBankRequest;
// import com.google.gson.Gson;

import java.io.*;
    //import com.icici.bean.RequestBean;
    
@Service
public class HybridEncryption {
    
        //Logger logger = Logger.getLogger(IviewController.class);
    //	static final String PUBLIC_CERTIFICATE = "D:\\Jagdeep\\ARFIN\\ICICIUATpubliccert\\ICICIUATpubliccert.cer";
    //	static final String PUBLIC_CERTIFICATE = "D:\\Mungi Vyshnavi Reddy\\fpcashdeposition ca\\RSA encryption & Decryption\\public-cert-file.cer";
        // static final String PUBLIC_CERTIFICATE =  "E:\\SLU\\Spring 24\\5550 Computer Networks\\project\\public-cert-file.cer";
        static final String PUBLIC_CERTIFICATE =  "E:\\SLU Spring 24\\Spring 24\\5550 Computer Networks\\project\\Encryption Port 8080\\public-cert-file1.cer";
        
        public String encryptData(String incomingData) {
            System.out.println("Inside HybridEncService.encryptData()");
           
            System.out.println("Incoming Data : " + incomingData);
            String data = null;
            try {
                data = getiViewResponse(incomingData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;//Response.status(200).entity(data).build();
        }
    
        public String getiViewResponse(String XMLRequest) throws Exception {
            String randomNumber = generateRandom(16);
            String randomNumberiv = generateRandom(16);
            String Encryptedkey = getRequestkey(randomNumber);
            String EncryptedData = getRequestData(randomNumber, randomNumberiv, XMLRequest);
            String responseData = EncryptedData + "EncryptedDataEncryptedkey" + Encryptedkey;
            
            System.out.println("------------------encrytpedKey-----------\n" + Encryptedkey);
            System.out.println("------------------encrypted_data-------------- \n" + EncryptedData);
            return responseData;
        }
    
        public String generateRandom(int prefix) {
            Random rand = new Random();
    
            long x = (long) (rand.nextDouble() * 100000000000000L);
    
            String s = String.valueOf(prefix) + String.format("%014d", x);
            return s;
        }
    
        private String getRequestData(String secretKey, String ivKey, String strToEncrypt)
                throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
    
            try {
                IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("UTF-8"));
                SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
    
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
                strToEncrypt = ivKey + strToEncrypt;
                byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes());
                return Base64.getEncoder().encodeToString((encrypted));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    
        private String getRequestkey(String randomNUmber) throws NoSuchAlgorithmException, NoSuchPaddingException,
                InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException {
            Cipher ci = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    
            X509Certificate cert = getCertificate(PUBLIC_CERTIFICATE);
            ci.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());
    
            byte[] input = randomNUmber.getBytes("UTF-8");
            String key = Base64.getEncoder().encodeToString(ci.doFinal(input));
    
            
            return key;
        }
    
        private X509Certificate getCertificate(String path) {
            System.out.println("file is :" + path);
            X509Certificate cert = null;
            try {
                FileInputStream inputStream = new FileInputStream(path);
                CertificateFactory f = CertificateFactory.getInstance("X.509");
                cert = (X509Certificate) f.generateCertificate(inputStream);
                inputStream.close();
                System.out.println("Certificate Public Key is :" + cert.getPublicKey());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            return cert;
        }
    
        /**************************
         * Decryption
         * 
         * @throws IOException
         * @throws KeyStoreException
         * @throws CertificateException
         * @throws UnrecoverableKeyException
         * @throws BadPaddingException
         * @throws IllegalBlockSizeException
         * @throws InvalidAlgorithmParameterException
         * @throws NoSuchPaddingException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeyException
         ***************************/
    
        static final String KEYSTORE_FILE = "E:\\SLU Spring 24\\Spring 24\\5550 Computer Networks\\project\\Encryption Port 8080\\keystore.p12";
        static final String KEYSTORE_PWD = "vyshnavi";
        static final String KEYSTORE_ALIAS = "wookie";
        static final String KEYSTORE_INSTANCE = "PKCS12";
        static final String ASYMM_CIPHER = "RSA/ECB/PKCS1Padding";
        
   
    
        public static String decryptKey(String b64EncryptedMsg, String filePath)
                throws NoSuchAlgorithmException, NoSuchPaddingException, CertificateException, InvalidKeyException,
                IllegalBlockSizeException, BadPaddingException, UnrecoverableKeyException, KeyStoreException, IOException {
            Cipher cipher = Cipher.getInstance(ASYMM_CIPHER);
            Key key = loadPrivateKeyFromFile(filePath);
            byte[] encryptedMsg = Base64.getDecoder().decode(b64EncryptedMsg);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMsg = cipher.doFinal(encryptedMsg);
            return new String(decryptedMsg);
        }
    
        private static Key loadPrivateKeyFromFile(String privateKeyPath) throws CertificateException, KeyStoreException,
                NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
            //Key key = null;
            KeyStore ks = KeyStore.getInstance(KEYSTORE_INSTANCE);
            // get user password and file input stream
            FileInputStream fis = new FileInputStream(privateKeyPath);
            ks.load(fis, KEYSTORE_PWD.toCharArray());
            Key myKey = ks.getKey(KEYSTORE_ALIAS, KEYSTORE_PWD.toCharArray());
            return myKey;
        }

             
        public String DecryptData(String incomingData) throws UnrecoverableKeyException, CertificateException,
                KeyStoreException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
                InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Inside HybridEncService.DecryptData()");
            // StringBuilder sb = new StringBuilder();
            // try {
            //     BufferedReader br = new BufferedReader(new InputStreamReader(incomingData));
            //     String line = null;
            //     while ((line = br.readLine()) != null) {
            //         sb.append(line.trim());
            //     }
            //     System.out.println("From decryptdata() the obtained  data before decryption is :----\n"+sb.toString());
            // } catch (Exception e) {
            //     e.printStackTrace();
            //     //logger.error("Exception Occured : " + e);
            // }
            // System.out.println("Incoming Data : " + sb);
            // UPIMandateBankRequest reqData=null;
            // reqData = new Gson().fromJson(sb.toString(), UPIMandateBankRequest.class);
            String[] reqData = incomingData.split("EncryptedDataEncryptedkey");
            String secretKey = null;
            try {
    
                secretKey = decryptKey(reqData[1], KEYSTORE_FILE);
                System.out.println("Secret key---------------\n"+secretKey);
            } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException
                    | IllegalBlockSizeException | BadPaddingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            byte[] ivrec = getIVSpec(reqData[0]);
            String decryptResponse = getDecryptdata(reqData[0], secretKey, ivrec);
    //		decryptResponse = decryptResponse.substring(decryptResponse.indexOf("{\""), decryptResponse.length());
            System.out.println("-------------------decrypt response.length() after removing first 16 bytes i.e IV ---------------"+decryptResponse.length());
            decryptResponse = decryptResponse.replace("\s+", "");
            // decryptResponse = decryptResponse.replaceAll("\n|\s+", "");
 
             System.out.println("Modified string: "+decryptResponse);
             int startIndex = decryptResponse.indexOf("\"status\"");
             decryptResponse = "{"+decryptResponse.substring(startIndex);
            System.out.println("in decrypt   decrypt response  " + decryptResponse);
            
            //return Response.status(200).entity(decryptResponse).build();
            return decryptResponse;
        }
    
        String result;
        private byte[] getIVSpec(String encryptedData) {
    
            byte[] IV = Base64.getDecoder().decode(encryptedData.getBytes());
            byte[] resbyte = new byte[16];
            for (int i = 0; i < 16; i++) {
                System.out.print(IV[i]+",");
                resbyte[i] = IV[i];
            }
             result = new String(resbyte);
            System.out.println("result of 16byte IV DATA   " + result + " length : "+result.length() +"  return resbyte string  :"+resbyte.toString());
            return resbyte;
        }
    
        private String removeIV(String encryptedData) {
    
            byte[] IV = Base64.getDecoder().decode(encryptedData.getBytes());
            byte[] filteredByteArray = Arrays.copyOfRange(IV, 16, IV.length - 16);
            String dataAfterIVRemove = Base64.getEncoder().encodeToString(filteredByteArray);
            return dataAfterIVRemove;
        }
    
        private String getDecryptdata(String data, String key, byte[] ivrec)
                throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
            System.out.println("Inside decrypted data");
    
            IvParameterSpec ivspec = new IvParameterSpec(ivrec);
            System.out.println("number is " + key);
            
            Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            byte[] actualkey = key.getBytes();
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(actualkey, "AES");
            
            ci.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
            
            byte[] result = Base64.getDecoder().decode(data.getBytes());
            
            String decryptedData = new String(ci.doFinal(result));
        
            System.out.println("decrypted data close    " + decryptedData);
    
            return decryptedData;
        }
        
        // public static void main(String args[]) throws Exception
        // {
        //     HybridEncryption obj=new HybridEncryption();
            
        //     //String src="{\"UTR\":\"G22102018370\",\"TransactionDate\":\"20201022183701\",\"Amount\":\"60.0\",\"DepositSlipNumber\":\"3111822801\",\"StopID\":\"ICCHA75500\",\"CustomerCode\":\"SUYASH1\",\"HierarchyCode\":null,\"DenominationDetails\":\"10X5,1X10\",\"DepositorMobileNumber\":\"8977909045\",\"RetailerCode\":\"TAP\"}";
        //     // String src="{\"StatusCode\":\"000\",\"Message\":\"SUCCESS\",\"Response\":[{\"UTR\":\"G22102018512\",\"TransactionDate\":\"20201022185129\",\"Amount\":\"80.0\",\"DepositSlipNumber\":\"3111812801\",\"StopID\":\"ICCHA00006\",\"CustomerCode\":\"AUTOMATIC\",\"HierarchyCode\":\"HIECOD\",\"DenominationDetails\":\"20X1,50X1\",\"DepositorMobileNumber\":\"8977909045\",\"RetailerCode\":\"TAP\"}]}";
        //     String file = "Filename:SRNo0123456789.txt";
        //     String encryp_txt = "How are you?";
        //     String src=file+ encryp_txt;
        //     System.out.println("-------------------original string length---------------"+src.length());
        //    // InputStream input = new ByteArrayInputStream(src.getBytes());
        //     String encry_src=obj.encryptData(src);
        //     System.out.println("=================== encrypted src ====================");
        //     System.out.println(encry_src);
        //     InputStream output = new ByteArrayInputStream(encry_src.getBytes());
        //     String decry_src=obj.DecryptData(output);
        //     System.out.println("=================== decrypted src ====================");
        //     System.out.println(decry_src);
        //     String decryptedText = "";
        //     int startIndex = decry_src.indexOf("Filename:SRNo");
        //     if (startIndex != -1) {
        //         startIndex += "Filename:SRNo".length() + 14;  // Skip "Filename:SRN0" and the first 10 characters
        //          decryptedText = decry_src.substring(startIndex);
        //     }
        //     System.out.println("after IV : "+decryptedText);
    
        //     if(decryptedText.equals(encryp_txt))
        //         System.out.println("******************* Encryption==decryption *******************");
        //     else
        //         System.out.println("******************* Encryption!=decryption *******************");
        // }
    }
    