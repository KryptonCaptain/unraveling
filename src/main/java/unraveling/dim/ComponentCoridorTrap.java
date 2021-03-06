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
import unraveling.block.UBlocks;
import unraveling.UnravelingConfig;


public class ComponentCoridorTrap extends ComponentRotatable {

	public ComponentCoridorTrap() {
		super();
	}

	public ComponentCoridorTrap(Random rand, int x, int y, int z, int mode) {
		super(mode);
        this.coordBaseMode = mode;
        int segWidth = (PyramidMain.oddBias + PyramidMain.evenBias) * 3;
        int segDepth = (PyramidMain.oddBias + PyramidMain.evenBias) * 3;
        int roomHeight = PyramidMain.height;

        this.boundingBox = new StructureBoundingBox(x, y, z, x + segWidth, y + roomHeight, z + segDepth);
        if (UnravelingConfig.debug) {
            System.out.println("Creating a room at " + x + " " + y + " " + z);
            System.out.println("Orientation is " + mode);
        }

	}
    public void fillWithOutsideWalls(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        Block what = PyramidMain.headBlockID;
        int meta = PyramidMain.headBlockMeta;
        if (UnravelingConfig.debug) {
            what = Blocks.stone;
            meta = 0;
        }
        
        fillWithMetadataBlocks(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, what, meta, what, meta, false);
    }

    public void createTripwire(World world, StructureBoundingBox sbb, int z) {
        int m = getHookMetadataWithOffset(Blocks.tripwire_hook, 1) | 4;
        int m2 = getHookMetadataWithOffset(Blocks.tripwire_hook, 3) | 4;
        int y = 1;
        int minX = 1;
        int maxX = 3;
        
        int pace = PyramidMain.evenBias + PyramidMain.oddBias;

        placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, m2, minX, y, z, sbb);
        if (isAirAtCurrentPosition(world, maxX+1, y, z, sbb)) {
            maxX += pace;
        }
        for (int x = minX+1; x < maxX; ++x) {
            placeBlockAtCurrentPosition(world, Blocks.tripwire, 0, x, y, z, sbb);
        }
        placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, m, maxX, y, z, sbb);
    }
    public void createSliceWithControl(World world, StructureBoundingBox sbb, int z) {
        int y = 0;
        int minX = -3;
        int maxX = 0;
        
        int m = getPistonMetadataWithOffset(Blocks.sticky_piston, 4);
        fillWithOutsideWalls(world, sbb, minX, -1, z, maxX, 1, z);
        placeBlockAtCurrentPosition(world, Blocks.sticky_piston, m, maxX, y, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, maxX-1, y, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, maxX-2, y, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, maxX-3, y, z, sbb);
    }
    public void createSliceWithGolems(World world, StructureBoundingBox sbb, int z) {
        int minY = -1;
        int maxY = 1;
        int minX = -3;
        int maxX = 0;
        
        
        fillWithOutsideWalls(world, sbb, minX, minY, z, maxX, maxY, z);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, maxX, maxY, z, sbb);
        //placeBlockAtCurrentPosition(world, Blocks.air, 0, maxX, maxY+1, z, sbb);
        
        // decoration
        // placeBlockAtCurrentPosition(world, ConfigBlocks.blockEldritch, 4, maxX, maxY+1, z, sbb);
        placeBlockAtCurrentPosition(world, UBlocks.ebricks, 4, maxX, maxY+1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.glowstone, 4, maxX, maxY+2, z, sbb);
        

        placeBlockAtCurrentPosition(world, UBlocks.golemSpawner, 0, maxX - 1, maxY, z, sbb);

        placeBlockAtCurrentPosition(world, Blocks.air, 0, maxX - 2, maxY-1, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, maxX - 3, maxY-1, z, sbb);
        
        placeBlockAtCurrentPosition(world, Blocks.sticky_piston, 1, maxX, minY, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, maxX - 1, minY, z, sbb);
        placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, maxX - 2, minY, z, sbb);

    }

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random random) {
	}
    
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        fillWithOutsideWalls(world, sbb, -3, -2, 1, 0, 1, 3);
        
        createSliceWithGolems(world, sbb, 1);
        createSliceWithControl(world, sbb, 2);
        createSliceWithGolems(world, sbb, 3);
        createTripwire(world, sbb, 2);
		return true;
	}
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) { }

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) { }
    
}
