package com.example.finance.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CircleChartView extends View {

    private Paint circlePaint;
    private Paint textPaint;
    private RectF circleBounds;
    private List<Sector> sectors = new ArrayList<>(); // List of sectors
    private int centerX;
    private int centerY;
    private float radius;
    private float innerRadiusRatio = 0.5f; // радиус внутреннего круга
    private String centerText = "";


    public CircleChartView(Context context) {
        super(context);
        init();
    }

    public CircleChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setCenterText(String text){
        this.centerText = text;
        invalidate();
    }
    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
        invalidate();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w, h) / 2f;
        float padding = 20f;
        float rectSize = radius - padding;
        circleBounds = new RectF(centerX-rectSize, centerY-rectSize, centerX+rectSize, centerY+rectSize);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 0f;
        for(Sector sector : sectors){
            float sectorAngle = 360f * (sector.getPercentage()/100f);
            circlePaint.setColor(sector.getColor());
            canvas.drawArc(circleBounds, startAngle, sectorAngle, true, circlePaint);
            startAngle += sectorAngle;
        }
        float innerCircleRadius = radius * innerRadiusRatio;
        circlePaint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerY, innerCircleRadius, circlePaint);
        float textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(centerText, centerX,textY, textPaint);
    }
    public static class Sector {
        private float percentage;
        private int color;

        public Sector(float percentage, int color) {
            this.percentage = percentage;
            this.color = color;
        }
        public float getPercentage() {
            return percentage;
        }
        public int getColor() {
            return color;
        }
    }
}