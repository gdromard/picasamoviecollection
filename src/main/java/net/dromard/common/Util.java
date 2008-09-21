/*
 * Created on 3 mai 07
 * By Gabriel DROMARD
 */
package net.dromard.common;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Util {
    
    private Util() {
        // Private constructor because it is an util class.
    }
    
    public static ArrayList<String> IMAGES_EXTENTIONS = new ArrayList<String>();
    static {
        IMAGES_EXTENTIONS.add("gif");
        IMAGES_EXTENTIONS.add("jpg");
        IMAGES_EXTENTIONS.add("jpeg");
    }

    /**
     * Retreive a resource from classpath.
     * @param name The resource name.
     * @return The URL of the resource, null if not found.
     */
    public static URL getResource(String name) {
        URL url = null;
        try {
            if (name.startsWith("/")) name = name.substring(1);
            
            url = ClassLoader.getSystemResource(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
    
    /**
     * Return the same string with the first letter transformed into upper case.
     * @param sentence The sentence to format.
     * @return The formated result.
     */
    public static String setFirstLetterUpperCase(String sentence) {
        if (sentence == null) return null;
        return sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
    }

    /**
     * Return the same string with the first letter transformed into upper case.
     * @param sentence The sentence to format.
     * @return The formated result.
     */
    public static String setFirstLetterUpperCaseOfEachWord(String sentence) {
        if (sentence == null) return null;
        String rsult = "";
        String[] words = sentence.split(" ");
        for(int i=0; i<words.length; i++) {
            rsult += words[i].substring(0, 1).toUpperCase() + words[i].substring(1) + " ";
        }
        return rsult.substring(0, rsult.length()-1);
    }

    /**
     * Load an image from classpath.
     * @param name The image resource name.
     * @return The image or null if not found.
     */
    public static Image loadImage(String name) {
        Image image = null;
        URL url = getResource(name);

        if (url != null) image = new ImageIcon(url).getImage();
        else System.err.println("[Util.LoadImage] Unable to load the picture " + name);
        return image;
    }
}
