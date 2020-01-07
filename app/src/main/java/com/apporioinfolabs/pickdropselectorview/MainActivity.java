package com.apporioinfolabs.pickdropselectorview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apporioinfolabs.pickdropview.PickDropView;
import com.apporioinfolabs.pickdropview.model.ModelAddress;

public class MainActivity extends Activity {

    PickDropView pickDropView ;
    EditText drop_address_edt, pick_address_edt ;

    PickDropView.OnPickDropViewListeners onPickDropViewListeners = new PickDropView.OnPickDropViewListeners() {
        @Override
        public void onElementSelect(String data) {

        }

        @Override
        public void onPickLocationClick(ModelAddress modelAddress, int clickCount) {
            if(modelAddress == null){
                Toast.makeText(MainActivity.this, "No Pick address you can open Place Picker Activity., click:"+clickCount, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, ""+modelAddress.Address+" "+modelAddress.latitude+", "+modelAddress.longitude+", click:"+clickCount, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onDropLocationClick(ModelAddress modelAddress, int clickCount) {
            if(modelAddress == null){
                Toast.makeText(MainActivity.this, "No Drop address , click:"+clickCount, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, ""+modelAddress.Address+" "+modelAddress.latitude+", "+modelAddress.longitude+", click:"+clickCount, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickDropView = findViewById(R.id.pick_drop_view);
        drop_address_edt = findViewById(R.id.drop_address_edt);
        pick_address_edt = findViewById(R.id.pick_address_edt);

        pickDropView.setListners(onPickDropViewListeners);

        findViewById(R.id.enable_multi_drop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDropView.enableMultiDropFunctionality(!pickDropView.isMultiDropEnable());
            }
        });


        findViewById(R.id.add_drop_address_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drop_address_edt.getText().equals("") ){
                    pickDropView.addDropAddress(""+drop_address_edt.getText().toString(), 22.78979, 77.78974);
                    drop_address_edt.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter Some Address", Toast.LENGTH_SHORT).show();
                }
            }
        });



        findViewById(R.id.add_pick_address_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pick_address_edt.getText().equals("") ){
                    pickDropView.setPickAddress(""+pick_address_edt.getText().toString(), 22.789946, 77.3298747);
                    pick_address_edt.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter Some Address", Toast.LENGTH_SHORT).show();
                }
            }
        });


        pickDropView.abc();



    }


}
