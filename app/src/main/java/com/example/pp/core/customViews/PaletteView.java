package com.example.pp.core.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pp.colorify.R;
import com.example.pp.core.models.Cell;
import com.example.pp.core.models.ColorifyColor;
import com.example.pp.core.models.ColorifyPalette;

import java.util.ArrayList;
import java.util.Collections;

public class PaletteView extends LinearLayout {

    private int buttonCount;

    private static String BUTTON_ID_PREFIX = "custom_palette_button_";

    public PaletteView(Context context) {
        super(context);
        init(context);
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
        final int marginWidth = 3;

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(marginWidth, marginWidth, marginWidth, marginWidth);
        for (int i = 0; i < buttonCount; ++i) {
            Button b = getButton(context, BUTTON_ID_PREFIX + i);
            b.setLayoutParams(layoutParams);
            b.setBackgroundColor(Color.BLACK);  // adding default color as BLACK.
            addView(b);
        }
    }


    private Button getButton(Context context, String id) {
        Button b = new Button(context);
        int resourceId = getResources().getIdentifier(id, "id", getContext().getPackageName());
        b.setId(resourceId);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String resourceName = v.getResources().getResourceName(v.getId());
                Log.i(PaletteView.class.getName(), "buttonId " + resourceName);
                Toast.makeText(getContext(), v.getId() + " button clicked" + resourceName, Toast.LENGTH_SHORT).show();
            }
        });
        return b;
    }

    public void setPalette(ColorifyPalette palette) {
        if (palette.getPaletteCells().size() != buttonCount)
            throw new RuntimeException("Palette Size mismatch : " + buttonCount + " and " + palette.getPaletteCells().size());
        ArrayList<Cell> paletteCells = palette.getPaletteCells();
        Collections.shuffle(paletteCells); // palette colors are displayed in random order.
        for (int i = 0; i < buttonCount; ++i) {
            int cellValue = paletteCells.get(i).getCell();
            ColorifyColor cellColor = ColorifyColor.valueOf(cellValue);
            getChildAt(i).setBackgroundColor(cellColor.getColor());
        }
    }

    public void setOnClickListenerForButtons(OnClickListener paletteButtonOnclickListener) {
        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).setOnClickListener(paletteButtonOnclickListener);
        }
    }

    public void enable(boolean enable) {
        enablePalette(enable);
        fadePalette(enable);
    }


    private void enablePalette(boolean enable) {
        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).setEnabled(enable);
            if (!enable) getChildAt(i).setBackgroundColor(Color.GRAY);
        }
    }

    private void fadePalette(boolean fade) {
        int duration = fade ? 200 : 100;
        float alph = fade ? 0.5f : 0.1f;

        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            child.animate().alpha(alph).setDuration(duration);
        }
    }
}
