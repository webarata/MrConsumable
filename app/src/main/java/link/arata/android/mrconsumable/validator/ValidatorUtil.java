package link.arata.android.mrconsumable.validator;

import android.support.annotation.Nullable;

import java.util.List;

public abstract class ValidatorUtil {
    /**
     * textをvalidatorsでチェックする。<br>
     * 最初に見つかったエラーを返す。<br>
     * validationにすべて成功した場合にはnullを返す
     * @param text 検査するテキスト
     * @param validators textを検査するValidator
     * @return エラーメッセージ。なければnull
     */
    @Nullable
    public static String validate(String text, Validator... validators) {
        String trimText = text.trim();
        String message = null;
        for (Validator validator: validators) {
            message = validator.validate(trimText);
            if (message != null) {
                break;
            }
        }
        return message;
    }
}
