package com.lucas.specterlimite.listeners;

import com.lucas.specterlimite.cache.Cache;
import com.lucas.specterlimite.connection.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lucas.specterlimite.objetos.LimitePlayer;

public class JoinEventRunas implements Listener {
	@EventHandler
	public void alo(PlayerJoinEvent e) {
		if (!Database.hasJogador(e.getPlayer())) {
			Database.addJogador(e.getPlayer());
		}
		Cache.jogadores.put(e.getPlayer(), new LimitePlayer(e.getPlayer()));
	}

	@EventHandler
	public void quitevent(PlayerQuitEvent e) {
		if (Cache.jogadores.containsKey(e.getPlayer())) {
			Cache.jogadores.get(e.getPlayer()).saveDatabase();
			Cache.jogadores.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void kickevent(PlayerKickEvent e) {
		if (Cache.jogadores.containsKey(e.getPlayer())) {
			Cache.jogadores.get(e.getPlayer()).saveDatabase();
			Cache.jogadores.remove(e.getPlayer());
		}
	}
}
