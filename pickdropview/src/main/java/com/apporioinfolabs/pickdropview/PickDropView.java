package com.apporioinfolabs.pickdropview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.apporioinfolabs.pickdropview.utils.Difusor;
import com.apporioinfolabs.pickdropview.utils.Logs;

import java.util.ArrayList;
import java.util.List;

public class PickDropView  extends LinearLayout {

    private Context mContext  = null  ;
    private static final String TAG = "PickDropView";
    private ImageView plus_button ;
    private LinearLayout drop_location_container ;
    private List<String> drop_location = new ArrayList<>();

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
            plus_button = findViewById(R.id.plus_button);
            drop_location_container = findViewById(R.id.drop_location_container);
            // should place after finding id of every element in whole view.
            setClickAction();
        }catch (Exception e){ Logs.e(TAG , ""+e.getMessage()); }
    }


    public void setClickAction(){


        plus_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Difusor.ENABLE_ADD_MORE_DESTINATION){
                    Toast.makeText(mContext, "Blocked By Difusor", Toast.LENGTH_SHORT).show();
                }else{
                    drop_location.add("");
                    setDropLocationsFromList();
                }
            }
        });
    }


    private void setDropLocationsFromList(){

        drop_location_container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        for(int i =0 ; i < drop_location.size() ; i ++){
            View inflatedLayout= inflater.inflate(R.layout.item_drop_location, null, false);
            drop_location_container.addView(inflatedLayout);
        }
    }


    public void initialiseListeners(OnCarcategoryViewListeners onCarcategoryViewListeners) {
        this.onCarcategoryViewListeners = onCarcategoryViewListeners ;
    }





    public interface OnCarcategoryViewListeners{
        void onElementSelect(String data);
    }

}