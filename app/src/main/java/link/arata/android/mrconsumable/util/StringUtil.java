package link.arata.android.mrconsumable.util;

public class StringUtil {
    /**
     * 文字列がnullか空文字列かをチェックする
     * @param target 検査する文字列
     * @return 文字列がnullもしくは空文字列の場合true
     */
    public static boolean isEmpty(String target) {
        if (target == null) {
            return true;
        }
        if (target.length() == 0) {
            return true;
        }
        return false;
    }
}
