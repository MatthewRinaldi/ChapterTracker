package com.example.chaptertracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.chaptertracker.databinding.FragmentChapterBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChapterFragment extends Fragment {

    public ChapterFragment() {
        // Required empty public constructor
    }

    FragmentChapterBinding binding;
    ChapterViewModel chapterViewModel;
    BookViewModel bookViewModel;
    NoteViewModel noteViewModel;

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

        noteViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(NoteViewModel.class);

        chapterViewModel.getChapterById(chapterId).observe(getViewLifecycleOwner(), chapter -> {
            binding.chapterTitleTextView.setText(chapter.getChapterName());
            binding.hasBeenRead.setChecked(chapter.getChapterRead());

            if (chapter.getTimestamp() != 0) {
                binding.chapterCompletionDateTextView.setText(SimpleDateFormat.getDateTimeInstance().format(new Date(chapter.getTimestamp())));
                binding.chapterHeaderLayout.setBackgroundColor(0xFF90EE90);
                binding.hasBeenRead.setText("Completed");
            } else binding.chapterHeaderLayout.setBackgroundColor(Color.LTGRAY);

            binding.hasBeenRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        chapter.setTimestamp(new Date().getTime());
                        chapter.setChapterRead(true);
                        binding.chapterCompletionDateTextView.setText(SimpleDateFormat.getDateTimeInstance().format(new Date(chapter.getTimestamp())));
                        binding.chapterHeaderLayout.setBackgroundColor(0xFF90EE90);
                        binding.hasBeenRead.setText("Completed");
                    } else {
                        chapter.setTimestamp(0);
                        chapter.setChapterRead(false);
                        binding.chapterCompletionDateTextView.setText("Has Not Been Completed");
                        binding.chapterHeaderLayout.setBackgroundColor(Color.LTGRAY);
                        binding.hasBeenRead.setText("Incomplete");
                    }
                    chapterViewModel.updateChapter(chapter);
                }
            });

            binding.buttonCreateNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    View dialogView = inflater.inflate(R.layout.chapter_note_dialog, null);
                    alert.setView(dialogView);

                    EditText note = dialogView.findViewById(R.id.chapterNoteEditText);

                    alert.setTitle("Create Chapter Note")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    noteViewModel.insertNote(new Note(chapterId, note.getText().toString().trim()));
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });

            bookViewModel.getBookById(chapter.getBookId()).observe(getViewLifecycleOwner(), book -> {
                binding.chapterBookTitleTextView.setText(book.getBookTitle());
            });
        });

        noteViewModel.getNotesFromChapter(chapterId).observe(getViewLifecycleOwner(), notes -> {
            ArrayList<String> stringNotes = new ArrayList<>();
            for (Note note : notes) {
                stringNotes.add(note.getNote());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, stringNotes);
            binding.chapterNotesList.setAdapter(adapter);
        });
        return view;
    }
}