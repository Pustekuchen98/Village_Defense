package pl.plajer.villagedefense3.kits.kitapi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.plajer.villagedefense3.Main;
import pl.plajer.villagedefense3.handlers.ChatManager;
import pl.plajer.villagedefense3.handlers.ConfigurationManager;
import pl.plajer.villagedefense3.kits.ArcherKit;
import pl.plajer.villagedefense3.kits.BlockerKit;
import pl.plajer.villagedefense3.kits.CleanerKit;
import pl.plajer.villagedefense3.kits.DogFriendKit;
import pl.plajer.villagedefense3.kits.GolemFriendKit;
import pl.plajer.villagedefense3.kits.HardcoreKit;
import pl.plajer.villagedefense3.kits.HealerKit;
import pl.plajer.villagedefense3.kits.HeavyTankKit;
import pl.plajer.villagedefense3.kits.KnightKit;
import pl.plajer.villagedefense3.kits.LightTankKit;
import pl.plajer.villagedefense3.kits.LooterKit;
import pl.plajer.villagedefense3.kits.MedicKit;
import pl.plajer.villagedefense3.kits.MediumTankKit;
import pl.plajer.villagedefense3.kits.NakedKit;
import pl.plajer.villagedefense3.kits.PremiumHardcoreKit;
import pl.plajer.villagedefense3.kits.PuncherKit;
import pl.plajer.villagedefense3.kits.RunnerKit;
import pl.plajer.villagedefense3.kits.ShotBowKit;
import pl.plajer.villagedefense3.kits.TeleporterKit;
import pl.plajer.villagedefense3.kits.TerminatorKit;
import pl.plajer.villagedefense3.kits.TornadoKit;
import pl.plajer.villagedefense3.kits.WizardKit;
import pl.plajer.villagedefense3.kits.WorkerKit;
import pl.plajer.villagedefense3.kits.ZombieFinderKit;
import pl.plajer.villagedefense3.kits.kitapi.basekits.FreeKit;
import pl.plajer.villagedefense3.kits.kitapi.basekits.Kit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Kit registry class for registering new kits.
 *
 * @author TomTheDeveloper
 */
public class KitRegistry {

    private static List<Kit> kits = new ArrayList<>();
    private static Kit defaultKit = null;
    private static Main plugin = JavaPlugin.getPlugin(Main.class);
    private static List<Class> classKitNames = Arrays.asList(LightTankKit.class, ZombieFinderKit.class, ArcherKit.class, PuncherKit.class, HealerKit.class, LooterKit.class, RunnerKit.class, MediumTankKit.class, WorkerKit.class, GolemFriendKit.class, TerminatorKit.class, HardcoreKit.class, CleanerKit.class, TeleporterKit.class, HeavyTankKit.class, ShotBowKit.class, DogFriendKit.class, PremiumHardcoreKit.class, TornadoKit.class, BlockerKit.class, MedicKit.class, NakedKit.class, WizardKit.class);

    public static void init() {
        setupGameKits();
    }

    /**
     * Method for registering new kit
     *
     * @param kit Kit to register
     */
    public static void registerKit(Kit kit) {
        kits.add(kit);
    }

    /**
     * Return default game kit
     *
     * @return default game kit
     */
    public static Kit getDefaultKit() {
        return defaultKit;
    }

    /**
     * Sets default game kit
     *
     * @param defaultKit default kit to set, must be FreeKit
     */
    public static void setDefaultKit(FreeKit defaultKit) {
        KitRegistry.defaultKit = defaultKit;
    }

    /**
     * Returns all available kits
     *
     * @return list of all registered kits
     */
    public static List<Kit> getKits() {
        return kits;
    }

    /**
     * Get registered kit by it's represented item stack
     *
     * @param itemStack itemstack that kit represents
     * @return Registered kit or default if not found
     */
    public static Kit getKit(ItemStack itemStack) {
        Kit returnKit = getDefaultKit();
        for(Kit kit : kits) {
            if(itemStack.getType() == kit.getMaterial()) {
                returnKit = kit;
                break;
            }
        }
        return returnKit;
    }

    private static void setupGameKits() {
        KnightKit knightkit = new KnightKit(plugin);
        for(Class kitClass : classKitNames) {
            if(ConfigurationManager.getConfig("kits").getBoolean("Enabled-Game-Kits." + kitClass.getSimpleName().replaceAll("Kit", ""))) {
                try {
                    Class.forName(kitClass.getName()).getConstructor(Main.class).newInstance(plugin);
                } catch(ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                    System.out.println("[VillageDefense] FATAL ERROR! COULDN'T REGISTER EXISTING KIT! REPORT THIS TO THE DEVELOPER!");
                }
            }
        }

        KitRegistry.setDefaultKit(knightkit);
        plugin.getKitManager().setMaterial(Material.NETHER_STAR);
        plugin.getKitManager().setItemName(ChatManager.colorMessage("Kits.Kit-Menu-Item-Name"));
        plugin.getKitManager().setMenuName(ChatManager.colorMessage("Kits.Kit-Menu.Title"));
        plugin.getKitManager().setDescription(new String[]{ChatManager.colorMessage("Kits.Open-Kit-Menu")});
    }

}
