package com.sdsmdg.kd.trianglifyexample;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.views.TrianglifyView;

import java.io.IOException;
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
    private final int PERMISSION_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        trianglifyView = findViewById(R.id.trianglify_main_view);
        trianglifyView.setBitmapQuality(TrianglifyView.DRAWING_CACHE_QUALITY_HIGH);

        customPalette = trianglifyView.getPalette();

        varianceSeekBar = findViewById(R.id.variance_seekbar);
        varianceSeekBar.setMax(100);
        varianceSeekBar.setProgress(trianglifyView.getVariance());
        varianceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setVariance(progress + 1);
                trianglifyView.smartUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cellSizeSeekBar = findViewById(R.id.cell_size_seekbar);
        int maxCellSize = 150;

        cellSizeSeekBar.setMax(maxCellSize);
        cellSizeSeekBar.setProgress(trianglifyView.getCellSize() - 100);
        cellSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trianglifyView.setCellSize(progress + 100);
                trianglifyView.smartUpdate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paletteSeekBar = findViewById(R.id.palette_seekbar);
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

        strokeCheckBox = findViewById(R.id.draw_stroke_checkbox);
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

        fillCheckBox = findViewById(R.id.draw_fill_checkbox);
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

        randomColoringCheckbox = findViewById(R.id.random_coloring_checkbox);
        randomColoringCheckbox.setChecked(trianglifyView.isRandomColoringEnabled());
        randomColoringCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                trianglifyView.setRandomColoring(isChecked);
                trianglifyView.smartUpdate();
            }
        });

        customPaletteCheckbox = findViewById(R.id.custom_palette_checkbox);
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

        if (!trianglifyView.isFillTriangle() && !trianglifyView.isDrawStrokeEnabled()) {
            trianglifyView.setDrawStrokeEnabled(true);
            trianglifyView.setFillTriangle(true);
        }

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

    // Click handlers for action bar menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                exportImage();
                break;
            case R.id.action_about:
                Intent aboutActivityIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutActivityIntent);
                break;
            case R.id.action_refresh:
                randomizeTrianglifyParameters(trianglifyView);
                trianglifyView.generateAndInvalidate();
                break;
            case R.id.custom_palette_picker:
                Intent customPalettePickerIntent = new Intent(this, CustomPalettePickerActivity.class);
                customPalettePickerIntent.putExtra(getResources().getString(R.string.palette_color_array),
                        trianglifyView.getPalette().getColors());
                startActivityForResult(customPalettePickerIntent, 1);
                customPaletteCheckbox.setChecked(true);
                break;
            case R.id.action_set_wall:
                setWallpaper(MainActivity.this.trianglifyView);
                break;
            default:
                break;
        }

        return true;
    }

    public void showColoringError() {
        Toast.makeText(this, "View should at least be set to draw strokes or fill triangles or both.",
                Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            customPalette = new Palette(data.getIntArrayExtra(CustomPalettePickerActivity.CUSTOM_PALETTE_COLOR_ARRAY));
            if (customPaletteCheckbox.isChecked()) {
                trianglifyView.setPalette(customPalette);
                trianglifyView.smartUpdate();
            }
        }
    }

    private void exportImage() {
        // Checks if permission is required for android version > 6
        boolean permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;

        if (permissionStatus) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_CODE);
        } else {
            Bitmap bitmap = trianglifyView.getBitmap();
            if (bitmap != null) {
                addImageToGallery(bitmap, this);
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to generate image, please try again",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void addImageToGallery(Bitmap bitmap, Context context) {
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
    }

    // Sets bitmap from trianglify view as wallpaper of device
    public void setWallpaper(final TrianglifyView view) {
        AlertDialog.Builder alertDgBuilder = new AlertDialog.Builder(this);
        alertDgBuilder.setMessage(getString(R.string.wall_alert_dg_text));
        alertDgBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WallpaperManager trianglifyWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    trianglifyWallpaperManager.setBitmap(view.getBitmap());
                    Toast.makeText(MainActivity.this, "Wallpaper set successfuly",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Something went wrong, please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDgBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform inbuilt functions
            }
        });

        alertDgBuilder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportImage();
                } else {
                    Toast.makeText(this, "Storage access failed, check permission",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
