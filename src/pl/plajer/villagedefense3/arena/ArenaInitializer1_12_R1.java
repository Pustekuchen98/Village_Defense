package pl.plajer.villagedefense3.arena;

import net.minecraft.server.v1_12_R1.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.plajer.villagedefense3.Main;
import pl.plajer.villagedefense3.creatures.CreatureUtils;
import pl.plajer.villagedefense3.creatures.v1_12_R1.BabyZombie;
import pl.plajer.villagedefense3.creatures.v1_12_R1.FastZombie;
import pl.plajer.villagedefense3.creatures.v1_12_R1.GolemBuster;
import pl.plajer.villagedefense3.creatures.v1_12_R1.HardZombie;
import pl.plajer.villagedefense3.creatures.v1_12_R1.PlayerBuster;
import pl.plajer.villagedefense3.creatures.v1_12_R1.RidableIronGolem;
import pl.plajer.villagedefense3.creatures.v1_12_R1.RidableVillager;
import pl.plajer.villagedefense3.creatures.v1_12_R1.TankerZombie;
import pl.plajer.villagedefense3.creatures.v1_12_R1.WorkingWolf;
import pl.plajer.villagedefense3.handlers.ChatManager;

import java.util.Random;

/**
 * Created by TomVerschueren on 9/06/2017.
 */
public class ArenaInitializer1_12_R1 extends Arena {

    private Main plugin;

    public ArenaInitializer1_12_R1(String ID, Main plugin) {
        super(ID, plugin);
        this.plugin = plugin;
    }

    public void spawnFastZombie(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        FastZombie fastZombie = new FastZombie(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.setRemoveWhenFarAway(false);
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie((Zombie) fastZombie.getBukkitEntity());

        super.subtractZombiesToSpawn();
    }

    @Override
    public void spawnHalfInvisibleZombie(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        FastZombie fastZombie = new FastZombie(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.setRemoveWhenFarAway(false);
        zombie.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie((Zombie) fastZombie.getBukkitEntity());

        super.subtractZombiesToSpawn();
    }

    @Override
    public void spawnKnockbackResistantZombies(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        TankerZombie fastZombie = new TankerZombie(location.getWorld());
        fastZombie.getAttributeInstance(GenericAttributes.c).setValue(Double.MAX_VALUE);
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.getEquipment().setItemInHand(new ItemStack(Material.GOLD_AXE));
        zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        zombie.setRemoveWhenFarAway(false);
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie((Zombie) fastZombie.getBukkitEntity());

        super.subtractZombiesToSpawn();
    }

    public void spawnBabyZombie(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        BabyZombie fastZombie = new BabyZombie(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        CreatureUtils.applyHealthBar(zombie);
        zombie.setRemoveWhenFarAway(false);
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        this.addZombie((Zombie) fastZombie.getBukkitEntity());

        super.subtractZombiesToSpawn();
    }

    public void spawnHardZombie(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        HardZombie fastZombie = new HardZombie(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        zombie.setRemoveWhenFarAway(false);
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie(zombie);
        super.subtractZombiesToSpawn();
    }

    @Override
    public void spawnSoftHardZombie(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        HardZombie fastZombie = new HardZombie(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
        zombie.setRemoveWhenFarAway(false);
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie(zombie);
        super.subtractZombiesToSpawn();
    }

    public void spawnGolemBuster(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        GolemBuster fastZombie = new GolemBuster(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.getEquipment().setHelmet(new ItemStack(Material.TNT));
        zombie.getEquipment().setHelmetDropChance(0.0F);
        zombie.getEquipment().setItemInHandDropChance(0F);
        zombie.setRemoveWhenFarAway(false);
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie(zombie);

        super.subtractZombiesToSpawn();
    }

    public void spawnPlayerBuster(Random random) {
        Location location = zombieSpawns.get(random.nextInt(zombieSpawns.size()));
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        PlayerBuster fastZombie = new PlayerBuster(location.getWorld());
        fastZombie.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(fastZombie, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Zombie zombie = (Zombie) fastZombie.getBukkitEntity();
        zombie.getEquipment().setHelmet(new ItemStack(Material.TNT));
        zombie.getEquipment().setHelmetDropChance(0.0F);
        zombie.getEquipment().setItemInHandDropChance(0F);
        zombie.getEquipment().setBoots(new ItemStack(Material.GOLD_BOOTS));
        zombie.getEquipment().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        zombie.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        CreatureUtils.applyHealthBar(zombie);
        this.addZombie(zombie);

        super.subtractZombiesToSpawn();
    }

    public void spawnVillager(Location location) {
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        RidableVillager ridableVillager = new RidableVillager(location.getWorld());
        ridableVillager.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(ridableVillager, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Villager villager = (Villager) ridableVillager.getBukkitEntity();
        villager.setRemoveWhenFarAway(false);
        this.addVillager((Villager) ridableVillager.getBukkitEntity());
    }

    public void spawnGolem(Location location, Player player) {
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        RidableIronGolem ironGolem = new RidableIronGolem(location.getWorld());
        ironGolem.setPosition(location.getX(), location.getY(), location.getZ());
        ironGolem.setCustomName(ChatManager.colorMessage("In-Game.Spawned-Golem-Name").replaceAll("%player%", player.getName()));
        ironGolem.setCustomNameVisible(true);
        McWorld.addEntity(ironGolem, CreatureSpawnEvent.SpawnReason.CUSTOM);

        this.addIronGolem((org.bukkit.entity.IronGolem) ironGolem.getBukkitEntity());
    }

    public void spawnWolf(Location location, Player player) {
        net.minecraft.server.v1_12_R1.World McWorld = ((CraftWorld) location.getWorld()).getHandle();
        WorkingWolf wolf = new WorkingWolf(location.getWorld());
        wolf.setPosition(location.getX(), location.getY(), location.getZ());
        McWorld.addEntity(wolf, CreatureSpawnEvent.SpawnReason.CUSTOM);
        wolf.setCustomName(ChatManager.colorMessage("In-Game.Spawned-Wolf-Name").replaceAll("%player%", player.getName()));
        wolf.setCustomNameVisible(true);
        wolf.setInvisible(false);
        ((Wolf) wolf.getBukkitEntity()).setOwner(player);

        this.addWolf((Wolf) wolf.getBukkitEntity());
    }
}
