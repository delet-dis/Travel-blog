package com.delet_dis.travelblog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

  private TextInputLayout textUsernameLayout;
  private TextInputEditText usernameInput;
  private TextInputLayout textPasswordLayout;
  private TextInputEditText passwordInput;

  private Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);

	textUsernameLayout = findViewById(R.id.textUsernameLayout);
	usernameInput = findViewById(R.id.usernameInput);

	textPasswordLayout = findViewById(R.id.textPasswordLayout);
	passwordInput = findViewById(R.id.passwordInput);

	loginButton = findViewById(R.id.loginButton);

	usernameInput.addTextChangedListener(emptyInputTextWatcher(usernameInput, textUsernameLayout));
	passwordInput.addTextChangedListener(emptyInputTextWatcher(passwordInput, textPasswordLayout));


  }


  private TextWatcher emptyInputTextWatcher(TextInputEditText processingField, TextInputLayout processingFieldParentLayout) {
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
}