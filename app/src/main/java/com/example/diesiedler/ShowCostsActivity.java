package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Activity which shows the Building-Costs.
 */
public class ShowCostsActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(ShowCostsActivity.class.getName()); // Logger

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_costs);
    }

    /**
     * Loads once more the ChooseActionActivity.
     *
     * @param view View to react on Buttonclick
     */
    public void ahead(View view) {
        Intent intent = new Intent(this, ChooseActionActivity.class);
        startActivity(intent);
    }
}
