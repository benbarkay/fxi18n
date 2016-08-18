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

And just make a `SomeWindowStrings` resource bundle somewhere..
```
title=Greetings!
greet=Hi there, %s! I'm a just a tool, but I'll greet you anyway! Nice to meet you!
```

And then just have it work, perhaps like this?

```
SomeWindowStrings myStrings = strings.get(SomeWindowStrings.class);
titleLabel.textProperty().bind(myStrings.title());
greetingLabel.textProperty().bind(myStrings.greet(fullName));
```

And be able to update all of your UI text to the chosen translation
instantly, just by changing the locale?

```
myLocale.setValue(someLocale);
// or strings.setLocale(someLocale)
```

If so, then this is that project. But it's in its infancy. So stay tuned,
or help if you won't take it personally if your pull request is rejected.