package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;

//Geladen, wenn eine Trade Message vom Server kommt

/**
 * TODO: Implement server answer
 */
public class AnswerToTradeActivity extends AppCompatActivity {

    private GameSession game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_to_trade);

        game = (GameSession) getIntent().getSerializableExtra("game");
        String tradeMessage = getIntent().getStringExtra("tradeMessage");
        TextView tradeMes = findViewById(R.id.tradeMes);
        tradeMes.setText(tradeMessage);
    }

    public void dismiss(View view) {
        /**TODO: Implement server call: send map to the Server*/

        String toSendanswer = "dismissed";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void accept(View view) {
        /**TODO: Implement server call: send answer to the Server*/

        String toSendanswer = "accepted";
    }
}
