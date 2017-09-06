/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.component;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.CssClasses;
import org.fujion.ancillary.CssStyles;
import org.fujion.ancillary.IDisable;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.event.KeyCode;

/**
 * The base class from which all UI components derive.
 */
public abstract class BaseUIComponent extends BaseComponent implements IDisable {
    
    private final CssStyles styles = new CssStyles();
    
    private final CssClasses classes = new CssClasses();
    
    private String height;
    
    private String width;
    
    private String flex;
    
    private String hint;
    
    private String balloon;
    
    private boolean disabled;
    
    private boolean visible = true;
    
    private int tabindex;
    
    private String css;
    
    private String dragid;
    
    private String dropid;
    
    private Popup context;
    
    private Popup popup;
    
    private String keycapture;
    
    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     */
    public void addMask() {
        addMask(null);
    }
    
    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     *
     * @param label Text to display in the center of the mask.
     */
    public void addMask(String label) {
        invoke("addMask", label);
    }
    
    /**
     * Creates a shaded mask over the component, effectively preventing direct user interaction.
     *
     * @param label Text to display in the center of the mask.
     * @param popup Popup to display when right-clicking on the mask.
     */
    public void addMask(String label, Popup popup) {
        invoke("addMask", label, popup);
    }
    
    /**
     * Removes the mask, if present.
     */
    public void removeMask() {
        invoke("removeMask");
    }
    
    /**
     * Returns the styles set for this component.
     *
     * @return The styles set for this component.
     */
    @PropertyGetter("style")
    public String getStyles() {
        return styles.toString();
    }
    
    /**
     * Sets the styles for this component, replacing any existing styles.
     *
     * @param styles Styles to set, represented as a string in the same format as the HTML style
     *            attribute.
     */
    @PropertySetter("style")
    public void setStyles(String styles) {
        this.styles.parse(styles, true);
        _syncStyles();
    }
    
    private String _syncStyle(String name, String dflt) {
        String current = styles.get(name);
        
        if (current != null) {
            return current;
        }
        
        styles.put(name, dflt);
        return dflt;
    }
    
    /**
     * Syncs style properties with stored styles and with the client.
     */
    protected void _syncStyles() {
        height = _syncStyle("height", height);
        width = _syncStyle("width", width);
        flex = _syncStyle("flex", flex);
        sync("style", styles.toString());
    }
    
    /**
     * Returns the value of the named style.
     *
     * @param name The style name (e.g., "color").
     * @return The value of the style, or null if no value set for the style.
     */
    public String getStyle(String name) {
        return styles.get(name);
    }
    
    /**
     * Adds a style to existing styles. If that style already exists, it will be overwritten.
     *
     * @param name The style name (e.g., "color").
     * @param value The style value.
     * @return The previous value of the style, or null if none.
     */
    public String addStyle(String name, String value) {
        String oldValue = styles.put(name, value);
        _syncStyles();
        return oldValue;
    }
    
    /**
     * Adds styles specified as a string.
     *
     * @param styles Styles to add, represented as a string in the same format as the HTML style
     *            attribute.
     */
    public void addStyles(String styles) {
        this.styles.parse(styles, false);
        _syncStyles();
    }
    
    /**
     * Removes a style.
     *
     * @param name The style name (e.g., "color").
     * @return The value of the removed style, or null if none.
     */
    public String removeStyle(String name) {
        return addStyle(name, null);
    }
    
    /**
     * Returns the CSS classes set for this component.
     *
     * @return Space-delimited list of CSS classes.
     */
    @PropertyGetter("class")
    public String getClasses() {
        return classes.toString();
    }
    
    /**
     * Sets the CSS classes for this component, replacing any existing classes.
     *
     * @param classes Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    public void setClasses(String classes) {
        this.classes.parse(classes);
        _syncClasses();
    }
    
    /**
     * Synchronize class settings with the client.
     */
    protected void _syncClasses() {
        sync("clazz", classes.toString(true));
    }
    
    /**
     * Adds one or more classes to existing classes.
     *
     * @param value Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    @PropertySetter("class")
    public void addClass(String value) {
        if (classes.add(value)) {
            _syncClasses();
        }
    }
    
    /**
     * Removes one or more classes from existing classes.
     *
     * @param value Space-delimited list of CSS classes. Extended syntax is supported (see
     *            {@link org.fujion.ancillary.CssClasses});
     */
    public void removeClass(String value) {
        if (classes.remove(value)) {
            _syncClasses();
        }
    }
    
