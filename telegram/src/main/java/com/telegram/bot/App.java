package com.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

// TODO: Auto-generated Javadoc
/**
 * Hello world!.
 */
public class App 
{

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args )
	{
		ApiContextInitializer.init();

		// Instantiate Telegram Bots API
		TelegramBotsApi botsApi = new TelegramBotsApi();

		// Register our bot
		try {
			botsApi.registerBot(new TxtFrmImg());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}


	}
}
