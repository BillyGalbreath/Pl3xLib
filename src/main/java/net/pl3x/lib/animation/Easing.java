package net.pl3x.lib.animation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.pl3x.lib.util.Mathf;

@SuppressWarnings("unused")
public class Easing {
    public static class Back {
        public static final Function in = new Function("back-in", Back::in);
        public static final Function out = new Function("back-out", Back::out);
        public static final Function inOut = new Function("back-in-out", Back::inOut);

        private static final float s = 1.70158F;
        private static final float s2 = 2.5949095F;

        public static float in(float t) {
            return t * t * ((s + 1F) * t - s);
        }

        public static float out(float t) {
            return (t -= 1F) * t * ((s + 1F) * t + s) + 1;
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return 0.5F * (t * t * ((s2 + 1F) * t - s2));
            return 0.5F * ((t -= 2F) * t * ((s2 + 1F) * t + s2) + 2F);
        }
    }

    public static class Bounce {
        public static final Function in = new Function("bounce-in", Bounce::in);
        public static final Function out = new Function("bounce-out", Bounce::out);
        public static final Function inOut = new Function("bounce-in-out", Bounce::inOut);

        public static float in(float t) {
            return 1F - out(1F - t);
        }

        public static float out(float t) {
            if (t < (1F / 2.75F)) {
                return 7.5625F * t * t;
            } else if (t < (2F / 2.75F)) {
                return 7.5625F * (t -= (1.5F / 2.75F)) * t + 0.75F;
            } else if (t < (2.5F / 2.75F)) {
                return 7.5625F * (t -= (2.25F / 2.75F)) * t + 0.9375F;
            } else {
                return 7.5625F * (t -= (2.625F / 2.75F)) * t + 0.984375F;
            }
        }

        public static float inOut(float t) {
            if (t < 0.5F) return in(t * 2F) * 0.5F;
            return out(t * 2F - 1F) * 0.5F + 0.5F;
        }
    }

    public static class Circular {
        public static final Function in = new Function("circular-in", Circular::in);
        public static final Function out = new Function("circular-out", Circular::out);
        public static final Function inOut = new Function("circular-in-out", Circular::inOut);

        public static float in(float t) {
            return 1F - Mathf.sqrt(1F - t * t);
        }

        public static float out(float t) {
            return Mathf.sqrt(1F - ((t -= 1F) * t));
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return -0.5F * (Mathf.sqrt(1F - t * t) - 1F);
            return 0.5F * (Mathf.sqrt(1F - (t -= 2F) * t) + 1F);
        }
    }

    public static class Cubic {
        public static final Function in = new Function("cubic-in", Cubic::in);
        public static final Function out = new Function("cubic-out", Cubic::out);
        public static final Function inOut = new Function("cubic-in-out", Cubic::inOut);

        public static float in(float t) {
            return t * t * t;
        }

        public static float out(float t) {
            return 1F + ((t -= 1F) * t * t);
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return 0.5F * t * t * t;
            return 0.5F * ((t -= 2F) * t * t + 2F);
        }
    }

    public static class Elastic {
        public static final Function in = new Function("elastic-in", Elastic::in);
        public static final Function out = new Function("elastic-out", Elastic::out);
        public static final Function inOut = new Function("elastic-in-out", Elastic::inOut);

        public static float in(float t) {
            if (t == 0F) return 0F;
            if (t == 1F) return 1F;
            return -Mathf.pow(2F, 10F * (t -= 1F)) * Mathf.sin((t - 0.1F) * (2F * Mathf.PI) / 0.4F);
        }

