package cs3100.cse.iitm.breakout;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomSurfaceView view = (CustomSurfaceView) findViewById(R.id.customSurfaceView);
//        view.setZOrderOnTop(true);

        view.startThread();
    }
}
