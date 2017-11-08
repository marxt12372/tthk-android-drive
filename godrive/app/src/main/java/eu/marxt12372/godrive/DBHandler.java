package eu.marxt12372.godrive;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper
{
	public static final int LOGIN_INFO_USERNAME = 1;
	public static final int LOGIN_INFO_PASSWORD = 2;

	public DBHandler(Context context)
	{
		super(context, "andmebaas", null, 5);
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE andmed (id INTEGER PRIMARY KEY AUTOINCREMENT, username CHAR(64), password CHAR(64));");
		db.execSQL("INSERT INTO andmed (id, username, password) VALUES ('1', 'NULL', 'NULL')");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS andmed");
		onCreate(db);
	}

	public void setLoginInfo(String username, String password)
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE andmed SET username = '" + username + "', password = '" + password + "' WHERE id = 1;");
		db.close();
	}

	public String getLoginInfo(int type)
	{
		String username = "", password = "";
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM andmed WHERE id = 1;", null);
		c.moveToFirst();
		username = c.getString(c.getColumnIndex("username"));
		password = c.getString(c.getColumnIndex("password"));
		db.close();

		if(type == LOGIN_INFO_USERNAME)
		{
			return username;
		}
		else if(type == LOGIN_INFO_PASSWORD)
		{
			return password;
		}
		return "NULL";
	}
}
