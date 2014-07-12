import java.io.File;
import java.io.IOException;


public class Driver {

	
	public static void main(String[] args) throws IOException{
        Steganog nog = new Steganog();
        File embeddedPic = new File("C:\\Users\\dbrown\\IdeaProjects\\DBrown_Steganog\\src\\Steganog\\DocMcStuffins.png");
        File newPic = new File("embedded.png");

        nog.embedIntoImage(embeddedPic, "I TOTALLY LOVE DOC MCSTUFFINS! SHE IS SO CUTE AND A LITTLE ROLE MODEL. ");
        System.out.println(nog.retretiveFromImage(newPic));
	}

}
