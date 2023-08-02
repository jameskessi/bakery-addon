package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.events.entity.player.InteractEntityEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

import java.util.List;

public class ItemFrameDupe extends Module {
    public boolean isDuping = false;

    private final Setting<Double> destroyTime = settings.getDefaultGroup().add(new DoubleSetting.Builder()
        .name("Destroy Time")
        .description("Delay for breaking / replacing.")
        .defaultValue(50)
        .min(1)
        .max(1000)
        .sliderMin(50)
        .sliderMax(1000)
        .build()
    );

    private final Setting<Boolean> alwaysActive = settings.getDefaultGroup().add(new BoolSetting.Builder()
        .name("Always on")
        .description("Always dupes without interaction.")
        .defaultValue(false)
        .build()
    );
    public ItemFrameDupe() {
        super(Addon.CATEGORY, "Item Frame Dupe", "Dupes an item by quickly replacing an item frame.");
    }

    @Override
    public void onActivate() {
        super.onActivate();
        doItemFrameDupe();
    }

    public boolean getShouldDupe()
    {
        if (isActive()) {
            if (!alwaysActive.get()) {
                return mc.mouse.wasRightButtonClicked();
            }
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onInteractItemFrame(InteractEntityEvent interactEntityEvent)
    {
        if (getShouldDupe()) {
            if (!isDuping) {
                if (!(interactEntityEvent.entity instanceof ItemFrameEntity)) {
                    return;
                }
                Thread t = new Thread(this::doItemFrameDupe);
                t.start();
            } else {
                return;
            }
        }
    }

    public void doItemFrameDupe() throws AssertionError {
        isDuping = true;
        ClientPlayerInteractionManager c = mc.interactionManager;
        PlayerEntity p = mc.player;
        ClientWorld w = mc.world;
        if (c != null) {
            if (p != null) {
                if (w != null) {
                    List<ItemFrameEntity> itemFrames;
                    ItemFrameEntity itemFrame;
                    Box box;

                    while (true) {
                        if (getShouldDupe()) {
                            try {
                                Thread.sleep((long) (destroyTime.get() * 0.5));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            box = new Box(p.getEyePos().add(-3, -3, -3), p.getEyePos().add(3, 3, 3));
                            itemFrames = w.getEntitiesByClass(ItemFrameEntity.class, box, itemFrameEntity -> true);
                            if (!itemFrames.isEmpty()) {
                                itemFrame = itemFrames.get(0);
                                c.interactEntity(p, itemFrame, Hand.MAIN_HAND);
                                try {
                                    Thread.sleep((long) (destroyTime.get() * 0.5));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (itemFrame.getHeldItemStack().getCount() <= 0) {
                                    continue;
                                }

                                c.interactEntity(p, itemFrame, Hand.MAIN_HAND);

                                try {
                                    Thread.sleep((long) (destroyTime.get() * 0.7));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                c.attackEntity(p, itemFrame);
                                try {
                                    Thread.sleep((long) (destroyTime.get() * 0.7));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            break;
                        }

                    }
                    isDuping = false;
                } else {
                    throw new AssertionError();
                }
            } else {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }

    }
}
