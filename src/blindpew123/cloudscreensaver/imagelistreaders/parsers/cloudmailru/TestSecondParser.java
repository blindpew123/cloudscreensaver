package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestSecondParser {

	public static void main(String[] args) throws IOException {
		
		
		String url = "https://cloud.mail.ru/public/Cw2G/Vkvwnmq9p/2014/5...%D0%A8%D0%B2%D0%B5%D1%86%D0%B8%D1%8F/DSC04460.jpg";

		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		connection.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("c:/1/out.html"))));
		
		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine+'\n');
		    response.append(inputLine);
		}
		in.close();
		writer.close();
	}

}
