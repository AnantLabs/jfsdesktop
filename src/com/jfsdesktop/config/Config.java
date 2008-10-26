package com.jfsdesktop.config;

import java.util.ResourceBundle;


/** <P>Configurações gerais da aplicação. */
public class Config {

	private static ResourceBundle props;
	
	static
	{
		props = ResourceBundle.getBundle("com.jfsdesktop.properties.Config");
	}
	
	public static String getProperty(String key)
	{
		return props.getString(key);
	}
	
}
