import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API {

	private static String CLIENT_ID = "bebcef28ebfd41549408ad18eb638059";
	private static String CLIENT_SECRET = "63834c8901ed4008886a25691e790bc7";
	private static String TOKEN_URL = "https://accounts.spotify.com/api/token";
	private static String ACCESS_TOKEN_NAME = "access_token";
	
	public static void main(String[] args) {
		sendPost(TOKEN_URL, CLIENT_ID, CLIENT_SECRET);

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
				System.out.println(response);
				
				String accessToken = getJsonMember(ACCESS_TOKEN_NAME, response.toString());		
				System.out.println("accessToken:  " + accessToken);
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
