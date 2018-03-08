package relay;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Trida zaklada vlakno, ktere jednorazove nebo opakovane ceka nahodnou dobu z intervalu 
 * 0 az zadana hodnota a nasledne prepina rele 
 * @author Ondřej Mejzlík
 */
public class RandomTimerThread extends Thread
{
    //Hodnota nejvyssiho cisla, ktere muze generator nahodnych cisel pouzit predana z GUI ze zalozky random
    private int randomMax = 0;
    //Promenna pro tridu prepinani rele
    private RelayFlipper relayFlipper;
    //Jestli ma cyklit
    private boolean loop;
    //Promenna pro generator nahodnych cisel s inicializaci
    private Random randomNumberGenerator;
    
    /**
     * Konstruktor vlakna RandomTimerThread
     * @param randomMax nastavuje maximalni hodnotu generatoru nahodnych cisel
     * @param relayFlipper instance prepinace rele
     * @param loop Urcuje jestli ma vlakno cyklit nebo ne
     * @param randomNumberGenerator inicializovany generator nahodnych cisel
     */
    public RandomTimerThread(boolean loop, int randomMax, RelayFlipper relayFlipper, Random randomNumberGenerator)
    {
        this.loop = loop;
        this.randomMax = randomMax;
        this.relayFlipper = relayFlipper;
        this.randomNumberGenerator = randomNumberGenerator;
    }
    
    @Override
    public void run()
    {
        //Cekej nahodny cas porad jedno nebo porad dokola dokud nebude vlakno ukonceno a prepinej rele
        do
        {
            try 
            {
                // +1 nechceme nikdy 0
                TimeUnit.SECONDS.sleep(randomNumberGenerator.nextInt(randomMax) + 1);
            } 
            catch (InterruptedException e)
            {
                break;
            }
            //Prepni rele
            relayFlipper.flipRelay();
        } while(loop);
    }
}
