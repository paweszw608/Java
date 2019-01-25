package pl.polsl.lab.worldcupstats.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.polsl.lab.worldcupstats.R;
import pl.polsl.lab.worldcupstats.exceptions.TextContainsForbiddenCharsException;
import pl.polsl.lab.worldcupstats.exceptions.WrongGroupIndexException;
import pl.polsl.lab.worldcupstats.models.WorldCupStatsModel;


/**
 * Class representing an activity for setting group matches results.
 *
 * @author Paweł Szwajnoch
 * @version 1.6
 */
public class SettingResultsActivity extends Activity {

    /**
     * WorldCupStatsModel reference
     */
    WorldCupStatsModel theModel;

    /**
     * TextView that stores names of teams playing in match number 1
     */
    TextView textView;
    /**
     * TextView that stores names of teams playing in match number 2
     */
    TextView textView2;
    /**
     * TextView that stores names of teams playing in match number 3
     */
    TextView textView3;
    /**
     * TextView that stores names of teams playing in match number 4
     */
    TextView textView4;
    /**
     * TextView that stores names of teams playing in match number 5
     */
    TextView textView5;
    /**
     * TextView that stores names of teams playing in match number 6
     */
    TextView textView6;

    /**
     * User input Edit text field for result of match number 1
     */
    EditText editText;
    /**
     * User input Edit text field for result of match number 2
     */
    EditText editText2;
    /**
     * User input Edit text field for result of match number 3
     */
    EditText editText3;
    /**
     * User input Edit text field for result of match number 4
     */
    EditText editText4;
    /**
     * User input Edit text field for result of match number 5
     */
    EditText editText5;
    /**
     * User input Edit text field for result of match number 6
     */
    EditText editText6;
    /**
     * TextView that stores name of the group
     */
    TextView textViewGroupName;


    /**
     * Invokes when this Activity is being created
     *
     * @param savedInstanceState object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_results);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        theModel = (WorldCupStatsModel) bundle.getSerializable("WorldCupStatsModel");

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);

        textViewGroupName = findViewById(R.id.textViewGroupName);

        String[] firstGroupMatches = theModel.getActualGroupMatches();
        setMatchLabels(firstGroupMatches);

    }

    /**
     * Executes operation of confirming results
     */
    public void confirmResultsButtonClicked(View view) {

        try {
            theModel.setActualGroupResults(getTextFieldsContent());
            theModel.setActualGroupResultsConfirmed();
            Toast.makeText(this, "Confirmed successfully", Toast.LENGTH_SHORT).show();
        } catch (TextContainsForbiddenCharsException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (theModel.areGroupsConfirmed()) {
                Toast.makeText(this, "All groups confirmed, moving to the next step", Toast.LENGTH_SHORT).show();
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

            if (theModel.isActualGroupConfirmed()) {
                String[] results = theModel.getActualGroupResults();
                setResultsTextFields(results);
            } else {
                clearTextFields();
            }
            String[] actualGroupMatches = theModel.getActualGroupMatches();
            setMatchLabels(actualGroupMatches);
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

            if (theModel.isActualGroupConfirmed()) {
                String[] results = theModel.getActualGroupResults();
                setResultsTextFields(results);
            } else {
                clearTextFields();
            }
            String[] actualGroupMatches = theModel.getActualGroupMatches();
            setMatchLabels(actualGroupMatches);
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
        theModel.calculateEachGroupTeamsPoints();
        theModel.sortEachGroupTeams();

        Intent intent = new Intent(SettingResultsActivity.this, ReviewingStandingsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WorldCupStatsModel", theModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Returns array of strings from EditText Fields (that holds match results)
     *
     * @return Returns array of strings from EditText Fields
     */
    private String[] getTextFieldsContent() {
        String[] content = new String[]{editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString(),
                editText4.getText().toString(), editText5.getText().toString(), editText6.getText().toString()};

        return content;
    }

    /**
     * Fills Labels with correct matches
     *
     * @param actualGroupMatches String array that stores matches
     */
    private void setMatchLabels(String[] actualGroupMatches) {

        textView.setText(actualGroupMatches[0]);
        textView2.setText(actualGroupMatches[1]);
        textView3.setText(actualGroupMatches[2]);
        textView4.setText(actualGroupMatches[3]);
        textView5.setText(actualGroupMatches[4]);
        textView6.setText(actualGroupMatches[5]);
    }

    /**
     * Clears Edit Text Fields
     */
    private void clearTextFields() {
        editText.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
        editText5.setText("");
        editText6.setText("");
    }

    /**
     * Fills Plain Text Fields with correct results
     *
     * @param results String array that stores results
     */
    private void setResultsTextFields(String[] results) {
        editText.setText(results[0]);
        editText2.setText(results[1]);
        editText3.setText(results[2]);
        editText4.setText(results[3]);
        editText5.setText(results[4]);
        editText6.setText(results[5]);
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
