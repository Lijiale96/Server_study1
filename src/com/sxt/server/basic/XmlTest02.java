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
 * ��ϤSAX��������
 * 
 */
public class XmlTest02 {
  public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	//SAX����
	 //1����ȡ��������
	 SAXParserFactory factory = SAXParserFactory.newInstance();
	 //2���ӽ���������ȡ������
	 SAXParser parse = factory.newSAXParser();
	 //3����д������
	 //4�������ĵ�Documentע�ᴦ����
	 PersonHandler handler= new PersonHandler();
	 //5������
	parse.parse(Thread.currentThread().getContextClassLoader().
			getResourceAsStream("com/sxt/server/basic/p.xml"), handler);
	
	//��ȡ����
	 List<Person> persons =  handler.getPersons();
	 System.out.println(persons.size()+"-->");
	 for (Person p:persons) {
			System.out.println(p.getName()+ "-->" + p.getAge());
		}	
      }
}
class PersonHandler extends DefaultHandler{
	private List <Person> persons;
	private Person person;
	private String tag;//�洢������ǩ
  @Override
    public void startDocument() throws SAXException {
	persons = new ArrayList<Person>();
   }
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (null!=qName) {
			tag = qName;//�洢��ǩ��
			if (tag.equals("person")) {
				person = new Person();
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch,start,length).trim();
		if (null!=tag) { //�����˿�
			if (tag.equals("name")) {
				person.setName(contents);
			}else if(tag.equals("age")){
				if (contents.length()>0) {
					person.setAge(Integer.valueOf(contents));
				}
			}
		}
}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (null!=qName) { //�����˿�
		  if (qName.equals("person")) {
		  	persons.add(person);
		  }
		}
		tag = null;//tag����
	}
	
	@Override
		public void endDocument() throws SAXException {

		}

	public List<Person> getPersons() {
		return persons;
	}
}