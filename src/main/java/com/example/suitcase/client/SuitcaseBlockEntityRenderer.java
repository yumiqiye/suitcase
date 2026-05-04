package com.example.suitcase.client;

import com.example.suitcase.entity.SuitcaseBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SuitcaseBlockEntityRenderer implements BlockEntityRenderer<SuitcaseBlockEntity> {

    public SuitcaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SuitcaseBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // The actual model rendering will be handled by the block entity renderer system
        // This is a basic implementation that can be extended with custom BBModel rendering
        
        BlockState state = blockEntity.getBlockState();
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            
            // Rotate the model based on the block's facing direction
            float rotation = switch (facing) {
                case NORTH -> 0;
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 0;
            };
            
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(net.minecraft.util.Mth.DEG_TO_RAD * -rotation);
            poseStack.translate(-0.5, 0, -0.5);
        }
        
        // Render sticky note overlay if present
        if (blockEntity.hasStickyNote()) {
            renderStickyNote(poseStack, bufferSource, packedLight, blockEntity.getCustomName());
        }
    }

    private void renderStickyNote(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, String customName) {
        // Sticky note rendering logic would go here
        // This is a placeholder for the actual rendering code
    }
}
