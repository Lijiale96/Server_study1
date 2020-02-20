package com.sxt.server.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * 熟悉SAX解析流程
 * 
 */
public class XmlTest03 {
  public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	//SAX解析
	 //1、获取解析工厂
	 SAXParserFactory factory = SAXParserFactory.newInstance();
	 //2、从解析工厂获取解析器
	 SAXParser parse = factory.newSAXParser();
	 //3、编写处理器
	 //4、加载文档Document注册处理器
	 PerHandler handler= new PerHandler();
	 //5、解析
	parse.parse(Thread.currentThread().getContextClassLoader().
			getResourceAsStream("com/sxt/server/basic/p.xml"), handler);
	
	
	//获取数据
	List<Person> persons = handler.getPersons();
	System.out.println(persons.size());
	for (Person p : persons) {
		
		System.out.println(p.getName()+"-->"+p.getAge());
	}
}
}
class PerHandler extends DefaultHandler{
	
	private List<Person> persons;
	private Person person;
	private String tag;

  @Override
public void startDocument() throws SAXException {
	 persons = new ArrayList<Person>();
	 
}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (null!=qName) {
			tag = qName;
			if (tag.equals("person")) {
				person = new Person();
			}			
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch,start,length).trim();
		if (null!=tag) { //
			if (tag.equals("name")) {
				person.setName(contents);
			}else if(tag.equals("age")) {
				if (contents.length()>0) {
					person.setAge(Integer.valueOf(contents));
				}		
			}
		}
	
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	if (null!=qName) { //
	if (qName.equals("person")) {
			persons.add(person);
	 }
		}
	tag=null;
	}
	
	@Override
		public void endDocument() throws SAXException {
	
		}

	public List<Person> getPersons() {
		return persons;
	}


	
}