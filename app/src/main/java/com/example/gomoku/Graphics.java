package com.example.gomoku;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.*;
import android.view.MotionEvent;
import android.view.View;

import java.lang.*;

public class Graphics extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private Paint greyPaint = new Paint();
    private Paint yellowPaint = new Paint();
    private boolean[][] cellChecked;
    private int[][] pieceColor;
    private int checkflag = 1;
    AlertDialog.Builder builder;


    public Graphics(Context context) {
        this(context, null);
    }

    public Graphics(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        greyPaint.setColor(Color.GRAY);
        yellowPaint.setColor(Color.RED);
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setStrokeWidth(5);

    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];
        pieceColor = new int[numColumns][numRows];

        invalidate();
    }

    private String equalColor(int i, int j){

        int color = pieceColor[i][j];
        int cleft = 0;
        int ctop = 0;
        int cleftroll = 0;
        int crightroll = 0;


        //left not going to happen
        if(color == 1 || color == 2) {
            for (int t = 1; t < 5; t++) {
                if (i - t >= 0 && pieceColor[i - t][j] == color) {
                    //left
                    cleft++;
                    Log.d("cleft ","plus 1");
                }
                if (j - t >= 0 && pieceColor[i][j - t] == color) {
                    //top
                    ctop++;
                }
                if (j - t >= 0 && i - t >= 0 && pieceColor[i - t][j - t] == color) {
                    //leftroll
                    cleftroll++;
                }
                if (j - t >= 0 && i + t < numRows && pieceColor[i + t][j - t] == color) {
                    //rightroll
                    crightroll++;
                }
            }
        }
        if(cleft > 0){
            Log.d("cleft is "," "+cleft);
        }

        String realcolor;

        if(color == 1){
            realcolor = "BLACK";
        } else {
            realcolor = "GREY";
        }


        if(cleft >= 4){
            return "cleft";
        }

        if(ctop >= 4){
            return "ctop";
        }

        if(crightroll >= 4){
            return "crightroll";
        }

        if(cleftroll >= 4){
            return "cleftroll";
        }
        return "nowin";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellChecked[i][j]) {

                    int a = pieceColor[i][j];

                    if(a == 1) {
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                blackPaint);
                        //Log.d("checkflag",Integer.toString(checkflag));
                    } else{
                        canvas.drawOval(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                greyPaint);
                    }
                }
            }
        }

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                String result = equalColor(i, j);
                if (!result.equals("nowin")) {
                    if (result.equals("crightroll")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i + t) * cellWidth, (j - t) * cellHeight,
                                    (i + t + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("cleft")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i-t) * cellWidth, j * cellHeight,
                                    (i-t + 1) * cellWidth, (j + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("ctop")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval(i * cellWidth, (j - t) * cellHeight,
                                    (i + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else if (result.equals("cleftroll")) {
                        Log.d("have winner", "yes " + pieceColor[i][j]);
                        for (int t = 0; t < 5; t++) {
                            canvas.drawOval((i - t) * cellWidth, (j - t) * cellHeight,
                                    (i - t + 1) * cellWidth, (j - t + 1) * cellHeight,
                                    yellowPaint);
                        }
                    } else {
                        //nothing need to be change
                    }
                    cellChecked = new boolean[numColumns][numRows];
                    pieceColor = new int[numColumns][numRows];
                }
            }
        }



        for (int i = 1; i <= numColumns; i++) {
            float k = (float)(i - 0.5);
            canvas.drawLine(k * cellWidth, 0, k * cellWidth, height, blackPaint);
        }

        for (int i = 1; i <= numRows; i++) {
            float k = (float)(i - 0.5);
            canvas.drawLine(0, k * cellHeight, width, k * cellHeight, blackPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            if(checkflag == 1) {
                pieceColor[column][row] = 1;
                checkflag = 2;
                Log.d("get", "1");
            }else{
                pieceColor[column][row] = 2;
                checkflag = 1;
                Log.d("get", "2");
            }

            Log.d("column", Integer.toString(column));
            Log.d("row",Integer.toString(row));


            cellChecked[column][row] = !cellChecked[column][row];
            invalidate();
        }

        return true;
    }
}
