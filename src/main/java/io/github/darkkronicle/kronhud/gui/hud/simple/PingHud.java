package io.github.darkkronicle.kronhud.gui.hud.simple;

import io.github.darkkronicle.kronhud.gui.entry.SimpleTextHudEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.PerformanceLog;


public class PingHud extends SimpleTextHudEntry {
    public static final Identifier ID = new Identifier("kronhud", "pinghud");

    private long lastTick = -1;
    private long lastUpdate = -1;
    private double ping = -1;

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public String getValue() {
        if (ping < 0) {
            return "- ms";
        }
        return String.format("%d ms", Math.round(ping));
    }

    @Override
    public String getPlaceholder() {
        return "68 ms";
    }

    public void updateTime(long ticks) {
        if (lastTick < 0) {
            lastTick = ticks;
            lastUpdate = System.nanoTime();
            return;
        }

        long time = System.nanoTime();
        // In nano seconds, so 1000000000 in a second
        // Or 1000000 in a millisecond
        double elapsedMilli = (time - lastUpdate) / 1000000d;
        int passedTicks = (int) (ticks - lastTick);
        if (passedTicks > 0) {
            PerformanceLog pingLog = MinecraftClient.getInstance().getDebugHud().getPingLog();
            if (pingLog.size() > 0 && pingLog.getMaxIndex() > 0) {
                int size = pingLog.getMaxIndex();
                int total = 0;
                for (int i = 0; i < size - 1; i++) {
                    total += pingLog.get(i);
                }
                ping = total / size;
            }
        }

        lastTick = ticks;
        lastUpdate = time;

    }
}
