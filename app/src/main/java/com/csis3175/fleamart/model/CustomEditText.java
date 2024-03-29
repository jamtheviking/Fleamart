package com.csis3175.fleamart.model;

import android.content.Context;
import android.util.AttributeSet;



//Search Edit Text example  https://github.com/syuraj/Fastly/tree/master/app/src/main
//This class overrides performClick so that we can configure onTouch and on Key listener

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

}
