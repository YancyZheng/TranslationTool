package com.yan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

public class Translation {
	private String urlTemplate = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=%s&tl=%s&dt=t&q=%s";

	public String Translate(String transSrc, String inputCode, String languageCode) throws IOException {
		if (transSrc == null || transSrc.trim().equals(""))
			return "";
		if (languageCode == null || languageCode.trim().equals(""))
			return "";
		
		URL url = BuildUrl(transSrc, inputCode, languageCode);		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36");

		InputStream inputStream = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader bReader = new BufferedReader(reader);

		StringBuilder sb = new StringBuilder();
		while (true) {
			String result = bReader.readLine();
			if (result == null) {
				break;
			}
			sb.append(result);
		}

		bReader.close();

		return sb.toString();
	}

	String GetTransResult(String jsonInput) throws JsonParseException, JsonMappingException, IOException {
		JSONArray start = new JSONArray(jsonInput);
		System.out.println(start.getJSONArray(0).getJSONArray(0).getString(0));
		
		return start.getJSONArray(0).getJSONArray(0).getString(0);
	}
	
	URL BuildUrl(String transSrc, String inputCode, String languageCode) throws IOException {
		String outputLanguageCode = languageCode;
		
		String input = URLEncoder.encode(transSrc, StandardCharsets.UTF_8.toString());

		String adr = String.format(urlTemplate, inputCode, outputLanguageCode, input);
        URL url = new URL(String.format(urlTemplate, inputCode, outputLanguageCode, input));
        
        return url;
	}
}
