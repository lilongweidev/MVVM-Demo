package com.llw.mvvm.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.transition.AutoTransition;

/**
 * 简易动画
 * @author llw
 * @description EasyAnimation
 */
public class EasyAnimation {

    private AutoTransition autoTransition;

    /**
     * 开始眨眼动画
     *
     * @param view 需要设置动画的View
     */
    public static void startBlink(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setStartOffset(20);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 开始眨眼动画
     *
     * @param view           需要设置动画的View
     * @param alphaAnimation 透明度动画（自行配置）
     */
    public static void startBlink(View view, AlphaAnimation alphaAnimation) {
        view.startAnimation(alphaAnimation);
    }


    /**
     * 停止眨眼动画
     *
     * @param view 需要清除动画的View
     */
    public static void stopBlink(View view) {
        if (view != null) {
            view.clearAnimation();
        }
    }

    /**
     * 移动指定View的宽度
     *
     * @param view
     */
    public static void moveViewWidth(View view, TranslateCallback callback) {
        view.post(() -> {
            //通过post拿到的tvTranslate.getWidth()不会为0。
            TranslateAnimation translateAnimation = new TranslateAnimation(0, view.getWidth(), 0, 0);
            translateAnimation.setDuration(1000);
            translateAnimation.setFillAfter(true);
            view.startAnimation(translateAnimation);

            //动画监听
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //检查Android版本
                    callback.animationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        });
    }

    /**
     * 移动指定View的宽度
     *
     * @param view               需要位移的View
     * @param callback           位移动画回调
     * @param translateAnimation 位移动画 （自行配置）
     */
    public static void moveViewWidth(View view, TranslateCallback callback, TranslateAnimation translateAnimation) {
        view.post(() -> {
            //通过post拿到的tvTranslate.getWidth()不会为0。

            view.startAnimation(translateAnimation);

            //动画监听
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //检查Android版本
                    callback.animationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        });
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }





    public interface TranslateCallback {
        //动画结束
        void animationEnd();
    }
}
