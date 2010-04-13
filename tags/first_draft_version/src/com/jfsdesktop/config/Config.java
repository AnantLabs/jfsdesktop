package com.jfsdesktop.config;

import java.util.ResourceBundle;


/** <P>Configura��es gerais da aplica��o. */
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
