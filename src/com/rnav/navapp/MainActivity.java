
package com.rnav.navapp;

import java.sql.Timestamp;
import java.util.Date;

import android.hardware.Sensor;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;


public class MainActivity extends Activity implements SensorEventListener{

	Sensor accelerometer;
	Sensor compass;
	Sensor gyrometer;
	SensorManager SensorMan;
	TextView acceleration;
	TextView gyroscope;
	TextView compassDisp;
	
	double[] acc = new double[3];
	double a[][];
	Timestamp timestamp;
	int i =0;
	double sx=0,sy=0,sz=0;
	double vx=0,vy=0,vz=0;
	
	double avgx = 0,avgy = 0,avgz = 0;

	
	/* Called When the activity is first created */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SensorMan=(SensorManager)getSystemService(SENSOR_SERVICE);
		
		accelerometer = SensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		gyrometer = SensorMan.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		compass = SensorMan.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	
		SensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		SensorMan.registerListener(this, gyrometer, SensorManager.SENSOR_DELAY_NORMAL);
		SensorMan.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
		
		acceleration = (TextView)findViewById(R.id.acceleration);
		gyroscope = (TextView)findViewById(R.id.gyroscope);
		compassDisp = (TextView)findViewById(R.id.compass);
		
		
		timestamp= new Timestamp(new Date().getTime());
	
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	//
	}
	 
	

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		int sensorType = event.sensor.getType();
		
		
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			
			Timestamp newtimestamp = new Timestamp(new Date().getTime());
			double timediff = newtimestamp.getTime()-timestamp.getTime();
			
			// Print timediff
			TextView tvTimeDiff =(TextView) findViewById(R.id.timediff);
			timediff=timediff/(1000000);
			tvTimeDiff.setText("Timediff: "+timediff);
			
			
			for (int z=0; z<3; z++){
				acc[z] = event.values[z];
			}	
			
			acceleration.setText("ax: "+ acc[0] +"\nay: "+acc[1]+"\naz: "+acc[2] );
			
			timestamp=newtimestamp;
			
		}
		
		if (sensorType == Sensor.TYPE_GYROSCOPE){
			float gyro[] = new float[] {0, 0, 0} ;
	
			
			for(int z=0;z<3;z++){
				gyro[z] = event.values[z];
			}	
			
			gyroscope.setText("Gx: " + Float.toString(gyro[0]) +"\nGy: " + Float.toString(gyro[1]) + "\nGz: " + Float.toString(gyro[2]));
			
		}
		
		if (sensorType == Sensor.TYPE_MAGNETIC_FIELD){
			float compass_readings[] = new float[] {0, 0, 0};
			
			for (int z=0; z<3; z++){
				compass_readings[z] = event.values[z];				
			}
	
			compassDisp.setText("Cx: "+ compass_readings[0] + "\nCy: " + compass_readings[1] + "\nCz:" + compass_readings[2]);
			
			
		}

		
	}
}