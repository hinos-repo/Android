package com.mysample.customviewtest.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Toast;


public class ScetchCircle extends ScetchView
{
    int radius = -1;
    boolean bResizeMode = false;

    Paint linePaint;

    public ScetchCircle(Context context)
    {
        super(context);
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
        seletPaint.setAntiAlias(true);
        seletPaint.setStrokeWidth(10.0f);
        seletPaint.setStyle(Paint.Style.FILL);
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
            case TOUCH_VERTEXT2:
            case TOUCH_VERTEXT3:
            case TOUCH_VERTEXT4:
                int tmpRadius = (int) Math.sqrt(Math.pow((startX - nX), 2) + Math.pow((startY - nY), 2));
                if(radius <= tmpRadius)
                {
                    radius = radius + tmpRadius;
                }
                else
                {
                    radius = radius - tmpRadius;
                }

                this.ShapeDraw();
                break;
            case TOUCH_BODY:
//                ShapeMove(nX, nY);
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
                case TOUCH_VERTEXT2:
                case TOUCH_VERTEXT3:
                case TOUCH_VERTEXT4:
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
        radius = (int) Math.sqrt(Math.pow((startX - nX), 2) + Math.pow((startY - nY), 2));
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
        if((startX - radius - selectRadius <= nX && nX <= startX - radius + selectRadius) && (startY - radius - selectRadius <= nY && nY <= startY - radius + selectRadius))
        {
            Toast.makeText(context, "첫번째", Toast.LENGTH_SHORT).show();
            return TOUCH_VERTEXT1;
        }
        else if((startX + radius - selectRadius <= nX && nX <= startX + radius + selectRadius) && (startY - radius - selectRadius <= nY && nY <= startY - radius + selectRadius))
        {
            Toast.makeText(context, "두번째", Toast.LENGTH_SHORT).show();

            return TOUCH_VERTEXT2;
        }
        else if((startX - radius - selectRadius <= nX && nX <= startX - radius + selectRadius) && (startY + radius - selectRadius <= nY && nY <= startY + radius + selectRadius))
        {
            Toast.makeText(context, "세번째", Toast.LENGTH_SHORT).show();

            return TOUCH_VERTEXT3;
        }
        else if((startX + radius - selectRadius <= nX && nX <= startX + radius + selectRadius) && (startY + radius - selectRadius <= nY && nY <= startY + radius + selectRadius))
        {
            Toast.makeText(context, "네번째", Toast.LENGTH_SHORT).show();

            return TOUCH_VERTEXT4;
        }
        else if((startX - radius <= nX && nX <= startX + radius) && (startY - radius <= nY && nY <= startY + radius))
        {
            Toast.makeText(context, "몸통", Toast.LENGTH_SHORT).show();

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
            canvas.drawCircle(startX, startY, radius, paint);
            if(currentMode != DRAW_MODE)
            {
                canvas.drawRect(startX - radius, startY - radius, startX + radius, startY + radius, linePaint);

                canvas.drawCircle(startX - radius, startY - radius, selectRadius, seletPaint); //1
                canvas.drawCircle(startX + radius, startY - radius, selectRadius, seletPaint); //2
                canvas.drawCircle(startX - radius, startY + radius, selectRadius, seletPaint); //3
                canvas.drawCircle(startX + radius, startY + radius, selectRadius, seletPaint); //4
            }
        }
    }
}
