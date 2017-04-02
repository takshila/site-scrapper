package com.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler{
	private Parser scanner;
	
	public void execute(String uri, Path path) throws IOException {
		Document doc = Jsoup.connect(uri).get();
		Elements elements = doc.children();
		
		scanner = new Parser(elements);
		scanner.parse();
		
		writeToFile(path.toFile());
	}
	
	private void writeToFile(File file) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		bw.append("[links]");
		bw.append(System.getProperty("line.separator"));
		bw.append(scanner.getLinks().append(System.getProperty("line.separator")));
		
		bw.append("[HTML]");
		bw.append(System.getProperty("line.separator"));
		bw.append(scanner.getTags().append(System.getProperty("line.separator")));
		bw.append(System.getProperty("line.separator"));
		
		bw.append("[sequences]");
		bw.append(System.getProperty("line.separator"));
		bw.append(scanner.getSeqs());
		
		bw.flush();
		bw.close();
	}
}