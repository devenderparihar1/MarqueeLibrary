package com.example.it114.marqueelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by IT114 on 02-08-2017.
 */

public class MarqueeTextView extends TextView {

    private static int count_loop;
    private int ThreadTime = 30;
    private static  boolean firstTime = false;
    private int i=0;
    private Thread thread;
    static boolean suspendThread ;
    ArrayList<TextListener> textListenerArrayList ;

    public MarqueeTextView(Context context) {
        super(context);
        setMovement();
        textListenerArrayList = new ArrayList<>();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovement();
        textListenerArrayList = new ArrayList<>();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMovement();
        textListenerArrayList = new ArrayList<>();
    }

    void setMovement(){
        setMovementMethod(LinkMovementMethod.getInstance());
        setSelected(true);
        setSingleLine(true);
    }

    void spannableText(String marqueeText){
        SpannableString SpanString = new SpannableString(marqueeText);
        for(TextListener textListener :textListenerArrayList ){
            SpanString.setSpan(textListener.clickableSpan, textListener.startIndex, textListener.endIndex, 0);
            SpanString.setSpan(textListener.foregroundColorSpan, textListener.startIndex, textListener.endIndex, 0);
            SpanString.setSpan(new UnderlineSpan(), textListener.startIndex, textListener.endIndex, 0);
        }
        setText(SpanString);
    }


    void move(final int threadTime){
        spannableText(getText().toString());
        count_loop = getText().length();
        Runnable runnable =  new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    if (firstTime && i < count_loop)
                    {
                        scrollBy(1, 0);
                        i++;
                    }else if(!firstTime && i < getWidth()+count_loop){
                        scrollBy(1, 0);
                        i++;
                    }
                    else
                    {
                        i = 0;
                        scrollTo(-(getWidth()),0);
                        if(firstTime){
                            firstTime = false;
                      }
                    }
                    try {
                        Thread.sleep(threadTime);
                        synchronized(thread) {
                            while (suspendThread) {
                                thread.wait();
                                System.out.println("wait");
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }}};
        thread = new Thread(runnable);
        thread.start();
        }

        void pause(){
            suspendThread = true;
        }
        void resume(){
            suspendThread = false;
            synchronized (thread) {

                System.out.println("notify");
                thread.notify();
            }
        }

        void spinnerText(int startIndex, int endIndex, ClickableSpan clickableSpan , int color){
            textListenerArrayList.add(new TextListener(startIndex,endIndex,clickableSpan,new ForegroundColorSpan(color)));
        }


        class TextListener{
            int startIndex;
            int endIndex;
            ClickableSpan clickableSpan;
            ForegroundColorSpan foregroundColorSpan;
            TextListener(int startIndex, int endIndex, ClickableSpan clickableSpan , ForegroundColorSpan foregroundColorSpan){
                this.startIndex = startIndex;
                this.endIndex = endIndex;
                this.clickableSpan = clickableSpan;
                this.foregroundColorSpan = foregroundColorSpan;
            }
        }


}
