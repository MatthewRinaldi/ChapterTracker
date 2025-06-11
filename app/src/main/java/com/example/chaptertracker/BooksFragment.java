package com.example.chaptertracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.chaptertracker.databinding.BookListItemBinding;
import com.example.chaptertracker.databinding.FragmentBooksBinding;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    FragmentBooksBinding binding;
    private BookViewModel bookViewModel;
    private ChapterViewModel chapterViewModel;
    private BooksRecyclerViewAdapter adapter;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        chapterViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(ChapterViewModel.class);

        adapter = new BooksRecyclerViewAdapter();
        binding.booksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.booksRecyclerView.setAdapter(adapter);

        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            adapter.setBookList(books);
        });

        binding.booksFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateBookDialog();
            }
        });

        return view;
    }

    private void showCreateBookDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.create_book_dialog, null);

        EditText titleInput = dialogView.findViewById(R.id.editTextBookTitle);
        EditText descInput = dialogView.findViewById(R.id.editTextBookDesc);
        EditText chapterInput = dialogView.findViewById(R.id.editTextNumberOfChapter);

        new AlertDialog.Builder(getContext())
                .setTitle("Create New Book")
                .setView(dialogView)
                .setPositiveButton("Create", (dialog, which) -> {
                    String title = titleInput.getText().toString().trim();
                    String desc = descInput.getText().toString().trim();
                    int numberOfChapters = Integer.parseInt(chapterInput.getText().toString().trim());

                    Book newBook = new Book(title, desc, numberOfChapters);
                    bookViewModel.insertBook(newBook, bookId -> {
                        // Chapter object creation
                        for (int i = 1; i <= numberOfChapters; i++) {
                            Chapter chapter = new Chapter(
                                    bookId,
                                    null,
                                    "",
                                    false,
                                    0,
                                    0
                            );
                            chapterViewModel.insertChapter(chapter);
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.BooksViewHolder>{
    List<Book> bookList = new ArrayList<>();
    public BooksRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public BooksRecyclerViewAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListItemBinding mBinding = BookListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BooksRecyclerViewAdapter.BooksViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksRecyclerViewAdapter.BooksViewHolder holder, int position) {
        holder.bind(bookList.get(position));
        holder.position = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        BookListItemBinding mBinding;
        int position;
        public BooksViewHolder(BookListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Book book) {
            mBinding.bookTitleTextView.setText(book.getBookTitle());
            mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = bookList.get(position);
                    BooksFragmentDirections.BookFragmentAction action = BooksFragmentDirections.bookFragmentAction(book.getBookId());
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }

    }

    public void setBookList(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }
}