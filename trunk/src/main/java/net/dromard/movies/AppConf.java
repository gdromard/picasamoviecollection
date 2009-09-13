package net.dromard.movies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.dromard.common.swing.SwingPropertiesHelper;
import net.dromard.common.util.StringHelper;

public final class AppConf implements AppConstants {
	
	private static AppConf singleton = new AppConf();
	private ResourceBundle conf = ResourceBundle.getBundle("application");

	public static AppConf getInstance() {
		return singleton;
	}

	/**
	 * Retrieve a property value.
	 * @param name The property name
	 * @return The property value
	 */
	public String getProperty(String key) {
		return getProperty(key, null);
	}

	/**
	 * Retrieve a property value.
	 * @param name The property name
	 * @param defaultValue The default value to be used if no values has been found.
	 * @return The property value
	 */
	public String getProperty(String key, String defaultValue) {
		String value = null;
		try {
			value = conf.getString(key);
			if (value == null) {
				value = defaultValue;
			} else {
				value = transform(value);
			}
		} catch (MissingResourceException e) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Retrieve a property integer value.
	 * @param name The property name
	 * @param defaultValue The default value to be used if no values has been found.
	 * @return The property value
	 */
	public int getProperty(String key, int defaultValue) {
		return Integer.parseInt(getProperty(key, "" + defaultValue));
	}

	/**
	 * Retrieve and transform the property value into a Color.
	 * @param name The property name
	 * @return The corresponding color
	 */
	public Color getPropertyAsColor(String name) {
		return SwingPropertiesHelper.asColor(getProperty(name));
	}

	/**
	 * Retrieve and transform the property value into a Dimension.
	 * The comma is used to distinguish x from y.
	 * @param name The property name
	 * @return The dimension if the value is valid
	 * @throws IOException If an error occurred while accessing the properties file.
	 */
	public Dimension getPropertyAsDimension(String name) {
		return SwingPropertiesHelper.asDimension(getProperty(name));
	}

	/**
	 * Retrieve and transform the property value into a Font.
	 * @param name The property name
	 * @return A valid font (or null if invalid)
	 * @throws IOException If an error occurred while accessing the properties file.
	 */
	public Font getPropertyAsFont(String name) {
		return SwingPropertiesHelper.asFont(getProperty(name));
	}

    /**
     * Retreive a property using a formatter.
     * @see java.util.Properties#getProperty(java.lang.String)
     * @see java.text.MessageFormat#format(java.lang.String, java.lang.Object[])
     * @param key       The property key.
     * @param arguments The MessageFormat arguments.
     * @return The property value formated.
     */
    public final String getFormatedProperty(final String key, final Object[] arguments) {
        return MessageFormat.format(transform(getProperty(key)), arguments);
    }

    /**
     * This method is in charge of retreiving values of internaly referenced keys.
     * @param propertyValue The propertyValue to be parsed and resolved.
     * @return The propertyValue with inner references to others keys resolved.
     */
    private String transform(final String propertyValue) {
        String result = propertyValue;
        if (propertyValue != null) {
            while (result.indexOf("$(") != -1) {
                String intermediateResult = result;
                String key = intermediateResult.substring(intermediateResult.indexOf("$(") + 2);
                key = key.substring(0, key.indexOf(')'));
                String value = getProperty(key);
                // Check intermediate key value
                if (value != null && !propertyValue.equals(value)) {
                    result = StringHelper.replaceAll(result, "$(" + key.toString() + ")", value);
                }
                // Check that we modify some thing so as to prevent loop
                if (intermediateResult.equals(result)) {
                    return result;
                }
            }
            while (result.indexOf("${") != -1) {
                String intermediateResult = result;
                String key = intermediateResult.substring(intermediateResult.indexOf("${") + 2);
                key = key.substring(0, key.indexOf('}'));
                String value = getProperty(key);
                // Check intermediate key value
                if (value != null && !propertyValue.equals(value)) {
                    result = StringHelper.replaceAll(result, "${" + key.toString() + "}", value);
                }
                // Check that we modify some thing so as to prevent loop
                if (intermediateResult.equals(result)) {
                    return result;
                }
            }
        }
        return result;
    }
}