    /**
     * Toggles the presence of two mutually exclusive classes based on a condition. Extended syntax
     * is supported for each of the class specifiers (see {@link org.fujion.ancillary.CssClasses})
     *
     * @param yesValue Classes to be added if the condition is true, or removed if false.
     * @param noValue Classes to be added if the condition is false, or removed if true.
     * @param condition The condition value.
     */
    public void toggleClass(String yesValue, String noValue, boolean condition) {
        if (classes.toggle(yesValue, noValue, condition)) {
            _syncClasses();
        }
    }
    
    /**
     * Hides the component - a shortcut for <code>setVisible(false)</code>.
     */
    public void hide() {
        setVisible(false);
    }
    
    /**
     * Shows the component - a shortcut for <code>setVisible(true)</code>.
     */
    public void show() {
        setVisible(true);
    }
    
    /**
     * Returns the height. This is synchronized with the height style setting.
     *
     * @return The height.
     */
    @PropertyGetter("height")
    public String getHeight() {
        return height;
    }
    
    /**
     * Sets the height. This is synchronized with the height style setting.
     *
     * @param height The height.
     */
    @PropertySetter("height")
    public void setHeight(String height) {
        height = trimify(height);
        
        if (!areEqual(height, this.height)) {
            this.height = height;
            addStyle("height", height);
        }
    }
    
    /**
     * Returns the width. This is synchronized with the width style setting.
     *
     * @return The width.
     */
    @PropertyGetter("width")
    public String getWidth() {
        return width;
    }
    
    /**
     * Sets the width. This is synchronized with the width style setting.
     *
     * @param width The width.
     */
    @PropertySetter("width")
    public void setWidth(String width) {
        width = trimify(width);
        
        if (!areEqual(width, this.width)) {
            this.width = width;
            addStyle("width", width);
        }
    }
    
    /**
     * Returns the flex setting. This is synchronized with the flex style setting.
     *
     * @return The flex setting.
     */
    @PropertyGetter("flex")
    public String getFlex() {
        return flex;
    }
    
    /**
     * Sets the flex setting. This is synchronized with the flex style setting.
     *
     * @param flex The flex setting.
     */
    @PropertySetter("flex")
    public void setFlex(String flex) {
        flex = trimify(flex);
        
        if (!areEqual(flex, this.flex)) {
            this.flex = flex;
            addStyle("flex", flex);
        }
    }
    
    /**
     * Sets/removes the input focus to/from this component. If the component cannot receive the
     * input focus, the request is ignored.
     *
     * @param focus If true, the component receives the input focus if possible. If false and the
     *            component currently has the input focus, the focus is relinquished.
     */
    @PropertySetter(value = "focus", defer = true)
    public void setFocus(boolean focus) {
        invoke("focus", focus);
    }
    
    /**
     * Sets the input focus to this component. If the component cannot receive the input focus, the
     * request is ignored. This is a shortcut for <code>setFocus(true)</code>.
     */
    public void focus() {
        setFocus(true);
    }
    
    /**
     * Returns the CSS specifier for this component. This is similar to an embedded style sheet, but
     * is associated with this component and is added to or removed from the client as this
     * component is added or removed. Moreover, EL expressions can be used in the selector to
     * reference this component or one of its subcomponents by their id. For example,
     *
     * <pre>
     *      ##{id}>.inline{font-style:italic; background: white}
     * </pre>
     *
     * @return The CSS specifier.
     */
    @PropertyGetter("css")
    public String getCss() {
        return css;
    }
    
    /**
     * Sets the CSS specifier for this component. This is similar to an embedded style sheet, but is
     * associated with this component and is added to or removed from the client as this component
     * is added or removed. Moreover, EL expressions can be used in the selector to reference this
     * component or one of its subcomponents by their id. For example,
     *
     * <pre>
     *      ##{id}>.inline{font-style:italic; background: white}
     * </pre>
     *
     * @param css The CSS specifier.
     */
    @PropertySetter("css")
    public void setCss(String css) {
        if (!areEqual(css = nullify(css), this.css)) {
            sync("css", this.css = css);
        }
    }
    
