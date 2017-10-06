package cs3100.cse.iitm.breakout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    CustomSurfaceView view;
    float standardGravity, thresholdGravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (CustomSurfaceView) findViewById(R.id.customSurfaceView);

        standardGravity = SensorManager.STANDARD_GRAVITY;
        thresholdGravity = standardGravity / 2;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        
        view.startThread();

//        try {
//            view.stopThread();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Intent i = new Intent(MainActivity.this, FinishActivity.class);
//            i.putExtra("status", (view.win == 0) ? "lose" : "win");
//            i.putExtra("score", String.valueOf(view.mechanics.Score));
//            startActivity(i);
//        }
    }

    @Override
     protected void onResume() {
         sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
         super.onResume();
    }
     
    @Override
     protected void onPause() {
         sensorManager.unregisterListener(this, sensor);
         super.onPause();
     }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float z = event.values[0];

        if(z<-0.5){
            view.paddleMove = (int)(Math.sqrt((view.width/50)*(view.height/100)));
        }
        else if(z>0.5){
            view.paddleMove = -(int)(Math.sqrt((view.width/50)*(view.height/100)));
        }
        else{
            view.paddleMove = 0;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
