package link.arata.android.mrconsumable.validator;

import android.support.annotation.Nullable;

public class RequiredValidator implements Validator {
    @Nullable
    @Override
    public String validate(String text) {
        if (text.trim().length() == 0) {
            return "入力してください";
        }
        return null;
    }
}
