package com.example.pp.core.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.pp.colorify.R;
import com.example.pp.core.models.Board;
import com.example.pp.core.models.ColorifyColor;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class BoardView extends GridLayout { // todo: extend LinearLayout instead.

    @Getter
    @Setter
    private int boardSize = 10;
    private final int marginWidth = 1; // todo : set via xml.
    private ArrayList<ColorifyColor> colors; // todo: with LinerLayout extension, this colors will also be 2D list.

    public BoardView(Context context) {
        super(context);
        setColumnCount(boardSize);
        setRowCount(boardSize);
        setUseDefaultMargins(true);
        initializeDefaultBoard(boardSize);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBoardSize(attrs);
        setColumnCount(this.boardSize);
        setRowCount(this.boardSize);
        setUseDefaultMargins(true);
        initializeDefaultBoard(boardSize);
    }

    private void setBoardSize(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0);
        try {
            boardSize = a.getInteger(R.styleable.BoardView_boardSize, 8);
        } finally {
            a.recycle();
        }
    }

    private int getIndex(int r, int c) {
        return r * boardSize + c;
    }

    public void setColor(int row, int column, int color) {
        View square = getChildAt(getIndex(row, column));
        square.setBackgroundColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Calculate the maximum square size that will fit in both dimensions of the available space
        int boardMeasuremeWidth = getBoardSize(widthMeasureSpec, heightMeasureSpec);
        int squareSize = getSquareSize(widthMeasureSpec, heightMeasureSpec);

        // Set the size of the board to fit within the available space
        setMeasuredDimension(boardMeasuremeWidth, boardMeasuremeWidth);

        // Set the size of each square in the grid
        for (int i = 0; i < getChildCount(); i++) {
            View square = getChildAt(i);
            square.getLayoutParams().width = squareSize;
            square.getLayoutParams().height = squareSize;
        }
    }

    private int getBoardSize(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        int parentSquareSize = Math.min(parentWidth, parentHeight);
        return (parentSquareSize / boardSize) * boardSize;

    }

    private int getSquareSize(int widthMeasureSpec, int heightMeasureSpec) {
        // Get the available space for the board
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        int parentSquareSize = Math.min(parentWidth, parentHeight);

        int childSquareSize = parentSquareSize / boardSize - 2 * marginWidth;
        return childSquareSize;
    }

    private View getNewSquare() {
        View square = new View(getContext());
        square.setBackgroundColor(Color.CYAN);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(marginWidth, marginWidth, marginWidth, marginWidth);
        square.setLayoutParams(params);
        return square;
    }

    private void initializeDefaultBoard(int boardSize) {
        setPadding(0, 0, 0, 0);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                addView(getNewSquare());
            }
        }
    }

    public void setBoard(final Board board) {
        if (board.getCols() != boardSize || board.getRows() != boardSize) {
            Log.e(BoardView.class.getName(), "size mismatch {" + board.getRows() + "," + board.getCols() + "} and boardSize=" + boardSize);
            throw new RuntimeException("size mismatch {" + board.getRows() + "," + board.getCols() + "} and boardSize=" + boardSize);
        }
        for (int r = 0; r < board.getRows(); ++r)
            for (int c = 0; c < board.getCols(); ++c) {
                int cellValue = board.getCell(r,c).getCell();
                ColorifyColor cellColor = ColorifyColor.valueOf(cellValue);
                getChildAt(getIndex(r,c)).setBackgroundColor(cellColor.getColor()); // Color
            }
    }
}
