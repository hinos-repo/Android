package com.mysample.customviewtest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class CustomViewDrawable extends View
{
    private Context context;

    public CustomViewDrawable(Context context) {
        super(context);

        init(context);
    }

    public CustomViewDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        this.context = context;

//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        int width = display.getWidth();
//        int heigth = display.getHeight();
//
//        Resources curRes = getResources();
//        int blackColor = curRes.getColor(R.color.color01);
//        int grayColor = curRes.getColor(R.color.color02);
//        int darkGrayColor = curRes.getColor(R.color.color03);

//        upperDrawable = new ShapeDrawable();
//
//        RectShape rectangle = new RectShape();
//        rectangle.resize(width, heigth * 2/3);
//        upperDrawable.setShape(rectangle);
//        upperDrawable.setBounds(0, 0, width, heigth*2/3);
//
//
//        LinearGradient gradient = new LinearGradient(0, 0, 0, heigth*2/3, grayColor, blackColor, Shader.TileMode.CLAMP);
//
//        Paint paint = upperDrawable.getPaint();
//
//        paint.setShader(gradient);

//        lowerDrawable = new ShapeDrawable();
//
//        RectShape rectangle2 = new RectShape();
//        rectangle2.resize(width, heigth*1/3);
//        lowerDrawable.setShape(rectangle2);
//        lowerDrawable.setBounds(0, heigth*2/3, width, heigth);
//
//        int a = heigth*1/3;
//
//
//        LinearGradient gradient2 = new LinearGradient(0, 0, 0, 0, curRes.getColor(R.color.colorAccent), curRes.getColor(R.color.colorPrimary), Shader.TileMode.CLAMP);
//
//        Paint paint2 = lowerDrawable.getPaint();
//        paint2.setShader(gradient2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        Resources curRes = getResources();
        int blackColor = curRes.getColor(R.color.color01);
        int grayColor = curRes.getColor(R.color.color02);
        int darkGrayColor = curRes.getColor(R.color.color03);

        ShapeDrawable drawable = new ShapeDrawable();
        RectShape rectShape = new RectShape();
        rectShape.resize(width, height /2);
        drawable.setShape(rectShape);
        drawable.setBounds(0, 0, width, height/2);

        Paint paint55 = drawable.getPaint();
        paint55.setColor(blackColor);
        drawable.draw(canvas);



        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(16.0F);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.BEVEL);

        Path path = new Path();
        path.moveTo(20, 20);
        path.lineTo(120, 20);
        path.lineTo(160,  90);
        path.lineTo(180, 80);
        path.lineTo(200, 120);
        canvas.drawPath(path, paint);

        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path.offset(30, 120);
        canvas.drawPath(path, paint);
    }
}
