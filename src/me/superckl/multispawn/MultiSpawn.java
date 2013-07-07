package me.superckl.multispawn;

import org.bukkit.plugin.java.JavaPlugin;

public class MultiSpawn extends JavaPlugin{
	
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(new SpawnWorld(), this);
	}
}
