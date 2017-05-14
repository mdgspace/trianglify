package com.sdsmdg.kd.trianglifyexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sdsmdg.kd.trianglify.views.TrianglifyView;
import com.sdsmdg.kd.trianglify.models.Palette;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public TrianglifyView trianglifyView;
    private SeekBar varianceSeekBar;
    private SeekBar cellSizeSeekBar;
    private SeekBar paletteSeekBar;
    private CheckBox strokeCheckBox;
    private CheckBox fillCheckBox;
    private CheckBox randomColoringCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_main_view);
        varianceSeekBar = (SeekBar) findViewById(R.id.variance_seekbar);
        varianceSeekBar.setMax(100);
        varianceSeekBar.setProgress(trianglifyView.getVariance());
        varianceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setVariance(progress+1);
                trianglifyView.generateAndInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cellSizeSeekBar = (SeekBar) findViewById(R.id.cell_size_seekbar);
        int maxCellSize = 150;

        cellSizeSeekBar.setMax(maxCellSize);
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize());
        cellSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setCellSize(progress+100);
                trianglifyView.generateAndInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paletteSeekBar = (SeekBar) findViewById(R.id.palette_seekbar);
        paletteSeekBar.setMax(Palette.values().length-1);
        paletteSeekBar.setProgress(1);
        paletteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setPalette(Palette.values()[progress]);
                trianglifyView.generateAndInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        strokeCheckBox = (CheckBox) findViewById(R.id.draw_stroke_checkbox);
        strokeCheckBox.setChecked(trianglifyView.isDrawStrokeEnabled());
        strokeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setDrawStrokeEnabled(isChecked);
                trianglifyView.generateAndInvalidate();
            }
        });

        fillCheckBox = (CheckBox) findViewById(R.id.draw_fill_checkbox);
        fillCheckBox.setChecked(trianglifyView.isFillTriangle());
        fillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setFillTriangle(isChecked);
                trianglifyView.generateAndInvalidate();
            }
        });

        randomColoringCheckbox = (CheckBox) findViewById(R.id.random_coloring_checkbox);
        randomColoringCheckbox.setChecked(trianglifyView.isRandomColoringEnabled());
        randomColoringCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setRandomColoring(isChecked);
                trianglifyView.generateAndInvalidate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void updateUIElements(TrianglifyView trianglifyView){
        fillCheckBox.setChecked(trianglifyView.isFillTriangle());
        strokeCheckBox.setChecked(trianglifyView.isDrawStrokeEnabled());
        randomColoringCheckbox.setChecked(trianglifyView.isRandomColoringEnabled());

        varianceSeekBar.setProgress(trianglifyView.getVariance());
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize());
        paletteSeekBar.setProgress(Palette.indexOf(trianglifyView.getPalette()));
    }

    public void randomizeTrianglifyParameters(TrianglifyView trianglifyView){
        Random rnd = new Random(System.currentTimeMillis());
        trianglifyView.setCellSize(dpToPx(rnd.nextInt(10) + 35))
                .setPalette(Palette.values()[rnd.nextInt(10)])
                .setRandomColoring(rnd.nextInt(2) == 0)
                .setFillTriangle(rnd.nextInt(2) == 0)
                .setVariance(rnd.nextInt(60));
        updateUIElements(trianglifyView);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_about was selected
            case R.id.action_about:
                Intent aboutActivityIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutActivityIntent);
                break;
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                randomizeTrianglifyParameters(trianglifyView);
                trianglifyView.generateAndInvalidate();
                break;
            default:
                break;
        }

        return true;
    }

    public void checkForColoringError(TrianglifyView trianglifyView) {
        if (!(trianglifyView.isFillTriangle() | trianglifyView.isDrawStrokeEnabled())) {
            Toast.makeText(this, "view should atleast be set to draw strokes or fill triangles or both.", Toast.LENGTH_LONG).show();
        }
    }
}
