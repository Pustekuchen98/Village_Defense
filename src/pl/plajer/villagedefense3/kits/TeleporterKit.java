package pl.plajer.villagedefense3.kits;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import pl.plajer.villagedefense3.Main;
import pl.plajer.villagedefense3.arena.Arena;
import pl.plajer.villagedefense3.arena.ArenaRegistry;
import pl.plajer.villagedefense3.handlers.ChatManager;
import pl.plajer.villagedefense3.handlers.PermissionsManager;
import pl.plajer.villagedefense3.kits.kitapi.KitRegistry;
import pl.plajer.villagedefense3.kits.kitapi.basekits.PremiumKit;
import pl.plajer.villagedefense3.user.UserManager;
import pl.plajer.villagedefense3.utils.ArmorHelper;
import pl.plajer.villagedefense3.utils.Util;
import pl.plajer.villagedefense3.utils.WeaponHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tom on 18/08/2014.
 */
public class TeleporterKit extends PremiumKit implements Listener {

    private Main plugin;

    public TeleporterKit(Main plugin) {
        this.plugin = plugin;
        setName(ChatManager.colorMessage("Kits.Teleporter.Kit-Name"));
        List<String> description = Util.splitString(ChatManager.colorMessage("Kits.Teleporter.Kit-Description"), 40);
        this.setDescription(description.toArray(new String[description.size()]));
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        KitRegistry.registerKit(this);
    }

    @Override
    public boolean isUnlockedByPlayer(Player player) {
        return player.hasPermission(PermissionsManager.getVip()) || player.hasPermission(PermissionsManager.getMvp()) || player.hasPermission(PermissionsManager.getElite()) || player.hasPermission("villagedefense.kit.teleporter");
    }

    @Override
    public void giveKitItems(Player player) {
        ArmorHelper.setArmor(player, ArmorHelper.ArmorType.GOLD);
        player.getInventory().addItem(WeaponHelper.getUnBreakingSword(WeaponHelper.ResourceType.STONE, 10));

        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
        player.getInventory().addItem(new ItemStack(Material.SADDLE));
        ItemStack enderpealteleporter = new ItemStack(Material.GHAST_TEAR);
        List<String> teleporationlore = Util.splitString(ChatManager.colorMessage("Kits.Teleporter.Game-Item-Lore"), 40);
        this.setItemNameAndLore(enderpealteleporter, ChatManager.colorMessage("Kits.Teleporter.Game-Item-Name"), teleporationlore.toArray(new String[teleporationlore.size()]));
        player.getInventory().addItem(enderpealteleporter);
    }

    @Override
    public Material getMaterial() {
        return Material.ENDER_PEARL;
    }

    @Override
    public void reStock(Player player) {}

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Arena arena = ArenaRegistry.getArena(e.getPlayer());
            if(arena == null || e.getPlayer().getItemInHand() == null || !e.getPlayer().getItemInHand().hasItemMeta() ||
                    !e.getPlayer().getItemInHand().getItemMeta().hasDisplayName())
                return;
            if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatManager.colorMessage("Kits.Teleporter.Game-Item-Name"))) {
                Inventory inventory = plugin.getServer().createInventory(null, 18, ChatManager.colorMessage("Kits.Teleporter.Game-Item-Menu-Name"));
                for(Player player : e.getPlayer().getWorld().getPlayers()) {
                    if(ArenaRegistry.getArena(player) != null && !UserManager.getUser(player.getUniqueId()).isFakeDead()) {
                        ItemStack skull = new ItemStack(397, 1, (short) 3);
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwner(player.getName());
                        meta.setDisplayName(player.getName());
                        meta.setLore(Collections.singletonList(""));
                        skull.setItemMeta(meta);
                        inventory.addItem(skull);
                    }
                }
                for(Villager villager : arena.getVillagers()) {
                    ItemStack villagerItem = new ItemStack(Material.EMERALD);
                    this.setItemNameAndLore(villagerItem, villager.getCustomName(), new String[]{villager.getUniqueId().toString()});
                    inventory.addItem(villagerItem);
                }
                e.getPlayer().openInventory(inventory);
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Arena arena = ArenaRegistry.getArena(p);
        if(arena == null || e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName() ||
                !e.getCurrentItem().getItemMeta().hasLore())
            return;
        if(e.getInventory().getName().equalsIgnoreCase(ChatManager.colorMessage("Kits.Teleporter.Game-Item-Menu-Name"))) {
            e.setCancelled(true);
            if((e.isLeftClick() || e.isRightClick())) {
                if(e.getCurrentItem().getType() == Material.EMERALD) {
                    for(Villager villager : arena.getVillagers()) {
                        if(villager.getCustomName() == null) {
                            villager.remove();
                        }
                        if(villager.getCustomName().equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName()) && villager.getUniqueId().toString().equalsIgnoreCase(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0)))) {
                            e.getWhoClicked().teleport(villager.getLocation());
                            if(plugin.is1_9_R1() || plugin.is1_11_R1() || plugin.is1_12_R1()) {
                                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
                            } else {
                                p.getWorld().playSound(p.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1, 1);
                            }
                            p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 30);
                            p.sendMessage(ChatManager.colorMessage("Kits.Teleporter.Teleported-To-Villager"));
                            return;
                        }
                    }
                    p.sendMessage(ChatManager.colorMessage("Kits.Teleporter.Villager-Warning"));
                } else { /*if(e.getCurrentItem().getType() == Material.SKULL_ITEM || e.getCurrentItem().getType() == Material.SKULL)*/
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    for(Player player : arena.getPlayers()) {
                        if(player.getName().equalsIgnoreCase(meta.getDisplayName()) || ChatColor.stripColor(meta.getDisplayName()).contains(player.getName())) {
                            p.sendMessage(ChatManager.formatMessage(arena, ChatManager.colorMessage("Kits.Teleporter.Teleported-To-Player"), player));
                            p.teleport(player);
                            if(plugin.is1_9_R1() || plugin.is1_11_R1() || plugin.is1_12_R1()) {
                                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
                            } else {
                                p.getWorld().playSound(p.getLocation(), Sound.valueOf("ENDERMAN_TELEPORT"), 1, 1);
                            }
                            p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 30);
                            p.closeInventory();
                            e.setCancelled(true);
                            return;
                        }
                    }
                    p.sendMessage(ChatManager.colorMessage("Kits.Teleporter.Player-Not-Found"));
                }
            }
        }
    }

}
