package com.tool.gym.lua_trainingsapp;

import android.app.Service;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Created by matthiasbrandel on 11.05.17.
 */

public class RelationalAlgebraKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {


    private KeyboardView kv;
    private Keyboard keyboard;
    private Keyboard keyboardsymbols;
    private boolean standardkeyboad;

    private boolean caps = false;

    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this,R.xml.qwertz);
        keyboardsymbols = new Keyboard(this,R.xml.keyboard_relational);
        standardkeyboad = true;
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;


    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        InputConnection ic = getCurrentInputConnection();
        switch (i) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                handleshift();
                break;
            default:
                char code = (char) i;
                if(Character.isLetter(code) && caps)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(Character.toString(code),1);
        }
    }

    private void handleshift() {
        if(standardkeyboad)
        {
            kv.setKeyboard(keyboardsymbols);
            standardkeyboad = false;
        }
        else
            {
            kv.setKeyboard(keyboard);
                standardkeyboad = true;
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
