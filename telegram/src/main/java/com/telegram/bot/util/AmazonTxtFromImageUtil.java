package com.telegram.bot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.util.IOUtils;

public class AmazonTxtFromImageUtil {


	public static String textFromImage(InputStream inputStream) throws IOException
	{

		BasicAWSCredentials credential ;
		StringBuilder strBuilder=new StringBuilder();
		try
		{
			credential = new BasicAWSCredentials("xxx", "xxx");
		}
		catch(Exception e)
		{
			throw new AmazonClientException("credentional not allowed");
		}

		ByteBuffer imageBytes = null;
		try 
		{
			imageBytes=java.nio.ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			inputStream.close();
		}
		AmazonRekognition rekognitionClient=AmazonRekognitionClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credential)).build();
		DetectTextRequest request = new DetectTextRequest()
				.withImage(new Image()
						.withBytes(imageBytes));
		try
		{
			DetectTextResult result = rekognitionClient.detectText(request);
			List<TextDetection> textDetections = result.getTextDetections(); 

			for (TextDetection text: textDetections) {

				if("LINE".equals(text.getType())) {
					strBuilder.append(text.getDetectedText()).append("\n");
				}
			}
		}
		catch(AmazonRekognitionException amrk)
		{
			amrk.printStackTrace();
		}
		return strBuilder.toString();
	}
}
