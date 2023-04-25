package com.example.pp.core.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.pp.colorify.R;

public class ScoreBoardView extends TableLayout {
    private String id;

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

    public void setName(String id, String name)
    {
        TextView playerName = this.findViewById(R.id.scoreBoard_playerName);
        playerName.setText(name);
        this.id = id;
    }
    public void setScore(int captured, int total) {
        TextView numericalTextView = this.findViewById(R.id.scoreBoard_scoreNumerical);
        TextView percentTextView = this.findViewById(R.id.scoreBoard_scorePercent);

        numericalTextView.setText(captured + "/" + total);
        percentTextView.setText((captured*100)/total);
    }
}
