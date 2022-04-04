package uz.sanjar.dictionarysql.core.app;

import android.app.Application;

import uz.sanjar.dictionarysql.core.database.Database;
import uz.sanjar.dictionarysql.core.database.MemoryHelper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Database.init(this);
        MemoryHelper.init(this);
    }
}
