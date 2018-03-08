package relay;


import com.sun.media.sound.JavaSoundAudioClip;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Trida slouzi k prepnuti rele pomoci prehrani zvuku
 * @author Ondřej Mejzlík
 */
public class RelayFlipper 
{
    //Novy zvukovy klip
    private JavaSoundAudioClip flipSound;
    
    /**
     * Inicializace zvukoveho zaznamu pro spinani rele.
     * V pripade vyjimky vypise IOException do notifikacnich poli okna
     */
    public RelayFlipper()
    {
        try 
        {
            //Vyrobeni input stream tak, aby sel cist po zabaleni do .jar
            InputStream is = getClass().getResourceAsStream("tap.wav");
            flipSound = new JavaSoundAudioClip(is);
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "tap.wav - signal to flip the relay not found", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    /**
     * Prehraje zvuk ktery slouzi pro prepnuti rele
     */
    public void flipRelay() 
    {
        //Prehraj zvuk znovu
        flipSound.play();
    }
    
    /**
     * Zastavi prehravani zvuku
     */
    public void stop()
    {
        flipSound.stop();
    }
}
