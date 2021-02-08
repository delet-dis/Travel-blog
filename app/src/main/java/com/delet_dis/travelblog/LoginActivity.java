package com.delet_dis.travelblog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

  private TextInputLayout textUsernameLayout;
  private TextInputEditText usernameInput;


  private TextInputLayout textPasswordLayout;
  private TextInputEditText passwordInput;

  private Button loginButton;

  private ProgressBar loginProgressBar;

  private BlogPreferences blogPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	blogPreferences = new BlogPreferences(this);

	if (blogPreferences.isLoggedIn()) {
	  startMainActivity();
	  finish();
	  return;
	}

	setContentView(R.layout.activity_login);

	findViewElements();

	addFieldsListeners();
  }

  private void addFieldsListeners() {
	usernameInput.addTextChangedListener(emptyInputTextWatcher(textUsernameLayout));
	passwordInput.addTextChangedListener(emptyInputTextWatcher(textPasswordLayout));

	loginButton.setOnClickListener(v -> onLoginClicked());
  }

  private void findViewElements() {
	textUsernameLayout = findViewById(R.id.textUsernameLayout);
	usernameInput = findViewById(R.id.usernameInput);

	textPasswordLayout = findViewById(R.id.textPasswordLayout);
	passwordInput = findViewById(R.id.passwordInput);

	loginButton = findViewById(R.id.loginButton);

	loginProgressBar = findViewById(R.id.loginProgressBar);
  }


  private TextWatcher emptyInputTextWatcher(TextInputLayout processingFieldParentLayout) {
	return new TextWatcher() {
	  @Override
	  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	  }

	  @Override
	  public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (s.toString().isEmpty()) {
		  processingFieldParentLayout.setError(getResources().getText(R.string.fieldEmptyError));
		} else {
		  processingFieldParentLayout.setError(null);
		}

	  }

	  @Override
	  public void afterTextChanged(Editable s) {

	  }
	};
  }

  private void showLoginErrorDialog() {
	new AlertDialog.Builder(this)
			.setTitle(getResources().getText(R.string.loginFailedTitleAlertDialogError))
			.setMessage(getResources().getText(R.string.loginFailedMessageAlertDialogError))
			.setPositiveButton(getResources().getText(R.string.positiveOkText), ((dialog, which) -> dialog.dismiss()))
			.show();
  }

  private void onLoginClicked() {
	if (isLoginFieldsAreEmpty()) {
	  showLoginErrorDialog();
	} else {
	  performLogin();
	}
  }

  private boolean isLoginFieldsAreEmpty() {
	return Objects.requireNonNull(usernameInput.getText()).toString().isEmpty() || Objects.requireNonNull(passwordInput.getText()).toString().isEmpty();
  }

  private void disableLoginFields() {
	textUsernameLayout.setEnabled(false);
	textPasswordLayout.setEnabled(false);
  }

  private void performLogin() {
	disableLoginFields();

	blogPreferences.setLoginState(true);

	loginButton.setVisibility(View.INVISIBLE);
	loginProgressBar.setVisibility(View.VISIBLE);

	Handler handler = new Handler(Looper.getMainLooper());
	handler.postDelayed(() -> {
	  startMainActivity();
	  finish();
	}, 2000);
  }

  private void startMainActivity() {
	Intent intent = new Intent(this, MainActivity.class);
	startActivity(intent);
  }
}