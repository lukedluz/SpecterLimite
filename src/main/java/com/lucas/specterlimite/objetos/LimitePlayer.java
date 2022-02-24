package com.lucas.specterlimite.objetos;

import com.lucas.specterlimite.connection.Database;
import org.bukkit.entity.Player;

public class LimitePlayer {
	Player p;
	double limites;

	public LimitePlayer(Player p) {
		this.p = p;
		this.limites = Database.getJogador(p, "limite");
	}

	public LimitePlayer(Player p, Double limites) {
		this.p = p;
		this.limites = limites;
	}

	public Double getLimites() {
		return limites;
	}

	public void saveDatabase() {
		Database.setJogador(p, limites);
	}
}
