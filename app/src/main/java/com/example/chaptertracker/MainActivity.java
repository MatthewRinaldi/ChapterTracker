package com.example.chaptertracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.chaptertracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /* TODO:
    * Develop a main fragment where a list of user generated books are present
    * Each book should detail a progress bar signifying it's completion
    * Use a FAB to prompt the user for a book's creation, prompting the name of the book, number of chapters, and a brief description of the book
    * When a book is interacted with on the main menu, navigate to a separate fragment for the chapter view
    * This fragment should have the title of the book with the progress bar and brief description of it beneath it, and finally beneath that should be a RecyclerView with the chapters listed
    * The chapters should display the name of the chapter (by default the chapter number unless otherwise modified by the user), whether it is completed yet (the will be signified by background color change, by default gray, when finished dark green), and if completed, a timestamp of when it was completed
    * Interacting with a chapter in this list should pull up a menu to edit such details, if the chapter was already modified before, the details of the previous edit should autofill the text entries for each component
    * * Consider using Room for local storage to persist books and chapters
        - Each Book entry has: title, total chapters, description, progress
        - Each Chapter entry has: book_id (foreign key), name, completed, timestamp, notes (optional)
    * When a book is deleted, all related chapters should be deleted as well (onCascade in Room)
    * Optionally allow the user to reorder chapters manually (drag-and-drop support in RecyclerView)
    * Long press on a chapter could offer a quick "delete" or "edit" menu
    * Provide optional fields in the chapter dialog for: chapter notes, or a 1–5 "difficulty"/"mood" tag
    * Add a daily reading goal system (e.g., 1 chapter per day tracker)
        - Could appear as a subtle banner in the main menu or chapter screen
    * In main menu, show estimated completion date for each book based on average chapter completion speed
    * Optional: Theme toggles (dark/light/pastel vibe?) or color options for completed chapters
    * */
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}