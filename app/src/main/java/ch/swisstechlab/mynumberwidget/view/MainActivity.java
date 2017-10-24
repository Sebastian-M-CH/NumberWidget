package ch.swisstechlab.mynumberwidget.view;

import android.appwidget.AppWidgetManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_PHONE_STATE;

import ch.swisstechlab.mynumberwidget.R;
import ch.swisstechlab.mynumberwidget.controller.MainController;
import ch.swisstechlab.mynumberwidget.permissions.PermissionHandler;

public class MainActivity extends Activity {

    Button assignRightsButton;
    Button copieButton;
    CheckBox permissionsCheckBox;
    CheckBox compatibilityCheckBox;
    TextView diagnoseTextView;
    MainController mainController;
    int READ_PHONE_STATE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignRightsButton = (Button) findViewById(R.id.assignRightsButton);
        copieButton = (Button) findViewById(R.id.copieButton);
        permissionsCheckBox = (CheckBox) findViewById(R.id.permissionsCheckBox);
        compatibilityCheckBox = (CheckBox) findViewById(R.id.compatibilityCheckBox);
        diagnoseTextView = (TextView) findViewById(R.id.diagnoseTextView);
        mainController = new MainController(this);
        setState();
        setListeners();
    }

    private void setState() {
        boolean hasPermission = mainController.hasPermission(READ_PHONE_STATE);

        permissionsCheckBox.setChecked(hasPermission);
        if(hasPermission) {
            assignRightsButton.setEnabled(false);
            compatibilityCheckBox.setChecked(mainController.isCompatible());
            compatibilityCheckBox.setVisibility(View.VISIBLE);
        }

        diagnoseTextView.setText("Fine");
    }

    private void setListeners() {
        assignRightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.requestPermission(READ_PHONE_STATE, READ_PHONE_STATE_REQUEST_CODE);
            }
        });
        copieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String number = mainController.getPhoneNumber();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PhoneNumber", number);
                clipboard.setPrimaryClip(clip);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        setState();
        mainController.updateWidget();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
