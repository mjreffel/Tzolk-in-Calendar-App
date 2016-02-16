package mjreffel.tzolkincalendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.GregorianCalendar;

public class whatbirthdate extends AppCompatActivity {

    //Setup the necessary Global accessible variables
    private Button calculate;
    private EditText inputDate;
    private TextView tzolkinResults;
    private TextView longResults;
    private TextView haabResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatbirthdate);
        calculate = (Button) findViewById(R.id.calculate);
        inputDate = (EditText) findViewById(R.id.enteredDate);
        tzolkinResults = (TextView) findViewById(R.id.tzolkinDate);
        longResults = (TextView) findViewById(R.id.longDate);
        haabResults = (TextView) findViewById(R.id.haabDate);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_whatbirthdate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        //Get info entered into text vield
        String enteredDate = inputDate.getText().toString();

        //Parse entered string to create Date object
        String[] explodedDate = enteredDate.split("/");
        int day = Integer.parseInt(explodedDate[0]);
        int month = Integer.parseInt(explodedDate[1]);
        int year = Integer.parseInt(explodedDate[2]);

        //Perform calculations necessary for display

        GregorianCalendar bDate = new GregorianCalendar(year, month, day);
        onCalculateTzolkin(bDate);
        //onCalculateLong(bDate);
        //onCalculateHaab(bDate);


    }

    private void onCalculateTzolkin(GregorianCalendar bDate) {
        //Setup Default Date
        String calculation = "";

        // 01/01/1900 => 4 Etz'nab'

        GregorianCalendar startDate = new GregorianCalendar(1939,3,21);

        double miliDiff = bDate.getTimeInMillis() - startDate.getTimeInMillis();
        int calcTotal = (int) (miliDiff / (1000*60*60*24));

        //Setup the corresponding arrays to do modulo based on
        String [] innerCircle = new String[13];
        String [] outerCircle = new String[20];

        //Setup Inner Circle
        innerCircle[0] = "1";
        innerCircle[1] = "2";
        innerCircle[2] = "3";
        innerCircle[3] = "4";
        innerCircle[4] = "5";
        innerCircle[5] = "6";
        innerCircle[6] = "7";
        innerCircle[7] = "8";
        innerCircle[8] = "9";
        innerCircle[9] = "10";
        innerCircle[10] = "11";
        innerCircle[11] = "12";
        innerCircle[12] = "13";

        //Setup Outer Circle
        outerCircle[0] = "Imix'";
        outerCircle[1] = "Ik'";
        outerCircle[2] = "Ak'b'al";
        outerCircle[3] = "K'an";
        outerCircle[4] = "Chikchan";
        outerCircle[5] = "Kimi";
        outerCircle[6] = "Manik'";
        outerCircle[7] = "Lamat";
        outerCircle[8] = "Muluk";
        outerCircle[9] = "Ok";
        outerCircle[10] = "Chuwen";
        outerCircle[11] = "Eb'";
        outerCircle[12] = "B'en";
        outerCircle[13] = "Ix";
        outerCircle[14] = "Men";
        outerCircle[15] = "Kib'";
        outerCircle[16] = "Kab'an";
        outerCircle[17] = "Etz'nab'";
        outerCircle[18] = "Kawak";
        outerCircle[19] = "Ajaw";


        Log.d("Calculate pre Total", Integer.toString(calcTotal));

        calcTotal = calcTotal%260;
        Log.d("Calculate Total", Integer.toString(calcTotal));

        //Fix off by one error
        calcTotal++;





        //Perform the modulo needed based on the base day and how many days have passed
        int whatInnerNum = (calcTotal % 13);
        Log.d("Inner Circle Num", Integer.toString((whatInnerNum)));
        int whatOuterNum = (calcTotal % 20);
        Log.d("Outer Circle Num", Integer.toString((whatOuterNum)));
        String whatInner = innerCircle[whatInnerNum];
        String whatOuter = outerCircle[whatOuterNum];

        //Setup the calculation based on given strings
        calculation = whatInner + " " + whatOuter;


        //Output the desired date to the results text box
        tzolkinResults.setText(calculation);


    }

    private void onCalculateLong (GregorianCalendar bDate) {

        // 01/01/1900 => 12.14.5.6.18

        GregorianCalendar startDate = new GregorianCalendar(1,1,1900);

        //calculate the number of days different from the birthdate entered and base date
        double miliDiff = bDate.getTimeInMillis() - startDate.getTimeInMillis();
        int calcTotal = (int) (miliDiff / (1000*60*60*24));






        // 01/01/1900 => 12.14.5.6.18
        int [] longDate = new int [5];

        //Calculate Baktun
        longDate[0] = 12 + calcTotal/144000;
        calcTotal -= calcTotal/144000;
        Log.d("Calculated baktun", Integer.toString(longDate[0]));

        //Calculate Katun
        longDate[1] = 14 + calcTotal/7200;
        calcTotal -= calcTotal/7200;
        Log.d("Calculated katun", Integer.toString(longDate[1]));

        //Calculate Tun
        longDate[2] = 5 + calcTotal/360;
        calcTotal -= calcTotal/360;
        Log.d("Calculated tun", Integer.toString(longDate[2]));

        //Calculate Unial
        longDate[3] = 6 + calcTotal/20;
        calcTotal -= calcTotal/20;
        Log.d("Calculated uinal", Integer.toString(longDate[3]));

        //Calculate K'in
        longDate[4] = 18 + calcTotal;
        Log.d("Calculated k'in", Integer.toString(longDate[4]));


        String longDateFormatted = "";
        for(int i = 0; i < longDate.length; i++) {
            longDateFormatted = longDateFormatted + Integer.toString(longDate[i]) + ".";
        }

        longDateFormatted = longDateFormatted.replaceAll(" \\.$", "");

        longResults.setText(longDateFormatted);


    }
    private void onCalculateHaab (Date bDate) {

    }
}
