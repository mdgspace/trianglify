package com.sdsmdg.kd.trianglifyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.sdsmdg.kd.trianglify.TrianglifyView;
import com.sdsmdg.kd.trianglify.models.Palette;

public class MainActivity extends AppCompatActivity {
    Palette palettes[] = {Palette.YlGn, Palette.YlGnBu, Palette.GnBu, Palette.BuGn, Palette.PuBuGn,
            Palette.PuBu, Palette.BuPu, Palette.RdPu, Palette.PuRd, Palette.OrRd, Palette.YlOrRd,
            Palette.YlOrBr, Palette.Purples, Palette.Blues, Palette.Greens, Palette.Oranges,
            Palette.Reds, Palette.Greys, Palette.PuOr, Palette.BrBG, Palette.PRGn, Palette.PiYG,
            Palette.RdBu, Palette.RdGy, Palette.RdYlBu, Palette.Spectral, Palette.RdYlGn};

    public TrianglifyView trianglifyView;
    private SeekBar varianceSeekBar;
    private SeekBar cellSizeSeekBar;
    private SeekBar paletteSeekBar;

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
                trianglifyView.setVariance(progress);
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
        int maxCellSize = trianglifyView.getGridHeight() > trianglifyView.getGridWidth()
                ? trianglifyView.getGridHeight()
                : trianglifyView.getGridWidth();

        cellSizeSeekBar.setMax(maxCellSize);
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize());
        cellSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setCellSize(progress);
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
        paletteSeekBar.setMax(palettes.length);
        paletteSeekBar.setProgress(1);
        paletteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setPalette(palettes[progress]);
                Log.e("error", palettes[progress].toString());
                trianglifyView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
