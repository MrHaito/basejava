package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest {
    private final Storage storage;
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_1 = "James Hetfield";
    private static final String FULLNAME_4 = "Robert Trujillo";
    private static final Resume RESUME_4 = new Resume(UUID_4, FULLNAME_4);

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test
    public void saveOverflow() throws NoSuchFieldException, IllegalAccessException {
        storage.clear();
        int storageLength  = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 0; i < storageLength; i++) {
                storage.save(new Resume(FULLNAME_1));
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени");
        }
        StorageException thrown = Assertions.assertThrows(StorageException.class, () -> {
            storage.save(RESUME_4);
        });
    }
}
