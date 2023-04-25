package com.example.pp.core.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pp.colorify.R;

public class PaletteView extends LinearLayout {

    private int buttonCount;

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
            Button b = getButton(context, "custom_palette_button_" + i);
            b.setLayoutParams(layoutParams);
//            b.setBackgroundColor(buttonColors.get(i));
            addView(b);
        }
    }


    private Button getButton(Context context, String id) {
        Button b = new Button(context);
//        Log.i(this.getClass().getName(), "buttonId " + id);
        int resourceId = getResources().getIdentifier(id, "id", null);
//        Log.i(this.getClass().getName(), "buttonId " + resourceId);
        b.setId(resourceId);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "button clicked" + b.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        return b;
    }
}
