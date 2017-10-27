package com.sdsmdg.kd.trianglifyexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "CustomPalleteActivity";

    private Palette palette;
    private TrianglifyView trianglifyView;
    private Context context;
    private ImageView[] imageViews = new ImageView[9];
    private int[] colors = {Color.BLACK, Color.BLUE, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.RED, Color.MAGENTA, Color.LTGRAY};
    public static final String CUSTOM_PALETTE_COLOR_ARRAY = "Custom Palette Color Array";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_custom_palette_picker);

        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (java.lang.NullPointerException e) {
            Log.e(TAG, "Null pointer exception in generating back action button");
        }

        try {
            actionBar.setTitle("Custom Palette Picker");
        } catch (java.lang.NullPointerException e) {
            Log.e(TAG, "Null pointer exception on setting About activity title");
        }

        colors = getIntent().getIntArrayExtra(MainActivity.PALETTE_COLOR_ARRAY);

        context = this;

        trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_custom_palette_view);
        trianglifyView.setPalette(new Palette(colors));
        trianglifyView.smartUpdate();

        for (int i = 0; i < imageViews.length; i++) {
            String imageViewNumber = "custom_palette_c" + String.valueOf(i);
            int resID = getResources().getIdentifier(imageViewNumber, "id", getPackageName());
            imageViews[i] = (ImageView) findViewById(resID);
            imageViews[i].setBackgroundColor(colors[i] + 0xff000000);

            final int finalI = i;

            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog dialog = ColorPickerDialogBuilder
                            .with(context)
                            .initialColor(colors[finalI] + 0xff000000)
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
                                    colors[finalI] = color - 0xff000000;
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
                intent.putExtra(CUSTOM_PALETTE_COLOR_ARRAY, colors);
                setResult(RESULT_OK, intent);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
