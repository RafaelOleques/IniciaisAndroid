package example.com;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import example.com.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements SensorEventListener {

    private FragmentFirstBinding binding;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    EditText xValue, yValue, zValue;
    private float first_x, first_y, first_z;
    private boolean first_change = false, mudar_pagina = false;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        xValue = (EditText) getView().findViewById(R.id.xValue);
        yValue = (EditText) getView().findViewById(R.id.yValue);
        zValue = (EditText) getView().findViewById(R.id.zValue);


        sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            sensorManager.registerListener(FirstFragment.this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            xValue.setText("Accelerometer sensor not supported");
        }

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
                action.setMessage("Mudança de tela!");
                mudar_pagina = true;

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!mudar_pagina) {
            Sensor sensor = sensorEvent.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                final float alpha = (float) 0.8;
                float[] gravity = new float[3];
                float[] linear_acceleration = new float[3];
                // Isolate the force of gravity with the low-pass filter.
                gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

                // Remove the gravity contribution with the high-pass filter.
                linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
                linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
                linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

                if (!first_change) {
                    first_x = linear_acceleration[0];
                    first_y = linear_acceleration[1];
                    first_z = linear_acceleration[2];
                    first_change = true;

                    xValue.setText("x: " + linear_acceleration[0]);
                    yValue.setText("y: " + linear_acceleration[1]);
                    zValue.setText("z: " + linear_acceleration[2]);

                } else if (Math.abs(first_x - linear_acceleration[0]) > 2 ||
                        Math.abs(first_y - linear_acceleration[1]) > 2 ||
                        Math.abs(first_z - linear_acceleration[2]) > 2) {

                    mudar_pagina = true;
                    FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
                    action.setMessage("Mudança no acelerômetro!");

                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(action);
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}