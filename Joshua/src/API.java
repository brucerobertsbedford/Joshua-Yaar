import java.net.MalformedURLException;
import java.io.*;
import java.net.*;

public class API {

	private static String CLIENT_ID = "bebcef28ebfd41549408ad18eb638059";
	private static String CLIENT_SECRET = "63834c8901ed4008886a25691e790bc7";
	private static String TOKEN_URL = "https://accounts.spotify.com/api/token";
	private static String PARAMS = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
	
	public static void main(String[] args) {
		sendPost(TOKEN_URL, CLIENT_ID, CLIENT_SECRET);

	}

	private static void sendPost(String url, String client_id, String client_secret) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(PARAMS.getBytes());
			os.flush();
			os.close();
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response);
				
			} else {
				System.out.println("Post returned HTTP error code:  " + responseCode);
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Malforemed url:  " + url);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error opening connection to url:  " + url);
			e.printStackTrace();
		}
		
	}

}
