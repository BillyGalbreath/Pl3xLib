package net.pl3x.lib.animation;

import java.util.ArrayList;
import java.util.List;
import net.pl3x.lib.util.Colors;
import net.pl3x.lib.util.Mathf;

@SuppressWarnings("unused")
public class Animation {
    public static final List<Animation> ANIMATIONS = new ArrayList<>();

    private final float start;
    private final float end;
    private final int ticks;
    private final Easing.Function function;

    private float deltaSum;
    private boolean finished;
    private float value;

    public Animation(float start, float end, int ticks, Easing.Function function) {
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
        this.value = lerp(this.start, this.end, step, this.function);

        if (step >= 1) {
            this.finished = true;
            this.value = 1;
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public float lerp(float start, float end, float step, Easing.Function function) {
        return isEnabled() ? Mathf.lerp(start, end, tween(step, function)) : end;
    }

    public int lerpARGB(int start, int end, float step, Easing.Function function) {
        return isEnabled() ? Colors.lerpARGB(start, end, tween(step, function)) : end;
    }

    public float tween(float step, Easing.Function function) {
        return isEnabled() ? (function != null ? function.apply(step) : step) : step;
    }
}
