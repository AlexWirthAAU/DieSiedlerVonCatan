package com.example.diesiedler.presenter;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diesiedler.R;

import java.util.Random;

/**
 * @Author Alex Wirth
 * This class is handling the dicing action. When the phone is shaken in the RollDiceActivity, a random number between 1-6 for each dice is determined. Depending on the number, the
 * imageviews of the two dices will be refreshed. The total of the two dices will be transmitted to the RollDiceActivity where the further game action is handled.
 */
public class RollDice {

    private ImageView diceOne;
    private ImageView diceTwo;
    private TextView figure;
    private Random random = new Random();
    int random1;
    int random2;


    public RollDice(Activity activity) {
        diceOne = activity.findViewById(R.id.diceOne);
        diceTwo = activity.findViewById(R.id.diceTwo);
        figure = activity.findViewById(R.id.figure);
        diceOne.setImageResource(R.drawable.one);
        diceOne.setImageResource(R.drawable.one);
        figure.setText("Gewürfelte Zahl");
    }

    /**
     * Two random ints are determined by the help of the Random class.
     * Depending on the randoms, the imageviews of the dices are updated.
     * The text right below the two dices will be updated, showing the player which number he rolled.
     *
     * @return = total of dice-values -> called in "RollDiceActivity"
     */
    public int rollDice() {
        random1 = setRandom1();
        random2 = setRandom2();
        int sum = random1 + random2;
        String sumText = "" + sum;

        setDiceOne(random1);
        setDiceTwo(random2);
        figure.setText(sumText);
        return random2 + random1;
    }

    public void setDiceOne(int n) {
        int number = n;

        switch (number) {
            case 1:
                diceOne.setImageResource(R.drawable.one);
                break;
            case 2:
                diceOne.setImageResource(R.drawable.two);
                break;
            case 3:
                diceOne.setImageResource(R.drawable.three);
                break;
            case 4:
                diceOne.setImageResource(R.drawable.four);
                break;
            case 5:
                diceOne.setImageResource(R.drawable.five);
                break;
            case 6:
                diceOne.setImageResource(R.drawable.six);
                break;
            default:
                break;
        }
    }

    public void setDiceTwo(int n) {
        int number = n;

        switch (number) {
            case 1:
                diceTwo.setImageResource(R.drawable.one);
                break;
            case 2:
                diceTwo.setImageResource(R.drawable.two);
                break;
            case 3:
                diceTwo.setImageResource(R.drawable.three);
                break;
            case 4:
                diceTwo.setImageResource(R.drawable.four);
                break;
            case 5:
                diceTwo.setImageResource(R.drawable.five);
                break;
            case 6:
                diceTwo.setImageResource(R.drawable.six);
                break;
            default:
                break;
        }
    }

    private int setRandom1() {
        return random.nextInt(6) + 1;
    }

    private int setRandom2() {
        return random.nextInt(6) + 1;
    }

}