    /**
     * Returns the popup text to be displayed when hover over this component.
     *
     * @return The hint text.
     */
    @PropertyGetter("hint")
    public String getHint() {
        return hint;
    }
    
    /**
     * Sets the popup text to be displayed when hover over this component.
     *
     * @param hint The hint text.
     */
    @PropertySetter("hint")
    public void setHint(String hint) {
        if (!areEqual(hint = nullify(hint), this.hint)) {
            sync("hint", this.hint = hint);
        }
    }
    
    /**
     * Returns the balloon text to be displayed adjacent to the component.
     *
     * @return The balloon text.
     */
    @PropertyGetter("balloon")
    public String getBalloon() {
        return balloon;
    }
    
    /**
     * Sets the balloon text to be displayed adjacent to the component.
     *
     * @param balloon The balloon text.
     */
    @PropertySetter("balloon")
    public void setBalloon(String balloon) {
        if (!areEqual(balloon, this.balloon)) {
            sync("balloon", this.balloon = balloon);
        }
    }
    
    @Override
    @PropertyGetter("disabled")
    public boolean isDisabled() {
        return disabled;
    }
    
    @Override
    @PropertySetter("disabled")
    public void setDisabled(boolean disabled) {
        if (disabled != this.disabled) {
            sync("disabled", this.disabled = disabled);
        }
    }
    
    /**
     * Returns the visibility state of the component.
     *
     * @return The visibility state of the component.
     */
    @PropertyGetter("visible")
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Sets the visibility state of the component.
     *
     * @param visible The visibility state of the component.
     */
    @PropertySetter("visible")
    public void setVisible(boolean visible) {
        if (visible != this.visible) {
            sync("visible", this.visible = visible);
        }
    }
    
    /**
     * Returns the tab index for the component.
     *
     * @return The tab index.
     */
    @PropertyGetter("tabindex")
    public int getTabindex() {
        return tabindex;
    }
    
    /**
     * Sets the tab index for the component.
     *
     * @param tabindex The tab index.
     */
    @PropertySetter("tabindex")
    public void setTabindex(int tabindex) {
        tabindex = tabindex < 0 ? 0 : tabindex;
        
        if (tabindex != this.tabindex) {
            sync("tabindex", this.tabindex = tabindex);
        }
    }
    
    /**
     * Returns a space-delimited list of drag id's associated with this component. A non-empty value
     * enables dragging of the component. A dragged component may be dropped upon a target component
     * whose drop id(s) matches at least one of the dragged component's drag id's. A drag id value
     * of "*" will match any drop id.
     *
     * @return The drag id(s).
     */
    @PropertyGetter("dragid")
    public String getDragid() {
        return dragid;
    }
    
    /**
     * Sets the drag id(s) to be associated with this component.
     *
     * @param dragid A space-delimited list of drag id's to be associated with this component. A
     *            non-empty value enables dragging of the component. A dragged component may be
     *            dropped upon a target component whose drop id(s) matches at least one of the drag
     *            id's. A drag id value of "*" will match any drop id.
     */
    @PropertySetter("dragid")
    public void setDragid(String dragid) {
        dragid = trimify(dragid);
        
        if (!areEqual(dragid, this.dragid)) {
            sync("dragid", this.dragid = dragid);
        }
    }
    
    /**
     * Returns a space-delimited list of drop id's associated with this component. A non-empty value
     * enables this component to act as a drop target. A dragged component may be dropped upon a
     * target component whose drop id(s) matches at least one of the dragged component's drag id's.
     * A drop id value of "*" will match any drag id.
     *
     * @return The drag id(s).
     */
    @PropertyGetter("dropid")
    public String getDropid() {
        return dropid;
    }
    
    /**
     * Sets the drag id(s) to be associated with this component.
     *
     * @param dropid A space-delimited list of drop id's to be associated with this component. A
     *            non-empty value enables this component to act as a drop target. A dragged
     *            component may be dropped upon a target component whose drop id(s) matches at least
     *            one of the dragged component's drag id's. A drop id value of "*" will match any
     *            drag id.
     */
    @PropertySetter("dropid")
    public void setDropid(String dropid) {
        dropid = trimify(dropid);
        
        if (!areEqual(dropid, this.dropid)) {
            sync("dropid", this.dropid = dropid);
        }
    }
    
