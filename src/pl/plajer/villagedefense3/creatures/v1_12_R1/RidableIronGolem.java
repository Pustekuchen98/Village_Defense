package pl.plajer.villagedefense3.creatures.v1_12_R1;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityIronGolem;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.IMonster;
import net.minecraft.server.v1_12_R1.Navigation;
import net.minecraft.server.v1_12_R1.PathfinderGoalDefendVillage;
import net.minecraft.server.v1_12_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalOfferFlower;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import pl.plajer.villagedefense3.creatures.CreatureUtils;

import java.util.LinkedHashSet;

/**
 * Created by Tom on 17/08/2014.
 */
public class RidableIronGolem extends EntityIronGolem {

    public RidableIronGolem(org.bukkit.World world){
        this(((CraftWorld) world).getHandle());
    }

    public RidableIronGolem(World world) {
        super(world);

        LinkedHashSet goalB = (LinkedHashSet) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet) CreatureUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet) CreatureUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        this.a(1.4F, 2.9F);
        ((Navigation) getNavigation()).b(true);
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));

        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, false, true, IMonster.e));
        this.setHealth(500);
    }

    public void a(float f, float f1, float f2) {
        EntityLiving entityliving = null;
        for(final Entity e : passengers) {
            if(e instanceof EntityHuman) {
                entityliving = (EntityLiving) e;
                break;
            }
        }
        if(entityliving == null) {
            this.P = 0.5F;
            this.aR = 0.02F;
            this.k((float) 0.12);
            super.a(f, f1, f2);
            return;
        }
        this.lastYaw = this.yaw = entityliving.yaw;
        this.pitch = entityliving.pitch * 0.5F;
        this.setYawPitch(this.yaw, this.pitch);
        this.aO = this.aM = this.yaw;

        f = entityliving.be * 0.5F * 0.75F;
        f2 = entityliving.bg;
        if(f2 <= 0.0f) {
            f2 *= 0.25F;
        }
        k(0.12f);
        super.a(f, f1, f2);
        P = (float) 1.0;
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(150D);
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {
     /*   int j = this.random.nextInt(3);

        int k;

        for (k = 0; k < j; ++k) {
            this.a(Item.getItemOf(Blocks.RED_ROSE), 1, 0.0F);
        }

        k = 3 + this.random.nextInt(3);

        for (int l = 0; l < k; ++l) {
            this.a(ItemUtils.IRON_INGOT, 1);
        } */
    }

}
