package com.lucas.specterlimite.comandos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lucas.specterlimite.api.LimiteAPI;
import com.lucas.specterlimite.cache.Cache;

public class LimiteCommand implements CommandExecutor {
	public static String format(double valor) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
		return decimalFormat.format(valor);
	}

	public static String getFormat(double valor) {
		String[] simbols = new String[] { "", "k", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TD", "QD", "QID", "SD", "SSD", "OD", "ND"};
		int index;
		for (index = 0; valor / 1000.0 >= 1.0; valor /= 1000.0, ++index) {
		}
		return format(valor) + simbols[index];
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		if (!(s instanceof Player)) {
			if (args.length < 2) {
				s.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			Player t = Bukkit.getPlayerExact(args[0]);
			if (t == null) {
				s.sendMessage("§cHey! O jogador " + args[0] + " não está online");
				return false;
			}
			if (!isDouble(args[1])) {
				s.sendMessage("§cHey! O valor " + args[1] + " é invalido.");
				return false;
			}
			Double i = Double.parseDouble(args[1]);
			LimiteAPI.adicionarLimites(t, i);
			s.sendMessage("§aYay! Você adicionou ❂" + args[1] + " de limite ao " + t.getName() + " com sucesso.");
			return true;
		}
		if (!c.getName().equalsIgnoreCase("limite"))
			return false;
		Player p = (Player) s;
		if (args.length < 1) {
			p.sendMessage("§aVocê tem ❂" + getFormat(LimiteAPI.getLimites(p)) + " de limite");
			return true;
		}
		if (args[0].equalsIgnoreCase("ajuda")) {
			if (!p.hasPermission("limite.admin")) {
				p.sendMessage(new String[] { "§f§lREDE §b§lSPECTER §7- §fSistema de Limites", "",
						" §b/limite <jogador> §7- §fVer o limite de um jogador.", "" });
			} else {
				p.sendMessage(new String[] { "§f§lREDE §b§lSPECTER §7- §fSistema de Limites", "",
						" §b/limite <jogador> §7- §fVer o limite de um jogador.",
						" §b/limite remover <jogador> <quantia> §7- §fRemover limite de um jogador.",
						" §b/limite adicionar <jogador> <quantia> §7- §fAdicionar limite a um jogador.",
						" §b/limite setar <jogador> <quantia> §7- §fSeta o limite de um jogador.",
						" §b/limite resetar <jogador> §7- §fReseta o limite de um jogador.", "" });
			}
			return true;
		}
		if (args[0].equalsIgnoreCase("remover")) {
			if (!p.hasPermission("limite.admin")) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			if (args.length < 3) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if (t == null) {
				p.sendMessage("§cHey! O jogador " + args[1] + " não está online");
				return false;
			}
			if (!isDouble(args[2])) {
				p.sendMessage("§cHey! O valor " + args[2] + " é invalido.");
				return false;
			}
			Double i = Double.parseDouble(args[2]);
			LimiteAPI.removerLimites(t, i);
			p.sendMessage("§aYay! Você removeu ❂" + args[2] + " runa(s) de " + t.getName() + " com sucesso.");
			return true;
		}
		if (args[0].equalsIgnoreCase("adicionar")) {
			if (!p.hasPermission("limite.admin")) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			if (args.length < 3) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if (t == null) {
				p.sendMessage("§cHey! O jogador " + args[1] + " não está online");
				return false;
			}
			if (!isDouble(args[2])) {
				p.sendMessage("§cHey! O valor " + args[2] + " é invalido.");
				return false;
			}
			Double i = Double.parseDouble(args[2]);
			LimiteAPI.adicionarLimites(t, i);
			p.sendMessage("§aYay! Você adicionou ❂" + args[2] + " runa(s) ao " + t.getName() + " com sucesso.");
			return true;
		}
		if (args[0].equalsIgnoreCase("resetar")) {
			if (!p.hasPermission("limite.admin")) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			if (args.length < 2) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if (t == null) {
				p.sendMessage("§cHey! O jogador " + args[1] + " não está online");
				return false;
			}
			LimiteAPI.resetarLimites(t);
			p.sendMessage("§aYay! Você resetou o limite de " + t.getName() + " com sucesso.");
			return true;
		}
		if (args[0].equalsIgnoreCase("setar")) {
			if (!p.hasPermission("limite.admin")) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			if (args.length < 3) {
				p.sendMessage("§cHey! Utilize /limite ajuda para obter ajuda!");
				return false;
			}
			Player t = Bukkit.getPlayerExact(args[1]);
			if (t == null) {
				p.sendMessage("§cHey! O jogador " + args[1] + " não está online");
				return false;
			}
			if (!isDouble(args[2])) {
				p.sendMessage("§cHey! O valor " + args[2] + " é invalido.");
				return false;
			}
			Double i = Double.parseDouble(args[2]);
			LimiteAPI.setarLimites(t, i);
			p.sendMessage("§aYay! Você setou a conta de " + t.getName() + " para ❂" + i + " com sucesso.");
			return true;
		}
		Player t = Bukkit.getPlayerExact(args[0]);
		if (t == null) {
			p.sendMessage("§cHey! O jogador " + args[0] + " não está online");
			return false;
		}
		if (t == p) {
			p.sendMessage("§aSeu limite é de ❂" + getFormat(LimiteAPI.getLimites(p)));
			return true;
		}
		p.sendMessage(
				"§aLimite de §c" + t.getName() + "§c: §a❂ " + getFormat(Cache.jogadores.get(t).getLimites()));
		return true;
	}

	private boolean isDouble(String s) {
		try {
			if (Double.parseDouble(s) >= 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
