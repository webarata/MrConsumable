package link.arata.android.mrconsumable.validator;

import android.support.annotation.Nullable;

public interface Validator {
    /**
     * バリデーションする
     * @param text バリデーション対象の文字列
     * @return エラーの場合エラーメッセージを返す。エラーでない場合nullを返す。
     */
    @Nullable
    String validate(String text);
}
