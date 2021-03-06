package unraveling.dim;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import unraveling.block.UBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import net.minecraft.nbt.NBTTagCompound;

import thaumcraft.common.config.ConfigBlocks;
import unraveling.UnravelingConfig;


public class ComponentPyramidTrap extends ComponentPyramidRoom {

    public int trap_type;
	public ComponentPyramidTrap() {
		super();
	}

	public ComponentPyramidTrap(Random rand, int x, int y, int z, int mode, int trap_type) {
		super(rand, x, y, z, PyramidMap.ROOM);
        this.coordBaseMode = mode % 4;
        this.trap_type = trap_type;
	}
    
    public void createTripwire(World world, StructureBoundingBox sbb, int x, int y, int z) {

        int doorwaySize = PyramidMain.oddBias;
        int m = getHookMetadataWithOffset(Blocks.tripwire_hook, 3) | 4;
        int m2 = getHookMetadataWithOffset(Blocks.tripwire_hook, 1) | 4;
        placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, m2, x+3, y+1, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.tripwire, 0, x+2, y+1, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, m, x+1, y+1, 0, sbb);
        
        if (UnravelingConfig.debug) {
            if (coordBaseMode == 3) {
                                
                //placeBlockAtCurrentPosition(world, Blocks.piston, 4, x+3, y+3, 0, sbb);
                //placeBlockAtCurrentPosition(world, Blocks.piston, 3, x+2, y+3, 0, sbb);
                //placeBlockAtCurrentPosition(world, Blocks.piston, 2, x+1, y+3, 0, sbb);
                
                // placeBlockAtCurrentPosition(world, Blocks.piston, 5, x+3, y+2, 0, sbb);

            }
            if (coordBaseMode == 2) {
                // placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, 0|4, x+3, y+1, 0, sbb);
                // placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, 1|4, x+2, y+1, 0, sbb);
                // placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, 2|4, x+1, y+1, 0, sbb);
                
                // placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, 3|4, x+3, y+2, 0, sbb);
                // placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, 4|4, x+2, y+2, 0, sbb);
            }
        }
    }
    public void createFloorTrap(World world, StructureBoundingBox sbb, int x, int y, int z) {
        fillWithAir(world, sbb, x, y-2, z, x, y+2, z);

        fillWithMetadataBlocks(world, sbb, 0, y-2, z, x+2, y-2, z, Blocks.redstone_wire, 0, Blocks.redstone_wire, 0, false);
        fillWithMetadataBlocks(world, sbb, x, y-2, 0, x, y-2, z, Blocks.redstone_wire, 0, Blocks.redstone_wire, 0, false);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y-2, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, 0, y-2, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.sticky_piston, 1, x, y-2, z, sbb);
        placeBlockAtCurrentPosition(world, PyramidMain.wallBlockID, PyramidMain.wallBlockMeta, x, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, UBlocks.golemSpawner, 0, x, y, z, sbb);
        if (UnravelingConfig.debug) {
            placeBlockAtCurrentPosition(world, UBlocks.ebricks, 0, x, y, z, sbb);
        }

        placeBlockAtCurrentPosition(world, Blocks.sticky_piston, 0, 0, y+1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.sticky_piston, 0, x, y+1, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, 0, y, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, x, y, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, 0, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y-1, 0, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 0, y-2, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x, y-2, 0, sbb);
    } 
    public void createHiddenLever(World world, StructureBoundingBox sbb, int x, int y, int z) {

        placeBlockAtCurrentPosition(world, Blocks.stone_pressure_plate, 0, x, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x-1, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x, y-1, z-1, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x+1, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x, y-1, z+1, sbb);

        placeBlockAtCurrentPosition(world, Blocks.air, 0, x-2, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y-1, z-2, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x+2, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y-1, z+2, sbb);
    } 

    public void createTrappedChest(World world, StructureBoundingBox sbb, int x, int y, int z) {
        placeBlockAtCurrentPosition(world, Blocks.trapped_chest, 0, x, y, z, sbb);
        placeBlockAtCurrentPosition(world, PyramidMain.wallBlockID, PyramidMain.wallBlockMeta, x, y-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.piston, 0, x, y-2, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, x, y-3, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, x, y-4, z, sbb);

        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x-1, y-4, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x, y-4, z-1, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x+1, y-4, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, x, y-4, z+1, sbb);
    } 

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random random) {
	}
    
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        makeFancyEntrance(world, sbb);
        int pace = PyramidMain.evenBias + PyramidMain.oddBias;
        createFloorTrap(world, sbb, pace, -1, pace);
        trap_type = 0;
        switch(trap_type) {
            case 0: {
                createTripwire(world, sbb, pace, 0, pace);
                break;
            }
            case 1: {
                createTrappedChest(world, sbb, pace + 2, 1, pace + 2);
                break;
            }
        }
		return true;
	}
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("trap_type", trap_type);
   
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
        super.func_143011_b(par1NBTTagCompound);
        this.trap_type = par1NBTTagCompound.getInteger("trap_type");
 	}
    
}
