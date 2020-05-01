package com.example.diesiedler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.richpath.RichPathView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("BuildSettlement");
    }

    public void clicked(String s) throws ExecutionException, InterruptedException {
        new PresenterBuild().chooseAssetID(s);
    }

}
