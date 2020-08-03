package se.umu.oi17wln.workoutplanner.model.person;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import java.util.Collections;
import java.util.List;
import org.junit.Assert.*;
import se.umu.oi17wln.workoutplanner.model.Database;
import se.umu.oi17wln.workoutplanner.model.person.PersonDao;
import se.umu.oi17wln.workoutplanner.model.person.PersonEntity;

/**
 * Unit test class for validating the DAO operations
 * on the database for the Person-table.
 */
@RunWith(AndroidJUnit4.class)
public class PersonDaoTest {

    @Rule
    public TestRule synchronousExec = new InstantTaskExecutorRule();

    @Mock
    private Observer<List<PersonEntity>> listObserver;

    @Mock
    private Observer<PersonEntity> personObserver;

    private Database db;
    private PersonDao dao;


    @Before
    public void setUp() throws Exception {
        // init mockito for this test class
        MockitoAnnotations.initMocks(this);
        // get context and init a temp database in memory
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
        // given
        PersonEntity pe = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        dao.getAll().observeForever(listObserver);
        // when
        dao.insert(pe);
        // then
        verify(listObserver).onChanged(Collections.singletonList(pe));
    }


    /*
    @Test
    public void getLatestEntryReturnsLatestEntry() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        PersonEntity p2 = new PersonEntity(160, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        dao.getLatestEntry().observeForever(personObserver);
        //when
        dao.insert(p2);
        //then
        assertEquals();

        //PersonEntity subject = dao.getLatestEntry();
        //assertEquals(subject, p2);
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
        //List<PersonEntity> list = dao.getAll();
        //assertEquals(0, list.size());
    }


    @Test
    public void getAllFetchesAllEntries() {
        PersonEntity p1 = new PersonEntity(180, 80, "Test", "Test2", "Test3");
        PersonEntity p2 = new PersonEntity(160, 80, "Test", "Test2", "Test3");
        dao.insert(p1);
        dao.insert(p2);
        //List<PersonEntity> list = dao.getAll();
        //assertEquals(2, list.size());
    }*/
}