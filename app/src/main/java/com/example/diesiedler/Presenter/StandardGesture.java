package com.example.diesiedler.Presenter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

public class StandardGesture implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

    private View view;
    private GestureDetector gesture;
    private ScaleGestureDetector gestureScale;
    private float scaleFactor = 1;
    private boolean inScale;

    //Does not yet work properly, as it makes problems with GameBoardClickListener


    public StandardGesture(Context c) {
        gesture = new GestureDetector(c, this);
        gestureScale = new ScaleGestureDetector(c, this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.view = view;
        gesture.onTouchEvent(event);
        gestureScale.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float x, float y) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float x, float y) {
        float newX = view.getX();
        float newY = view.getY();
        if (!inScale) {
            newX -= x;
            newY -= y;
        }
        WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);

        if (newX > (view.getWidth() * scaleFactor - p.x) / 2) {
            newX = (view.getWidth() * scaleFactor - p.x) / 2;
        } else if (newX < -((view.getWidth() * scaleFactor - p.x) / 2)) {
            newX = -((view.getWidth() * scaleFactor - p.x) / 2);
        }

        if (newY > (view.getHeight() * scaleFactor - p.y) / 2) {
            newY = (view.getHeight() * scaleFactor - p.y) / 2;
        } else if (newY < -((view.getHeight() * scaleFactor - p.y) / 2)) {
            newY = -((view.getHeight() * scaleFactor - p.y) / 2);
        }

        view.setX(newX);
        view.setY(newY);

        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        view.setVisibility(View.GONE);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = scaleFactor < 1 ? 1 : scaleFactor;        // prevent our image from becoming too small
        scaleFactor = (float) (int) (scaleFactor * 100) / 100;  // Change precision to help with jitter when user just rests their fingers //
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        onScroll(null, null, 0, 0);         // call scroll to make sure our bounds are still ok //
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        inScale = true;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        inScale = false;
        onScroll(null, null, 0, 0); // call scroll to make sure our bounds are still ok //
    }
}
