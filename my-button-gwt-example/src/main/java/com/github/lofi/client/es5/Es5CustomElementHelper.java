package com.github.lofi.client.es5;

import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.*;
import jsinterop.base.Js;
import jsinterop.base.JsConstructorFn;

import java.util.function.Consumer;

import static elemental2.dom.DomGlobal.customElements;
import static jsinterop.base.Js.asConstructorFn;

public class Es5CustomElementHelper {

    /**
     * Registers an ES5 class as a webcomponent on the page with the given tagname. ES5 classes cannot
     * be used as web components directly, this includes a workaround to rewrite your class to make it
     * behave.
     *
     * Note that this breaks the contructor and instance initializer of your type, and probably the static
     * initializer as well - beware of initializing fields either with the field declaration or in the
     * class's constructor or initialization blocks, it will not work when used in this way. Instead,
     * consider adding an "init" method of some kind and passing it as the third optional argument here -
     * it will be called after the element is created.
     *
     * It is not required that the provided web component type is marked as @JsType, but the web component
     * lifecycle methods must be exported to be invoked by the browser, be sure you are doing that correctly
     * in your build tooling.
     *
     * The returned factory method can be used in your own code to programmatically create your new element
     * instead of your own constructor (which won't work anyway).
     *
     *
     * @param tagName the name of the tag to register the element
     * @param webComponentType the class of the custom element to register
     * @param initializer optional initializer function to correctly set up the element after creation (since
     *                    the constructor won't work)
     * @param <T> the type of the custom element
     * @return a constructor function to let you create your own instances programmatically
     */
    @JsIgnore
    public static <T extends HTMLElement> JsConstructorFn<? extends T> registerEs5(String tagName, Class<T> webComponentType, Consumer<T> initializer) {
        Type<HTMLElement> htmlElementConstructor = Js.uncheckedCast(Js.asPropertyMap(DomGlobal.window).get("HTMLElement"));
        JsConstructorFn<? extends T> shimType = Type.subtypeOf(htmlElementConstructor, asConstructorFn(webComponentType), initializer).asClass();
        customElements.define(tagName, shimType);
        return shimType;
    }

    @JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
    public static class Type<T> {
        @JsFunction
        public interface Constructor {
            Object construct();
        }
        @JsOverlay
        public static <T, U extends T> Type<? extends U> subtypeOf(Type<T> superclass, JsConstructorFn<U> realClass, Consumer<U> initializer) {
            Type<U> realType = Js.cast(realClass);
            final Constructor syntheticCtor;
            if (initializer != null) {
                syntheticCtor = new Constructor() {
                    @Override
                    public Object construct() {
                        U instance = (U) Reflect.construct(superclass, new JsArray<>(), this);
                        initializer.accept(instance);
                        return instance;
                    }
                };
            } else {
                syntheticCtor = new Constructor() {
                    @Override
                    public Object construct() {
                        return Reflect.construct(superclass, new JsArray<>(), this);
                    }
                };
            }

            // make sure the real type is correctly a subclass of the superclass
            realType.prototype.__proto__ = Js.<Type<T>>cast(superclass).prototype;
            realType.__proto__ = superclass;

            // wire up our shim as a subclass of the real type
            // This cast isn't accurate, but it is an anonymous type, a subclass of U
            Type<U> shimType = Js.cast(syntheticCtor);
            shimType.prototype.__proto__ = realType.prototype;
            shimType.__proto__ = realType;

            return shimType;
        }

        public Prototype prototype;
        public Object __proto__;

        @JsOverlay
        public final JsConstructorFn<? extends T>  asClass() {
            return Js.cast(this);
        }
    }
    @JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
    public static class Prototype {
        public Object __proto__;
    }
    @JsType(isNative = true, namespace = JsPackage.GLOBAL)
    public static class Reflect {
        public static native Object construct(Object superClass, JsArray<?> args, Object target);
    }
}
