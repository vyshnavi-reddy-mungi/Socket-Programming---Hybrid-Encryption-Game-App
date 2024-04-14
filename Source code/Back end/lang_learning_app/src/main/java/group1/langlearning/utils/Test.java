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
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// import com.ipfs.ipld.IpldClient;
// import com.ipld.crypto.CID;  // Assuming CID is from IPLD Java (for CID handling)

// import io.ipfs.api.IPFS;
// import io.ipfs.multihash.Multihash;


// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;


import java.io.*;
import java.net.HttpURLConnection;
//import com.icici.bean.RequestBean;
import java.net.URL;

// import io.ipfs.api.IPFS;
// import io.ipfs.api.MerkleNode;
//import io.ipfs.multihash.Multihash;

public class Test {

	//Logger logger = Logger.getLogger(IviewController.class);
//	static final String PUBLIC_CERTIFICATE = "D:\\Jagdeep\\ARFIN\\ICICIUATpubliccert\\ICICIUATpubliccert.cer";
//	static final String PUBLIC_CERTIFICATE = "D:\\Mungi Vyshnavi Reddy\\fpcashdeposition ca\\RSA encryption & Decryption\\public-cert-file.cer";
	//static final String PUBLIC_CERTIFICATE =  "C:\\Users\\vyshn\\Downloads\\project (1)\\project\\public-cert-file.cer";
	 static final String PUBLIC_CERTIFICATE = "QmcNjddQ1j26k1Lz2KYSj4aZiJBcgF5A3wSu6ZUuA9ALQB";
	// static final String PUBLIC_CERTIFICATE = "QmdpfFjdcDZHSJm8a36uWhkFg9uJE5kdYgKdgJE27He9nX";
	//Qma5cLtP67hYXD9eo81rpbyn6ztsZVKyapTHQSLRXtMbQy
    //public String Encryptedkey;
	//public String EncryptedData;
	
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

		// X509Certificate cert = getCertificate(PUBLIC_CERTIFICATE);
        X509Certificate cert = null;
        try {
            cert = getCertificate(PUBLIC_CERTIFICATE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		ci.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());

		byte[] input = randomNUmber.getBytes("UTF-8");
		String key = Base64.getEncoder().encodeToString(ci.doFinal(input));

		
		return key;
	}

