package juanhg.eog.machine.listener;

import java.util.EventListener;

public interface IEOG extends EventListener  {
	public abstract void rightEvent();
	public abstract void leftEvent();
	public abstract void upEvent();
	public abstract void downEvent();
	public abstract void blinkEvent();
}
