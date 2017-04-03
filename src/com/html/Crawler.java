package com.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler{
	private Parser parser;
	
	public void execute(String uri, Path path) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		// Connect to the url and get the document.
		Document doc = Helper.getDocument(uri);
		
		// There should be one main element but might depend on the HTML.
		Elements elements = doc.children();
		
		// Parse the retrieved HTML page.
		parser = new Parser(elements);
		parser.parse();
		
		// Write back the information to the disk.
		writeToFile(path.toFile());
	}
	
	private void writeToFile(File file) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		bw.append("[links]");
		bw.append(System.getProperty("line.separator"));
		bw.append(parser.getLinks().append(System.getProperty("line.separator")));
		
		bw.append("[HTML]");
		bw.append(System.getProperty("line.separator"));
		bw.append(parser.getTags().append(System.getProperty("line.separator")));
		bw.append(System.getProperty("line.separator"));
		
		bw.append("[sequences]");
		bw.append(System.getProperty("line.separator"));
		bw.append(parser.getSeqs());
		
		bw.flush();
		bw.close();
	}
}