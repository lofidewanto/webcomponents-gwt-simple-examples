# webcomponents-gwt-simple-examples

Web Components Simple Examples with GWT and J2CL

## General Information ECMAScript ES5 and ES6

- What is JavaScript, what is ECMAScript? See: https://www.freecodecamp.org/news/whats-the-difference-between-javascript-and-ecmascript-cba48c73a2b5/
- What is the difference between ES5 and ES6? See: https://www.geeksforgeeks.org/difference-between-es5-and-es6/


## Transpilers GWT and J2CL
- GWT 2.x could only generate ES5 JS code
- J2CL could generate ES5 or ES6 JS code


## Web Components (WC)

- Introduction to Web Components: https://www.webcomponents.org/introduction
- WC only works with ES6 or maybe using Custom Elements Polyfill: https://stackoverflow.com/questions/45747646/what-is-the-es5-way-of-writing-web-component-classes
- In this repo I try to create some examples of how doing WC with GWT / J2CL. Thanks to Colin (https://github.com/niloc132) and Thomas (https://github.com/tbroyer) for all the tips and examples from https://gitter.im/gwtproject/gwt! 


## Example 1: my-button-gwt-example

### Running Example 1

To run the examples: just go to the Maven project directory and run

```
mvn gwt:generate-module gwt:devmode
```

### Explanation Example 1

This example shows how to build Web Component completely in Java / GWT / JsInterop. Afterwards you
can use it by just adding the component ```<my-button></my-button>``` in the index.html

- This example is taken from this introduction: https://www.robinwieruch.de/web-components-tutorial 
- Also using this GWT / J2CL example: https://github.com/vegegoku/gwt-j2cl-mind-palace/tree/poc-gwt-j2cl-web-component/gwt-j2cl-web-component

### Comments from Colin: See: https://gitter.im/gwtproject/gwt

- You can't do this in ES5 JS - and GWT 2.x won't generate ES6 classes. J2CL can do that.
- It is possible to shim this - creating the actual custom element ctor (constructor) in some cheater code which sets it up so you can call it from JS
the problem is that the "subclass" calls its superclass, which is HTMLElement - but in ES5, that means calling HTMLElement.call(this), roughly, instead of just saying "extends HTMLElement" and in the JS constructor calling "super()"
- The HTMLElement constructor doesn't permit calling the constructor as a function, only as a super constructor (its possible I am not mapping that error correctly, its easier to tell from the JS instead of the sourcemapped Java, but the idea is right at least).

### Building Web Component (WC): comments from Colin:

- If you made a Web Component for every widget in GMD, domino-ui, etc, i think you'd regret it.
- If you made a large Web Component that embedded an entire "app" with just a handful of config options, I think you'd be happy. An "app" could be even small like "YouTube widget" or "Github build status checker" etc.
- But the point is that you wouldnt build a giant app out of hundreds of these as sort of a "modern uibinder" in plain html, after registering all possible widgets you were using ```<foo bar="baz"></foo>``` means that in the Foo class, the "bar" attribute can't know that even though you only assigned it once with the "baz" string, the value will always be that one constant - the API for changing attributes can't detect that you'll never change it, and you'll only pass a constant.
In contrast, UIBinder or just calling a setter (UIBinder just emits a setter) will let GWT or Closure notice you only set that property once, to a single value, and it will probably optimize to assume just that - perhaps removing other branches of a switch/case or if/else when compiling, or inline that string when rendering, etc.
- But for a big app made of Web Components, with 100 different component types (possibly 1 or 100 instances of each) each with a handful (5? 20?) of possible properties that could be changed, that could reasonably be a lot of impossible optimizations.
- Now, once upon a time I was told that some optimizations were coming - that you could feed a polymer tool a "static html string" and the Web Components you use, and it would rewrite all your attribute setting funcs to just use the static values but I never heard more about it and can't find evidence that this is a thing
```attributeChangedCallback``` and ```observedAttributes``` is what I'm referring to.
- It could also be considered a feature that this happens, this degree of isolation and keeping the API consistent, since perhaps you don't want the app to compile like this, so that each Web Component can use a different version of whatever libraries it wants, but again, I think that will surprise you when you see how big your app gets.
- For a small app you'll never care that you're adding 10 or 100kb per widget.


## Example 2: my-button-j2cl-es5-example

### Running Example 2

To run the examples: just go to the Maven project directory and run

```
mvn j2cl:build
```

and open the index.html in following directory (without server, just double click the file)

```
target/my-button-j2cl-es5-example-1.0.0-SNAPSHOT/index.html
```

### Explanation Example 2

This example is just similar to Example 1 but we use J2CL to transpile to ES5.
It works well and show the same result as Example 1, the main difference is the transpiler we use.


## Example 3: my-button-j2cl-es6-example

... Todo ...


## Example 4: use-my-button-gwt-example

This example shows how to use already created Web Components in your Java / GWT project. In this case you have at the moment 2 possibilities in GWT:
- You are using UIBinder with Standard GWT Widgets and any other UI frameworks based on this (GWT integrated Widgets, GWT Material Design).
- You are using DOM / Elemento / Elemental2 with or without UI frameworks (DominoUI, ...)

### Tip to consume Web Components from GWT: comments from Thomas:

- I use widgets, so a have a Widget subclass whose ctor (constructor) calls setElement(Document.get().createElement("my-wc")), and I map the element's API as a native type (I actually used an interface that extends EventTarget, as I don't really care about the class itself, which BTW could be "private" in JS) and cast the result of getElement().
- In the constructor, I also have a DomGlobal.customElements.whenDefined("my-wc").then(ignored -> { /* do something */; return null; }); to account for the possible async loading of the JS (and with the interface for the native type, this means I don't care if I get an UnknownHTMLElement or the actual element; as long as I don't call specific methods but only set properties and attach event handlers).

... Todo ...
