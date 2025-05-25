package com.example.chaptertracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chaptertracker.databinding.BookListItemBinding;
import com.example.chaptertracker.databinding.FragmentBooksBinding;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    private FragmentBooksBinding binding;
    private BookViewModel bookViewModel;
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
        // Inflate the layout for this fragment
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bookViewModel = new ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(BookViewModel.class);

        adapter = new BooksRecyclerViewAdapter();
        binding.booksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.booksRecyclerView.setAdapter(adapter);

        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), books -> {
            adapter.setBookList(books);
        });

        return view;
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
        }

    }

    public void setBookList(List<Book> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }
}