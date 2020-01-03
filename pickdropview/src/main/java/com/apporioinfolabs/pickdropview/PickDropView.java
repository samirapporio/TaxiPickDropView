package com.apporioinfolabs.pickdropview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.apporioinfolabs.pickdropview.utils.Logs;

public class PickDropView  extends LinearLayout {

    private Context mContext  = null  ;
    private static final String TAG = "PickDropView";
    private TextView text ;

    // interfaces
    private OnCarcategoryViewListeners onCarcategoryViewListeners ;


    public PickDropView(Context context) {
        super(context);
        initializeViews(context);
    }

    public PickDropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public PickDropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @SuppressLint("NewApi")
    public PickDropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }


    private void initializeViews(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pick_drop_layout, this);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try{
            text = findViewById(R.id.text);

            // should place after finding id of every element in whole view.
            setClickAction();
        }catch (Exception e){ Logs.i(TAG , ""+e.getMessage()); }
    }


    public void setClickAction(){
        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCarcategoryViewListeners.onElementSelect(""+text.getText());
            }
        });
    }


    public void initialiseListeners(OnCarcategoryViewListeners onCarcategoryViewListeners) {
        this.onCarcategoryViewListeners = onCarcategoryViewListeners ;
    }



    public void setTextValue(String textValue){
        text.setText(""+textValue);
    }


    public interface OnCarcategoryViewListeners{
        void onElementSelect(String data);
    }

}