package com.darknoe.soap;

public class SoapProperty {
	private String AttributeName;
	private String AttributeValue;
	
	public SoapProperty(){}
	
	public SoapProperty(String AttributeName, String AttributeValue){
		this.AttributeName = AttributeName;
		this.AttributeValue = AttributeValue;
	}

	public String getAttributeName() {
		return AttributeName;
	}

	public void setAttributeName(String attributeName) {
		AttributeName = attributeName;
	}

	public String getAttributeValue() {
		return AttributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		AttributeValue = attributeValue;
	}

}
