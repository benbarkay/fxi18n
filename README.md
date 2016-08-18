# fxi18n - JavaFX Internationalization Library
The purpose of this library is to provide easy means of internationalization
while maintaining that there are no dependencies on ResourceBundles or other such
string repository mechanisms for UI views or controllers.

## In a nutshell

Have you wished at some point that you could just do something of
this sort?

```
public interface SomeWindowStrings {
    ObservableStringValue title();
    ObservableStringValue greet(String name);
}
```

And just make a `SomeWindowSettings` ResourceBundle bundle somewhere..
```
title=Greetings!
greet=Hi there, %s! I'm a just a tool, but I'll greet you anyway! Nice to meet you!
```

And then just have it work, perhaps like this?

```
SomeWindowStrings myStrings = strings.get(SomeWindowStrings.class);
titleLabel.bind(myStrings.title());
greetingLabel.bind(myStrings.greet(fullName));
```

And then be able to update all of your text to the chosen translation
instantly, just by changing the locale?

```
myLocale.setValue(someLocale);
// or strings.setLocale(someLocale)
```

If so, then this is that project.

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