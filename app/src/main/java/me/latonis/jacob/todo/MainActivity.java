package me.latonis.jacob.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.latonis.jacob.todo.db.TaskContract;
import me.latonis.jacob.todo.db.TaskDbHelper;

public class MainActivity extends AppCompatActivity {
    List<ListItem> listItems = new ArrayList<>();
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(R.id.toDoList);
        updateUI();
        /*final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listItems.add(new ListItem());
                TextView trial = (TextView) findViewById(R.id.yoon);
                if (trial.getText() == getString(R.string.other)) {
                    trial.setText(R.string.notOther);
                } else {
                    trial.setText(R.string.other);
                }
                ;
                for (int i = 0; i < listItems.size(); i++) {
                   Log.d("myTag", listItems.get(i).getDate());
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(index));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.todoitem, R.id.taskTitle, taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d("LOG", "Add a new task");
                final EditText taskEditText = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a new task");
                builder.setMessage("What do you want to accomplish next?");
                builder.setView(taskEditText);
                AlertDialog dialog = builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = String.valueOf(taskEditText.getText());
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();
                    }
                }).setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


