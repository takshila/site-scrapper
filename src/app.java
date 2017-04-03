import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.html.Crawler;
import com.html.Helper;

public class app {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		// Test that exactly two arguments are provided to the application. Exit otherwise.
		if(args == null || args.length != 2){
			System.out.println("Missing arguments. Need a url link and an output file location");
			System.exit(0); //exit code - 0. Gracefully.
		}
		
		// Add the http:// transport protocol if not present.
		String url = Helper.getValidURL(args[0]);
		
		// Test the validity of the URL. Exit otherwise.
		if(!Helper.isValidURL(url)){
			System.out.println("Incorrect URL");
	    	System.exit(0);
		}
		
		// Test the validity of the path. Should be able to create the file at the location. Exit otherwise.
		Path file = Paths.get(args[1]);
		try {
			file = Files.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Execute the crawler and write to the file.
		Crawler crawler = new Crawler();
		try {
			crawler.execute(url, file);
		} catch (Exception e) {
			try {
				// delete the file if there was an error.
				Files.delete(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.exit(0);
		}
	}
}