package com.lucas.specterlimite.api;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemBuilder {

	private ItemStack item;

	public ItemBuilder(ItemStack item) {
		this.item = item;
	}

	public ItemBuilder(String nickname, int a) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(nickname);
		skull.setItemMeta(meta);
		item = skull;
	}

	public ItemBuilder(String url) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.getEncoder()
				.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = itemMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(itemMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		item.setItemMeta(itemMeta);
		this.item = item;
	}

	public ItemBuilder(Material type) {
		this(new ItemStack(type));
	}

	public ItemBuilder(Material type, int amount) {
		this(new ItemStack(type, 1));
	}

	public ItemBuilder(Material type, int amount, int meta) {
		this(new ItemStack(type, 1, (short) meta));
	}

	public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
		consumer.accept(item);
		return this;
	}

	public ItemBuilder changeItemMeta(Consumer<ItemMeta> consumer) {
		ItemMeta itemMeta = item.getItemMeta();
		consumer.accept(itemMeta);
		item.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder name(String name) {
		return changeItemMeta(it -> it.setDisplayName(name));
	}

	public ItemBuilder setLore(String... lore) {
		return changeItemMeta(it -> it.setLore(Arrays.asList(lore)));
	}

	public ItemBuilder setLore(List<String> lore) {
		return changeItemMeta(it -> it.setLore(lore));
	}

	public ItemStack build() {
		return item;
	}

}