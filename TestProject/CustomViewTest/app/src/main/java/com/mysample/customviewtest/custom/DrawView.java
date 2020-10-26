package com.mysample.customviewtest.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView extends View
{
    final public static int SELECT_NONE        = 0;
    final public static int SELECT_LINE        = 1;
    final public static int SELECT_RECT        = 2;
    final public static int SELECT_CIRCLE      = 3;
    final public static int SELECT_MODIFY      = 4;
    final public static int SELECT_BOARD       = 5;

    public int nSelected = SELECT_NONE;
    public int nPosition = -1;
    public int nModifyPosition = -1;
    public int nPrevSelected = SELECT_NONE;


    Context context;
    ArrayList<ScetchView> arrScetch;

    public DrawView(Context context)
    {
        super(context);
        this.context = context;
        arrScetch = new ArrayList<>();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        arrScetch = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int i;
        ScetchView v;

        for(i=0; i<arrScetch.size(); i++)
        {
            v = arrScetch.get(i);
            v.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();

        int action = event.getAction();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if(nSelected == SELECT_LINE)
                {
                    ScetchView view = new ScetchLine(context);
                    view.InitStartPixel(X, Y);
                    view.shapeKinds = ScetchView.SHAPE_LINE;
                    arrScetch.add(view);
                    nPosition = arrScetch.size()-1;
                }
                else if(nSelected == SELECT_RECT)
                {
                    ScetchView view = new ScetchRect(context);
                    view.InitStartPixel(X, Y);
                    view.shapeKinds = ScetchView.SHAPE_RECT;
                    arrScetch.add(view);
                    nPosition = arrScetch.size() -1;
                }
                else if(nSelected == SELECT_CIRCLE)
                {
                    ScetchView view = new ScetchOval(context);
                    view.InitStartPixel(X, Y);
                    view.shapeKinds = ScetchView.SHAPE_CIRCLE;
                    arrScetch.add(view);
                    nPosition = arrScetch.size() -1;
                }

                else if(nSelected == SELECT_MODIFY)
                {
                    for(int i = arrScetch.size()-1; i>=0; i--)
                    {
                        int vertex = arrScetch.get(i).HitCheck(X, Y);

                        boolean bResult = arrScetch.get(i).OnTouchModify(vertex, X, Y);
                        if(bResult)
                        {
                            nModifyPosition = i;
                            break;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP :
            case MotionEvent.ACTION_MOVE :
                if(nPosition >= 0)
                {
                    if(nSelected == SELECT_LINE || nSelected == SELECT_RECT || nSelected == SELECT_CIRCLE)
                    {
                        arrScetch.get(nPosition).CmdOnDraw(X, Y);
                        this.invalidate();
                    }
                    else if(nSelected == SELECT_MODIFY)
                    {
                        if(nModifyPosition != -1)
                        {
                            int vertex = arrScetch.get(nModifyPosition).HitCheck(X, Y);
                            arrScetch.get(nModifyPosition).CmdOnResize(vertex, X, Y);
                            this.invalidate();
                        }
                    }
                }
                break;
        }
        return true;
    }


    public void selectShape(int nSelected)
    {
        this.nSelected = nSelected;
    }

    public void doModifyMode()
    {
        if(nSelected != DrawView.SELECT_MODIFY)
        {
            nPrevSelected = nSelected;

            selectShape(DrawView.SELECT_MODIFY);

            for(int i = 0; i<arrScetch.size(); i++)
            {
                arrScetch.get(i).setMode(ScetchView.MODIFY_MODE);
            }
        }
        else
        {
            for (int i = 0; i<arrScetch.size(); i++)
            {
                arrScetch.get(i).setMode(ScetchView.DRAW_MODE);
            }
            nModifyPosition = -1;
            selectShape(nPrevSelected);
        }
        this.invalidate();
    }

    public void doClearView()
    {
        nModifyPosition = -1;
        arrScetch.clear();
        invalidate();
    }
}
