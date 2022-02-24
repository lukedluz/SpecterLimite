package com.lucas.specterlimite.api;

import com.lucas.specterlimite.Main;
import com.lucas.specterlimite.cache.Cache;
import com.lucas.specterlimite.objetos.LimitePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LimiteAPI {
	public static Double getLimites(Player p)
	{
		return Cache.jogadores.get(p).getLimites();
	}

	public static void adicionarLimites(Player p, Double quantidade) {
		double limites = getLimites(p) + quantidade;
		Cache.jogadores.replace(p, new LimitePlayer(p, limites));
	}

	public static void removerLimites(Player p, Double quantidade) {
		double limites = getLimites(p) - quantidade;
		Cache.jogadores.replace(p, new LimitePlayer(p, limites));
	}

	public static void setarLimites(Player p, Double quantidade) {
		double limites = quantidade;
		Cache.jogadores.replace(p, new LimitePlayer(p, limites));
	}

	public static void resetarLimites(Player p) {
		Cache.jogadores.replace(p, new LimitePlayer(p, 1.0));
	}
}
