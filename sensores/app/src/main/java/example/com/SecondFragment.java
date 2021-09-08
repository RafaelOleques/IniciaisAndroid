package example.com;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import example.com.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

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

        if(getArguments() != null){
            SecondFragmentArgs args = SecondFragmentArgs.fromBundle(getArguments());
            String message = args.getMessage();

            TextView textview_first = (TextView) getView().findViewById(R.id.textview_first);
            textview_first.setText(message);
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}