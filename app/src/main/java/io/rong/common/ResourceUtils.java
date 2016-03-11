package io.rong.common;

import android.content.Context;
import android.content.res.Resources;

public final class ResourceUtils {
    public static final String anim = "anim";
    public static final String animator = "animator";
    public static final String array = "array";
    public static final String attr = "attr";
    public static final String bool = "bool";
    public static final String color = "color";
    public static final String dimen = "dimen";
    public static final String drawable = "drawable";
    public static final String id = "id";
    public static final String interpolator = "interpolator";
    public static final String layout = "layout";
    public static final String menu = "menu";
    public static final String mipmap = "mipmap";
    public static String packageName = null;
    public static final String raw = "raw";
    public static Resources resources = null;
    public static final String string = "string";
    public static final String stringArray = "string-array";
    public static final String style = "style";
    public static final String styleable = "styleable";
    public static final String xml = "xml";

    private ResourceUtils() {
    }

    public static void init(Context ctx) {
        packageName = ctx.getPackageName();
        resources = ctx.getResources();
    }

    public static int getResDrawableID(String resName) {
        return resources.getIdentifier(resName, drawable, packageName);
    }

    public static int getResLayoutID(String resName) {
        return resources.getIdentifier(resName, layout, packageName);
    }

    public static int getResAnimID(String resName) {
        return resources.getIdentifier(resName, anim, packageName);
    }

    public static int getResAnimatorID(String resName) {
        return resources.getIdentifier(resName, animator, packageName);
    }

    public static int getResAttrID(String resName) {
        return resources.getIdentifier(resName, attr, packageName);
    }

    public static int getResColorID(String resName) {
        return resources.getIdentifier(resName, color, packageName);
    }

    public static int getResDimenID(String resName) {
        return resources.getIdentifier(resName, dimen, packageName);
    }

    public static int getResIdID(String resName) {
        return resources.getIdentifier(resName, id, packageName);
    }

    public static int getResRawID(String resName) {
        return resources.getIdentifier(resName, raw, packageName);
    }

    public static int getResStringID(String resName) {
        return resources.getIdentifier(resName, string, packageName);
    }

    public static int getResStyleID(String resName) {
        return resources.getIdentifier(resName, style, packageName);
    }

    public static int getResStyleableID(String name) {
        return resources.getIdentifier(name, styleable, packageName);
    }

    public static int getResXmlID(String resName) {
        return resources.getIdentifier(resName, xml, packageName);
    }

    public static int getResInterpolatorID(String resName) {
        return resources.getIdentifier(resName, interpolator, packageName);
    }

    public static int getResMenuID(String resName) {
        return resources.getIdentifier(resName, menu, packageName);
    }

    public static int getResMipmapID(String resName) {
        return resources.getIdentifier(resName, mipmap, packageName);
    }

    public static int getResArrayID(String resName) {
        return resources.getIdentifier(resName, array, packageName);
    }

    public static int getResBoolID(String resName) {
        return resources.getIdentifier(resName, bool, packageName);
    }

    public static int getResStringArrayID(String resName) {
        return resources.getIdentifier(resName, stringArray, packageName);
    }
}
