package com.example.pp.core.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;

import com.example.pp.colorify.R;

public class ScoreBoardView extends TableLayout {
    public ScoreBoardView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.score_board, this, true);
    }

    public ScoreBoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }


}
