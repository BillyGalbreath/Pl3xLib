package net.pl3x.lib.gui.animation;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import net.pl3x.lib.util.Mathf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Animation {
    private static final Set<Animation> ANIMATIONS = ConcurrentHashMap.newKeySet();

    public static void tick(float delta) {
        try {
            Iterator<Animation> iter = ANIMATIONS.iterator();
            while (iter.hasNext()) {
                Animation animation = iter.next();
                animation.tickAnimation(delta);
                if (animation.isFinished()) {
                    iter.remove();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private float start;
    private float end;
    private float ticks;
    private boolean repeat;
    private Easing.Function function;
    private Consumer<Animation> onFinish;

    private float deltaSum;
    private float value;

    public Animation(int ticks) {
        this(0, 1, ticks);
    }

    public Animation(float start, float end, int ticks) {
        this(start, end, ticks, Easing.Linear.flat);
    }

    public Animation(float start, float end, float ticks, @NotNull Easing.Function function) {
        this(start, end, ticks, false, function);
    }

    public Animation(float start, float end, float ticks, boolean repeat, @NotNull Easing.Function function) {
        this(start, end, ticks, repeat, function, null);
    }

    public Animation(float start, float end, float ticks, boolean repeat, @NotNull Easing.Function function, @Nullable Consumer<Animation> onFinish) {
        this.start = start;
        this.end = end;
        this.ticks = ticks;
        this.repeat = repeat;
        this.function = function;
        this.onFinish = onFinish;

        this.value = this.start;

        ANIMATIONS.add(this);
    }

    public float getStart() {
        return this.start;
    }

    public float setStart(float start) {
        return this.start = start;
    }

    public float getEnd() {
        return this.end;
    }

    public float setEnd(float end) {
        return this.end = end;
    }

    public float getTicks() {
        return this.ticks;
    }

    public float setTicks(float ticks) {
        return this.ticks = ticks;
    }

    public boolean isRepeating() {
        return this.repeat;
    }

    public boolean setRepeating(boolean repeat) {
        return this.repeat = repeat;
    }

    public @NotNull Easing.Function getFunction() {
        return this.function;
    }

    public @NotNull Easing.Function setFunction(@NotNull Easing.Function function) {
        Preconditions.checkNotNull(function, "Function cannot be null");
        return this.function = function;
    }

    public @Nullable Consumer<Animation> getOnFinish() {
        return this.onFinish;
    }

    public @Nullable Consumer<Animation> setOnFinish(@Nullable Consumer<Animation> onFinish) {
        return this.onFinish = onFinish;
    }

    public float getDeltaSum() {
        return this.deltaSum;
    }

    public float setDeltaSum(float deltaSum) {
        return this.deltaSum = deltaSum;
    }

    public float getValue() {
        return this.value;
    }

    public float setValue(float value) {
        return this.value = value;
    }

    public boolean isFinished() {
        return this.deltaSum >= this.ticks;
    }

    public void start() {
        ANIMATIONS.add(this);
    }

    public void stop() {
        ANIMATIONS.remove(this);
    }

    public void reset() {
        this.deltaSum = 0;
        this.value = this.start;
    }

    public void tickAnimation(float delta) {
        float step = Mathf.inverseLerp(0, this.ticks, this.deltaSum += delta);
        this.value = Mathf.lerp(this.start, this.end, this.function.apply(step));

        if (isFinished()) {
            float oldTicks = this.ticks;
            if (this.onFinish != null) {
                this.onFinish.accept(this);
            }
            if (this.repeat) {
                this.deltaSum -= (int) this.deltaSum;
                this.deltaSum = Math.max(this.deltaSum - oldTicks, 0);
                this.value = this.start;
            } else {
                this.value = this.end;
            }
        }
    }
}
