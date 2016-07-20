package de.salocin.gameenginefx.util.error;

public enum ExceptionCode {
	
	NONE(0), PLUGIN_CONSTRUCTOR_ERROR(1), GAME_STARTER_ERROR(2), GAME_STAGE_ERROR(3);
	
	public final int id;
	
	private ExceptionCode(int id) {
		this.id = id;
	}
	
}
