package net.justacoasterfan.justastoragemod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class StorageCable extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");


    public static final BooleanProperty NORTH_CONNECTOR = BooleanProperty.create("north_connector");
    public static final BooleanProperty SOUTH_CONNECTOR = BooleanProperty.create("south_connector");
    public static final BooleanProperty EAST_CONNECTOR = BooleanProperty.create("east_connector");
    public static final BooleanProperty WEST_CONNECTOR = BooleanProperty.create("west_connector");
    public static final BooleanProperty UP_CONNECTOR = BooleanProperty.create("up_connector");
    public static final BooleanProperty DOWN_CONNECTOR = BooleanProperty.create("down_connector");


    public StorageCable(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH_CONNECTOR, false)
                .setValue(SOUTH_CONNECTOR, false)
                .setValue(EAST_CONNECTOR, false)
                .setValue(WEST_CONNECTOR, false)
                .setValue(UP_CONNECTOR, false)
                .setValue(DOWN_CONNECTOR, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN,
                NORTH_CONNECTOR, SOUTH_CONNECTOR, EAST_CONNECTOR, WEST_CONNECTOR, UP_CONNECTOR, DOWN_CONNECTOR);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Block.box(6, 6, 6, 10, 10, 10);  // core

        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, Block.box(6, 6, 0, 10, 10, 6)); // North arm (Y: 0 -> 6)
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, Block.box(6, 6, 10, 10, 10, 16)); // South arm (Y: 10 -> 16)
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, Block.box(10, 6, 6, 16, 10, 10)); // East arm (X: 10 -> 16)
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, Block.box(0, 6, 6, 6, 10, 10)); // West arm (X: 0 -> 5)
        }
        if (state.getValue(UP)) {
            shape = Shapes.or(shape, Block.box(6, 10, 6, 10, 16, 10)); // Up arm (Y: 10 -> 16)
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, Block.box(6, 0, 6, 10, 6, 10)); // Down arm (Y: 0 -> 6)
        }


        return shape;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        updateConnections(level, pos, state);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        updateConnections(level, pos, state);
    }





    private void updateConnections(Level level, BlockPos pos, BlockState state) {

        // update cable connections
        boolean north = canConnectTo(level, pos.north());
        boolean south = canConnectTo(level, pos.south());
        boolean east = canConnectTo(level, pos.east());
        boolean west = canConnectTo(level, pos.west());
        boolean up = canConnectTo(level, pos.above());
        boolean down = canConnectTo(level, pos.below());

        // update cable connectors
        boolean northConn = false;
        boolean southConn = false;
        boolean eastConn = false;
        boolean westConn = false;
        boolean upConn = false;
        boolean downConn = false;

        if(north) {northConn = shouldPlaceConnector(level, pos.north());}
        if(south) {southConn = shouldPlaceConnector(level, pos.south());}
        if(east) {eastConn = shouldPlaceConnector(level, pos.east());}
        if(west) {westConn = shouldPlaceConnector(level, pos.west());}
        if(up) {upConn = shouldPlaceConnector(level, pos.above());}
        if(down) {downConn = shouldPlaceConnector(level, pos.below());}

        BlockState newState = state
                .setValue(NORTH, north)
                .setValue(SOUTH, south)
                .setValue(EAST, east)
                .setValue(WEST, west)
                .setValue(UP, up)
                .setValue(DOWN, down)
                .setValue(NORTH_CONNECTOR, northConn)
                .setValue(SOUTH_CONNECTOR, southConn)
                .setValue(EAST_CONNECTOR, eastConn)
                .setValue(WEST_CONNECTOR, westConn)
                .setValue(UP_CONNECTOR, upConn)
                .setValue(DOWN_CONNECTOR, downConn);

        if(!newState.equals(state)) {
            level.setBlock(pos, newState, 2);

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                BlockState neighborState = level.getBlockState(neighborPos);
                if (neighborState.getBlock() instanceof StorageCable cable) {
                    cable.updateConnections(level, neighborPos, neighborState);
                }
            }
        }
    }

    private boolean canConnectTo(Level level, BlockPos pos) {
        BlockState neighbor = level.getBlockState(pos);
        Block neighborBlock = neighbor.getBlock();

        return neighborBlock instanceof StorageCable || shouldPlaceConnector(level, pos);
    }

    private boolean shouldPlaceConnector(Level level, BlockPos pos) {
        BlockState neighbor = level.getBlockState(pos);
        Block neighborBlock = neighbor.getBlock();

        BlockEntity neighborBlockEntity = level.getBlockEntity(pos);


        return (neighborBlockEntity != null && neighborBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) || neighborBlock instanceof StorageController ;
    }

}
