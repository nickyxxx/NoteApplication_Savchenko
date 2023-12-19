import com.example.noteapplication_savchenko.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {

    }

    @Test
    public void testDeleteNoteAndRefresh() {


    }

    @Test
    public void testLoadNotesFromSharedPreferences() {


    }

}
