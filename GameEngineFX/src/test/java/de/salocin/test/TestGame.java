package de.salocin.test;

import de.salocin.gameenginefx.Scheduler;
import de.salocin.gameenginefx.plugin.CorePlugin;

public class TestGame extends CorePlugin {
	
	public static TestGame instance;
	
	public TestGame() {
		instance = this;
	}
	
	@Override
	protected void onEnable() {
		Scheduler.getInstance().setState(new TestState());
	}
	
	@Override
	protected void onDisable() {
		// TODO Auto-generated method stub
		
	}
	
}
