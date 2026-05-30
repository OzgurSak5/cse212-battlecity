package edu.yeditepe.cse212.battlecity.powerup;

public class PowerUpEffectThread implements Runnable{

	private long durationMs;
	private Runnable onExpire;
	
	public PowerUpEffectThread(long durationMs, Runnable onExpire) {
		this.durationMs = durationMs;
		this.onExpire = onExpire;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(durationMs);
			onExpire.run();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
