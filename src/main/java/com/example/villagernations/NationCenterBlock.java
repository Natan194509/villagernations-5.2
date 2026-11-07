package com.example.villagernations;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Centro da Nação.
 * Primeiro clique com a mão vazia: abre a tela de configuração para fundar a nação.
 * Depois: entra no Modo Prefeito e abre o painel. Agachado: mostra o status no chat.
 * Clique com esmeraldas na mão: deposita no tesouro.
 */
public class NationCenterBlock extends Block {
    public NationCenterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(Items.EMERALD)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        NationData data = NationData.get(serverLevel.getServer());
        if (data.center == null) {
            NationLogic.msg(player, "Funde a nação primeiro: clique no bloco com a mão vazia.");
            return ItemInteractionResult.sidedSuccess(false);
        }

        int quantidade = stack.getCount();
        stack.shrink(quantidade);
        data.emeralds += quantidade;
        data.setDirty();
        NationLogic.msg(player, "Você depositou " + quantidade + " esmeraldas. Tesouro: " + data.emeralds);
        return ItemInteractionResult.sidedSuccess(false);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.sidedSuccess(true);
        }
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.sidedSuccess(false);
        }

        if (serverLevel.dimension() != Level.OVERWORLD) {
            NationLogic.msg(player, "O Centro da Nação só funciona no Overworld.");
            return InteractionResult.sidedSuccess(false);
        }

        NationData data = NationData.get(serverLevel.getServer());

        if (data.center == null) {
            NationLogic.pedirConfiguracao(serverPlayer, pos);
            return InteractionResult.sidedSuccess(false);
        }

        if (!data.center.equals(pos)) {
            if (serverLevel.getBlockState(data.center).is(this)) {
                BlockPos c = data.center;
                NationLogic.msg(player, "Sua nação já tem um Centro em " + c.getX() + ", " + c.getY() + ", " + c.getZ() + ".");
                return InteractionResult.sidedSuccess(false);
            }
            data.center = pos.immutable();
            data.setDirty();
            NationLogic.msg(player, "O Centro da Nação foi movido para este bloco.");
        }

        if (player.isShiftKeyDown()) {
            NationLogic.mostrarStatus(player, data);
        } else {
            NationLogic.entrarModoPrefeito(serverPlayer);
            NationLogic.abrirPainel(serverPlayer, data);
        }
        return InteractionResult.sidedSuccess(false);
    }
}
