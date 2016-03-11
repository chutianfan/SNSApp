package shouji.gexing.framework.utils;

import android.app.Activity;

import com.gitrose.mobile.R;

import java.util.ArrayList;

public class FaceUtils extends Activity {
    private static int[] face;
    private static ArrayList<Integer> faceList;
    private static String[] faceName;
    private static ArrayList<String> faceNameList;

    static {
        faceName = new String[]{"\u5fae\u7b11", "\u90c1\u95f7", "\u8272", "\u5446", "\u5f97\u610f", "\u6d41\u6cea", "\u5bb3\u7f9e", "\u95ed\u5634", "\u7761", "\u54ed\u4e86", "\u6012", "\u751f\u6c14", "\u8c03\u76ae", "\u54c8\u54c8", "\u5403\u60ca", "\u96be\u8fc7", "\u9177", "\u5bd2", "\u5b9d\u5b9d", "\u5410", "\u5077\u7b11", "\u53ef\u7231", "\u767d\u773c", "\u998b", "\u82b1\u75f4", "\u56f0", "\u7ed9\u529b", "\u5927\u7b11", "\u732e\u82b1", "\u6d41\u6c57", "\u594b\u6597", "\u5492\u9a82", "\u7591\u95ee", "\u5618", "\u6655", "\u6298\u78e8", "\u8870", "\u5916\u661f\u4eba", "\u751f\u75c5\u4e86", "\u518d\u89c1", "\u7cd7\u5927\u4e86", "\u7ea0\u7ed3", "\u9f13\u638c", "\u6316\u9f3b\u5b54", "\u574f\u7b11", "\u5de6\u5618\u5618", "\u53f3\u5618\u5618", "\u65e0\u804a", "\u9119\u89c6", "\u59d4\u5c48", "\u56e7", "\u5978\u7b11", "MUA", "\u5413!?", "\u53ef\u601c", "\u70e7\u9999", "\u5237\u7259", "\u6d6e\u4e91", "\u7ed9\u8dea\u4e86", "\u60f3\u8e6d\u996d", "\u732e\u6bb7\u52e4", "\u6446\u9614", "\u9676\u9189", "\u7231", "\u732a\u5934", "\u5c0fG", "\u86cb\u7cd5", "\u56db\u53f6\u8349", "\u4fbf\u4fbf", "\u5496\u5561", "\u793c\u7269", "\u5fc3\u788e", "\u5fc3", "\u82f9\u679c", "NO", "\u8d5e", "give me five!", "\u8036", "\u62f3\u5934", "\u5dee\u52b2", "\u95ea\u7535"};
        face = new int[]{R.drawable.face001, R.drawable.face002, R.drawable.face003, R.drawable.face004, R.drawable.face005, R.drawable.face006, R.drawable.face007, R.drawable.face008, R.drawable.face009, R.drawable.face010, R.drawable.face011, R.drawable.face012, R.drawable.face013, R.drawable.face014, R.drawable.face015, R.drawable.face016, R.drawable.face017, R.drawable.face018, R.drawable.face019, R.drawable.face020, R.drawable.face021, R.drawable.face022, R.drawable.face023, R.drawable.face024, R.drawable.face025, R.drawable.face026, R.drawable.face027, R.drawable.face028, R.drawable.face029, R.drawable.face030, R.drawable.face031, R.drawable.face032, R.drawable.face033, R.drawable.face034, R.drawable.face035, R.drawable.face036, R.drawable.face037, R.drawable.face038, R.drawable.face039, R.drawable.face040, R.drawable.face041, R.drawable.face042, R.drawable.face043, R.drawable.face044, R.drawable.face045, R.drawable.face046, R.drawable.face047, R.drawable.face048, R.drawable.face049, R.drawable.face050, R.drawable.face051, R.drawable.face052, R.drawable.face053, R.drawable.face054, R.drawable.face055, R.drawable.face056, R.drawable.face057, R.drawable.face058, R.drawable.face059, R.drawable.face060, R.drawable.face061, R.drawable.face062, R.drawable.face063, R.drawable.face064, R.drawable.face065, R.drawable.face066, R.drawable.face067, R.drawable.face068, R.drawable.face069, R.drawable.face070, R.drawable.face071, R.drawable.face072, R.drawable.face073, R.drawable.face074, R.drawable.face075, R.drawable.face076, R.drawable.face077, R.drawable.face078, R.drawable.face079, R.drawable.face080, R.drawable.face081};
        faceList = new ArrayList();
        for (int valueOf : face) {
            faceList.add(Integer.valueOf(valueOf));
        }
        faceNameList = new ArrayList();
        for (String str : faceName) {
            faceNameList.add("[" + str + "]");
        }
    }

    public static ArrayList<Integer> getFaceResource() {
        return faceList;
    }

    public static ArrayList<String> getFaceNameResource() {
        return faceNameList;
    }

    public static boolean hasFace(String face) {
        return faceNameList.contains(face);
    }

    public static String getFace(String str) {
        if (hasFace(str)) {
            return "<img src='" + faceList.get(faceNameList.indexOf(str)) + "'/>";
        }
        return str;
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
            if (getFaceNameResource().indexOf(facename) != -1) {
                content = content.replace(facename, "<img src='" + ((Integer) getFaceResource().get(getFaceNameResource().indexOf(facename))).intValue() + "'/>");
            }
        }
        return content;
    }
}
