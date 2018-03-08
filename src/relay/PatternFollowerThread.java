package relay;


import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Trida zaklada vlakno, ktere prepina rele podle predaneho vzoru slozeneho ze znaku 0 a 1
 * @author Ondřej Mejzlík
 */
public class PatternFollowerThread extends Thread
{
    //Promenna pro tridu prepinani rele
    private RelayFlipper relayFlipper;
    //Promenna pro ulozeni vzorce prepinani rele
    private String pattern;
    //Promenna indikujici cykleni vzoru
    private boolean loop;
    
    /**
     * Konstruktor vlakna PatternFollowerThread
     * @param loop promenna indikujici cykleni vzoru
     * @param pattern promenna pro ulozeni vzorce prepinani rele
     * @param relayFlipper instance prepinace rele
     */
    public PatternFollowerThread(boolean loop, String pattern, RelayFlipper relayFlipper)
    {
        this.relayFlipper = relayFlipper;
        this.pattern = pattern;
        this.loop = loop;
    }
    
    @Override
    public void run()
    {
        //Provadej vzorec prepinani rele porad dokola pokud je nastaveno loop = true
        do
        {
            try
            {
                //Prochazej vzorec prepinani rele a prepinej rele nebo cekej
                for(int i = 0; i < pattern.length(); i++)
                {
                    //Na znak 0 cekej
                    if(pattern.charAt(i) == '0')
                    {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    //Na znak 1 prepni
                    else if(pattern.charAt(i) == '1')
                    {
                        //Prepni rele
                        relayFlipper.flipRelay();
                        //Ochranne cekani 1s, rele neumi reagovat spolehlive tak rychle
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            }
            //Bylo-li vlakno preruseno, znamena to, ze chceme aby skoncilo, vyskoc z cyklu a ukonci praci vlakna
            catch (InterruptedException e)
            {
                break;
            } 
        } while(loop);
    }
}