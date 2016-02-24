package link.arata.android.mrconsumable.validator;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;

import link.arata.android.common.validator.Validator;
import link.arata.android.common.validator.ValidatorUtil;

public abstract class EditTextValidatorUtil {
    /**
     * EditTextのエラーチェックを行う。エラーの場合falseを返す
     *
     * @param editText   検査するEditText
     * @param validators 検査するValidator
     * @return エラーの場合false
     */
    public static boolean valid(Context context, EditText editText, Validator... validators) {
        String text = editText.getText().toString();
        String message = ValidatorUtil.validate(context, text, validators);
        if (message != null) {
            editText.setError(message);
            return false;
        }
        return true;
    }
}
