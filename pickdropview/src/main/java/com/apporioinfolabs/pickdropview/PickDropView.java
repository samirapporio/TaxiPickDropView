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

import com.apporioinfolabs.pickdropview.model.ModelAddress;
import com.apporioinfolabs.pickdropview.utils.Logs;

import java.util.ArrayList;
import java.util.List;

public class PickDropView  extends LinearLayout {

    private Context mContext  = null  ;
    private static final String TAG = "PickDropView";
    private ImageView plus_button ;
    private LinearLayout drop_location_container ;
    private TextView pick_address_text, drop_address_text ;
    private List<ModelAddress> drop_location = new ArrayList<>();
    private static boolean MULTI_DROP_ENABLE = false ;
    private int pick_click_count = 0 , drop_click_count = 0 ;



    private ModelAddress PickAddress = null , DropAddress = null;
    private List<ModelAddress> multiAddress = new ArrayList<>();
    // interfaces
    private OnPickDropViewListeners onPickDropViewListeners;


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

    public void setListners(OnPickDropViewListeners onPickDropViewListeners){
        this.onPickDropViewListeners = onPickDropViewListeners ;
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
            drop_address_text = findViewById(R.id.drop_address_text);
            pick_address_text = findViewById(R.id.pick_address_text);


            setPlusDestinationVisibility();

            // should place after finding id of every element in whole view.
            setClickAction();
        }catch (Exception e){ Logs.e(TAG , ""+e.getMessage()); }
    }


    public void abc(){
        Toast.makeText(mContext, "ABC", Toast.LENGTH_SHORT).show();
    }

    // PLUS BUTON
    public void enableMultiDropFunctionality(boolean value){
        MULTI_DROP_ENABLE = value ;
        setPlusDestinationVisibility();
    }
    public boolean isMultiDropEnable(){ return MULTI_DROP_ENABLE ;}
    private void setPlusDestinationVisibility(){
        if(MULTI_DROP_ENABLE){
            plus_button.setVisibility(VISIBLE);
        }
        else{
            plus_button.setVisibility(GONE);
        }
    }
    public void setClickAction(){

        plus_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MULTI_DROP_ENABLE){
                    Toast.makeText(mContext, "Multi-Drop point is disable.", Toast.LENGTH_SHORT).show();
                }else{
                    if(DropAddress != null){
                        if(drop_location.size() == 0 ){
                            drop_location.add(null);
                        }else{
                            if(drop_location.get(drop_location.size() - 1) == null){
                                Toast.makeText(mContext, "Please Add last stop before adding the new one.", Toast.LENGTH_SHORT).show();
                            }else{
                                drop_location.add(null);
                            }
                        }

                        setDropLocationsFromList();
                    }else{
                        Toast.makeText(mContext, "Please fill drop address before adding another one.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        pick_address_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drop_click_count = 0 ;
                pick_click_count = pick_click_count + 1 ;
                onPickDropViewListeners.onPickLocationClick(PickAddress, pick_click_count);
            }
        });

        drop_address_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pick_click_count = 0 ;
                drop_click_count = drop_click_count +1 ;
                onPickDropViewListeners.onDropLocationClick(DropAddress, drop_click_count);
            }
        });
    }



    // ADDRESS SETTER
    public void setPickAddress(String address, double latitude , double longitude){
        ModelAddress modelAddress = new ModelAddress(address, latitude, longitude) ;
        PickAddress = modelAddress ;
        pick_address_text.setText(""+address);
        pick_click_count = 0 ;
        drop_click_count = 0 ;
    }
    public void addDropAddress(String address, double latitude , double longitude){
        if(DropAddress == null){

            DropAddress = new ModelAddress(address, latitude, longitude) ;
            drop_address_text.setText(""+address);
            pick_click_count = 0 ;
            drop_click_count = 0 ;
        }else{
            if(drop_location.size()> 0 ){
                drop_location.set(drop_location.size()-1,new ModelAddress(address, latitude, longitude));
                setDropLocationsFromList();
            }else{
                Toast.makeText(mContext, "Got No view to add address", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public ModelAddress getPickAddress(){
        return PickAddress ;
    }
    public ModelAddress getDropAddress(){
        return DropAddress;
    }




    private void setDropLocationsFromList(){

        drop_location_container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        for(int i =0 ; i < drop_location.size() ; i ++){
            View inflatedLayout= inflater.inflate(R.layout.item_drop_location, null, false);
            final int finalI = i;
            TextView addressText = inflatedLayout.findViewById(R.id.address_text);

            if(drop_location.get(i) != null){ addressText.setText(""+drop_location.get(i).Address); }





            inflatedLayout.findViewById(R.id.delete).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    drop_location.remove(finalI);
                    setDropLocationsFromList();
                }
            });
            drop_location_container.addView(inflatedLayout);
        }
    }


    public void initialiseListeners(OnPickDropViewListeners onPickDropViewListeners) {
        this.onPickDropViewListeners = onPickDropViewListeners;
    }


    public interface OnPickDropViewListeners {
        void onElementSelect(String data);
        void onPickLocationClick(ModelAddress modelAddress, int clickCount);
        void onDropLocationClick(ModelAddress modelAddress, int clickCount);

    }

}