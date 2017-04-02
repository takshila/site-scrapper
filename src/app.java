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
		// TODO Auto-generated method stub
		if(args == null || args.length != 2){
			System.out.println("Missing arguments. Need a url link and an output file location");
			System.exit(0); //exit code - 0. Gracefully.
		}
		
		String uri = Helper.getValidURI(args[0]);
		
		if(!Helper.isValidURI(uri)){
			System.out.println("Incorrect URL");
	    	System.exit(0);
		}
		
		
		// Check Path Validity
		Path file = Paths.get(args[1]);
		try {
			file = Files.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		Crawler crawler = new Crawler();
		try {
			crawler.execute(uri, file);
		} catch (Exception e) {
			try {
				Files.delete(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.exit(0);
		}
	}
}