package net.pl3x.lib.util;

import net.minecraft.util.Mth;

public class Mathf {
    public static final float PI = (float) Math.PI;
    public static final float SQRT_OF_2 = Mathf.sqrt(2F);
    public static final float DEG_TO_RAD = PI / 180F;

    public static float cosRads(float degree) {
        return Mth.cos(degree * DEG_TO_RAD);
    }

    public static float sinRads(float degree) {
        return Mth.sin(degree * DEG_TO_RAD);
    }

    public static float cos(float value) {
        return Mth.cos(value);
    }

    public static float sin(float value) {
        return Mth.sin(value);
    }

    public static double clamp(double min, double max, double value) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(float min, float max, float value) {
        return Math.min(Math.max(value, min), max);
    }

    public static int clamp(int min, int max, int value) {
        return Math.min(Math.max(value, min), max);
    }

    public static double lerp(double start, double end, double step) {
        return start + step * (end - start);
    }

    public static float lerp(float start, float end, float step) {
        return start + step * (end - start);
    }

    public static double inverseLerp(double start, double end, double step) {
        return (step - start) / (end - start);
    }

    public static float inverseLerp(float start, float end, float step) {
        return (step - start) / (end - start);
    }

    public static double remap(double origStart, double origEnd, double targetStart, double targetEnd, double step) {
        return lerp(targetStart, targetEnd, inverseLerp(origStart, origEnd, step));
    }

    public static float remap(float origStart, float origEnd, float targetStart, float targetEnd, float step) {
        return lerp(targetStart, targetEnd, inverseLerp(origStart, origEnd, step));
    }

    public static int distanceSquared(int x1, int z1, int x2, int z2) {
        return square(x1 - x2) + square(z1 - z2);
    }

    public static int square(int n) {
        return n * n;
    }

    public static int pow2(int value) {
        return 1 << value;
    }

    public static double pow(double value, double power) {
        return Math.pow(value, power);
    }

    public static float pow(float value, float power) {
        return (float) Math.pow(value, power);
    }

    public static double sqrt(double value) {
        return Math.sqrt(value);
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static long asLong(long x, long z) {
        return (x & 0xFFFFFFFFL) | (z & 0xFFFFFFFFL) << 32;
    }

    public static int longToX(long pos) {
        return (int) (pos & 0xFFFFFFFFL);
    }

    public static int longToZ(long pos) {
        return (int) (pos >>> 32 & 0xFFFFFFFFL);
    }
}
