package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SQLStorageTest extends AbstractStorageTest {
    public SQLStorageTest() {
        super(new SQLStorage(Config.getInstance().getDbURL(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword()));
    }
}
