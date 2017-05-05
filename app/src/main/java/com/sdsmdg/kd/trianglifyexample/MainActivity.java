package com.sdsmdg.kd.trianglifyexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sdsmdg.kd.trianglify.views.TrianglifyView;
import com.sdsmdg.kd.trianglify.models.Palette;

public class MainActivity extends AppCompatActivity {
    Palette palettes[] = {Palette.YlGn, Palette.YlGnBu, Palette.GnBu, Palette.BuGn, Palette.PuBuGn,
            Palette.PuBu, Palette.BuPu, Palette.RdPu, Palette.PuRd, Palette.OrRd, Palette.YlOrRd,
            Palette.YlOrBr, Palette.Purples, Palette.Blues, Palette.Greens, Palette.Oranges,
            Palette.Reds, Palette.Greys, Palette.PuOr, Palette.BrBG, Palette.PRGn, Palette.PiYG,
            Palette.RdBu, Palette.RdGy, Palette.RdYlBu, Palette.Spectral, Palette.RdYlGn, Palette.Yl};

    public TrianglifyView trianglifyView;
    private SeekBar varianceSeekBar;
    private SeekBar cellSizeSeekBar;
    private SeekBar paletteSeekBar;
    private CheckBox strokeCheckBox;
    private CheckBox fillCheckBox;
    private CheckBox randomColoring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_main_view);

        trianglifyView.setGridWidth(trianglifyView.getWidth())
                .setGridHeight(trianglifyView.getHeight());

        varianceSeekBar = (SeekBar) findViewById(R.id.variance_seekbar);
        varianceSeekBar.setMax(100);
        varianceSeekBar.setProgress(trianglifyView.getVariance());
        varianceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setVariance(progress+1);
                trianglifyView.invalidate();
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
                trianglifyView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paletteSeekBar = (SeekBar) findViewById(R.id.palette_seekbar);
        paletteSeekBar.setMax(palettes.length-1);
        paletteSeekBar.setProgress(1);
        paletteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setPalette(palettes[progress]);
                trianglifyView.invalidate();
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
                trianglifyView.invalidate();
            }
        });

        fillCheckBox = (CheckBox) findViewById(R.id.draw_fill_checkbox);
        fillCheckBox.setChecked(trianglifyView.isFillTriangle());
        fillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setFillTriangle(isChecked);
                trianglifyView.invalidate();
            }
        });

        randomColoring = (CheckBox) findViewById(R.id.random_coloring_checkbox);
        randomColoring.setChecked(trianglifyView.isFillTriangle());
        randomColoring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setRandomColoring(isChecked);
                trianglifyView.invalidate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
                trianglifyView.invalidate();
                break;
            default:
                break;
        }

        return true;
    }
}
