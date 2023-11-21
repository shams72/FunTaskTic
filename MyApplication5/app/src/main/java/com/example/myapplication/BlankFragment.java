package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentBlankBinding;
import com.example.myapplication.databinding.FragmentFirstBinding;
import com.example.myapplication.databinding.FragmentSecondBinding;

/**
 * A simple {@link// Fragment} subclass.
 * Use the {@link //BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class BlankFragment extends Fragment {

    private FragmentBlankBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentBlankBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use the NavController to navigate to SecondFragment
                Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use the NavController to navigate to SecondFragment

                int count = 5;

                BlankFragmentDirections.ActionBlankFragmentToSecondFragment action = BlankFragmentDirections.actionBlankFragmentToSecondFragment(count);
                NavHostFragment.findNavController(BlankFragment.this).navigate(action);

            }
        });
    }
}
