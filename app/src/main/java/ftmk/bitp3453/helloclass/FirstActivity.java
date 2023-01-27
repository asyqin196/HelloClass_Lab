package ftmk.bitp3453.helloclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.BreakIterator;

public class FirstActivity extends AppCompatActivity {

    TextView txtvwAge;
    EditText edtName,edtYear;
    Button btnClick;
    ImageView imgVwPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        txtvwAge = (TextView)  findViewById(R.id.txtvwAge);
        edtName = (EditText) findViewById(R.id.edtTxtName);
        edtYear = (EditText) findViewById(R.id.edtYear);
    }

    public void fnThreadActivity(View vw)
    {
        Intent intent = new Intent(this,ThreadedActivity.class);
        String strMsg =((EditText) findViewById(R.id.edtTxtName)).getText().toString();
        intent.putExtra("varStr1",strMsg);
        startActivity(intent);

    }
    public void fnGreet(View vw)
    {
        String strName = edtName.getText().toString();
        txtvwAge.setText("Helloooo and Welcome " + strName);

    }

    public void GoToThreadActivity(View view) {
        startActivity(new Intent(this,ThreadedActivity.class));
    }
}