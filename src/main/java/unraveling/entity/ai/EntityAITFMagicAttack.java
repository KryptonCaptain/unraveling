package unraveling.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import unraveling.entity.EntityTFTomeBolt;
import unraveling.entity.boss.EntityTFLichBolt;


public class EntityAITFMagicAttack extends EntityAIBase
{
    public static final int LICH = 3;
    public static final int TOME = 2;

	World worldObj;

    /** The entity the AI instance has been applied to */
    EntityLiving entityHost;
    EntityLivingBase attackTarget;

    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    int rangedAttackTime = 0;
    float moveSpeed;
    int ticksLookingAtTarget = 0;

    /**
     * The ID of this ranged attack AI. This chooses which entity is to be used as a ranged attack.
     */
    int rangedAttackID;

    /**
     * The maximum time the AI has to wait before peforming another ranged attack.
     */
    int maxRangedAttackTime;
    
    float attackChance;

    public EntityAITFMagicAttack(EntityLiving par1EntityLiving, float speed, int id, int time)
    {
        this(par1EntityLiving, speed, id, time, 1.0F);
    }

    public EntityAITFMagicAttack(EntityLiving par1EntityLiving, float speed, int id, int time, float chance)
    {
        this.entityHost = par1EntityLiving;
        this.worldObj = par1EntityLiving.worldObj;
        this.moveSpeed = speed;
        this.rangedAttackID = id;
        this.maxRangedAttackTime = time;
        this.attackChance = chance;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
    	EntityLivingBase var1 = this.entityHost.getAttackTarget();

        if (var1 == null || this.entityHost.getRNG().nextFloat() > attackChance)
        {
            return false;
        }
        else
        {
            this.attackTarget = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.attackTarget = null;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        double maxRange = 100.0D;
        double targetDistance = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (canSee)
        {
            ++this.ticksLookingAtTarget;
        }
        else
        {
            this.ticksLookingAtTarget = 0;
        }

        if (targetDistance <= maxRange && this.ticksLookingAtTarget >= 20)
        {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.moveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);

        if (this.rangedAttackTime <= 0)
        {
            if (targetDistance <= maxRange && canSee)
            {
                this.doRangedAttack();
                this.rangedAttackTime = this.maxRangedAttackTime;
            }
        }
    }

    /**
     * Performs a ranged attack according to the AI's rangedAttackID.
     */
    protected void doRangedAttack()
    {
    	EntityThrowable projectile = null;

        if (this.rangedAttackID == TOME) {
    		projectile = new EntityTFTomeBolt(this.worldObj, this.entityHost);
    		this.worldObj.playSoundAtEntity(this.entityHost, "mob.ghast.fireball", 1.0F, 1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
    	}
    	else if (this.rangedAttackID == LICH)
    	{
    		projectile = new EntityTFLichBolt(this.worldObj, this.entityHost);
    		this.worldObj.playSoundAtEntity(this.entityHost, "mob.ghast.fireball", 1.0F, 1.0F / (this.entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
    	}
    	// launch the projectile
    	if (projectile != null) {
    		double tx = this.attackTarget.posX - this.entityHost.posX;
    		double ty = this.attackTarget.posY + this.attackTarget.getEyeHeight() - 1.100000023841858D - projectile.posY;
    		double tz = this.attackTarget.posZ - this.entityHost.posZ;
    		float heightOffset = MathHelper.sqrt_double(tx * tx + tz * tz) * 0.2F;
    		projectile.setThrowableHeading(tx, ty + heightOffset, tz, 0.6F, 6.0F); // 0.6 speed, 6.0 inaccuracy
    		this.worldObj.spawnEntityInWorld(projectile);
    	}
    }
    
    
}
