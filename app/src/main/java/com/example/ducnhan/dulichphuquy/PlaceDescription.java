package com.example.ducnhan.dulichphuquy;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class PlaceDescription extends AppCompatActivity implements OnMapReadyCallback {

    PlaceInfo currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_description);

        currentPlace = (PlaceInfo) getIntent().getSerializableExtra("CLICKED_PLACE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);                          //will display the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//will display the back arrow '<-' button
        getSupportActionBar().setTitle(currentPlace.getName());

        //set the image under the CollapsingToolbarLayout
        ImageView i = (ImageView)findViewById(R.id.place);
        //i.setImageResource(currentPlace.getImageResourceId());
        Glide.with(this).load(currentPlace.getTopimage()).into(i);

        //to make status bar transparent i.e. image will cover status bar too
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //handle click event when back arrow '<-' is clicked on top
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set the text content on the ExpandableTextView

        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(currentPlace.getDescription());

        //to show the google map in lite mode
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //to display the images in custom slider view
        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);
        sliderShow.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));

        //add images to slides one by one and set time duration for each slide
        DefaultSliderView textSliderView = new DefaultSliderView(this);
        textSliderView.image(currentPlace.getTopimage()).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
        sliderShow.addSlider(textSliderView);
        sliderShow.setDuration(1500);

        DefaultSliderView textSliderView2 = new DefaultSliderView(this);
        textSliderView2.image(currentPlace.getSecondimage()).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
        sliderShow.addSlider(textSliderView2);
        sliderShow.setDuration(1500);

        DefaultSliderView textSliderView3 = new DefaultSliderView(this);
        textSliderView3.image(currentPlace.getThirdimage()).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
        sliderShow.addSlider(textSliderView3);
        sliderShow.setDuration(1500);

        DefaultSliderView textSliderView4 = new DefaultSliderView(this);
        textSliderView4.image(currentPlace.getFourthimage()).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
        sliderShow.addSlider(textSliderView4);
        sliderShow.setDuration(1500);

        //set the address
        TextView addr = (TextView)findViewById(R.id.address);
        addr.setText(currentPlace.getAddress());

        //set the phone number
        LinearLayout call_layer = (LinearLayout) findViewById (R.id.call_layout);
        TextView phNo = (TextView)findViewById(R.id.phone_No);
        if(!currentPlace.getPhoneno().equals("0"))
            phNo.setText(currentPlace.getPhoneno());
        else
            call_layer.setVisibility(View.GONE); //phone no will not be visible for places under shopping category

        //open call dialer when call icon or phone no is clicked
        call_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +  currentPlace.getPhoneno()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String name = currentPlace.getName();

        // Add a marker at location and move the camera.
        LatLng coordinates = new LatLng(currentPlace.getLattitude(), currentPlace.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(coordinates).title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
    }
}
