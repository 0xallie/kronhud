package io.github.darkkronicle.kronhud.gui.hud;

import io.github.darkkronicle.kronhud.config.KronConfig;
import io.github.darkkronicle.kronhud.gui.AbstractHudEntry;
import io.github.darkkronicle.kronhud.util.DrawPosition;
import io.github.darkkronicle.kronhud.util.ItemUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class ArmorHud extends AbstractHudEntry {
    public static final Identifier ID = new Identifier("kronhud", "armorhud");

    public ArmorHud() {
        super(20, 100);
    }

    @Override
    public void render(MatrixStack matrices, float delta) {
        matrices.push();
        scale(matrices);
        DrawPosition pos = getPos();
        if (background.getValue() && backgroundColor.getValue().alpha() > 0) {
            fillRect(matrices, getRenderBounds(), backgroundColor.getValue());
        }
        if (outline.getValue() && outlineColor.getValue().alpha() > 0) {
            outlineRect(matrices, getRenderBounds(), outlineColor.getValue());
        }
        int lastY = 2 + (4 * 20);
        renderMainItem(matrices, client.player.getInventory().getMainHandStack(), pos.x() + 2, pos.y() + lastY);
        lastY = lastY - 20;
        for (int i = 0; i <= 3; i++) {
            ItemStack item = client.player.getInventory().armor.get(i);
            renderItem(matrices, item, pos.x() + 2, lastY + pos.y());
            lastY = lastY - 20;
        }
        matrices.pop();
    }

    public void renderItem(MatrixStack matrices, ItemStack stack, int x, int y) {
        ItemUtil.renderGuiItemModel(getScale(), stack, x, y);
        ItemUtil.renderGuiItemOverlay(matrices, client.textRenderer, stack, x, y, null, textColor.getValue().color(), shadow.getValue());
    }

    public void renderMainItem(MatrixStack matrices, ItemStack stack, int x, int y) {
        ItemUtil.renderGuiItemModel(getScale(), stack, x, y);
        String total = String.valueOf(ItemUtil.getTotal(client, stack));
        if (total.equals("1")) {
            total = null;
        }
        ItemUtil.renderGuiItemOverlay(matrices, client.textRenderer, stack, x, y, total, textColor.getValue().color(), shadow.getValue());
    }

    @Override
    public void renderPlaceholder(MatrixStack matrices, float delta) {
        matrices.push();
        renderPlaceholderBackground(matrices);
        scale(matrices);
        DrawPosition pos = getPos();
        int lastY = 2 + (4 * 20);
        ItemUtil.renderGuiItemModel(getScale(), new ItemStack(Items.GRASS_BLOCK), pos.x() + 2, pos.y() + lastY);
        ItemUtil.renderGuiItemOverlay(matrices, client.textRenderer, new ItemStack(Items.GRASS_BLOCK), pos.x() + 2, pos.y() + lastY, "90",
                textColor.getValue().color(), shadow.getValue()
        );
        hovered = false;
        matrices.pop();
    }

    @Override
    public boolean movable() {
        return true;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public List<KronConfig<?>> getConfigurationOptions() {
        List<KronConfig<?>> options = super.getConfigurationOptions();
        options.add(textColor);
        options.add(shadow);
        options.add(background);
        options.add(backgroundColor);
        options.add(outline);
        options.add(outlineColor);
        return options;
    }

}
