package com.mysample.customviewtest.custom;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class ScetchLine extends ScetchView
{
    public ScetchLine(Context context)
    {
        super(context);
        Init(context);
    }

    public ScetchLine(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        Init(context);
    }

    @Override
    protected void Init(Context context)
    {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10.0f);
        paint.setColor(Color.BLACK);

        seletPaint = new Paint();
        seletPaint.setColor(Color.RED);
    }

    @Override
    public void InitStartPixel(int nStartX, int nStartY)
    {
        startX = nStartX;
        startY = nStartY;
    }

    @Override
    public void InitLastPixel(int nLastX, int nLastY)
    {
        lastX = nLastX;
        lastY = nLastY;
    }

    @Override
    public void ShapeMove(int nX, int nY)
    {
        int tmpX = clickX;
        int tmpY = clickY;

        int rr = nX - tmpX;
        clickX = nX;
        startX = startX + rr;
        lastX = lastX + rr;


        rr = nY - tmpY;
        clickY = nY;
        startY = startY + rr;
        lastY = lastY + rr;

        setMode(ScetchView.MOVE_MODE);
        ShapeDraw();
    }

    @Override
    public void CmdOnDraw(int nX, int nY)
    {
        this.InitLastPixel(nX, nY);
        this.ShapeDraw();
    }

    @Override
    public void CmdOnResize(int nType, int nX, int nY)
    {
        switch (nType)
        {
            case TOUCH_NONE:
                break;
            case TOUCH_VERTEXT1:
            case TOUCH_VERTEXT2:
                this.InitLastPixel(nX, nY);
                this.ShapeDraw();
                break;
            case TOUCH_VERTEXT3:
            case TOUCH_VERTEXT4:
            case TOUCH_BODY:
                ShapeMove(nX, nY);
                break;
        }
    }

    @Override
    public void ShapeDraw()
    {
//        this.invalidate();
    }

    @Override
    public int HitCheck(int nX, int nY)
    {
        if(((startX - selectRadius) <= nX && nX <=(startX + selectRadius)) && ((startY - selectRadius) <= nY && nY <=(startY + selectRadius)))
        {
            return TOUCH_VERTEXT1;
        }
        else if(((lastX - selectRadius) <= nX && nX <=(lastX + selectRadius)) && ((lastY - selectRadius) <= nY && nY <=(lastY + selectRadius)))
        {
            return TOUCH_VERTEXT2;
        }
        else if(((startX <= nX && nX <= lastX) || (lastX <= nX && nX <= startX)) && (((startY <= nY && nY <= lastY) || lastY <= nY && nY <= startY)))
        {
            return TOUCH_BODY;
        }
        else
        {
            return TOUCH_NONE;
        }
    }

    @Override
    public boolean OnTouchModify(int nType, int nX, int nY)
    {
        try {
            switch (nType)
            {
                case TOUCH_NONE:
                    return false;
                case TOUCH_VERTEXT1:
                    int tmpX = startX;
                    int tmpY = startY;
                    InitStartPixel(lastX, lastY);
                    InitLastPixel(tmpX, tmpY);
                    break;
                case TOUCH_VERTEXT2:
                    InitStartPixel(startX, startY);
                    break;
                case TOUCH_VERTEXT3:
                    break;
                case TOUCH_VERTEXT4:
                    break;
                case TOUCH_BODY:
                    clickX = nX;
                    clickY = nY;
                    break;
            }
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    @Override
    public void setMode(int nMode)
    {
        currentMode = nMode;
        ShapeDraw();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(startX != -1 && lastX != -1)
        {
            canvas.drawLine(startX, startY, lastX, lastY, paint);
            if(currentMode != DRAW_MODE)
            {
                canvas.drawCircle(startX, startY, selectRadius, seletPaint);
                canvas.drawCircle(lastX, lastY, selectRadius, seletPaint);
            }
        }
    }
}
