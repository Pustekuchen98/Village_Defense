package pl.plajer.villagedefense3.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.plajer.villagedefense3.Main;
import pl.plajer.villagedefense3.arena.Arena;
import pl.plajer.villagedefense3.handlers.ConfigurationManager;

/**
 * Created by Tom on 15/06/2015.
 */
public class SetupInventory {

    private Inventory inventory;
    private Main plugin = JavaPlugin.getPlugin(Main.class);

    public SetupInventory(Arena arena) {
        this.inventory = Bukkit.createInventory(null, 9 * 2, "Arena: " + arena.getID());

        addItem(new ItemBuilder(new ItemStack(Material.REDSTONE_BLOCK))
                .name(ChatColor.GOLD + "► Set" + ChatColor.RED + " ending " + ChatColor.GOLD + "location")
                .lore(ChatColor.GRAY + "Click to set the ending location")
                .lore(ChatColor.GRAY + "on the place where you are standing.")
                .lore(ChatColor.DARK_GRAY + "(location where players will be teleported")
                .lore(ChatColor.DARK_GRAY + "after the game)")
                .lore(isOptionDoneBool("instances." + arena.getID() + ".Endlocation"))
                .build());
        addItem(new ItemBuilder(new ItemStack(Material.LAPIS_BLOCK))
                .name(ChatColor.GOLD + "► Set" + ChatColor.WHITE + " lobby " + ChatColor.GOLD + "location")
                .lore(ChatColor.GRAY + "Click to set the lobby location")
                .lore(ChatColor.GRAY + "on the place where you are standing")
                .lore(isOptionDoneBool("instances." + arena.getID() + ".lobbylocation"))
                .build());

        addItem(new ItemBuilder(new ItemStack(Material.EMERALD_BLOCK))
                .name(ChatColor.GOLD + "► Set" + ChatColor.YELLOW + " starting " + ChatColor.GOLD + "location")
                .lore(ChatColor.GRAY + "Click to set the starting location")
                .lore(ChatColor.GRAY + "on the place where you are standing.")
                .lore(ChatColor.DARK_GRAY + "(location where players will be teleported")
                .lore(ChatColor.DARK_GRAY + "when game starts)")
                .lore(isOptionDoneBool("instances." + arena.getID() + ".Startlocation"))
                .build());
        addItem(new ItemBuilder(new ItemStack(Material.COAL, ConfigurationManager.getConfig("arenas").getInt("instances." + arena.getID() + ".minimumplayers")))
                .name(ChatColor.GOLD + "► Set" + ChatColor.DARK_GREEN + " minimum players " + ChatColor.GOLD + "size")
                .lore(ChatColor.GRAY + "LEFT click to decrease")
                .lore(ChatColor.GRAY + "RIGHT click to increase")
                .lore(ChatColor.DARK_GRAY + "(how many players are needed")
                .lore(ChatColor.DARK_GRAY + "for game to start lobby countdown)")
                .lore(isOptionDone("instances." + arena.getID() + ".minimumplayers"))
                .build());
        addItem(new ItemBuilder(new ItemStack(Material.REDSTONE, ConfigurationManager.getConfig("arenas").getInt("instances." + arena.getID() + ".maximumplayers")))
                .name(ChatColor.GOLD + "► Set" + ChatColor.GREEN + " maximum players " + ChatColor.GOLD + "size")
                .lore(ChatColor.GRAY + "LEFT click to decrease")
                .lore(ChatColor.GRAY + "RIGHT click to increase")
                .lore(ChatColor.DARK_GRAY + "(how many players arena can hold)")
                .lore(isOptionDone("instances." + arena.getID() + ".maximumplayers"))
                .build());
        if(!plugin.isBungeeActivated()) {
            addItem(new ItemBuilder(new ItemStack(Material.SIGN))
                    .name(ChatColor.GOLD + "► Add game" + ChatColor.AQUA + " sign")
                    .lore(ChatColor.GRAY + "Target a sign and click this.")
                    .lore(ChatColor.DARK_GRAY + "(this will set target sign as game sign)")
                    .build());
        }
        addItem(new ItemBuilder(new ItemStack(Material.NAME_TAG))
                .name(ChatColor.GOLD + "► Set" + ChatColor.RED + " map name " + ChatColor.GOLD + "(currently: " + arena.getMapName() + ")")
                .lore(ChatColor.GRAY + "Replace this name tag with named name tag.")
                .lore(ChatColor.GRAY + "It will be set as arena name.")
                .lore(ChatColor.RED + "" + ChatColor.BOLD + "Drop name tag here don't move")
                .lore(ChatColor.RED + "" + ChatColor.BOLD + "it and replace with new!!!")
                .build());
        addItem(new ItemBuilder(new ItemStack(Material.EMERALD, 1))
                .name(ChatColor.GOLD + "► Add" + ChatColor.GREEN + " villager " + ChatColor.GOLD + "spawn")
                .lore(ChatColor.GRAY + "Add new villager spawn")
                .lore(ChatColor.GRAY + "on the place you're standing at.")
                .lore(isOptionDoneList("instances." + arena.getID() + ".villagerspawns"))
                .build());
        inventory.addItem((new ItemBuilder(new ItemStack(Material.ROTTEN_FLESH))
                .name(ChatColor.GOLD + "► Add" + ChatColor.BLUE + " zombie " + ChatColor.GOLD + "spawn")
                .lore(ChatColor.GRAY + "Add new zombie spawn")
                .lore(ChatColor.GRAY + "on the place you're standing at.")
                .lore(isOptionDoneList("instances." + arena.getID() + ".zombiespawns"))
                .build()));
        inventory.addItem((new ItemBuilder(new ItemStack(Material.WOOD_DOOR))
                .name(ChatColor.GOLD + "► Add doors")
                .lore(ChatColor.GRAY + "Select your arena with Cuboid Selection")
                .lore(ChatColor.GRAY + "in WorldEdit (select minimum and maximum")
                .lore(ChatColor.GRAY + "arena opposite selections with wand)")
                .lore(ChatColor.GRAY + "And click this. Plugin will search")
                .lore(ChatColor.GRAY + "for doors in your selection automatically.")
                .lore(isOptionDoneList("instances." + arena.getID() + ".doors"))
                .build()));
        inventory.addItem(new ItemBuilder(new ItemStack(Material.CHEST))
                .name(ChatColor.GOLD + "► Set" + ChatColor.LIGHT_PURPLE + " chest " + ChatColor.GOLD + "shop")
                .lore(ChatColor.GRAY + "Target chest with configured game items")
                .lore(ChatColor.GRAY + "and click this.")
                .build());
        inventory.addItem(new ItemBuilder(new ItemStack(Material.FIREWORK))
                .name(ChatColor.GOLD + "► " + ChatColor.GREEN + "Register arena")
                .lore(ChatColor.GRAY + "Click this when you're done with configuration.")
                .lore(ChatColor.GRAY + "It will validate and register arena.")
                .build());
    }

    private String isOptionDoneList(String path){
        if(ConfigurationManager.getConfig("arenas").isSet(path)) {
            return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes " + ChatColor.GRAY + "(value: " + ConfigurationManager.getConfig("arenas").getConfigurationSection(path).getKeys(false).size() + ")";
        }
        return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
    }

    public static String isOptionDone(String path) {
        if(ConfigurationManager.getConfig("arenas").isSet(path)) {
            return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes " + ChatColor.GRAY + "(value: " + ConfigurationManager.getConfig("arenas").getString(path) + ")";
        }
        return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
    }

    private String isOptionDoneBool(String path){
        if(ConfigurationManager.getConfig("arenas").isSet(path)) {
            if(Bukkit.getServer().getWorlds().get(0).getSpawnLocation().equals(Util.getLocation(false, ConfigurationManager.getConfig("arenas").getString(path)))){
                return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
            }
            return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.GREEN + "Yes";
        }
        return ChatColor.GOLD + "" + ChatColor.BOLD + "Done: " + ChatColor.RED + "No";
    }

    public void addItem(ItemStack itemStack) {
        inventory.addItem(itemStack);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

}
