package com.mysample.customviewtest.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Toast;


public class ScetchOval extends ScetchView
{
    Paint linePaint;

    public ScetchOval(Context context)
    {
        super(context);
        Init(context);
    }

    public ScetchOval(Context context, @Nullable AttributeSet attrs)
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
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        seletPaint = new Paint();
        seletPaint.setColor(Color.RED);

        DashPathEffect dashPath = new DashPathEffect(new float[]{5,5}, 2);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setPathEffect(dashPath);
        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.BLUE);
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
    public void CmdOnResize(int nType, int nX, int nY)
    {
        switch (nType)
        {
            case TOUCH_NONE:
                break;
            case TOUCH_VERTEXT1:
                this.InitLastPixel(nX, nY);
                this.ShapeDraw();
            case TOUCH_VERTEXT2:
                this.InitLastPixel(nX, nY);
                this.ShapeDraw();
                break;
            case TOUCH_VERTEXT3:
            case TOUCH_VERTEXT4:
                this.InitLastPixel(nX, nY);
                this.ShapeDraw();
                break;
            case TOUCH_BODY:
                ShapeMove(nX, nY);
                break;
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
                    this.InitStartPixel(lastX, lastY);
                    this.InitLastPixel(tmpX, tmpY);
                    Toast.makeText(context, "첫번째", Toast.LENGTH_SHORT).show();
                    break;
                case TOUCH_VERTEXT2:
                    int tmpX2 = lastX;
                    int tmpY2 = startY;
                    this.InitStartPixel(startX, lastY);
                    this.InitLastPixel(tmpX2, tmpY2);
                    Toast.makeText(context, "두번째", Toast.LENGTH_SHORT).show();
                    break;
                case TOUCH_VERTEXT3:
                    int tmpX3 = startX;
                    int tmpY3 = lastY;
                    this.InitStartPixel(lastX, startY);
                    this.InitLastPixel(tmpX3, tmpY3);
                    Toast.makeText(context, "세번째", Toast.LENGTH_SHORT).show();
                    break;
                case TOUCH_VERTEXT4:
                    this.InitStartPixel(startX, startY);
                    Toast.makeText(context, "네번째", Toast.LENGTH_SHORT).show();
                    break;
                case TOUCH_BODY:
                    clickX = nX;
                    clickY = nY;
                    Toast.makeText(context, "몸통", Toast.LENGTH_SHORT).show();
                    break;
            }
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    @Override
    public void CmdOnDraw(int nX, int nY)
    {
        this.InitLastPixel(nX, nY);
        this.ShapeDraw();
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
        else if(((lastX - selectRadius) <= nX && nX <=(lastX + selectRadius)) && ((startY - selectRadius) <= nY && nY <=(startY + selectRadius)))
        {
            return TOUCH_VERTEXT2;
        }
        else if(((startX - selectRadius) <= nX && nX <=(startX + selectRadius)) && ((lastY - selectRadius) <= nY && nY <=(lastY + selectRadius)))
        {
            return TOUCH_VERTEXT3;
        }
        else if(((lastX - selectRadius) <= nX && nX <=(lastX + selectRadius)) && ((lastY - selectRadius) <= nY && nY <=(lastY + selectRadius)))
        {
            return TOUCH_VERTEXT4;
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
            canvas.drawOval(startX, startY, lastX, lastY, paint);
            if(currentMode != DRAW_MODE)
            {
                canvas.drawRect(startX, startY, lastX, lastY, linePaint);

                canvas.drawCircle(startX, startY, selectRadius, seletPaint);
                canvas.drawCircle(lastX, lastY, selectRadius, seletPaint);

                canvas.drawCircle(startX, lastY, selectRadius, seletPaint);
                canvas.drawCircle(lastX, startY, selectRadius, seletPaint);
            }
        }
    }
}
