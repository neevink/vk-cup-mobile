package com.neevin.vkcupmobile.cards;

import android.content.Context;

import com.wenchao.cardstack.CardStack;

public class CardDragHandler implements CardStack.CardEventListener {
    private Context context;
    private CardAdapter cardAdapter;
    private int DISTANCE = 200;

    public CardDragHandler(Context context, CardAdapter cardAdapter){
        this.context = context;
        this.cardAdapter = cardAdapter;
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        if(distance > DISTANCE){
            if(section == 0 || section == 2){
                //Toast.makeText(context, "dislike", Toast.LENGTH_LONG).show();
            }
            else{
                //Toast.makeText(context, "like", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return false;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        cardAdapter.lastIndex = mIndex;
    }

    @Override
    public void topCardTapped() {

    }
}
