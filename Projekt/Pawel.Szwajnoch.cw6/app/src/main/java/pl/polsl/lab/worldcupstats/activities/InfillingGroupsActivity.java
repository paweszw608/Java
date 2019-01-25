package pl.polsl.lab.worldcupstats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.polsl.lab.worldcupstats.R;
import pl.polsl.lab.worldcupstats.exceptions.*;
import pl.polsl.lab.worldcupstats.models.*;

/**
 * Class representing an activity for infilling groups with teams.
 *
 * @author Paweł Szwajnoch
 * @version 1.6
 */
public class InfillingGroupsActivity extends Activity {


    /**
     * WorldCupStatsModel reference
     */
    WorldCupStatsModel theModel;

    /**
     * TextView that stores name of the group
     */
    TextView textViewGroupName;
    /**
     * ListView that stores list of teams
     */
    ListView listViewTeamList;


    /**
     * Invokes when this Activity is being created
     *
     * @param savedInstanceState object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infilling_groups);

        theModel = new WorldCupStatsModel();

        listViewTeamList = findViewById(R.id.listViewTeamList);
        textViewGroupName = findViewById(R.id.textViewGroupName);
    }


    /**
     * Executes operation of adding new team to selected group
     */
    public void addTeamButtonClicked(View view) {

        String[] teamList;
        EditText editText = findViewById(R.id.editTextTeamName);

        try {
            String teamName = editText.getText().toString();
            theModel.addTeamToActualGroup(teamName);
            teamList = theModel.getActualGroupTeamsList();
            displayTeamsList(teamList);
        } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            editText.setText("");
            if (theModel.areGroupsFull()) {
                Toast.makeText(this, "All groups infilled, moving to the next stage", Toast.LENGTH_SHORT).show();
                prepareNextActivity();

            }
        }
    }


    /**
     * Moves 'pointer' to the next group
     */
    public void nextTeamButtonClicked(View view) {

        try {
            int actualIndex = theModel.getActualGroupIndex();
            theModel.setActualGroupIndex(actualIndex + 1);
            char groupName = theModel.getGroupName();
            displayGroupName(groupName);
            clearTeamList();
            String[] teamList = theModel.getActualGroupTeamsList();
            displayTeamsList(teamList);
        } catch (WrongGroupIndexException ex) {
            Toast.makeText(this, "No more groups!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Moves 'pointer' to the previous group
     */
    public void prevTeamButtonClicked(View view) {

        try {
            int actualIndex = theModel.getActualGroupIndex();
            theModel.setActualGroupIndex(actualIndex - 1);
            char groupName = theModel.getGroupName();
            displayGroupName(groupName);
            clearTeamList();
            String[] teamList = theModel.getActualGroupTeamsList();
            displayTeamsList(teamList);
        } catch (WrongGroupIndexException ex) {
            Toast.makeText(this, "No more groups!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Prepares operation of moving user to next Activity.
     * Sets Intents.
     */
    private void prepareNextActivity() {

        theModel.resetGroupIndex();
        theModel.setEachGroupMatches();

        Intent intent = new Intent(InfillingGroupsActivity.this, SettingResultsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WorldCupStatsModel", theModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * Displays list of teams added to currently presented group
     *
     * @param teamList array of teams names
     */
    private void displayTeamsList(String[] teamList) {
        ArrayList<String> listItems = new ArrayList<>();

        for (String elem : teamList) {
            listItems.add(elem);
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listViewTeamList.setAdapter(adapter);
    }


    /**
     * Clears (empties) list view
     */
    private void clearTeamList() {
        listViewTeamList.setAdapter(null);
    }


    /**
     * Displays actual group name
     *
     * @param groupName actual group name
     */
    private void displayGroupName(char groupName) {
        textViewGroupName.setText("GROUP " + groupName);
    }


}

