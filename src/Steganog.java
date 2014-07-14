import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Steganog {

	private PrimeIterator iterator;

	public File embedIntoImage(File cleanImage, String message) throws IOException {

		BufferedImage org = ImageIO.read(cleanImage);
		iterator = new PrimeIterator(org.getWidth()*org.getHeight());
		int msgRed, msgGreen, msgBlue;
		int valueR, valueG, valueB;
		int theColorRed, theColorGreen, theColorBlue;

		for(int p = iterator.next(), r=0; iterator.hasNext() && r < message.length(); p = iterator.next(), r++ ){

			String redString, greenString, blueString;
			Color theColor = new Color(org.getRGB(p%org.getWidth(), p/org.getWidth()));

			//bit-shifting and grabbing the chars
			valueB = (message.charAt(r) - ' ') + 64;
			valueG = valueB >> 2;
		valueR = valueB >> 4;

		//binary int to a string
		redString = Integer.toBinaryString(valueR);
		greenString = Integer.toBinaryString(valueG);
		blueString = Integer.toBinaryString(valueB);

		//taking last two values and converting to an int
		msgBlue = Integer.parseInt(blueString.substring(blueString.length()-2),2);
		msgGreen = Integer.parseInt(greenString.substring(greenString.length()-2),2);
		msgRed = Integer.parseInt(redString.substring(redString.length()-2),2);

		//taking away zeros
		theColorRed =(theColor.getRed()+64);
		theColorGreen =(theColor.getGreen()+64);
		theColorBlue =(theColor.getBlue()+64);

		//bit-shifting from 6 to 4 then 4 to 2, filling with zeros
		theColorRed=theColorRed >> 2;
		theColorGreen=theColorGreen >> 2;
		theColorBlue=theColorBlue >> 2;
		theColorRed=theColorRed << 2;
		theColorGreen=theColorGreen << 2;
		theColorBlue=theColorBlue << 2;

		//switching back to the original
		theColorRed += (msgRed - 64);
		theColorGreen += (msgGreen-64);
		theColorBlue += (msgBlue-64);

		//creating a whole new image the x&y with new color
		Color c = new Color(theColorRed, theColorGreen, theColorBlue);
		org.setRGB(p % org.getWidth(), p / org.getWidth(), c.getRGB());

		}

		File embededImage = new File("embedded.png");
		ImageIO.write(org, "PNG", embededImage);

		return cleanImage;
	}

	public String retretiveFromImage(File imageWithEmbeddedMsg) throws IOException {

		String hiddenMsg="";

		BufferedImage msgPic = ImageIO.read(imageWithEmbeddedMsg);
		iterator = new PrimeIterator(msgPic.getWidth()*msgPic.getHeight());

		for(int m = iterator.next(); iterator.hasNext(); m = iterator.next())
		{
			Color lor = new Color(msgPic.getRGB(m % msgPic.getWidth(), m / msgPic.getWidth()));
			String temporary, rString, gString, bString;
			Integer redValue, greenValue, blueValue;

			//make sure there is not a zero at the end
			redValue = (int) lor.getRed() + 64;
			greenValue = (int) lor.getGreen() + 64;
			blueValue = (int) lor.getBlue() + 64;

			//grabbing the binary form of the int
			rString = Integer.toBinaryString(redValue);
			gString = Integer.toBinaryString(greenValue);
			bString = Integer.toBinaryString(blueValue);

			//removing the last two values(6 to 4)
			rString = rString.substring(rString.length() - 2);
			gString = gString.substring(gString.length() - 2);
			bString = bString.substring(bString.length() - 2);

			//doing concat of the rgb values
			temporary = rString+gString+bString;

			//making the letters from the values
			char theLetter;
			theLetter = (char) (Integer.parseInt(temporary,2)+' ');

			if(Character.toString(theLetter).matches("([A-Z .!])")){
				hiddenMsg += Character.toString(theLetter);
			}

		}

		return hiddenMsg;
	}



}
