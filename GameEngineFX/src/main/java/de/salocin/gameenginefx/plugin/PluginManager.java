package de.salocin.gameenginefx.plugin;

import java.util.HashSet;

import de.salocin.gameenginefx.core.GameStarter;
import de.salocin.gameenginefx.util.error.ExceptionCode;
import de.salocin.gameenginefx.util.error.ReportedException;

public class PluginManager {
	
	private static PluginManager instance;
	private static CorePlugin corePlugin;
	private final GameStarter starter;
	private HashSet<Plugin> plugins = new HashSet<Plugin>();
	
	private PluginManager(GameStarter starter) {
		this.starter = starter;
	}
	
	public static void init(GameStarter starter) {
		init(starter, true);
	}
	
	public static void init(GameStarter starter, boolean startCorePlugin) {
		if (instance == null) {
			instance = new PluginManager(starter);
			
			if (startCorePlugin) {
				CorePlugin plugin = null;
				plugin = newInstance(starter.getCorePluginClass());
				
				if (plugin == null) {
					System.exit(ExceptionCode.PLUGIN_CONSTRUCTOR_ERROR.id);
				} else {
					setCorePlugin(plugin);
				}
			}
		} else {
			throw new RuntimeException("PluginManager is already initialized.");
		}
	}
	
	public static PluginManager getInstance() {
		return instance;
	}
	
	public static CorePlugin getCorePlugin() {
		return corePlugin;
	}
	
	public static void setCorePlugin(CorePlugin plugin) {
		if (plugin == null) {
			throw new IllegalArgumentException("CorePlugin can not be null.");
		}
		
		if (corePlugin != null) {
			corePlugin.disable(false);
		}
		
		corePlugin = plugin;
		corePlugin.enable();
	}
	
	public GameStarter getStarter() {
		return starter;
	}
	
	public static <T extends Plugin> T newInstance(Class<T> pluginClass) {
		try {
			return pluginClass.newInstance();
		} catch (Exception e) {
			new ReportedException(ExceptionCode.PLUGIN_CONSTRUCTOR_ERROR, "The plugin has to contain an empty constructor.", e).printStackTrace();
			return null;
		}
	}
	
	public <T extends Plugin> T load(Class<T> pluginClass) {
		return load(newInstance(pluginClass));
	}
	
	public <T extends Plugin> T load(T plugin) {
		Class<?> pluginClass = plugin.getClass();
		
		if (CorePlugin.class.isAssignableFrom(pluginClass)) {
			throw new IllegalArgumentException("You have to extend the SimplePlugin class.");
		} else {
			if (plugins.contains(plugin)) {
				throw new IllegalArgumentException("Plugin already loaded.");
			}
			
			plugins.add(plugin);
			plugin.enable();
			return plugin;
		}
	}
	
	public HashSet<Plugin> getPlugins() {
		return plugins;
	}
	
}
