package nyc.c4q.helenchan.simon;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by helenchan on 9/26/16.
 */

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "hi";
    private static final long ONE_SECOND = 1000;
    private Button redButton;
    private Button blueButton;
    private Button greenButton;
    private Button yellowButton;
    public int arrayNumberValues;
    public TextView roundNumber;
    public int level = 1;
    private int delayRound;
    private static boolean checkingForMatch;

    Map<Integer, String> colorGen = new HashMap<Integer, String>();
    ArrayList<Integer> ranNum = new ArrayList<>();
    ArrayList<Integer> userInput = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        initialize();
        colorGen.put(0, "red");
        colorGen.put(1, "yellow");
        colorGen.put(2, "blue");
        colorGen.put(3, "green");
        roundNumber.setText("Round: " + level);
        addColorNumToArray(randomNumberGen());
        colorFlashInGame();


    }

    public void initialize() {
        redButton = (Button) findViewById(R.id.red);
        blueButton = (Button) findViewById(R.id.blue);
        greenButton = (Button) findViewById(R.id.green);
        yellowButton = (Button) findViewById(R.id.yellow);
        roundNumber = (TextView) findViewById(R.id.round_number);
    }


    public int randomNumberGen() {
        Random rand = new Random();
        int randomColorNum = rand.nextInt(4);
        Log.d(TAG, String.valueOf((randomColorNum)));
        return randomColorNum;

    }


    public void addColorNumToArray(int randomNumber) {
        ranNum.add(randomNumberGen());

    }



    public void colorFlashInGame() {
        final MediaPlayer buttonSoundRed = MediaPlayer.create(this, R.raw.sound_one);
        final MediaPlayer buttonSoundYellow = MediaPlayer.create(this, R.raw.sound_two);
        final MediaPlayer buttonSoundBlue = MediaPlayer.create(this, R.raw.sound_three);
        final MediaPlayer buttonSoundGreen = MediaPlayer.create(this, R.raw.sound_four);

        for (int i = 0; i < ranNum.size(); i++) {
            final Handler handle = new Handler();
            final int finalI = i;
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
            arrayNumberValues = ranNum.get(finalI);

            String selectedColor = colorGen.get(arrayNumberValues);
            Log.d(TAG, selectedColor);

            switch (selectedColor) {
                case "green":
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashGreen();
                            buttonSoundGreen.start();
                        }
                    }, (ONE_SECOND * finalI)  );
                    break;

                case "red":
//                    redButton.setBackgroundColor(getResources().getColor(R.color.clickred));
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                flashRed();
                             buttonSoundRed.start();
//                            resetButtonColors();
                        }
                    }, (ONE_SECOND * finalI)  );
                    break;
                case "blue":
//                    blueButton.setBackgroundColor(getResources().getColor(R.color.clickblue));
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flashBlue();
                            buttonSoundBlue.start();
//                            resetButtonColors();
                        }
                    }, (ONE_SECOND * finalI)  );
                    break;
                case "yellow":
//                    yellowButton.setBackgroundColor(getResources().getColor(R.color.clickyellow));
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        flashYellow();
                            buttonSoundYellow.start();
//                            resetButtonColors();
                        }
                    }, (ONE_SECOND * finalI)  );
                    break;
            }
                }
            }, delayRound +=250);

        }
    }


    public void resetButtonColors() {
        redButton.setBackgroundResource(R.drawable.red_button);
        blueButton.setBackgroundResource(R.drawable.blue_button);
        greenButton.setBackgroundResource(R.drawable.green_button);
        yellowButton.setBackgroundResource(R.drawable.yellow_button);
    }


    public boolean areArraysEqual(ArrayList input, ArrayList setList) {
        if (setList.size() != input.size()) {
           for (int j = 0; j < input.size(); j++) {
                if (setList.get(j) != input.get(j))
                    return false;
            }
        }
        Log.d(TAG," AREARRAYSEQUAL this is going to be true");
        return true;
    }




    public void roundWon() {
        userInput.clear();
        addColorNumToArray(randomNumberGen());
        colorFlashInGame();
    }


    @Override
    public void onClick(View view) {
        MediaPlayer buttonSoundRed = MediaPlayer.create(this, R.raw.sound_one);
        MediaPlayer buttonSoundYellow = MediaPlayer.create(this, R.raw.sound_two);
        MediaPlayer buttonSoundBlue = MediaPlayer.create(this, R.raw.sound_three);
        MediaPlayer buttonSoundGreen = MediaPlayer.create(this, R.raw.sound_four);

        switch (view.getId()) {
            case R.id.red:
                userInput.add(0);
                buttonSoundRed.start();
                areArraysEqual(userInput, ranNum);

                break;
            case R.id.yellow:
                userInput.add(1);
                buttonSoundYellow.start();
                Log.i("RAN NUM ARRAY SIZE", ranNum.size() + "");
                areArraysEqual(userInput, ranNum);

                break;
            case R.id.blue:
                userInput.add(2);
                buttonSoundBlue.start();
                areArraysEqual(userInput, ranNum);

                break;
            case R.id.green:
                userInput.add(3);
                buttonSoundGreen.start();
                areArraysEqual(userInput, ranNum);
                break;
        }
        Log.d(TAG, "switching");


        if (areArraysEqual(userInput, ranNum) == true) {
            level++;
            roundNumber.setText("Round: " + String.valueOf(level));
            roundWon();

        } else if (areArraysEqual(userInput, ranNum) == false) {
//            Toast.makeText(GameScreen.this, "You Lose", Toast.LENGTH_LONG).show();
//            finish();
        }
    }

    private void flashRed() {
        Log.d(TAG,"RED_ANIMATION");
        Animation myAnimation = new AlphaAnimation(1,0);
        myAnimation.setDuration(100);
        myAnimation.setInterpolator(new LinearInterpolator());
        myAnimation.setRepeatCount(1);
        myAnimation.setRepeatMode(Animation.REVERSE);
        redButton.startAnimation(myAnimation);
    }

    private void flashBlue() {
        Log.d(TAG,"BLUE_ANIMATION");
        Animation myAnimation = new AlphaAnimation(1, 0);
        myAnimation.setDuration(100);
        myAnimation.setInterpolator(new LinearInterpolator());
        myAnimation.setRepeatCount(1);
        myAnimation.setRepeatMode(Animation.REVERSE);
        blueButton.startAnimation(myAnimation);
    }

    private void flashGreen() {
        Log.d(TAG,"GREEN_ANIMATION");
        Animation myAnimation = new AlphaAnimation(1, 0);
        myAnimation.setDuration(100);
        myAnimation.setInterpolator(new LinearInterpolator());
        myAnimation.setRepeatCount(1);
        myAnimation.setRepeatMode(Animation.REVERSE);
        greenButton.startAnimation(myAnimation);
    }
    private void flashYellow() {
        Log.d(TAG,"YELLOW_ANIMATION");
        Animation myAnimation = new AlphaAnimation(1, 0); //transparency
        myAnimation.setDuration(100); //how long to change color
        myAnimation.setInterpolator(new LinearInterpolator());  //rate of change
        myAnimation.setRepeatCount(1);  //change color once
        myAnimation.setRepeatMode(Animation.REVERSE); //change back to original color
        yellowButton.startAnimation(myAnimation); //to start animation method
    }

}


