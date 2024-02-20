package com.example.promptshowingtext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGeneraterActivity extends AppCompatActivity {

    EditText editTextData;
    Button buttonGenerate;
    ImageView imageViewQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generater);

        editTextData = findViewById(R.id.editTextData);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        imageViewQRCode = findViewById(R.id.imageViewQRCode);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editTextData.getText().toString();
                if (!data.isEmpty()) {
                    try {
                        Bitmap qrBitmap = generateQRCode(data);
                        imageViewQRCode.setImageBitmap(qrBitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private Bitmap generateQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, 200, 200);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
            }
        }
        return bitmap;
    }

}