package com.realex.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@SpringBootApplication
public class App {

	@Autowired
	HttpGet http;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

		return args -> {

			System.out.println("************************************************");
			System.out.println("************************************************");
			System.out.println("************************************************");

			System.out.println("Send Http GET request");
			try {
				http.sendGet();
			} catch (Exception e) {
				System.out.println("Got exception type: " + e.getClass() + " with message: " + e.getMessage());
			}

		};
    }

}

interface HttpGet {
	@Retryable(value = { java.net.UnknownHostException.class },
			//maxAttemptsExpression = "#{${variable.retry.max}}", 
			maxAttempts = 7, backoff = @Backoff(delay = 1000, maxDelay = 6000, multiplier = 2))
	void sendGet() throws Exception;
}

class RetryingHttpGetExample implements HttpGet {

	private final String USER_AGENT = "Mozilla/5.0";

	// HTTP GET request
	@Override
	public void sendGet() throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println();

		String url = "http://failinghost:8080/health";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

}