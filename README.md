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
    ObservableStringValue status(ObservableDoubleValue percent,
                            ObservableStringValue status);
    ObservableStringValue bakingBread();
}
```

And just make a `SomeWindowStrings` resource bundle somewhere..
```
title=Greetings!
greet=Hi there, %s! I'm a just a tool, but I'll greet you anyway! Nice to meet you!
status=Progress is at %#.0, %s
bakingBread=baking bread...
```

And then just have it work, perhaps like this?

```
SomeWindowStrings myStrings = strings.get(SomeWindowStrings.class);
titleLabel.textProperty().bind(myStrings.title());
greetingLabel.textProperty().bind(myStrings.greet(fullName));
statusLabel.textProperty().bind(myStrings.status(progressProperty,
        statusTextProperty);
```

Worrying just about updating your specific properties, and have it all
work?

```
progressProperty.setValue(35.5);
statusTextProperty.setValue(myStrings.bakingBread());
// resulting in "Progress is at 35.5, baking bread..."
```

And be able to update all of your UI text to the chosen translation
instantly, just by changing the locale?

```
myLocale.setValue(someLocale);
// or strings.setLocale(someLocale)
```

If so, then this is that project. Although it's a fully functioning
codebase, it's still in its infancy. Feel free to use, fork, make
suggestions, make pull requests, review, critique.