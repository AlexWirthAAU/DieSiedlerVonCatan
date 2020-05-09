package com.example.diesiedler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.richpath.RichPathView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();
    }

    public void clicked(String s) {
        // not used?
    }

    // TODO: Handler

}
