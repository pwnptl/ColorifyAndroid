package com.example.pp.core.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.pp.colorify.R;

import java.util.ArrayList;

public class PaletteView extends LinearLayout {

    private int buttonCount;
    private final ArrayList<Integer> buttonColors = new ArrayList<Integer>() {
        {
            add(Color.CYAN);
            add(Color.BLUE);
            add(Color.YELLOW);
            add(Color.GREEN);
            add(Color.RED);
            add(Color.MAGENTA);
        }
    };
    private final int marginWidth = 3;

    public PaletteView(Context context) {
        super(context);
//        init(context );
    }


    public PaletteView(Context context, AttributeSet attr) {
        super(context, attr);
        setButtonCount(attr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // todo: implement this.
    }

    private void setButtonCount(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0);
        try {
            buttonCount = a.getInteger(R.styleable.PaletteView_buttonCount, 4);
        } finally {
            a.recycle();
        }
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(marginWidth, marginWidth, marginWidth, marginWidth);
        for (int i = 0; i < buttonCount; ++i) {
            Button b = getButton(context);
            b.setLayoutParams(layoutParams);
            b.setBackgroundColor(buttonColors.get(i));
            addView(b);
        }
    }


    private Button getButton(Context context) {
        Button b = new Button(context);
        return b;
    }
}
