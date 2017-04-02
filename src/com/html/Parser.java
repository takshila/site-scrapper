package com.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	private Elements elements;
	private StringBuilder links;
	private StringBuilder tags;
	private StringBuilder seqs;

	public Parser(Elements elements){
		this.elements = elements;
		this.links = new StringBuilder();
		this.tags = new StringBuilder();
		this.seqs = new StringBuilder();
	}
	
	public StringBuilder getLinks(){
		return this.links;
	}
	
	public StringBuilder getTags(){
		return this.tags;
	}
	
	public StringBuilder getSeqs(){
		return this.seqs;
	}
	
	public void parse(){
		for(Element element:elements){
			parseRec(element);
		}
	}
	
	private void parseRec(Element element){
		if(element.tag().isSelfClosing()){
			addSelfClosingTag(element);			
		}else{
			addStartTag(element);
			
			String text = element.ownText();
			if(!text.isEmpty()){
				addSeq(text);
			}
			
			for(Element child:element.children()){
				parseRec(child);
			}
			
			addEndTag(element);
		}
	}
	
	private void addLink(Element element){
		if(element.tagName().equalsIgnoreCase("a")){
			links.append(element.attr("href"));
			links.append(System.getProperty("line.separator"));
		}
	}
	
	private void addSelfClosingTag(Element element){
		tags.append("<"+element.tagName()+"/>");
		addLink(element);
	}
	
	private void addStartTag(Element element){
		tags.append("<"+element.tagName()+">");
		addLink(element);
	}
	
	private void addEndTag(Element element){
		tags.append("</"+element.tagName()+">");
	}
	
	private void addSeq(String text){
		String[] tokens = text.split("\\s+");
				
		Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = null;
		
		int i = 0;
		StringBuilder word = new StringBuilder();
		
		for(String token:tokens){
			m = p.matcher(token);
			if(token.length() > 1 && !m.find() && Character.isUpperCase(token.charAt(0))){
				i++;
				word.append(token).append(" ");
			}else{
				if(i > 1){
					seqs.append(word.toString().trim());
					seqs.append(System.getProperty("line.separator"));
				}
				word = new StringBuilder();
				i = 0;
			}
		}
		
		if(i > 1){
			seqs.append(word.toString().trim());
			seqs.append(System.getProperty("line.separator"));
		}
	}
}