/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package com.commonsware.android.anim.threepane;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ThreePaneLayout extends LinearLayout {
  private static final int ANIM_DURATION=500;
  private View left=null;
  private View middle=null;
  private View right=null;
  private int leftWidth=-1;
  private int middleWidthNormal=-1;

  public ThreePaneLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    initSelf();
  }

  void initSelf() {
    setOrientation(HORIZONTAL);
  }

  @Override
  public void onFinishInflate() {
    super.onFinishInflate();

    left=getChildAt(0);
    middle=getChildAt(1);
    right=getChildAt(2);
  }

  public View getLeftView() {
    return(left);
  }

  public View getMiddleView() {
    return(middle);
  }

  public View getRightView() {
    return(right);
  }

  public void hideLeft() {
    if (leftWidth == -1) {
      leftWidth=left.getWidth();
      middleWidthNormal=middle.getWidth();
      resetWidget(left, leftWidth);
      resetWidget(middle, middleWidthNormal);
      resetWidget(right, middleWidthNormal);
      requestLayout();
    }

    left.animate().translationXBy(-1 * leftWidth)
        .setDuration(ANIM_DURATION);
    middle.animate().translationXBy(-1 * leftWidth)
          .setDuration(ANIM_DURATION);
    right.animate().translationXBy(-1 * leftWidth)
         .setDuration(ANIM_DURATION);

    ObjectAnimator.ofInt(this, "middleWidth", middleWidthNormal,
                         leftWidth).setDuration(ANIM_DURATION).start();
  }

  public void showLeft() {
    left.animate().translationXBy(leftWidth).setDuration(ANIM_DURATION);
    middle.animate().translationXBy(leftWidth)
          .setDuration(ANIM_DURATION);
    right.animate().translationXBy(leftWidth)
         .setDuration(ANIM_DURATION);

    ObjectAnimator.ofInt(this, "middleWidth", leftWidth,
                         middleWidthNormal).setDuration(ANIM_DURATION)
                  .start();
  }

  public void setMiddleWidth(int value) {
    middle.getLayoutParams().width=value;
    requestLayout();
  }

  private void resetWidget(View v, int width) {
    LinearLayout.LayoutParams p=
        (LinearLayout.LayoutParams)v.getLayoutParams();

    p.width=width;
    p.weight=0;
  }
}
