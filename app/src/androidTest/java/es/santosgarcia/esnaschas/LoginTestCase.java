package es.santosgarcia.esnaschas;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

/**
 * Created by Miguel on 06/02/2016.
 */
public class LoginTestCase extends ActivityInstrumentationTestCase2<LoginActivity> {

    // Instanciamos los componentes
    private LoginActivity actividad;
    private EditText usuario;
    private EditText password;
    private Button loginBtn;

    // Inicializacion de variables para el Test
    private static final String USERNAME = "Miguel";
    private static final String PASSWORD = "1";

    public LoginTestCase() {
        super(LoginActivity.class);
    }

    //  inicialización de todos los métodos de prueba
    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        usuario = (EditText) actividad.findViewById(R.id.Usernamefield);
        password = (EditText) actividad.findViewById(R.id.Passwordfield);
        loginBtn = (Button) actividad.findViewById(R.id.btnWithText);
    }

    // Comprobacion de campos no vacios.
    public void testPreconditions() {
        assertNotNull(usuario);
        assertNotNull(password);
    }

    //  Este codigo se ejecuta despues de cada test
    protected void tearDown() throws Exception {
        super.tearDown();
        if (ParseUser.getCurrentUser() != null)
            ParseUser.logOut();
    }

    // Pruebsas con metodos
    public void testAddValues() {

        TouchUtils.tapView(this, usuario);
        getInstrumentation().sendStringSync(USERNAME);

        TouchUtils.tapView(this, password);
        getInstrumentation().sendStringSync(PASSWORD);

        TouchUtils.clickView(this, loginBtn);

        String usuario = ParseUser.getCurrentUser().getUsername();

        assertTrue("Add result should be...", usuario.equals(USERNAME));


    }

}
