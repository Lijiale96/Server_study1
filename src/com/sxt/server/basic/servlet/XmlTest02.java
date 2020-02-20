package com.sxt.server.basic.servlet;

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
  public static void main(String[] args) throws Exception {
	//SAX����
	 //1����ȡ��������
	 SAXParserFactory factory = SAXParserFactory.newInstance();
	 //2���ӽ���������ȡ������
	 SAXParser parse = factory.newSAXParser();
	 //3����д������
	 //4�������ĵ�Documentע�ᴦ����
	 WebHandler handler= new WebHandler();
	 //5������
	parse.parse(Thread.currentThread().getContextClassLoader().
			getResourceAsStream("com/sxt/server/basic/servlet/web.xml"), handler);
	
	//��ȡ����
	WebContext context = new WebContext(handler.getEntitys(),handler.getMappings());
	//������������ /login
	String className = context.getClz("/login");
	Class clz = Class.forName(className);
	 Servlet servlet = (Servlet)clz.getConstructor().newInstance();
     System.out.println(servlet);
	 servlet.service();
       }
}
class WebHandler extends DefaultHandler{
	private List<Entity> entitys= new ArrayList<Entity>();
	private List<Mapping> mappings = new ArrayList<Mapping>();
	private Entity entity;
	private Mapping mapping;
	private String tag;//�洢������ǩ
	private boolean isMapping = false;
	
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (null!=qName) {
			tag = qName;//�洢��ǩ��
			if (tag.equals("servlet")) {
				entity = new Entity();
				isMapping= false;
			}else if (tag.equals("servlet-mapping")) {
				mapping = new Mapping();
				isMapping = true;
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch,start,length).trim();
		if (null!=tag) { //�����˿�
			if (isMapping) {//����servlet-mapping
				if (tag.equals("servlet-name")) {
					mapping.setName(contents);
				}else if(tag.equals("url-pattern")){			
					mapping.addPattern(contents);		
			   }
			}else {//����servlet
				if (tag.equals("servlet-name")) {
					entity.setName(contents);
				}else if(tag.equals("servlet-class")){			
					entity.setClz(contents);		
			}
		}			
	}
}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (null!=qName) { //�����˿�
		  if (qName.equals("servlet")) {
		    entitys.add(entity);
		  }else if (qName.equals("servlet-mapping")) {
			  mappings.add(mapping);
		  }
		}
		tag = null;//tag����
	}
	public List<Entity> getEntitys() {
		return entitys;
	}


	public List<Mapping> getMappings() {
		return mappings;
	}
}