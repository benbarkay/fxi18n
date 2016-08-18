# fxi18n - JavaFX Internationalization Library
The purpose of this library is to provide easy means of internationalization
while maintaining that there are no dependencies on ResourceBundles or other such
string repository mechanisms for UI views or controllers.

## In a nutshell

```
// Initialize an instance of `Strings` somewhere in our code
// The following is a verbose example of default settings, one could
// call `Strings.createDefault()` in order to obtain a default instance.
Property<Locale> localeProperty = new SimpleObjectProperty<>(Locale.getDefault());
Strings strings = Strings.builder()
    .locale(localeProperty)
    .formatter(new DefaultStringFormatter())
    .repositoryFactory(new ResourceBundleRepositoryFactory())
    .build();

// An interface denoting strings required by LoginWindow.
// Let's assume its fully qualified name is `example.login.LoginStrings`
public interface LoginStrings {
    ObservableStringValue username();
    ObservableStringValue password();
}

// Resource `/example/login/LoginStrings.properties`
username=How do you call yourself online?
password=What is your secret phrase?

// Implementation of LoginWindow controller
public class LoginWindowController {
    @FXML
    Label usernameLabel;
    
    @FXML
    Label passwordLabel;
    
    // irrelevant code excluded
    public void bindStrings(LoginStrings loginStrings) {
        usernameLabel.textProperty().bind(loginStrings.username());
        passwordLabel.textProperty().bind(loginStrings.password());
    }
}

public class LoginWindowFactory {
    private final Strings strings;
    // more instance variables, constructor etc...
    public LoginWindow newLoginWindow() {
        LoginWindowController controller;
        // FXML or other initialization code ...
        controller.bindStrings(strings.get(LoginStrings.class));
    }
}
```

Using the above setup, if we for example have a German translation,
we could simply do the following:

```
localeProperty.setValue(Locale.GERMAN);
```

And our UI will have updated to the correct German strings. We can
also use `Strings#setLocale(Locale)` to achieve the same effect.

## Project goals
This project aims to maintain the following:

* **Simplicity**: The API must be extremely simple, users should have
  close to no confusion as to what it is capable of doing or not.
  For additional functionality, this project can be extended by a
  dependent project.
* **Flexibility**: With the exception of JavaFX, this project must have
  no other dependencies. Each API dependency must be decoupled by an
  interface so that users can change underlying parts with ease.
* **Non-intrusiveness**: Aims to achieve the least impact on dependent
  codebases -- Require as little types special to this library to be
  passed around. Least amount of refactoring if one chooses to
  remove dependency of this library.
* **Testability**: Code implementing the paradigms advocated by or
  the types of this library should be easy to mock without any special
  tools. 