package com.github.lofi.client.components;

import java.util.ArrayList;
import java.util.logging.Logger;

import elemental2.dom.HTMLElement;
import elemental2.dom.ShadowRoot;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

// this means we can have at most one constructor defined, at least as far as JS is concerned,
// and that the ctor will be the main way to create instances
@JsType
public class MyButton extends HTMLElement {

    private static Logger logger = Logger.getLogger(MyButton.class.getName());

    private static final String LABEL = "label";

    private String field1;

    // this _may_ be lost when used as an es5 type, don't risk it
    private String field2 = "hasValueFromInitializer";

    private int field3;

    // this _may_ be lost when used as an es5 type, don't risk it
    private int field4 = 1;

    private ArrayList<String> field5;
    
    // this will be lost when used as an es5 type
    private ArrayList<String> field6 = new ArrayList<String>();

    public MyButton() {
        // these will all be lost when used as an es5 type
        field1 = "hasValueFromCtor";
        field3 = field1.length();
        field5 = new ArrayList<String>();
    }

    public void init() {
        field1 = "hasValueFromCtor";
        field3 = field1.length();
        field5 = new ArrayList<String>();
    }

    public void connectedCallback() {
        logger.info("MyButton constructor...");
        
        AttachShadowOptionsType options = AttachShadowOptionsType.create();
        options.setMode("open");

        ShadowRoot shadowRoot = attachShadow(options);
        shadowRoot.innerHTML = "<div class=\"container\"><button>My Button</button></div>";
        shadowRoot.firstElementChild.setAttribute("title", toString());
    }

    @JsProperty(name = LABEL)
    public String getRef() {
        String value = this.getAttribute(LABEL);
        return value == null ? toString() : value;
    }

    @JsProperty(name = LABEL)
    public void setRef(String value) {
        this.setAttribute(LABEL, value);
    }

    @JsProperty
    public String[] getObservedAttributes() {
        return new String[] { LABEL };
    }

    @Override
    public Object attributeChangedCallback(String attributeName, String oldValue, String newValue, String namespace) {
        return super.attributeChangedCallback(attributeName, oldValue, newValue, namespace);
    }

    @Override
    public String toString() {
        return field1 + field2 + field3 + field4 + field5 + field6;
    }
}