	private X509Certificate getCertificate(String path) {
		System.out.println("file is :" + path);
		X509Certificate cert = null;
    String url = "http://127.0.0.1:8081/ipfs/" + PUBLIC_CERTIFICATE;
    String cid_cert = "";
    try {
      cid_cert =  postData(url);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	System.out.println("Certificate ------------- :"+cid_cert);
			// Now create an input stream for the created file

			// Use the input stream to generate the X509Certificate
			try {
			// String fileName = "Certificate1.cer";
			// 	FileWriter writer = new FileWriter(fileName);
            // writer.write(cid_cert);
            // writer.close();
			
			// fileName = path+fileName;
			// System.out.println("path file : -------------"+fileName);
			// FileInputStream inputStream = new FileInputStream(path);
			// FileInputStream inputStream = new FileInputStream(path);

			cid_cert = cid_cert.replace("-----BEGIN CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\n");
			//cid_cert = cid_cert.replace("-----END CERTIFICATE-----", "\n-----END CERTIFICATE-----");

			System.out.println("After Certificate ------------- :"+cid_cert);
		

			CertificateFactory f = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) f.generateCertificate(new ByteArrayInputStream(cid_cert.getBytes()));
			//inputStream.close();
			
			// CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			// cert = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(cid_cert.getBytes()));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return cert;
	}

    public static String postData(String postUrl) throws IOException
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
        InputStream is = null;
        if (responseCode == HttpURLConnection.HTTP_OK) {
          // Read response
         is = connection.getInputStream();
          
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
        
      return content;
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

	static final String KEYSTORE_FILE = "C:\\Users\\vyshn\\Downloads\\project (1)\\project\\keystore.p12";
	static final String KEYSTORE_PWD = "vyshnavi";
	static final String KEYSTORE_ALIAS = "wookie";
	static final String KEYSTORE_INSTANCE = "PKCS12";
	static final String ASYMM_CIPHER = "RSA/ECB/PKCS1Padding";
	

	
	   public String DecryptData(String incomingData) throws UnrecoverableKeyException, CertificateException,
                KeyStoreException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
                InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("Inside HybridEncService.DecryptData()");
        
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
             int startIndex = decryptResponse.indexOf("\"firstName\"");
             decryptResponse = "{"+decryptResponse.substring(startIndex);
            System.out.println("in decrypt   decrypt response  " + decryptResponse);
            
            //return Response.status(200).entity(decryptResponse).build();
            return decryptResponse;
        }
    
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

//	private String getDecryptedData(String secret, String strToDecrypt)
//			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
//			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
//
//		try {
//			SecretKeySpec skeySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//			byte[] original = cipher.doFinal(strToDecrypt.getBytes());
//
//			return new String(original, Charset.forName("UTF-8"));
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}

//	private static SecretKeySpec secretKey;
//	private static byte[] key;
//
//	public static void setKey(String myKey) {
//		MessageDigest sha = null;
//		try {
//			key = myKey.getBytes("UTF-8");
//			sha = MessageDigest.getInstance("SHA-1");
//			key = sha.digest(key);
//			key = Arrays.copyOf(key, 16);
//			secretKey = new SecretKeySpec(key, "AES");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	}
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


	private String removeIV(String encryptedData) {

		byte[] IV = Base64.getDecoder().decode(encryptedData.getBytes());
		byte[] filteredByteArray = Arrays.copyOfRange(IV, 16, IV.length - 16);
		String dataAfterIVRemove = Base64.getEncoder().encodeToString(filteredByteArray);
		return dataAfterIVRemove;
	}

	public static void main(String args[]) throws Exception
	{
		Test obj=new Test();
		
		//String src="{\"UTR\":\"G22102018370\",\"TransactionDate\":\"20201022183701\",\"Amount\":\"60.0\",\"DepositSlipNumber\":\"3111822801\",\"StopID\":\"ICCHA75500\",\"CustomerCode\":\"SUYASH1\",\"HierarchyCode\":null,\"DenominationDetails\":\"10X5,1X10\",\"DepositorMobileNumber\":\"8977909045\",\"RetailerCode\":\"TAP\"}";
		// String src="{\"StatusCode\":\"000\",\"Message\":\"SUCCESS\",\"Response\":[{\"UTR\":\"G22102018512\",\"TransactionDate\":\"20201022185129\",\"Amount\":\"80.0\",\"DepositSlipNumber\":\"3111812801\",\"StopID\":\"ICCHA00006\",\"CustomerCode\":\"AUTOMATIC\",\"HierarchyCode\":\"HIECOD\",\"DenominationDetails\":\"20X1,50X1\",\"DepositorMobileNumber\":\"8977909045\",\"RetailerCode\":\"TAP\"}]}";
        String src = "{\"firstName\":\"Hello\"}";
        System.out.println("-------------------original string length---------------"+src.length());
	//	InputStream input = new ByteArrayInputStream(src.getBytes());
		String encry_src=obj.encryptData(src);
		System.out.println("=================== encrypted src ====================");
		System.out.println(encry_src);
		//InputStream output = new ByteArrayInputStream(encry_src.getBytes());
		String decry_src=obj.DecryptData(encry_src);
		System.out.println("=================== decrypted src ====================");
		System.out.println(decry_src);
		System.out.println(src);
		if(decry_src.equals(src))
			System.out.println("******************* Encryption==decryption *******************");
		else
			System.out.println("******************* Encryption!=decryption *******************");
	}
}
