package com.lucas.specterlimite;

import com.lucas.specterlimite.cache.Cache;
import com.lucas.specterlimite.comandos.LimiteCommand;
import com.lucas.specterlimite.connection.Database;
import com.lucas.specterlimite.console.AlertaConsole;
import com.lucas.specterlimite.listeners.JoinEventRunas;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.lucas.specterlimite.objetos.LimitePlayer;

public class Main extends JavaPlugin {
	static Main m;

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§7==========================");
		Bukkit.getConsoleSender().sendMessage("§7| §bSpecterLimite          §7|");
		Bukkit.getConsoleSender().sendMessage("§7| §bVersão 1.0             §7|");
		Bukkit.getConsoleSender().sendMessage("§7| §fStatus: §aLigado       §7|");
		Bukkit.getConsoleSender().sendMessage("§7==========================");
		Bukkit.getConsoleSender().sendMessage("");
		m = this;
		saveDefaultConfig();
		Database.openConnectionSQLite();
		getCommand("limite").setExecutor(new LimiteCommand());
		Bukkit.getPluginManager().registerEvents(new JoinEventRunas(), this);
		Bukkit.getOnlinePlayers().forEach(p -> {
			Cache.jogadores.put(p, new LimitePlayer(p));
		});
	}

	public void onDisable() {
		if (!Cache.jogadores.isEmpty()) {
			Cache.jogadores.keySet().forEach(p -> {
				Cache.jogadores.get(p).saveDatabase();
			});
		}
		Database.close();
	}

	public static Main getInstance() {
		return m;
	}
}
