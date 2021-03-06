package unraveling.dim;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import unraveling.block.UBlocks;
import net.minecraft.block.BlockDirectional;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import net.minecraft.nbt.NBTTagCompound;

import thaumcraft.common.config.ConfigBlocks;


public abstract class ComponentRotatable extends StructureComponent {

	public ComponentRotatable() {
		super();
	}
	public ComponentRotatable(int type) {
		super(type);
	}

    protected int getMetaAtCurrentPosition(World world, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_) {
        int l = this.getXWithOffset(p_151548_2_, p_151548_4_);
        int i1 = this.getYWithOffset(p_151548_3_);
        int j1 = this.getZWithOffset(p_151548_2_, p_151548_4_);
        return world.getBlockMetadata(l, i1, j1);
    }
    protected int getPistonMetadataWithOffset(Block block, int dir) {
        if (this.coordBaseMode == 0 || this.coordBaseMode == 1) {
            return super.getMetadataWithOffset(block, dir);
        }
        if (block == Blocks.piston || block == Blocks.sticky_piston || block == Blocks.lever || block == Blocks.dispenser) {
            
            if (this.coordBaseMode == 2) { // was 3
                if (dir == 4) {
                    return 5;
                }
                if (dir == 5) {
                    return 4;
                }
            }
            if (this.coordBaseMode == 3) { // max z
                if (dir == 4) {
                    return 3;
                }
                if (dir == 5) {
                    return 2;
                }
            }
            return super.getMetadataWithOffset(block, dir);
        }
        return dir;
    }

    protected int getHookMetadataWithOffset(Block block, int dir) {
        if (this.coordBaseMode == 0 || this.coordBaseMode == 1) {
            return super.getMetadataWithOffset(block, dir);
        }
        if (block == Blocks.tripwire_hook || (block instanceof BlockDirectional)) {
           
           
            if (this.coordBaseMode == 2) { // was 2
                if (dir == 2) {
                    return 0; // was 3
                }
                if (dir == 0) {
                    return 2; // was 1
                }
                if (dir == 1) {
                    return 3; // was 2
                }
                if (dir == 3) {
                    return 1; // was 0
                }
            }
            if (coordBaseMode == 3) {
                if (dir == 2) {
                    return 1; // was 3
                }
                if (dir == 0) {
                    return 3; // was 1
                }
                if (dir == 1) {
                    return 0; // was 2
                }
                if (dir == 3) {
                    return 2; // was 0
                }
            }
        }
        return dir;
    }
    
    
    @Override
    protected int getXWithOffset(int x, int z) {
        switch (this.coordBaseMode)
        {
            case 0:
                return this.boundingBox.minX + x;
            case 1:
                return this.boundingBox.maxX - z;
            case 2:
                return this.boundingBox.maxX - x;
            case 3:
                return this.boundingBox.minX + z;
            default:
                return x;
        }
    }

    @Override
    protected int getZWithOffset(int x, int z)
    {
        switch (this.coordBaseMode)
        {
            case 0:
                return this.boundingBox.minZ + z;
            case 1:
                return this.boundingBox.minZ + x;
            case 2:
                return this.boundingBox.maxZ - z;
            case 3:
                return this.boundingBox.maxZ - x;
            default:
                return z;
        }
    }    
    /**
     * current Position depends on currently set Coordinates mode, is computed here
     */
    @Override
    protected void placeBlockAtCurrentPosition(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_)
    {
        int i1 = this.getXWithOffset(p_151550_4_, p_151550_6_);
        int j1 = this.getYWithOffset(p_151550_5_);
        int k1 = this.getZWithOffset(p_151550_4_, p_151550_6_);

        //if (p_151550_7_.isVecInside(i1, j1, k1))
        //{
        p_151550_1_.setBlock(i1, j1, k1, p_151550_2_, p_151550_3_, 2);
        //}
    }    
    @Override
    protected Block getBlockAtCurrentPosition(World world, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_)
    {
        int l = this.getXWithOffset(p_151548_2_, p_151548_4_);
        int i1 = this.getYWithOffset(p_151548_3_);
        int j1 = this.getZWithOffset(p_151548_2_, p_151548_4_);
        return world.getBlock(l, i1, j1);
    }
    protected boolean isAirAtCurrentPosition(World world, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_)
    {
        int l = this.getXWithOffset(p_151548_2_, p_151548_4_);
        int i1 = this.getYWithOffset(p_151548_3_);
        int j1 = this.getZWithOffset(p_151548_2_, p_151548_4_);
        return world.isAirBlock(l, i1, j1);
    }
    protected boolean isStructurePartAtCurrentPosition(World world, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_)
    {
        // TODO
        int l = this.getXWithOffset(p_151548_2_, p_151548_4_);
        int i1 = this.getYWithOffset(p_151548_3_);
        int j1 = this.getZWithOffset(p_151548_2_, p_151548_4_);
        return world.isAirBlock(l, i1, j1);
    }

}
