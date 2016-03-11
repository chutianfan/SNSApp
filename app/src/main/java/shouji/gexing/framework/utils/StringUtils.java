package shouji.gexing.framework.utils;

import android.text.TextUtils;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import qalsdk.BaseConstants;

public class StringUtils {
    public static final String CLASSNAME_ARRAY = "java.util.ArrayList";
    public static final String CLASSNAME_BOOLEAN = "boolean";
    public static final String CLASSNAME_BYTE = "byte";
    public static final String CLASSNAME_CHAR = "char";
    public static final String CLASSNAME_DOUBLE = "double";
    public static final String CLASSNAME_FLOAT = "float";
    public static final String CLASSNAME_INT = "int";
    public static final String CLASSNAME_LONG = "long";
    public static final String CLASSNAME_STRING = "java.lang.String";
    public static int index;

    public static boolean isNotBlank(String str) {
        if (str == null || BaseConstants.ah.equals(str.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isNotBlank(String... strings) {
        for (String str : strings) {
            if (str == null || BaseConstants.ah.equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    static {
        index = -1;
    }

    public static ArrayList<String> getNewArray(ArrayList<String> array, int end) {
        ArrayList<String> newArray = new ArrayList();
        int i = 0;
        while (i < array.size()) {
            String str = (String) array.get(i);
            if (FaceUtils.hasFace(str)) {
                newArray.add(str);
            } else if ("[".equals(str)) {
                String str1 = "[";
                if (i == array.size() - 1) {
                    newArray.add(str);
                }
                int j = i + 1;
                while (j < array.size()) {
                    String string = (String) array.get(j);
                    str1 = new StringBuilder(String.valueOf(str1)).append(string).toString();
                    if (!"]".equals(string)) {
                        if (j == array.size() - 1) {
                            newArray.add(str);
                        }
                        j++;
                    } else if (FaceUtils.hasFace(str1)) {
                        newArray.add(str1);
                        i = j;
                        if (i > end) {
                            index = newArray.size();
                        }
                    } else {
                        newArray.add(str);
                    }
                }
            } else {
                newArray.add(str);
            }
            if (i == end) {
                index = newArray.size();
            }
            i++;
        }
        array.clear();
        return newArray;
    }

    public static String stringArrayToHtml(ArrayList<String> array) {
        String str = BaseConstants.ah;
        Iterator it = array.iterator();
        while (it.hasNext()) {
            str = new StringBuilder(String.valueOf(str)).append(FaceUtils.getFace((String) it.next())).toString();
        }
        return str;
    }

    public static String stringArrayToString(ArrayList<String> array) {
        String str = BaseConstants.ah;
        Iterator it = array.iterator();
        while (it.hasNext()) {
            str = new StringBuilder(String.valueOf(str)).append((String) it.next()).toString();
        }
        return str;
    }

    public static String getGenericsType(String type) {
        return type.substring(type.indexOf("<") + 1, type.indexOf(">"));
    }

    public static String setColor(String str, String color) {
        return "<font color='#" + color + "'>" + str + "</font>";
    }

    public static boolean isBaseDataType(String dataType) {
        if (CLASSNAME_BOOLEAN.equals(dataType) || CLASSNAME_INT.equals(dataType) || CLASSNAME_LONG.equals(dataType) || CLASSNAME_STRING.equals(dataType) || CLASSNAME_BYTE.equals(dataType) || CLASSNAME_CHAR.equals(dataType) || CLASSNAME_DOUBLE.equals(dataType) || CLASSNAME_FLOAT.equals(dataType)) {
            return true;
        }
        return false;
    }

    public static boolean getEmail(String line) {
        return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(line).find();
    }

    public static boolean checkStringLengthRange(String str, int minLen, int maxLen) {
        if (str.length() < minLen || str.length() > maxLen) {
            return false;
        }
        return true;
    }

    public static float getTextCount(String text) {
        float chineseCount = (float) ((text.getBytes().length - text.length()) / 2);
        return ((((float) text.length()) - chineseCount) / 2.0f) + chineseCount;
    }

    public static String getSubStringIfCountOut(String string, int flagNum) {
        String subString = BaseConstants.ah;
        String sub = BaseConstants.ah;
        float count4 = 0.0f;
        float needCount = (float) flagNum;
        int startIndex = 0;
        int endIndex = flagNum;
        while (count4 < ((float) flagNum)) {
            sub = string.substring(startIndex, endIndex);
            float textCount3 = getTextCount(sub);
            if (textCount3 > needCount) {
                return new StringBuilder(String.valueOf(subString)).append(sub.substring(0, Math.round(needCount) - 1)).toString();
            }
            if (textCount3 == needCount) {
                subString = new StringBuilder(String.valueOf(subString)).append(sub).toString();
                count4 += textCount3;
                needCount = ((float) flagNum) - count4;
            } else {
                subString = new StringBuilder(String.valueOf(subString)).append(sub).toString();
                count4 += textCount3;
                needCount = ((float) flagNum) - count4;
                startIndex = endIndex;
                endIndex = startIndex + Math.round(needCount);
            }
        }
        return subString;
    }

    public static String getCharSequence(String content) {
        int start = -1;
        int end = -1;
        while (true) {
            start = content.indexOf("[", start + 1);
            end = content.indexOf("]", end + 1);
            if (start == -1) {
                break;
            }
            if (end < start) {
                end = content.indexOf("]", start);
            }
            if (end < start) {
                break;
            }
            String facename = content.substring(start, end + 1);
            if (FaceUtils.getFaceNameResource().indexOf(facename) != -1) {
                content = content.replace(facename, "<img src='" + ((Integer) FaceUtils.getFaceResource().get(FaceUtils.getFaceNameResource().indexOf(facename))).intValue() + "'/>");
            }
        }
        return content;
    }

    public static String getMethodName(String field) {
        if (!field.startsWith("is")) {
            return "get" + firstCharUpperCase(field);
        }
        if (field.length() < 3 || !Character.isUpperCase(field.charAt(2))) {
            return "is" + firstCharUpperCase(field);
        }
        return field;
    }

    public static String setMehtodName(String str) {
        return "set" + firstCharUpperCase(str);
    }

    public static String firstCharUpperCase(String str) {
        return new StringBuilder(String.valueOf(str.substring(0, 1).toUpperCase())).append(str.substring(1)).toString();
    }

    private static String chinaToUnicode(String str) {
        String result = BaseConstants.ah;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if (chr1 < 19968 || chr1 > 171941) {
                result = new StringBuilder(String.valueOf(result)).append(str.charAt(i)).toString();
            } else {
                result = new StringBuilder(String.valueOf(result)).append("\\u").append(Integer.toHexString(chr1)).toString();
            }
        }
        return result;
    }

    public static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        if (ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChinese(String str) {
        boolean result = false;
        for (char c : chinaToUnicode(str).toCharArray()) {
            result = isChinese(c);
        }
        return result;
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        int i = 0;
        while (i < c.length) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uff00' && c[i] < '\uff5f') {
                c[i] = (char) (c[i] - 65248);
            }
            i++;
        }
        return new String(c);
    }

    public static String stringFilter(String str) {
        return Pattern.compile("[\u300e\u300f]").matcher(str.replaceAll("\u3010", "[").replaceAll("\u3011", "]").replaceAll("\uff01", "!").replaceAll("\uff1a", ":")).replaceAll(BaseConstants.ah).trim();
    }

    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str) || "NULL".equals(str) || str.equals(" ") || str.trim().equals(BaseConstants.ah)) {
            return true;
        }
        return false;
    }

    public static boolean isVerificationPassword(String str) {
        if (isEmpty(str) || str.length() < 6 || str.length() > 12) {
            return false;
        }
        return true;
    }
}