        public static float out(float t) {
            if (t == 0F) return 0F;
            if (t == 1F) return 1F;
            return Mathf.pow(2F, -10F * t) * Mathf.sin((t - 0.1F) * (2F * Mathf.PI) / 0.4F) + 1F;
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F)
                return -0.5F * Mathf.pow(2F, 10F * (t -= 1F)) * Mathf.sin((t - 0.1F) * (2F * Mathf.PI) / 0.4F);
            return Mathf.pow(2F, -10F * (t -= 1F)) * Mathf.sin((t - 0.1F) * (2F * Mathf.PI) / 0.4F) * 0.5F + 1F;
        }
    }

    public static class Exponential {
        public static final Function in = new Function("exponential-in", Exponential::in);
        public static final Function out = new Function("exponential-out", Exponential::out);
        public static final Function inOut = new Function("exponential-in-out", Exponential::inOut);

        public static float in(float t) {
            return t == 0F ? 0F : Mathf.pow(1024F, t - 1F);
        }

        public static float out(float t) {
            return t == 1F ? 1F : 1F - Mathf.pow(2F, -10F * t);
        }

        public static float inOut(float t) {
            if (t == 0F) return 0F;
            if (t == 1F) return 1F;
            if ((t *= 2F) < 1F) return 0.5F * Mathf.pow(1024F, t - 1F);
            return 0.5F * (-Mathf.pow(2F, -10F * (t - 1F)) + 2F);
        }
    }

    public static class Quadratic {
        public static final Function in = new Function("quadratic-in", Quadratic::in);
        public static final Function out = new Function("quadratic-out", Quadratic::out);
        public static final Function inOut = new Function("quadratic-in-out", Quadratic::inOut);

        public static float in(float t) {
            return t * t;
        }

        public static float out(float t) {
            return t * (2F - t);
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return 0.5F * t * t;
            return -0.5F * ((t -= 1F) * (t - 2F) - 1F);
        }
    }

    public static class Quartic {
        public static final Function in = new Function("quartic-in", Quartic::in);
        public static final Function out = new Function("quartic-out", Quartic::out);
        public static final Function inOut = new Function("quartic-in-out", Quartic::inOut);

        public static float in(float t) {
            return t * t * t * t;
        }

        public static float out(float t) {
            return 1F - ((t -= 1F) * t * t * t);
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return 0.5F * t * t * t * t;
            return -0.5F * ((t -= 2F) * t * t * t - 2F);
        }
    }

    public static class Quintic {
        public static final Function in = new Function("quintic-in", Quintic::in);
        public static final Function out = new Function("quintic-out", Quintic::out);
        public static final Function inOut = new Function("quintic-in-out", Quintic::inOut);

        public static float in(float t) {
            return t * t * t * t * t;
        }

        public static float out(float t) {
            return 1F + ((t -= 1F) * t * t * t * t);
        }

        public static float inOut(float t) {
            if ((t *= 2F) < 1F) return 0.5F * t * t * t * t * t;
            return 0.5F * ((t -= 2F) * t * t * t * t + 2F);
        }
    }

    public static class Sinusoidal {
        public static final Function in = new Function("sinusoidal-in", Sinusoidal::in);
        public static final Function out = new Function("sinusoidal-out", Sinusoidal::out);
        public static final Function inOut = new Function("sinusoidal-in-out", Sinusoidal::inOut);

        public static float in(float t) {
            return 1F - Mathf.cos(t * Mathf.PI / 2F);
        }

        public static float out(float t) {
            return Mathf.sin(t * Mathf.PI / 2F);
        }

        public static float inOut(float t) {
            return 0.5F * (1F - Mathf.cos(Mathf.PI * t));
        }
    }

    public record Function(String name, PrimitiveFloat function) {
        private static final Map<String, Function> BY_NAME = new HashMap<>();

        public Function {
            BY_NAME.put(name, this);
        }

        public float apply(float t) {
            return function().apply(t);
        }

        @FunctionalInterface
        private interface PrimitiveFloat {
            float apply(float t);
        }

        public static class Adapter implements JsonSerializer<Function>, JsonDeserializer<Function> {
            @Override
            public JsonElement serialize(Function function, Type type, JsonSerializationContext context) {
                return new JsonPrimitive(function.name);
            }

            @Override
            public Function deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
                return Function.BY_NAME.get(json.getAsString());
            }
        }
    }
}
