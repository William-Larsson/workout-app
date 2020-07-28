package se.umu.oi17wln.workoutplanner.model;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Unit test class for validating the DAO operations
 * on the database for the Person-table.
 */
@RunWith(AndroidJUnit4.class)
public class PersonDaoTest {

    private Database db;
    private PersonDao dao;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        dao = db.getPersonDao();
    }


    @After
    public void tearDown() throws Exception {
        db.close();
    }


    @Test
    public void insertDoesInsertGivenValue() {
        PersonEntity pe = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        dao.insert(pe);
        List<PersonEntity> list = dao.getAll();
        assertEquals(list.get(0), pe);
    }


    @Test
    public void deleteRemovesGivenEntry() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        p1 = dao.getLatestEntry(); // needed because p1 does not have an id before insertion.
        dao.delete(p1);
        List<PersonEntity> list = dao.getAll();
        assertEquals(0, list.size());
    }


    @Test
    public void getLatestEntryReturnsLatestEntry() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        PersonEntity p2 = new PersonEntity(160, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        dao.insert(p2);
        PersonEntity subject = dao.getLatestEntry();
        assertEquals(subject, p2);
    }


    @Test
    public void update() {
        // will .update() even be used?
    }


    @Test
    public void deleteAllRemovesAllEntries() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        PersonEntity p2 = new PersonEntity(160, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        dao.insert(p2);
        dao.deleteAll();
        List<PersonEntity> list = dao.getAll();
        assertEquals(0, list.size());
    }


    @Test
    public void getAllFetchesAllEntries() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        PersonEntity p2 = new PersonEntity(160, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        dao.insert(p2);
        List<PersonEntity> list = dao.getAll();
        assertEquals(2, list.size());
    }
}