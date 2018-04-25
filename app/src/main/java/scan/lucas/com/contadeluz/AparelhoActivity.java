package scan.lucas.com.contadeluz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scan.lucas.com.contadeluz.Adapters.AllAparelhosAdapter;
import scan.lucas.com.contadeluz.DTO.ItemPerfil;
import scan.lucas.com.contadeluz.DTO.Recurso;
import scan.lucas.com.contadeluz.DTO.Usuario;
import scan.lucas.com.contadeluz.Helpers.PreferenceHelper;
import scan.lucas.com.contadeluz.REST.ApiClient;
import scan.lucas.com.contadeluz.REST.Controller;

public class AparelhoActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private ImageView headerFoto;
    private EditText txtNome;
    private EditText txtDescricao;
    private EditText txtPotencia;
    private Spinner ddlVoltagem;
    private Button btnSalvar;
    private ProgressDialog mDialog;
    private Usuario mUser = new Usuario();
    private int recursoId = 0;
    ApiClient controllerApi;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    String selectedImagePath;
    AlertDialog.Builder myAlertDialog;
    Gson gson;
    Integer posicao;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        FloatingActionButton importFoto = (FloatingActionButton) findViewById(R.id.importFoto);
        headerFoto = (ImageView) findViewById(R.id.main_backdrop);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtDescricao = (EditText) findViewById(R.id.txtdesc);
        txtPotencia = (EditText) findViewById(R.id.txtPotencia);
        ddlVoltagem = (Spinner) findViewById(R.id.ddlVoltagem);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        List<String> list = new ArrayList<String>();
        list.add("110v");
        list.add("220v");
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlVoltagem.setAdapter(adp1);

        gson = new Gson();
        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(this);
        String json = preferences.getString("USUARIO", "");
        mUser = gson.fromJson(json, Usuario.class);

        Intent intent = getIntent();
        recursoId = intent.getIntExtra("recursoId", -1);
        posicao = intent.getIntExtra("posicao", 0);
        if (recursoId > 0)
            obterDadosAparelho(recursoId);
        importFoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startDialog();
                return true;
            }
        });

        btnSalvar.setOnClickListener(new SalvarDados());
    }

    private void startDialog() {
        myAlertDialog = new AlertDialog.Builder(AparelhoActivity.this);
        myAlertDialog.setTitle("Opções para o upload");
        myAlertDialog.setMessage("Escolha uma opção pra mandar a foto");
        myAlertDialog.setPositiveButton("Galeria",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        SampleClass sample = new SampleClass(mContext);
                        sample.openGalery();


                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        SampleClass sample = new SampleClass(mContext);
                        sample.openCamera();


                    }
                });
        myAlertDialog.show();
    }

    private void obterDadosAparelho(final int recursoId) {
        showProgress(true);
        ApiClient controllerApi = Controller.createService(ApiClient.class);
        //vai na API com esse id e traz o cliente

        retrofit2.Call<Recurso> request = controllerApi.GetRecurso(recursoId);
        request.enqueue(new Callback<Recurso>() {
            @Override
            public void onResponse(Call<Recurso> call, Response<Recurso> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    Recurso recurso = (Recurso) response.body();
                    txtNome.setText(recurso.getNome());
                    txtDescricao.setText(recurso.getDescricao());
                    txtPotencia.setText(String.valueOf(recurso.getPotencia()));
                    if (recurso.getVoltagem().contains("110"))
                        ddlVoltagem.setSelection(0);
                    else
                        ddlVoltagem.setSelection(1);

                    headerFoto.setImageBitmap(recurso.retornaFotoBmp());
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<Recurso> call, Throwable t) {
                showProgress(false);
            }
        });
    }

    private void showProgress(Boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show)
            mDialog = ProgressDialog.show(AparelhoActivity.this, "", "Carregando. Por favor Aguarde...", true);
        else {
            if (mDialog != null)
                mDialog.dismiss();
        }

    }

    private class SalvarDados implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            showProgress(true);
            controllerApi = Controller.createService(ApiClient.class);
            //vai na API com esse id e traz o cliente
            final Recurso rec = new Recurso();
            rec.setId(recursoId);
            rec.setDescricao(txtDescricao.getText().toString());
            rec.setNome(txtNome.getText().toString());
            String pot = txtPotencia.getText().toString();
            rec.setPotencia(Integer.parseInt(pot));
            rec.setVoltagem(ddlVoltagem.getSelectedItem().toString());
            rec.setUsuarioId(mUser.getId());
            rec.setItemPerfils(new ArrayList<ItemPerfil>());
            rec.setUsuario(null);
            headerFoto.buildDrawingCache();
            Bitmap bmap = headerFoto.getDrawingCache();
            if (bmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 40, stream);
                byte[] image = stream.toByteArray();
                rec.setFoto(Base64.encodeToString(image, 0));
            }

            retrofit2.Call<Integer> request;
            if (recursoId > 0) {
                request = controllerApi.PutRecurso(rec.getId(), rec);
            } else {
                request = controllerApi.PostRecurso(rec);
            }
            request.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {


                    if (response != null && response.isSuccessful()) {
                        rec.setId(response.body());

                        Intent intent = new Intent();

                        SharedPreferences preferences = PreferenceHelper.INSTANCE.defaultPrefs(AparelhoActivity.this);
                        preferences.edit().putString("aparelho", gson.toJson(rec, Recurso.class));
                        intent.putExtra("posicao", posicao);
                        setResult(RESULT_OK, intent);
                        showProgress(false);
                        finish();
                    } else
                        showProgress(false);

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    showProgress(false);
                    Toast.makeText(AparelhoActivity.this, "Erro ao salvar dados, tente novamnente", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            if (!f.exists()) {
                Toast.makeText(getBaseContext(), "Erro durante a captura da imagem", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);


                headerFoto.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();


                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);


                headerFoto.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class SampleClass extends Activity {

        private Context context;

        public SampleClass(Context context) {
            this.context = context;
        }

        public void openGalery() {
            Intent pictureActionIntent = null;

            pictureActionIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity) mContext).startActivityForResult(
                    pictureActionIntent,
                    GALLERY_PICTURE);

        }

        public void openCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

            Uri photoURI = FileProvider.getUriForFile(AparelhoActivity.this, AparelhoActivity.this.getPackageName() + ".my.package.name.provider", f);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            ((Activity) context).startActivityForResult(intent, CAMERA_REQUEST);
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            bitmap = null;
            selectedImagePath = null;

            if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                if (!f.exists()) {
                    Toast.makeText(getBaseContext(), "Erro durante a captura da imagem", Toast.LENGTH_LONG).show();
                    return;
                }

                try {

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                    bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                    int rotate = 0;
                    try {
                        ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                        int orientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL);

                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotate = 270;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotate = 180;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotate = 90;
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);


                    headerFoto.setImageBitmap(bitmap);
                    //storeImageTosdCard(bitmap);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
                if (data != null) {

                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath,
                            null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    selectedImagePath = c.getString(columnIndex);
                    c.close();


                    bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                    // preview image
                    bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);


                    headerFoto.setImageBitmap(bitmap);

                } else {
                    Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}