package com.example.chaptertracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chaptertracker.databinding.BookListItemBinding;
import com.example.chaptertracker.databinding.ChapterListItemBinding;
import com.example.chaptertracker.databinding.FragmentBookBinding;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {


    FragmentBookBinding binding;
    BookViewModel bookViewModel;
    public ChapterViewModel chapterViewModel;
    private ChapterRecyclerViewAdapter adapter;

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

        int bookId = BookFragmentArgs.fromBundle(getArguments()).getBookId();

        bookViewModel.getBookById(bookId).observe(getViewLifecycleOwner(), book -> {
            binding.individualBookTitleTextView.setText(book.getBookTitle().trim());
            binding.idividualBookDescTextView.setText(book.getBookDescription().trim());

        });

        chapterViewModel.getAllChaptersForBook(bookId).observe(getViewLifecycleOwner(), chapters -> {
            adapter.setChapterList(chapters);
        });

        return view;
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
                chapterViewModel.updateChapterName(chapter);
            }
            mBinding.chapterTitleTextView.setText(chapter.getChapterName());
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