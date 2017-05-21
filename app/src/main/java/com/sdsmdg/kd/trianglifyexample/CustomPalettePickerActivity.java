package com.sdsmdg.kd.trianglifyexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.views.TrianglifyView;

public class CustomPalettePickerActivity extends AppCompatActivity {
    private Palette palette;
    private TrianglifyView trianglifyView;
    private Context context;
    private ImageView c0;
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;
    private ImageView c4;
    private ImageView c5;
    private ImageView c6;
    private ImageView c7;
    private ImageView c8;
    private int[] colors = {Color.BLACK, Color.BLUE, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.RED, Color.MAGENTA, Color.LTGRAY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_palette_picker);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Custom Palette Picker");

        colors = getIntent().getIntArrayExtra("Palette Color Array");

        context = this;

        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_custom_palette_view);
        trianglifyView.setPalette(new Palette(colors));
        trianglifyView.smartUpdate();

        int[] imageViewID = {R.id.custom_palette_c0, R.id.custom_palette_c1, R.id.custom_palette_c2,
                R.id.custom_palette_c3, R.id.custom_palette_c4, R.id.custom_palette_c5,
                R.id.custom_palette_c6, R.id.custom_palette_c7, R.id.custom_palette_c8};

        final ImageView[] imageViews = {c0, c1, c2, c3, c4, c5, c6, c7, c8};

        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewID[i]);
            imageViews[i].setBackgroundColor(colors[i]);
            final int finalI = i;

            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog dialog = ColorPickerDialogBuilder
                            .with(context)
                            .initialColor(colors[finalI])
                            .setTitle("Choose Color")
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .density(9)
                            .showColorEdit(true)
                            .lightnessSliderOnly()
                            .setColorEditTextColor(0xff000000)
                            .showColorPreview(true)
                            .setOnColorSelectedListener(new OnColorSelectedListener() {
                                @Override
                                public void onColorSelected(int color) {
                                }
                            })
                            .setPositiveButton("ok", new ColorPickerClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int color, Integer[] allColors) {
                                    imageViews[finalI].setBackgroundColor(color);
                                    colors[finalI] = color;
                                    palette = new Palette(colors);
                                    trianglifyView.setPalette(palette);
                                    trianglifyView.smartUpdate();
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .build();

                    dialog.show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_custom_palette_picker, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.custom_palette_ok:
                Intent intent = new Intent();
                intent.putExtra("Custom Palette Color Array", colors);
                setResult(RESULT_OK, intent);
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
