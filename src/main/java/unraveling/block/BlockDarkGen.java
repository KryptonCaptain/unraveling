package unraveling.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockContainer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import unraveling.UnravelingMod;
import unraveling.item.TFItems;
import unraveling.tileentity.TileDarkGen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import thaumcraft.client.fx.particles.FXEssentiaTrail;
import thaumcraft.client.fx.particles.FXSmokeSpiral;
import thaumcraft.client.fx.particles.FXSmokeTrail;
import thaumcraft.client.fx.particles.FXSpark;
import thaumcraft.client.fx.particles.FXSparkle;
import thaumcraft.client.fx.particles.FXSparkleTrail;
import thaumcraft.client.fx.particles.FXSwarm;
import thaumcraft.client.fx.particles.FXVent;
import thaumcraft.client.fx.particles.FXWisp;
import thaumcraft.client.fx.particles.FXWispArcing;
import thaumcraft.common.Thaumcraft;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import unraveling.VoidAggregationHandler;

public class BlockDarkGen extends BlockContainer {
	
	public static IIcon gemDark;
	public static IIcon slabSide;
	public static IIcon slabTop;
	public static IIcon slabBottom;
	
	protected BlockDarkGen() {
		super(Material.rock);
		//this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
		this.setHardness(1.0F);
		this.setStepSound(Block.soundTypeStone);
        }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }

	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType()
    {
    	return UnravelingMod.proxy.getDarkGenRenderID();
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
        // the darkness gem will be rendered separately
        if (side == 0)
            return slabBottom;
        if (side == 1)
            return slabTop;
        return slabSide;
    }

    /**
     * Get a light value for this block, normal ranges are between 0 and 15
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
    @Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
    	return 0;
    }
    
    /**
     * Return true if the block is a normal, solid cube.  This
     * determines indirect power state, entity ejection from blocks, and a few
     * others.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is a full cube
     */
    public boolean isBlockNormalCube(World world, int x, int y, int z) 
    {
    	return false;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        // TODO: ignore darkness gem sometimes?
    	this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
    }
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDarkGen();
    }
  
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockDarkGen.gemDark = par1IconRegister.registerIcon(UnravelingMod.ID + ":darkgem");
        BlockDarkGen.slabSide = par1IconRegister.registerIcon(UnravelingMod.ID + ":darkgen_slabside");
        BlockDarkGen.slabTop = par1IconRegister.registerIcon(UnravelingMod.ID + ":darkgen_top");
        BlockDarkGen.slabBottom = par1IconRegister.registerIcon(UnravelingMod.ID + ":darkgen_bottom");
    }
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        /*
        TileDarkGen tileentity = (TileDarkGen) world.getTileEntity(x, y, z);
        int color = (rand.nextBoolean())? Aspect.DARKNESS.getColor() : Aspect.VOID.getColor();
        if (tileentity.power > 0) {
            color = Aspect.ELDRITCH.getColor();
        }
        if (tileentity.reserve || tileentity.charges > 0) {
            color = Aspect.AIR.getColor();
            if (tileentity.power > 0) {
                color = Aspect.MAGIC.getColor();
            }
        }
    	float dx = x + ((rand.nextFloat() - rand.nextFloat()) + 0.5F);
    	float dy = y + 1.1F;
    	float dz = z + ((rand.nextFloat() - rand.nextFloat()) + 0.5F);*/
        // Thaumcraft.proxy.drawVentParticles(world, x + 0.5, y + 1, z + 0.5, dx, dy, dz, color, 3.0F);
        // Thaumcraft.proxy.sourceStreamFX(world, x, y, z, dx, dy, dz, Aspect.DARKNESS.getColor());
        // Thaumcraft.proxy.nodeBolt(world, x, y, z, dx, dy, dz);
        //Thaumcraft.proxy.sourceStreamFX(world, x, y, z, dx, dy, dz, Aspect.DARKNESS.getColor());
        //Thaumcraft.proxy.drawInfusionParticles3(world, x, y, z, x + 1, y + 5, z + 3);
    }/*
    public void sourceStreamFX(World worldObj, double sx, double sy, double sz, float tx, float ty, float tz, int tagColor) {
        Color c = new Color(tagColor);
        FXWispArcing ef = new FXWispArcing(worldObj, tx, ty, tz, sx, sy, sz, 0.1f, (float)c.getRed() / 255.0f, (float)c.getGreen() / 255.0f, (float)c.getBlue() / 255.0f);
        ef.setGravity(0.0f);
        ParticleEngine.instance.addEffect(worldObj, ef);
    }*/
        
    public void breakBlock(World world, int par2, int par3, int par4, int par5, int par6) {
        TileDarkGen tileentity = (TileDarkGen) world.getTileEntity(par2, par3, par4);
        
        if(tileentity != null) {
            if (!world.isRemote) {
                VoidAggregationHandler.notifyOfDestruction((TileEntity)tileentity);
                tileentity.removeThisNode();
            }
        }
    }


}



