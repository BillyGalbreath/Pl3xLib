package net.pl3x.lib.gui.animation;

import com.google.common.base.Preconditions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.pl3x.lib.util.Colors;
import net.pl3x.lib.util.Mathf;
import org.jetbrains.annotations.NotNull;

public class Animation {
    private static final Set<Animation> ANIMATIONS = new HashSet<>();

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

    private float start;
    private float end;
    private float ticks;
    private boolean repeat;
    private Easing.Function function;

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
        setStart(start);
        setEnd(end);
        setTicks(ticks);
        setRepeating(repeat);
        setFunction(function);

        setValue(getStart());

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

    @NotNull
    public Easing.Function getFunction() {
        return this.function;
    }

    @NotNull
    public Easing.Function setFunction(@NotNull Easing.Function function) {
        Preconditions.checkNotNull(function, "Function cannot be null");
        return this.function = function;
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

    public boolean isEnabled() {
        return true;
    }

    public boolean isFinished() {
        return getDeltaSum() >= getTicks();
    }

    public void start() {
        ANIMATIONS.add(this);
    }

    public void stop() {
        ANIMATIONS.remove(this);
    }

    public void reset() {
        setDeltaSum(0);
        setValue(getStart());
    }

    public void tickAnimation(float delta) {
        float step = Mathf.inverseLerp(0, getTicks(), setDeltaSum(getDeltaSum() + delta));
        setValue(lerp(getStart(), getEnd(), step));

        if (isFinished()) {
            setValue(getEnd());
            if (isRepeating()) {
                reset();
            }
        }
    }

    public float lerp(float start, float end, float step) {
        return isEnabled() ? Mathf.lerp(start, end, tween(step)) : end;
    }

    public int lerpARGB(int start, int end, float step) {
        return isEnabled() ? Colors.lerpARGB(start, end, tween(step)) : end;
    }

    public float tween(float step) {
        return isEnabled() ? getFunction().apply(step) : step;
    }
}
