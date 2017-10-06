package cs3100.cse.iitm.breakout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent i = getIntent();
        String score = i.getStringExtra("score");
        String status = i.getStringExtra("status");

        TextView scoreView = (TextView) findViewById(R.id.score);
        TextView statusView = (TextView) findViewById(R.id.status);
        TextView yScore = (TextView) findViewById(R.id.textView4);
        
        scoreView.setText(score);
        statusView.setText("You " + status + "!");
    }
}
