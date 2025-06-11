package com.example.chaptertracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaptertracker.databinding.FragmentChapterBinding;

public class ChapterFragment extends Fragment {

    public ChapterFragment() {
        // Required empty public constructor
    }

    FragmentChapterBinding binding;
    ChapterViewModel chapterViewModel;
    BookViewModel bookViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChapterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int chapterId = ChapterFragmentArgs.fromBundle(getArguments()).getChapterId();

        chapterViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(ChapterViewModel.class);

        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        chapterViewModel.getChapterById(chapterId).observe(getViewLifecycleOwner(), chapter -> {
            binding.chapterTitleTextView.setText(chapter.getChapterName());

            bookViewModel.getBookById(chapter.getBookId()).observe(getViewLifecycleOwner(), book -> {
                binding.chapterBookTitleTextView.setText(book.getBookTitle());
            });
        });

        return view;
    }
}