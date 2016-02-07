package es.santosgarcia.esnaschas;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

import static com.parse.ParseUser.getCurrentUser;

/**
 * Santos García
 */

public class MyTestCase extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Button boton;
    private EditText username;
    private EditText password;
    private LoginActivity actividad;

    public MyTestCase() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        username = (EditText) actividad.findViewById(R.id.Usernamefield);
        password = (EditText) actividad.findViewById(R.id.Passwordfield);
        boton = (Button) actividad.findViewById(R.id.btnWithText);


    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private static final String USER = "111";
    private static final String PASSWORD = "111";
    private static final boolean RESULT = true;

    public void testAddValues() {
        //on value 1 entry
        TouchUtils.tapView(this, username);
        getInstrumentation().sendStringSync(USER);
        // now on value2 entry
        TouchUtils.tapView(this, password);
        getInstrumentation().sendStringSync(PASSWORD);
        // now on Add button
        TouchUtils.clickView(this, boton);

        ParseUser loginresult = getCurrentUser();

        assertTrue("Add result should be...", getCurrentUser().equals(loginresult));
    }


}
