package com.github.lofi.client.components;

import elemental2.dom.HTMLElement;
import elemental2.dom.ShadowRoot;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType
public class MyButton extends HTMLElement {

    public MyButton() {
        HTMLElement.AttachShadowOptionsType options = new AttachShadowOptionsType() {

            private String mode;

            @Override
            public void setMode(String mode) {
                this.mode = mode;
            }

            @Override
            public String getMode() {
                return this.mode;
            }
        };

        options.setMode("open");
        ShadowRoot shadowRoot = attachShadow(options);
        shadowRoot.innerHTML = "<div class=\"container\"><button>My Button</button></div>";
    }

    @JsProperty(name = "label")
    public String getRef() {
        String value = this.getAttribute("label");
        return value == null ? "" : value;
    }

    @JsProperty(name = "label")
    public void setRef(String value) {
        this.setAttribute("label", value);
    }

    @JsProperty
    public String[] getObservedAttributes() {
        return new String[] { "label" };
    }

    @Override
    public Object attributeChangedCallback(String attributeName, String oldValue, String newValue, String namespace) {
        return super.attributeChangedCallback(attributeName, oldValue, newValue, namespace);
    }

}
