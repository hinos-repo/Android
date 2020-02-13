package com.mmpc5.stream;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.Equalizer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;



import java.util.ArrayList;

import static com.mmpc5.stream.helper.Constants.LINK_FOR_TRACK;


public class MainActivity extends AppCompatActivity implements INetConnect{


    private PlayerView playerView;
    private Equalizer mEqualizer;

    // UI elements
    private LinearLayout mLinearLayout;
    private Button button_view;

    // Custom dialog
    private TextView textViewParams;
    private Button btnSendMail;


    private Uri mp4VideoUri = Uri.parse(LINK_FOR_TRACK);


    // preset level 1
    String strBandLevelOne = "";
    String strBandIndexOne ="";
    // preset level 2
    String strBandLevelTwo = "";
    String strBandIndexTwo ="";
    // preset level 3
    String strBandLevelThree = "";
    String strBandIndexThree ="";
    // preset level 4
    String strBandLevelFour = "";
    String strBandIndexFour ="";
    // preset level 5
    String strBandLevelFive = "";
    String strBandIndexFive ="";

    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkConnected() == true) {
            startExoPlayer(mp4VideoUri);
            button_view = findViewById(R.id.button_view);
            button_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    test = "Level: " + strBandLevelOne + "; Index: " + strBandIndexOne + "\n"
                            + "Level: " + strBandLevelTwo + "; Index: " + strBandIndexTwo + "\n"
                            + "Level: " + strBandLevelThree + "; Index: " + strBandIndexThree + "\n"
                            + "Level: " + strBandLevelFour + "; Index: " + strBandIndexFour + "\n"
                            + "Level: " + strBandLevelFive + "; Index: " + strBandIndexFive + "\n";

                    //sendMail();
                    openAlertDialog(test);
                }
            });
        }
        else{
            falseNetworkConnected();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing() && playerView != null) {

            mEqualizer.release();
            playerView = null;
        }
    }



    public void startExoPlayer(Uri uri){

        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create the player
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(MainActivity.this, trackSelector);





        playerView = findViewById(R.id.playerView);
        playerView.setVisibility(View.VISIBLE);
        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                playerView.showController();
            }
        });

        playerView.setPlayer(player);




        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, Util.getUserAgent(MainActivity.this, "yourApplicationName"), defaultBandwidthMeter);



        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        player.prepare(videoSource);




        player.setAudioDebugListener(new AudioRendererEventListener() {
            @Override
            public void onAudioEnabled(DecoderCounters counters) {

            }

            @Override
            public void onAudioSessionId(int audioSessionId) {

                mEqualizer = new Equalizer(0, audioSessionId);
                mEqualizer.setEnabled(true);
                setupEqualizerFxAndUI();
            }

            @Override
            public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

            }

            @Override
            public void onAudioInputFormatChanged(Format format) {

            }

            @Override
            public void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {

            }

            @Override
            public void onAudioDisabled(DecoderCounters counters) {

            }
        });


    }

    // Method for add "StatusBar" in Activity and setting values
    private void setupEqualizerFxAndUI() {



        //get reference to linear layout for the seekBars
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutEqual);

        //equalizer heading
        TextView equalizerHeading = new TextView(this);
        equalizerHeading.setText("Equalizer");
        equalizerHeading.setTextSize(20);
        equalizerHeading.setGravity(Gravity.CENTER_HORIZONTAL);
        mLinearLayout.addView(equalizerHeading);

        //get number frequency bands supported by the equalizer engine
        short numberFrequencyBands = mEqualizer.getNumberOfBands();

        //get the level ranges to be used in setting the band level
        //get lower limit of the range in milliBels
        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
        //get the upper limit of the range in millibels
        final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];

        //loop through all the equalizer bands to display the band headings, lower
        //& upper levels and the seek bars
        for (short i = 0; i < numberFrequencyBands; i++) {
            final short equalizerBandIndex = i;


        //frequency header for each seekBar
            TextView frequencyHeaderTextview = new TextView(this);
            frequencyHeaderTextview.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            frequencyHeaderTextview.setGravity(Gravity.CENTER_HORIZONTAL);
            frequencyHeaderTextview
                    .setText((mEqualizer.getCenterFreq(equalizerBandIndex) / 1000) + " Hz");
            mLinearLayout.addView(frequencyHeaderTextview);

        //set up linear layout to contain each seekBar
            LinearLayout seekBarRowLayout = new LinearLayout(this);
            seekBarRowLayout.setOrientation(LinearLayout.HORIZONTAL);

        //set up lower level textview for this seekBar
            TextView lowerEqualizerBandLevelTextview = new TextView(this);
            lowerEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            lowerEqualizerBandLevelTextview.setText((lowerEqualizerBandLevel / 100) + " dB");
        //set up upper level textview for this seekBar
            TextView upperEqualizerBandLevelTextview = new TextView(this);
            upperEqualizerBandLevelTextview.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            upperEqualizerBandLevelTextview.setText((upperEqualizerBandLevel / 100) + " dB");

            //            **********  the seekBar  **************
        //set the layout parameters for the seekbar
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;

        //create a new seekBar
            SeekBar seekBar = new SeekBar(this);
        //give the seekBar an ID
            seekBar.setId(i);

            seekBar.setLayoutParams(layoutParams);
            seekBar.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        //set the progress for this seekBar
            seekBar.setProgress(mEqualizer.getBandLevel(equalizerBandIndex));

        //change progress as its changed by moving the sliders
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    mEqualizer.setBandLevel(equalizerBandIndex,
                            (short) (progress + lowerEqualizerBandLevel));
                    Log.d("data_test","Band level:"+String.valueOf(equalizerBandIndex) + " Band index:" +  String.valueOf(progress + lowerEqualizerBandLevel));
                    // Toast toast = Toast.makeText(getApplicationContext(),
                    //"Band level:"+String.valueOf(equalizerBandIndex) + " Band index:" +  String.valueOf(progress + lowerEqualizerBandLevel), Toast.LENGTH_SHORT);
                    // toast.show();

                    if(equalizerBandIndex == 0){
                        strBandLevelOne = String.valueOf(equalizerBandIndex);
                        strBandIndexOne = String.valueOf(progress + lowerEqualizerBandLevel);
                    }
                    else if(equalizerBandIndex == 1){
                        strBandLevelTwo = String.valueOf(equalizerBandIndex);
                        strBandIndexTwo = String.valueOf(progress + lowerEqualizerBandLevel);
                    }
                    else if(equalizerBandIndex == 2){
                        strBandLevelThree = String.valueOf(equalizerBandIndex);
                        strBandIndexThree = String.valueOf(progress + lowerEqualizerBandLevel);
                    }
                    else if(equalizerBandIndex == 3){
                        strBandLevelFour = String.valueOf(equalizerBandIndex);
                        strBandIndexFour = String.valueOf(progress + lowerEqualizerBandLevel);
                    }
                    else if(equalizerBandIndex == 4){
                        strBandLevelFive = String.valueOf(equalizerBandIndex);
                        strBandIndexFive = String.valueOf(progress + lowerEqualizerBandLevel);
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    //not used
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                    //not used

                }
            });



            //add the lower and upper band level textviews and the seekBar to the row layout
            seekBarRowLayout.addView(lowerEqualizerBandLevelTextview);
            seekBarRowLayout.addView(seekBar);
            seekBarRowLayout.addView(upperEqualizerBandLevelTextview);

            mLinearLayout.addView(seekBarRowLayout);

            //        show the spinner
            equalizeSound();
        }
    }

    // Method for Spinner with categories music(jazz/pop etc.)
    private void equalizeSound() {
        //set up the spinner
        ArrayList<String> equalizerPresetNames = new ArrayList<String>();
        ArrayAdapter<String> equalizerPresetSpinnerAdapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                equalizerPresetNames);
        equalizerPresetSpinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner equalizerPresetSpinner = (Spinner) findViewById(R.id.spinner);

        //get list of the device's equalizer presets
        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {

            // edit name "Cinema" or "Concert"

            equalizerPresetNames.add(mEqualizer.getPresetName(i));

            //Log.d("SearchCountParams", String.valueOf( mEqualizer.getNumberOfPresets()));
        }

        equalizerPresetSpinner.setAdapter(equalizerPresetSpinnerAdapter);

        //handle the spinner item selections
        equalizerPresetSpinner.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                //first list item selected by default and sets the preset accordingly
                mEqualizer.usePreset((short) position);
                //get the number of frequency bands for this equalizer engine
                short numberFrequencyBands = mEqualizer.getNumberOfBands();
                //get the lower gain setting for this equalizer band
                final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];

                //set seekBar indicators according to selected preset
                for (short i = 0; i < numberFrequencyBands; i++) {
                    short equalizerBandIndex = i;

                    SeekBar seekBar = (SeekBar) findViewById(equalizerBandIndex);
                    //get current gain setting for this equalizer band
                    //set the progress indicator of this seekBar to indicate the current gain value
                    seekBar.setProgress(mEqualizer
                            .getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            //not used
            }
        });
    }



    // Method for check status network(true/false)
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            Log.d("isNetworkConnected","true");
            return true;

        } else {
            Log.d("isNetworkConnected","false");
            return false;
        }
    }

    // Method for call "openAlertDialog"
    public void falseNetworkConnected(){
        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Network disconnected");
        alert.setMessage("No access to the network! " +
                "Check the WiFi settings.");
        alert.setButton(Dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //ENG: closing the application
                //RUS: закрываем приложение
                finish();
            }
        });
        alert.show();
    }

    // Method for send data about presets equalizer
    private void sendMail(String data){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{""});  //
        i.putExtra(Intent.EXTRA_SUBJECT, "Presets equalizer");
        i.putExtra(Intent.EXTRA_TEXT   , data);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    // Method for open Dialog when don't work network
    private void openAlertDialog(final String dataEqualizer){



        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.send_custom_dialog,null);

        textViewParams = view.findViewById(R.id.textView_params);
        btnSendMail = view.findViewById(R.id.button_send_mail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(dataEqualizer);
            }
        });

        textViewParams.setText(dataEqualizer);


        final AlertDialog alertDialog =  builder.create();
        alertDialog.setView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        alertDialog.show();

    }

}
