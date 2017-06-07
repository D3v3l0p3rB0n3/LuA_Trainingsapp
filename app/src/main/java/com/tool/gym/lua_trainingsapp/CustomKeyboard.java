package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


class CustomKeyboard {

    private KeyboardView mKeyboardView;
    private LinearLayout title;
    private LinearLayout bottom;
    private Activity     mHostActivity;
    private boolean shifted = false;

    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        @Override public void onKey(int primaryCode, int[] keyCodes) {

            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if( editable!=null && start>0 ) editable.delete(start - 1, start);
                    break;
                case Keyboard.KEYCODE_MODE_CHANGE:
                    handleshift();
                    break;
                default:
                    editable.insert(start, Character.toString((char) primaryCode));
            }

            //}
        }

        private void handleshift() {
            if(!shifted)
            {
                mKeyboardView.setKeyboard(new Keyboard(mHostActivity, R.xml.keyboard_relational));
                shifted = true;
            }
            else
            {
                mKeyboardView.setKeyboard(new Keyboard(mHostActivity, R.xml.qwertz));
                shifted = false;
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };

    public CustomKeyboard(Activity host, int viewid, int layoutid, int titleid, int bottomid) {
        mHostActivity= host;
        title = (LinearLayout) mHostActivity.findViewById(titleid);
        bottom = (LinearLayout) mHostActivity.findViewById(bottomid);
        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        title.setVisibility(LinearLayout.GONE);
        bottom.setVisibility(LinearLayout.GONE);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        title.setVisibility(LinearLayout.VISIBLE);
        bottom.setVisibility(LinearLayout.VISIBLE);
        mKeyboardView.setEnabled(false);
    }


    public void registerEditText(int resid) {
        EditText edittext= (EditText)mHostActivity.findViewById(resid);
        edittext.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        edittext.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();
                edittext.setInputType(InputType.TYPE_NULL);
                edittext.onTouchEvent(event);
                edittext.setInputType(inType);
                return true;
            }
        });
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }


}
