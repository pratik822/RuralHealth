package com.hgs.ruralhealth.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hgs.ruralhealth.R;


public class LabelEditText extends LinearLayout {

    public EditText mEditText;
    private TextView mLabel;
    private TextView mRequired;
    private TextView mLeftLabel;
    private boolean isRequiredField;

    public LabelEditText(Context context) {
        this(context, null);
    }

    public LabelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LabelEditText, 0, 0);
        String text = a.getString(R.styleable.LabelEditText_labelText);
        int inputType = a.getInt(R.styleable.LabelEditText_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        int maxLength = a.getInt(R.styleable.LabelEditText_android_maxLength, 100);

        a.recycle();

        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.label_edit_text, this, true);

        mLabel = (TextView) getChildAt(0);
        mLabel.setText(text);


        LinearLayout mEditTextLayout = (LinearLayout) getChildAt(1);

        mEditText = (EditText) mEditTextLayout.findViewById(R.id.editText);
        mLeftLabel = (TextView) mEditTextLayout.findViewById(R.id.label_rewards);
        //getChildAt(1);
        int colorCodeHint = mEditText.getCurrentHintTextColor();
        mEditText.setHint(text);
        mEditText.setInputType(inputType);
        mRequired = (TextView) getChildAt(2);
        mRequired.setText(getResources().getString(R.string.required));
        mRequired.setTextColor(getResources().getColor(R.color.bw_red));
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mEditText.setTypeface(Typeface.DEFAULT);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mLabel.setVisibility(View.GONE);
                    if (isRequiredField) {
                        mRequired.setVisibility(VISIBLE);
                        mEditText.setHintTextColor(getResources().getColor(R.color.bw_red));
                        mEditText.setTextColor(getResources().getColor(R.color.bw_red));
                    }
                } else {
                    mLabel.setVisibility(View.VISIBLE);
                    if (isRequiredField) {
                        mRequired.setVisibility(View.GONE);
                        mEditText.setHintTextColor(getResources().getColor(R.color.bw_light_black));
                        mEditText.setTextColor(getResources().getColor(R.color.bw_dark_black));
                        mLabel.setTextColor(getResources().getColor(R.color.bw_light_black));
                    }
                }
            }
        });

    }


    public void setLeftLabelVisible(boolean showLeftLabel) {
        if(showLeftLabel)
            mLeftLabel.setVisibility(View.VISIBLE);
        else
            mLeftLabel.setVisibility(View.GONE);
    }

    public void setIsRequiredField(boolean isRequired) {
        this.isRequiredField = isRequired;
    }

    public boolean isRequiredField() {
        return isRequiredField;
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public void setEditHintColor(int colorId) {
        mEditText.setHintTextColor(colorId);
    }

    public void setEditTextColor(int colorId) {
        mEditText.setTextColor(colorId);
    }

    public int getEditTextHintTextColor() {
        return mEditText.getCurrentHintTextColor();
    }

    public int getLabelTextColor() {
        return mLabel.getCurrentTextColor();
    }

    public void setLabelTextColor(int colorId) {
        mLabel.setTextColor(colorId);
    }


    public boolean getVisibilityRequiredLabel() {
        return mRequired.getVisibility() == View.VISIBLE;
    }


    public void setVisibilityRequiredLabel(boolean isVisibile) {
        if (isVisibile)
            mRequired.setVisibility(View.VISIBLE);
        else
            mRequired.setVisibility(View.GONE);
    }

    public void setEnabled(boolean isEnable) {
        mEditText.setEnabled(isEnable);
    }

    public boolean isEnabled() {
        return  mEditText.isEnabled();
    }
}
