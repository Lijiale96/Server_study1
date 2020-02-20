package com.sxt.server.basic;

import java.io.IOException;
import java.util.jar.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * ��ϤSAX��������
 * 
 */
public class XmlTest01 {
  public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	//SAX����
	 //1����ȡ��������
	 SAXParserFactory factory = SAXParserFactory.newInstance();
	 //2���ӽ���������ȡ������
	 SAXParser parse = factory.newSAXParser();
	 //3����д������
	 //4�������ĵ�Documentע�ᴦ����
	 PHandler handler= new PHandler();
	 //5������
	parse.parse(Thread.currentThread().getContextClassLoader().
			getResourceAsStream("com/sxt/server/basic/p.xml"), handler);
}
}
class PHandler extends DefaultHandler{
	
  @Override
public void startDocument() throws SAXException {
	  System.out.println("------�����ĵ���ʼ------");
}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println(qName+"-->������ʼ");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch,start,length).trim();
		if (contents.length()>0) {
			System.out.println("����Ϊ->"+contents);
		}else {
			System.out.println("����Ϊ->"+"��");
		}
		System.out.println("����Ϊ"+contents);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println(qName+"-->��������");
	}
	
	@Override
		public void endDocument() throws SAXException {
		System.out.println("------�����ĵ�����------");
		}
}