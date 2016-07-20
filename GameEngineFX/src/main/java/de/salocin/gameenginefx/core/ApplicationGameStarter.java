package de.salocin.gameenginefx.core;

public abstract class ApplicationGameStarter extends GameStage implements GameStarter {
	
	@Override
	public Type getType() {
		return Type.APPLICATION;
	}
	
}
