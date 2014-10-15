/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsclient.coregui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author ben
 */
public class Style {
    
    public static final Color DEFAULT_GRAY = new Color(51,51,51);
    
    public final Color backgroundColor;
    public final Color foregroundColor;
    public final Font font;
    
    public Style(Color foregroundColor, Color backgroundColor, Font font){
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.font = font;       
    }
    
    public static Style getDefaultStyle(){
       
        return new Style(Color.white, DEFAULT_GRAY, new Font(new JLabel().getFont().getFontName(), Font.PLAIN, new JLabel().getFont().getSize() + 2));
    }
    
}
