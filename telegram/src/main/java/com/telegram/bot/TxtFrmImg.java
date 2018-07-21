package com.telegram.bot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.telegram.bot.util.AmazonTxtFromImageUtil;

public class TxtFrmImg extends TelegramLongPollingBot{

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "xxx";
	}


	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "xxx";
	}

	@Override
	public void onUpdateReceived(Update update) {
		long chat_id = update.getMessage().getChatId();
		if (update.hasMessage() && update.getMessage().hasPhoto()) {

			try {
				GetFile getFileRequest = new GetFile();
				List<PhotoSize> photos = update.getMessage().getPhoto();
				String f_id = photos.stream()
						.sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
						.findFirst()
						.orElse(null).getFileId();
				getFileRequest.setFileId(f_id);
				org.telegram.telegrambots.api.objects.File file = execute(getFileRequest);
				URL fileUrl=new URL(file.getFileUrl(getBotToken()));
				HttpURLConnection httpConn = (HttpURLConnection) fileUrl.openConnection();
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id)
						.setText(AmazonTxtFromImageUtil.textFromImage(httpConn.getInputStream()));

				execute(message); // Sending our message object to user

			} catch (TelegramApiException  | IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id)
						.setText("Give me image not text, like this "+update.getMessage().getText());
				execute(message); // Sending our message object to user
			}
			catch(TelegramApiException taexc)
			{

			}

		}

	}

}
