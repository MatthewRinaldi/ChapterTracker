package com.example.chaptertracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.chaptertracker.databinding.ChapterListItemBinding;
import com.example.chaptertracker.databinding.FragmentBookBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookFragment extends Fragment {


    private FragmentBookBinding binding;
    private BookViewModel bookViewModel;
    private ChapterViewModel chapterViewModel;
    private ChapterRecyclerViewAdapter adapter;
    private String bookTitle;
    private String bookDesc;
    private int bookChapters;
    private int bookId;

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

        chapterViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(ChapterViewModel.class);

        adapter = new ChapterRecyclerViewAdapter(chapterViewModel);
        binding.chaptersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.chaptersRecyclerView.setAdapter(adapter);

        bookId = BookFragmentArgs.fromBundle(getArguments()).getBookId();

        bookViewModel.getBookById(bookId).observe(getViewLifecycleOwner(), book -> {
            binding.individualBookTitleTextView.setText(book.getBookTitle().trim());
            binding.idividualBookDescTextView.setText(book.getBookDescription().trim());

            this.bookTitle = book.getBookTitle().trim();
            this.bookDesc = book.getBookDescription().trim();
            this.bookChapters = book.getChapterCount();
        });

        chapterViewModel.getAllChaptersForBook(bookId).observe(getViewLifecycleOwner(), chapters -> {
            adapter.setChapterList(chapters);
        });

        binding.editBookFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditBookDialog();
            }
        });

        return view;
    }

    private void showEditBookDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View dialogView = inflater.inflate(R.layout.create_book_dialog, null);
        alert.setView(dialogView);

        final EditText bookTitle = dialogView.findViewById(R.id.editTextBookTitle);
        final EditText bookDesc = dialogView.findViewById(R.id.editTextBookDesc);
        final EditText bookChapters = dialogView.findViewById(R.id.editTextNumberOfChapter);

        bookTitle.setText(this.bookTitle);
        bookDesc.setText(this.bookDesc);
        bookChapters.setText(String.valueOf(this.bookChapters));
        
        BookViewModel bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        ChapterViewModel chapterViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(ChapterViewModel.class);

        int bookId = this.bookId;
        alert.setTitle("Edit / Delete Book")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookViewModel.getSyncBookById(bookId, book -> {
                            int chapterCount = Integer.parseInt(bookChapters.getText().toString().trim());
                            if (book.getChapterCount() > chapterCount) {
                                final int delta = book.getChapterCount() - chapterCount;
                                chapterViewModel.getSyncChapterForBook(bookId, chapters -> {
                                    int x = chapters.size() - delta;
                                    for (int i = 0; i < delta; i++) {
                                        chapterViewModel.deleteChapter(chapters.get(x + i));
                                    }
                                });
                            } else if (book.getChapterCount() < chapterCount) {
                                final int delta = chapterCount - book.getChapterCount();
                                for (int i = 1; i <= delta; i++) {
                                    Chapter chapter;
                                    chapter = new Chapter(
                                            bookId,
                                            null,
                                            "",
                                            false,
                                            0,
                                            0
                                    );
                                    chapter.setChapterIndex(book.getChapterCount() + i);
                                    chapterViewModel.insertChapter(chapter);
                                }
                            }

                            book.setBookTitle(bookTitle.getText().toString().trim());
                            book.setBookDescription(bookDesc.getText().toString().trim());
                            book.setChapterCount(Integer.parseInt(bookChapters.getText().toString().trim()));

                            bookViewModel.updateBook(book);
                            adapter.notifyDataSetChanged();
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookViewModel.getSyncBookById(bookId, bookViewModel::deleteBook);

                        if (getView() != null) {
                            Navigation.findNavController(getView()).navigate(BookFragmentDirections.bookDeleteAction());
                        } else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error")
                                    .setMessage("Failed to redirect to Books fragment")
                                    .setPositiveButton("Continue", null)
                                    .show();
                        }
                    }
                })
                .show();
    }
}

class ChapterRecyclerViewAdapter extends RecyclerView.Adapter<ChapterRecyclerViewAdapter.ChapterViewHolder>{
    List<Chapter> chapterList = new ArrayList<>();
    final ChapterViewModel chapterViewModel;
    public ChapterRecyclerViewAdapter(ChapterViewModel chapterViewModel) {
        this.chapterViewModel = chapterViewModel;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChapterListItemBinding mBinding = ChapterListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChapterRecyclerViewAdapter.ChapterViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.bind(chapterList.get(position));
        holder.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {

        ChapterListItemBinding mBinding;
        int position;
        public ChapterViewHolder(ChapterListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        public void bind(Chapter chapter) {
            if (chapter.getChapterName() == null) {
                int position = getAdapterPosition();
                chapter.setChapterName("Chapter " + (position + 1));
                chapter.setChapterIndex(position + 1);
                chapterViewModel.updateChapter(chapter);
            }
            mBinding.chapterTitleTextView.setText(chapter.getChapterName());
            mBinding.chapterCompletionTextView.setText("");

            if (chapter.getChapterRead()) {
                mBinding.chapterListItemLayout.setBackgroundColor(0xFF90EE90);
                mBinding.chapterCompletionTextView.setText(SimpleDateFormat.getDateTimeInstance().format(new Date(chapter.getTimestamp())));
            }

            mBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    chapter.setChapterRead(true);
                    chapter.setTimestamp(new Date().getTime());

                    chapterViewModel.updateChapter(chapter);
                    return true;
                }

                @Override
                public boolean onLongClickUseDefaultHapticFeedback(@NonNull View v) {
                    return true;
                }
            });

            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookFragmentDirections.ChapterFragmentAction action = BookFragmentDirections.chapterFragmentAction(chapter.getChapterId());
                    Navigation.findNavController(v).navigate(action);

                }
            });
        }

    }

    public void setChapterList(List<Chapter> chapters) {
        this.chapterList = chapters;
        notifyDataSetChanged();
    }
}