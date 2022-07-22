package com.lairon.plugin.playertitles.title;

import lombok.NonNull;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimationTitle extends Title{

    private List<String> animation;
    private double animationCount = 0;
    private int speed;
    private String currentTitle = "";
    @Setter
    private boolean cancel = false;

    public AnimationTitle(@NonNull String id,@NonNull String title,@NonNull String skullName,@NonNull List<String> animation,@NonNull int speed,@NonNull Plugin main) {
        super(id, title, skullName);
        this.animation = animation;
        this.speed = speed;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(cancel) this.cancel();
                animationCount += speed;
                if(animationCount > animation.size() -1 ) animationCount = 0;
                currentTitle = animation.get((int) animationCount);
            }
        }.runTaskTimerAsynchronously(main, speed, speed);
    }


    @Override
    public String getTitle() {
        return currentTitle;
    }
}
