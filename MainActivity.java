package com.example.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int RQS_VOICE_RECOGNITION = 1;
    TextView textResult;
    String setting = "設定";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSpeech = (Button) findViewById(R.id.button);
        textResult = (TextView) findViewById(R.id.text);
        buttonSpeech.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.TAIWAN.toString());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speech");
                startActivityForResult(intent, RQS_VOICE_RECOGNITION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        speechRecognitionAction(requestCode, resultCode, data);
    }

    public void speechRecognitionAction(int requestCode, int resultCode,
                                        Intent data) {

        if (requestCode == RQS_VOICE_RECOGNITION) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String tempStr = "";
                for (String str : result) {
                    tempStr += str + "\n";
                }

                for (int i = 0; i < result.size(); i++) {
                    if (setting.equals(result.get(i))) {
                        Intent intent = new Intent(
                                "com.android.settings.TTS_SETTINGS");
                        startActivity(intent);
                    }
                }
                textResult.setText(tempStr);
            }
        }
    }
}
