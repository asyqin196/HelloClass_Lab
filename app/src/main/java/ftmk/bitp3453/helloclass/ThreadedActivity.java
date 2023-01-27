package ftmk.bitp3453.helloclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ThreadedActivity extends AppCompatActivity {

    ImageView imgVwPic;
    TextView tvGreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threaded);

        imgVwPic = findViewById(R.id.imgVwPic);
        Intent intent = getIntent();

        String strName =intent.getStringExtra("varStrl");
        tvGreet = findViewById(R.id.tvGreet);
        tvGreet.setText("Welcome to second Activity..");
    }

    public void fnTakePic(View vw)
    {
        Runnable run =new Runnable() {
            @Override
            public void run() {
                // 1000 auto-generated method stub

                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //1000 auto-generated method stub
                        tvGreet.setText(tvGreet.getText().toString() + " This is your picture hehehe :p ..");
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imgVwPic.setImageBitmap(bp);
    }

    public void GoToNextActivity(View view){
        startActivity(new Intent(this,RegistrationActivity.class));
    }
}