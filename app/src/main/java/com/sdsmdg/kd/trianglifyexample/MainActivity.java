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
    private CheckBox customPaletteCheckbox;
    private Palette customPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_main_view);
        customPalette = trianglifyView.getPalette();

        varianceSeekBar = (SeekBar) findViewById(R.id.variance_seekbar);
        varianceSeekBar.setMax(100);
        varianceSeekBar.setProgress(trianglifyView.getVariance());
        varianceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setVariance(progress+1);
                trianglifyView.smartUpdate();
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
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize()-100);
        cellSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setCellSize(progress+100);
                trianglifyView.smartUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paletteSeekBar = (SeekBar) findViewById(R.id.palette_seekbar);
        paletteSeekBar.setMax(Palette.DEFAULT_PALETTE_COUNT - 1);
        paletteSeekBar.setProgress(Palette.indexOf(trianglifyView.getPalette()));
        paletteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setPalette(Palette.getPalette(progress));
                customPaletteCheckbox.setChecked(false);
                trianglifyView.smartUpdate();
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
                if (isChecked || trianglifyView.isFillTriangle()) {
                    trianglifyView.setDrawStrokeEnabled(isChecked);
                    strokeCheckBox.setChecked(isChecked);
                    trianglifyView.smartUpdate();
                } else {
                    strokeCheckBox.setChecked(!isChecked);
                    showColoringError();
                }
            }
        });

        fillCheckBox = (CheckBox) findViewById(R.id.draw_fill_checkbox);
        fillCheckBox.setChecked(trianglifyView.isFillTriangle());
        fillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked || trianglifyView.isDrawStrokeEnabled()) {
                    trianglifyView.setFillTriangle(isChecked);
                    fillCheckBox.setChecked(isChecked);
                    trianglifyView.smartUpdate();
                } else {
                    fillCheckBox.setChecked(!isChecked);
                    showColoringError();
                }
            }
        });

        randomColoringCheckbox = (CheckBox) findViewById(R.id.random_coloring_checkbox);
        randomColoringCheckbox.setChecked(trianglifyView.isRandomColoringEnabled());
        randomColoringCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setRandomColoring(isChecked);
                trianglifyView.smartUpdate();
            }
        });

        customPaletteCheckbox = (CheckBox) findViewById(R.id.custom_palette_checkbox);
        customPaletteCheckbox.setChecked(false);
        customPaletteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    trianglifyView.setPalette(customPalette);
                    trianglifyView.smartUpdate();
                } else {
                    trianglifyView.setPalette(Palette.getPalette(paletteSeekBar.getProgress()));
                    trianglifyView.smartUpdate();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void updateUIElements(TrianglifyView trianglifyView) {
        fillCheckBox.setChecked(trianglifyView.isFillTriangle());
        strokeCheckBox.setChecked(trianglifyView.isDrawStrokeEnabled());
        randomColoringCheckbox.setChecked(trianglifyView.isRandomColoringEnabled());

        varianceSeekBar.setProgress(trianglifyView.getVariance());
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize());
        paletteSeekBar.setProgress(Palette.indexOf(trianglifyView.getPalette()));
    }

    public void randomizeTrianglifyParameters(TrianglifyView trianglifyView) {
        Random rnd = new Random(System.currentTimeMillis());
        trianglifyView.setCellSize(dpToPx(rnd.nextInt(10) + 35))
                .setPalette(Palette.getPalette(rnd.nextInt(28)))
                .setRandomColoring(rnd.nextInt(2) == 0)
                .setFillTriangle(rnd.nextInt(2) == 0)
                .setDrawStrokeEnabled(rnd.nextInt(2) == 0)
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
            // action with ID custom_palette_picker was selected
            case R.id.custom_palette_picker:
                Intent customPalettePickerIntent = new Intent(this, CustomPalettePickerActivity.class);
                customPalettePickerIntent.putExtra("Palette Color Array",trianglifyView.getPalette().getColors());
                startActivityForResult(customPalettePickerIntent,1);
                break;
            default:
                break;
        }

        return true;
    }

    public void showColoringError() {
        Toast.makeText(this, "view should at least be set to draw strokes or fill triangles or both.", Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                customPalette = new Palette(data.getIntArrayExtra("Custom Palette Color Array"));
                if ( customPaletteCheckbox.isChecked()) {
                    trianglifyView.setPalette(customPalette);
                    trianglifyView.smartUpdate();
                }
            }
        }
    }
}
