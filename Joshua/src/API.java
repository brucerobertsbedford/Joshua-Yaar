import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API {


	private static String CLIENT_ID = "bebcef28ebfd41549408ad18eb638059";
	private static String CLIENT_SECRET = "63834c8901ed4008886a25691e790bc7";
	private static String TOKEN_URL = "https://accounts.spotify.com/api/token";
	private static String API_URL = "https://api.spotify.com/v1/";
	private static String ACCESS_TOKEN_NAME = "access_token";
	private static String accessToken = "BQB0zqosPXGFe9zXkzdLBlMCb5-PAWTwXcs-AVC4MEm3rlON4FnLCzgPiZPrQJxpiZQUkk1uEO4jOQQrkQYu0dmqPWG8QSBAhmMFEToUk7Qs4fHAF4Q";
	
	public static void main(String[] args) {
//		String accessToken = sendPost(TOKEN_URL, CLIENT_ID, CLIENT_SECRET);
	//	System.out.println("\n\naccess token:  '" + accessToken + "'");
		String artistId = getArtistId("Miles Davis");
//		getArtist(artistId);

	}
	
	private static String getArtistId(String artistName) {
		String url = API_URL + "search?q=" + URLEncoder.encode(artistName) + "&type=artist";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization",  "Bearer " + accessToken);
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String output;

	        StringBuffer response = new StringBuffer();
	        while ((output = in.readLine()) != null) {
	            response.append(output);
	        }

	        in.close();
	        // printing result from response
	        System.out.println("Response:-" + response.toString());
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Get response:  " + response);
				
			} else {
				return "";
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Malforemed url:  " + url);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error opening connection to url:  " + url);
			e.printStackTrace();
		}
		return "";
//		return "0TnOYISbd1XYRBk9myaseg";
	}
	
	private static String getArtist(String artistId) {
		String url = API_URL + "artists/" + artistId;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization",  "Bearer " + accessToken);
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String output;

	        StringBuffer response = new StringBuffer();
	        while ((output = in.readLine()) != null) {
	            response.append(output);
	        }

	        in.close();
	        // printing result from response
	        System.out.println("Response:-" + response.toString());
			int responseCode = con.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Get response:  " + response);
				
			} else {
				return "";
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Malforemed url:  " + url);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error opening connection to url:  " + url);
			e.printStackTrace();
		}
		return "";
		
	}

	private static String sendPost(String url, String client_id, String client_secret) {
		try {
			String params = "grant_type=client_credentials&client_id=" + client_id + "&client_secret=" + client_secret;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(params.getBytes());
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
				System.out.println("Post response:  " + response);
				
				String accessToken = getJsonMember(ACCESS_TOKEN_NAME, response.toString());		
				return accessToken;
			} else {
				System.out.println("Post returned HTTP error code:  " + responseCode);
				return "";
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Malforemed url:  " + url);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error opening connection to url:  " + url);
			e.printStackTrace();
		}
		return "";
		
	}
	
	private static String getJsonMember(String memberName, String json) {
		String result = "";
		String regex = "[.]*\"" + memberName + "\":\"(.+?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(json);
		if (matcher.find()) {
			result = matcher.group(1);
		}
		return result;
	}

}
