package com.example.diesiedler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.richpath.RichPathView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();
    }

    public void clicked(String s) {
        new PresenterBuild().chooseAssetID(s);
    }

}
