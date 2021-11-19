package com.llw.mvvm.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * @author llw
 * @description EasyAnimation
 * @date 2021/11/19 11:10
 */
public class EasyAnimation {

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

    public interface TranslateCallback {
        //动画结束
        void animationEnd();
    }
}
