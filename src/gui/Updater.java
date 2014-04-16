package gui;

import java.awt.EventQueue;
import java.util.TimerTask;

import org.joda.time.DateTime;

public class Updater extends TimerTask {
	
	private DateTime time;
	
	@Override
    public void run() {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                time = new DateTime();
            }
        });
    }
}
