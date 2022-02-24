package com.lucas.specterlimite.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucas.specterlimite.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lucas.specterlimite.console.AlertaConsole;
import com.lucas.specterlimite.console.AlertaConsole.AlertaNivel;

public class Database {
	public static Connection con = null;

	public static void openConnectionSQLite() {
		File file = new File(Main.getInstance().getDataFolder(), "database.db");
		String URL = "jdbc:sqlite:" + file;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(URL);
			criarTabela();
			Bukkit.getConsoleSender().sendMessage("§a[SpecterLimite] §aConexão com o §fSQLite §asucedida!");
		} catch (Exception e) {
			Bukkit.getConsoleSender()
					.sendMessage("§a[SpecterLimite] §cConexão com o §fSQLite §cfalhou, desabilitando plugin!");
			Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
		}
	}

	public static void close() {
		if (con != null) {
			try {
				con.close();
				con = null;
				Bukkit.getConsoleSender()
						.sendMessage("§a[SpecterLimite] §aConexão com o banco de dados foi fechada.");
			} catch (SQLException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender()
						.sendMessage("§a[SpecterLimite] §cNão foi possível fechar a conexão com o banco de dados.");
			}
		}
	}

	public static void criarTabela() {
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS `LimitePlayer` (`player` VARCHAR(24) NULL, `uuid` VARCHAR(45) NULL, `limite` DOUBLE NULL);");
			st.executeUpdate();			
			Bukkit.getConsoleSender()
					.sendMessage("§a[SpecterLimite] §6Tabela §f`LimitePlayer` §6criada/carregada com sucesso");
		} catch (SQLException e) {
			Bukkit.getConsoleSender()
					.sendMessage("§a[SpecterLimite] §cNão foi possivel criar a tabela §f`LimitePlayer`");
			Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
			e.printStackTrace();
		}
	}

	public static boolean hasJogador(Player p) {
		String uuid = p.getUniqueId().toString();
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("SELECT * FROM `LimitePlayer` WHERE `uuid` = ?");
			st.setString(1, uuid);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public static Double getJogador(Player p, String path) {
		String uuid = p.getUniqueId().toString();
		if (hasJogador(p)) {
			PreparedStatement stm = null;
			try {
				stm = con.prepareStatement("SELECT * FROM `LimitePlayer` WHERE `uuid` = ?");
				stm.setString(1, uuid);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					return rs.getDouble(path);
				}
				return 0.0;
			} catch (SQLException e) {
				return 0.0;
			}
		} else {
			addJogador(p);
			return 0.0;
		}
	}

	public static void setJogador(Player p, double nivel) {
		String uuid = p.getUniqueId().toString();
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("UPDATE `LimitePlayer` SET `limite` = ? WHERE `uuid` = ?");
			st.setDouble(1, nivel);
			st.setString(2, uuid);
			st.executeUpdate();
		} catch (SQLException e) {
			AlertaConsole.c("Não foi possível atualizar o jogador " + p.getName() + " na database", Main.getInstance(), AlertaNivel.ERRO);
		}
	}

	public static void addJogador(Player p) {
		String uuid = p.getUniqueId().toString();
		String nick = p.getName();
		PreparedStatement st = null;
		try {
			st = con.prepareStatement("INSERT INTO `LimitePlayer`(`player`, `uuid`, `limite`) VALUES (?,?,?)");
			st.setString(1, nick);
			st.setString(2, uuid);
			st.setDouble(3, 100);			
			st.executeUpdate();
		} catch (SQLException e) {
			AlertaConsole.c("Não foi possível inserir o jogador " + p.getName() + " na database", Main.getInstance(), AlertaNivel.ERRO);
		}
	}
}