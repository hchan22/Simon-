package nyc.c4q.helenchan.simon;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by helenchan on 9/26/16.
 */

public class GameScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "hi";
    private static final long ONE_SECOND = 700;
    private Button redButton;
    private Button blueButton;
    private Button greenButton;
    private Button yellowButton;
    public int arrayNumberValues;
    public TextView roundNumber;
    public long TWO_SECONDS = 2000;
    public int level = 1;


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
        //int randomColorNum = (int) (Math.random() * 4);
        Random rand = new Random();
        int randomColorNum = rand.nextInt(4);
        Log.d(TAG, String.valueOf((randomColorNum)));
        return randomColorNum;

    }


    public void addColorNumToArray(int randomNumber) {
        ranNum.add(randomNumberGen());

    }

//
//    public void listingArrayContents(ArrayList<Integer> array) {
//        for (int i = 0; i < array.size(); i++) {
//            arrayNumberValues = array.get(i);
//        }
//    }


    public void colorFlashInGame() {
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
                            greenButton.setBackgroundColor(getResources().getColor(R.color.clickgreen));
                        }
                    }, ONE_SECOND);
                    break;
                case "red":
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            redButton.setBackgroundColor(getResources().getColor(R.color.clickred));
                        }
                    }, ONE_SECOND);
                    break;
                case "blue":
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            blueButton.setBackgroundColor(getResources().getColor(R.color.clickblue));
                        }
                    }, ONE_SECOND);
                    break;
                case "yellow":
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            yellowButton.setBackgroundColor(getResources().getColor(R.color.clickyellow));
                        }
                    }, ONE_SECOND);
                    break;
            }
                }
            }, 200);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetButtonColors();
                }
            }, TWO_SECONDS);
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
            return false;

        } else {
            for (int j = 0; j < input.size(); j++) {
                if (setList.get(j) != input.get(j))
                    return false;
            }
        }
        return true;

    }

    public void roundWon() {
        userInput.clear();
        addColorNumToArray(randomNumberGen());
        colorFlashInGame();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red:
                userInput.add(0);
                break;
            case R.id.yellow:
                userInput.add(1);
                Log.i("RAN NUM ARRAY SIZE", ranNum.size() + "");

                break;
            case R.id.blue:
                userInput.add(2);
                break;
            case R.id.green:
                userInput.add(3);
                break;

        }
        Log.d(TAG, "switching");

        if (areArraysEqual(userInput, ranNum)) {
            level++;
            roundNumber.setText("Round: " + String.valueOf(level));
            roundWon();

        } else {
            Toast.makeText(GameScreen.this, "You Lose", Toast.LENGTH_LONG);

        }

    }


}


//    public void addUserInputToArr(){
//        userInput.add();
//    }

//    public void buttonClick(View v){
//        switch (v.getId()){
//            case R.id.blue:
//
//        }
//    }
//    public void getColor(){
//        for (Integer random:colorGen.keySet()){
//            colorGen.get(random);
//        }
//    }

//    Handler handler = new Handler();
//    handler.postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            // Do something after 5s = 5000ms
//        }
//    }, 5000);
//    colorGen.get(randomNumberGen())