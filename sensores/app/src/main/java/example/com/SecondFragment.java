package example.com;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import example.com.databinding.FragmentSecondBinding;
import example.com.GPSTracker;

public class SecondFragment extends Fragment implements SensorEventListener {

    private FragmentSecondBinding binding;

    private SensorManager sensorManager;
    private Sensor luminosidade, passos;
    TextView luminosidadeTV;
    TextView passosTV;
    private int steps=0;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);

        //Luminosidade

        luminosidadeTV = (TextView) getView().findViewById(R.id.luminosidadeTV);
        luminosidade = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (luminosidade != null) {
            sensorManager.registerListener(SecondFragment.this, luminosidade,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            luminosidadeTV.setText("Light sensor not supported");
        }

        //Passos

        passosTV = (TextView) getView().findViewById(R.id.passosTV);
        passos = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (passos != null) {
            sensorManager.registerListener(SecondFragment.this, passos,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            passosTV.setText("Step sensor not supported");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType() == Sensor.TYPE_LIGHT)
        {
            luminosidadeTV.setText("Light Intensity: " + sensorEvent.values[0]);
        }

        if(sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
        {
            steps++;
            passosTV.setText("Steps: " + steps);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}