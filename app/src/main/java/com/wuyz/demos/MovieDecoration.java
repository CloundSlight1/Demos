package com.wuyz.demos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by wuyz on 2017/1/12.
 * MovieDecoration
 */

public class MovieDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "MovieDecoration";

    private TextPaint textPaint;
    private Paint paint;
    private int topGap = 64;
    private Callback callback;

    public MovieDecoration(Context context, Callback callback) {
        this.callback = callback;
        paint = new Paint();
        paint.setColor(Color.GRAY);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(28);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String group = callback.getGroup(pos);
        if (group.equals("-1"))
            return;
        if (pos == 0 || !callback.getGroup(pos - 1).equals(group)) {
            outRect.top = topGap;
//            Log2.d(TAG, "getItemOffsets pos[%d], group[%s]", pos, group);
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preGroup = "";
        String group = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);
            preGroup = group;
            group = callback.getGroup(pos);
            if (TextUtils.isEmpty(group) || group.equals(preGroup))
                continue;
            int bottom = view.getBottom();
            float y = Math.max(topGap, view.getTop());
            if (pos + 1 < itemCount) {
                String nextGroup = callback.getGroup(pos + 1);
                if (!nextGroup.equals(group) && bottom < y)
                    y = bottom;
            }
            c.drawRect(left, y - topGap, right, y, paint);
            c.drawText(group, left + 40, y - 20, textPaint);
//            Log2.d(TAG, "onDrawOver pos[%d], group[%s], top[%f]", pos, group, y - topGap);
        }
    }

    interface Callback {
        String getGroup(int pos);
    }
}
