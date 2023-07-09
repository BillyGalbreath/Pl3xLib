package net.pl3x.lib.gui.animation;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.pl3x.lib.util.Colors;
import net.pl3x.lib.util.Mathf;
import org.jetbrains.annotations.NotNull;

public class Animation {
    private static final List<Animation> ANIMATIONS = new ArrayList<>();

    public static void tick(float delta) {
        Iterator<Animation> iter = ANIMATIONS.iterator();
        while (iter.hasNext()) {
            Animation animation = iter.next();
            animation.tickAnimation(delta);
            if (animation.isFinished()) {
                iter.remove();
            }
        }
    }

    private final float start;
    private final float end;
    private final int ticks;
    private final Easing.Function function;

    private float deltaSum;
    private boolean finished;
    private float value;

    public Animation(float start, float end, int ticks, @NotNull Easing.Function function) {
        Preconditions.checkNotNull(function, "Function cannot be null");

        this.start = start;
        this.end = end;
        this.ticks = ticks;
        this.function = function;

        ANIMATIONS.add(this);
    }

    public float getValue() {
        return this.value;
    }

    public void tickAnimation(float delta) {
        float step = Mathf.inverseLerp(0, this.ticks, this.deltaSum += delta);
        this.value = lerp(this.start, this.end, step);

        if (this.deltaSum >= this.ticks) {
            this.finished = true;
            this.value = this.end;
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public float lerp(float start, float end, float step) {
        return isEnabled() ? Mathf.lerp(start, end, tween(step)) : end;
    }

    public int lerpARGB(int start, int end, float step) {
        return isEnabled() ? Colors.lerpARGB(start, end, tween(step)) : end;
    }

    public float tween(float step) {
        return isEnabled() ? this.function.apply(step) : step;
    }
}
