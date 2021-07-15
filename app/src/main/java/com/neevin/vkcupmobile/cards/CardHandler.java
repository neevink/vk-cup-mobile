package com.neevin.vkcupmobile.cards;

import android.content.Context;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wenchao.cardstack.CardStack;

public class CardHandler implements CardStack.CardEventListener {
    private Context context;

    public CardHandler(Context context){
        this.context = context;
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        if(distance > 250){
            if(section == 0 || section == 2){
                Toast.makeText(context, "dislike", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context, "like", Toast.LENGTH_LONG).show();
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

    }

    @Override
    public void topCardTapped() {

    }
}
