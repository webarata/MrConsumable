package link.arata.dro.mrconsumable.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import link.arata.common.enums.LineBreakType;
import link.arata.common.enums.TrimType;
import link.arata.common.helper.ValidationHelper;
import link.arata.dro.common.validator.RequiredValidator;
import link.arata.dro.common.validator.ValidatorUtil;
import link.arata.dro.mrconsumable.R;
import link.arata.dro.mrconsumable.entity.Consumable;
import link.arata.dro.mrconsumable.entity.ConsumableBuilder;
import link.arata.dro.mrconsumable.model.ConsumableModel;
import link.arata.dro.mrconsumable.model.ModelEvent;
import link.arata.dro.mrconsumable.model.Observer;
import link.arata.dro.mrconsumable.util.ImageUtil;

public class NewActivity extends AppCompatActivity implements Observer {
    private AppCompatEditText nameEditText;
    private AppCompatEditText furiganaEditText;
    private AppCompatEditText noteEditText;
    private AppCompatButton registerButton;

    private Uri pictureUri;
    private String imagePath;
    private static final int REQUEST_CODE_TAKE_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CHOOSER = 1;

    private ConsumableModel consumableModel;

    @Override
    public void notify(ModelEvent modelEvent) {
        switch (modelEvent) {
            case FINISH_REGISTER_CONSUMABLE:
                // 登録できたらフォームの情報を消す
                nameEditText.setText("");
                furiganaEditText.setText("");
                noteEditText.setText("");

                finish();

                Toast.makeText(this, "消耗品を登録しました", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        consumableModel = ConsumableModel.getInstance();
        consumableModel.addObserver(this);

        nameEditText = (AppCompatEditText) findViewById(R.id.nameEditText);
        furiganaEditText = (AppCompatEditText) findViewById(R.id.furiganaEditText);
        noteEditText = (AppCompatEditText) findViewById(R.id.noteEditText);
        registerButton = (AppCompatButton) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String furigana = furiganaEditText.getText().toString();
                String note = noteEditText.getText().toString();

                ValidationHelper validationHelper = ValidationHelper.getInstance(TrimType.NONE, LineBreakType.LF);
                boolean isValid = ValidatorUtil.validateEditText(v.getContext(), validationHelper, nameEditText,
                    new RequiredValidator());
                isValid = isValid & ValidatorUtil.validateEditText(v.getContext(), validationHelper, furiganaEditText,
                    new RequiredValidator());

                if (isValid) {
                    Consumable consumable = new ConsumableBuilder()
                        .setConsumableName(name)
                        .setConsumableFurigana(furigana)
                        .setConsumableNote(note)
                        .createInstance();

                    consumableModel.registerConsumable(v.getContext(), consumable);
                }
            }
        });

        findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {

                String[] items = {"写真を撮影", "写真を選択"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setItems(
                    items,
                    new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    // ファイルとして画像を保存する
                                    // android.permission.WRITE_EXTERNAL_STORAGEが必要
                                    String filename = System.currentTimeMillis() + ".jpg";
                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE, filename);
                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                    pictureUri = getContentResolver()
                                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                                    Intent intent0 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent0.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                                    startActivityForResult(intent0, REQUEST_CODE_TAKE_IMAGE);
                                    break;
                                case 1:
                                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent1.setType("image/*");
                                    intent1.addCategory(Intent.CATEGORY_OPENABLE);
                                    startActivityForResult(intent1, REQUEST_CODE_IMAGE_CHOOSER);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

                // 表示
                dialog.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            if (pictureUri != null) {
                getContentResolver().delete(pictureUri, null, null);
                pictureUri = null;
            }
            return;
        }

        Uri uri = null;
        if (requestCode == REQUEST_CODE_TAKE_IMAGE) {
            uri = pictureUri;
        } else if (requestCode == REQUEST_CODE_IMAGE_CHOOSER) {
            uri = data.getData();
        }

        // 画像を取得
        ImageView iv = (ImageView) findViewById(R.id.consumableImageView);
        iv.setImageURI(uri);

        // test
        imagePath = ImageUtil.uriToPath(this, uri, requestCode == REQUEST_CODE_IMAGE_CHOOSER);

        pictureUri = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consumableModel.removeObserver(this);
    }
}
