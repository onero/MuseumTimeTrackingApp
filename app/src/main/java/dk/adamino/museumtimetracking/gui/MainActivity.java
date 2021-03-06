package dk.adamino.museumtimetracking.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import dk.adamino.museumtimetracking.R;
import dk.adamino.museumtimetracking.be.Guild;
import dk.adamino.museumtimetracking.be.VolunteerWork;
import dk.adamino.museumtimetracking.gui.model.GuildModel;

public class MainActivity extends AppCompatActivity {

    private TextView mVolunteerName;
    private Spinner mGuildSpinner;
    private NumberPicker mNumberPicker;
    private Button mDocumentHoursButton;

    private ArrayAdapter<Guild> mGuildAdapter;

    private GuildModel mGuildModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGuildModel = new GuildModel();

        instantiateViews();


        setAdapterForGuildSpinner();

        setNumberPickerValues();

        handleDocumentHours();


    }

    /**
     * Assign all fields to views
     */
    private void instantiateViews() {
        mVolunteerName = (TextView) findViewById(R.id.volunteerName);

        mGuildSpinner = (Spinner) findViewById(R.id.guildSpinner);

        mNumberPicker = (NumberPicker) findViewById(R.id.hourPicker);

        mDocumentHoursButton = (Button) findViewById(R.id.documentHoursButton);
    }

    /**
     * Set the onClickListener for mDocumentHoursButton
     */
    private void handleDocumentHours() {
        mDocumentHoursButton.setOnClickListener((View v) -> {

            int id = 166; //Adam Hansen
            String guild = mGuildSpinner.getSelectedItem().toString();
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();

            int hours = mNumberPicker.getValue();
            VolunteerWork newWork = new VolunteerWork(id, guild, date, hours);

            boolean documented = mGuildModel.documentVolunteerHours(newWork);

            if (documented) {
                Toast.makeText(this, "Dokumenteret!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "SHEIT!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Create and set adapter for the spinner
     */
    private void setAdapterForGuildSpinner() {
        mGuildAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_row, mGuildModel.getActiveGuildsFromDB());


        mGuildSpinner.setAdapter(mGuildAdapter);
    }



    /**
     * Set the min, max and default value on the NumberPicker
     */
    private void setNumberPickerValues() {
        mNumberPicker.setMinValue(1);

        mNumberPicker.setMaxValue(20);

        mNumberPicker.setValue(8);
    }


}
