package pl.polsl.lab.worldcupstats.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.polsl.lab.worldcupstats.R;
import pl.polsl.lab.worldcupstats.exceptions.WrongGroupIndexException;
import pl.polsl.lab.worldcupstats.models.WorldCupStatsModel;

/**
 * Class representing an activity for reviewing standings of groups.
 *
 * @author Paweł Szwajnoch
 * @version 1.6
 */
public class ReviewingStandingsActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewing_standings);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        theModel = (WorldCupStatsModel) bundle.getSerializable("WorldCupStatsModel");

        textViewGroupName = findViewById(R.id.textViewGroupName);
        listViewTeamList = findViewById(R.id.listViewTeamList);

        String[] firstGroupStandings = theModel.getActualGroupStandings();
        this.displayStandingsList(firstGroupStandings);
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

            String[] actualGroupStandings = theModel.getActualGroupStandings();
            displayStandingsList(actualGroupStandings);

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

            String[] actualGroupStandings = theModel.getActualGroupStandings();
            displayStandingsList(actualGroupStandings);

        } catch (WrongGroupIndexException ex) {
            Toast.makeText(this, "No more groups!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays list of standing of currently presented group
     *
     * @param actualGroupStandings array of teams names
     */
    private void displayStandingsList(String[] actualGroupStandings) {

        ArrayList<String> listItems = new ArrayList<>();

        for (String elem : actualGroupStandings) {
            listItems.add(elem);
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listViewTeamList.setAdapter(adapter);
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
