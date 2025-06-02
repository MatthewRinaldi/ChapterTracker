package com.example.chaptertracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chaptertracker.databinding.FragmentBookBinding;

public class BookFragment extends Fragment {


    FragmentBookBinding binding;
    BookViewModel bookViewModel;

    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        int bookId = BookFragmentArgs.fromBundle(getArguments()).getBookId();
        TextView textView = binding.temp;

        bookViewModel.getBookById(bookId).observe(getViewLifecycleOwner(), book -> {
            if (book != null)
                textView.setText(book.getBookTitle());
        });

        return view;
    }
}