    /**
     * Returns the popup component that will appear when right-clicking on this component.
     *
     * @return The popup component that will appear when right-clicking on this component.
     */
    @PropertyGetter("context")
    public Popup getContext() {
        if (context != null && context.isDead()) {
            context = null;
            sync("context", context);
        }
        
        return context;
    }
    
    /**
     * Sets the popup component that will appear when right-clicking on this component.
     *
     * @param context The id of the popup component that will appear when right-clicking on this
     *            component.
     */
    @PropertySetter(value = "context", defer = true)
    private void setContext(String context) {
        setContext(ConvertUtil.convert(context, Popup.class, this));
    }
    
    /**
     * Sets the popup component that will appear when right-clicking on this component.
     *
     * @param context The popup component that will appear when right-clicking on this component.
     */
    public void setContext(Popup context) {
        if (context != getContext()) {
            validate(context);
            sync("context", this.context = context);
        }
    }
    
    /**
     * Returns the popup component that will appear when hovering over this component.
     *
     * @return The popup component that will appear when hovering over this component.
     */
    @PropertyGetter("popup")
    public Popup getPopup() {
        if (popup != null && popup.isDead()) {
            popup = null;
            sync("popup", popup);
        }
        
        return popup;
    }
    
    /**
     * Sets the popup component that will appear when hovering over this component.
     *
     * @param popup The popup component that will appear when hovering over this component.
     */
    @PropertySetter(value = "popup", defer = true)
    public void setPopup(Popup popup) {
        if (popup != getPopup()) {
            validate(popup);
            sync("popup", this.popup = popup);
        }
    }
    
    /**
     * If the child being removed is the popup, set the popup to null.
     *
     * @see org.fujion.component.BaseComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);
        
        if (child == popup) {
            setPopup(null);
        }
    }
    
    /**
     * Returns the list of key codes to be captured. When a key in this list is typed, a
     * {@value org.fujion.event.KeycaptureEvent#TYPE} event will be sent to this component.
     *
     * @return List of key codes to be captured (see {@link org.fujion.event.KeyCode#fromString} for
     *         formatting details).
     */
    @PropertyGetter("keycapture")
    public String getKeycapture() {
        return keycapture;
    }
    
    /**
     * Sets the list of key codes to be captured. When a key in this list is typed, a
     * {@value org.fujion.event.KeycaptureEvent#TYPE} event will be sent to this component.
     *
     * @param keycapture List of key codes to be captured (see
     *            {@link org.fujion.event.KeyCode#fromString} for formatting details).
     */
    @PropertySetter("keycapture")
    public void setKeycapture(String keycapture) {
        if (!areEqual(keycapture = nullify(keycapture), this.keycapture)) {
            sync("keycapture", KeyCode.normalizeKeyCapture(keycapture));
            this.keycapture = keycapture;
        }
    }
    
    /**
     * Ensures that this component is visible within the view port by scrolling if necessary.
     */
    public void scrollIntoView() {
        invoke("scrollIntoView");
    }
    
    /**
     * Returns the first visible child, if any.
     *
     * @param recurse If true, all descendant levels are also searched using a breadth first
     *            strategy.
     * @return The first visible child encountered, or null if not found.
     */
    public BaseUIComponent getFirstVisibleChild(boolean recurse) {
        return getFirstVisibleChild(BaseUIComponent.class, recurse);
    }
    
    /**
     * Returns the first visible child of a given class, if any.
     *
     * @param <T> The child class.
     * @param clazz The child class to consider.
     * @param recurse If true, all descendant levels are also searched using a breadth first
     *            strategy.
     * @return The first visible child encountered, or null if not found.
     */
    public <T extends BaseUIComponent> T getFirstVisibleChild(Class<T> clazz, boolean recurse) {
        for (T child : getChildren(clazz)) {
            if (child.isVisible()) {
                return child;
            }
        }
        
        if (recurse) {
            for (T child : getChildren(clazz)) {
                T comp = child.getFirstVisibleChild(clazz, recurse);
                
                if (comp != null) {
                    return comp;
                }
            }
        }
        
        return null;
    }
    
}
