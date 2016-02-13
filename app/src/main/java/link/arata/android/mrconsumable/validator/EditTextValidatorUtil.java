package link.arata.android.mrconsumable.validator;

import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;

public abstract class EditTextValidatorUtil {
    /**
     * EditTextのエラーチェックを行う。エラーの場合falseを返す
     * @param editText 検査するEditText
     * @param validators 検査するValidator
     * @return エラーの場合false
     */
    public static boolean valid(EditText editText, Validator... validators) {
        String text = editText.getText().toString();
        for (Validator validator : validators) {
            String message = validator.validate(text);
            if (message != null) {
                editText.setError(message);
                return false;
            }
        }
        return true;
    }

    /**
     * EditTextのエラーチェックを行う。エラーの場合falseを返す
     * @param editText 検査するEditText
     * @param validators 検査するValidator
     * @return エラーの場合false
     */
    public static boolean valid(AppCompatEditText editText, Validator... validators) {
        String text = editText.getText().toString();
        for (Validator validator : validators) {
            String message = validator.validate(text);
            if (message != null) {
                editText.setError(message);
                return false;
            }
        }
        return true;
    }

}
