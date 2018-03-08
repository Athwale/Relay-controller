package relay;


import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Trida zaklada vlakno, ktere ceka jednorazove nebo opakovane dobu z zadanou v zalozce timer a nasledne prepne rele 
 * @author Ondřej Mejzlík
 */
public class ExactTimerThread extends Thread
{
    //Promenna pro tridu prepinani rele
    private RelayFlipper relayFlipper;
    //Promenna indikujici cykleni vzoru
    private boolean loop;
    //Promenna pro ulozeni casu k cekani
    private int timeToWait = 0;
    //Udava nastaveni rozsahu timeru
    private int timerSettings;
    //Promenna pro ulozeni predaneho Gui
    private Gui gui;
    
    /**
     * Konstruktor vlakna ExactTimerThread
     * @param loop promenna indikujici cykleni vzoru
     * @param timeToWait velikost casu k cekani
     * @param relayFlipper instance prepinace rele
     * @param gui graficke rozhrani kde ma timer prepisovat cas
     * @param timerSettings nastaveni timeru. 1 pro sekundy, 2 pro minuty, 3 pro hodiny
     */
    public ExactTimerThread(boolean loop, int timeToWait, RelayFlipper relayFlipper, Gui gui, int timerSettings)
    {
        this.relayFlipper = relayFlipper;
        this.loop = loop;
        this.timeToWait = timeToWait;
        this.timerSettings = timerSettings;
        this.gui = gui;
    }
    
    @Override
    public void run()
    {
        //Cekej zadany cas. Popripade porad dokola dokud nebude vlakno ukonceno
        do
        {
            //Pri startu vlakna nastav na display zadanou hodnotu casu, aktualizuje 
            //se pri psani do radku v gui, ale to se neprojevi pri restartu vlakna bez zadani noveho udaje
            //Pri opakovani cyku nastav do gui do displaye znovu zadanou 
            //hodnotu casu, ta by byla jinak o 1 mensi protoze cyklus 1 odecte
            gui.setDisplayedTime(timeToWait);
            try
            {
                if(timerSettings == 1)
                {
                    for(int i = 1; i <= timeToWait; i++)
                    {
                        TimeUnit.SECONDS.sleep(1);
                        gui.setDisplayedTime(timeToWait - i);    
                    }
                }
                if(timerSettings == 2)
                {
                    for(int i = 1; i <= timeToWait; i++)
                    {
                        TimeUnit.MINUTES.sleep(1);
                        gui.setDisplayedTime(timeToWait - i);    
                    }
                }
                if(timerSettings == 3)
                {
                    for(int i = 1; i <= timeToWait; i++)
                    {
                        TimeUnit.HOURS.sleep(1);
                        gui.setDisplayedTime(timeToWait - i);    
                    }
                }
            }
            //Bylo-li vlakno preruseno, znamena to, ze chceme aby skoncilo, vyskoc z cyklu a ukonci praci vlakna
            catch (InterruptedException e)
            {
                break;
            } 
            //Prepni rele
            relayFlipper.flipRelay();
        } while(loop);
    }
}