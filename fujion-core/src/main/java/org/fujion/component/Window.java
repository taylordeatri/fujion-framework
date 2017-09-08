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

import java.util.function.BooleanSupplier;

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.client.ExecutionContext;
import org.fujion.event.Event;
import org.fujion.event.IEventListener;

/**
 * A window component with a title bar and maximize/minimize/close buttons. May be used in modal,
 * popup, or inline modes.
 */
@Component(tag = "window", widgetClass = "Window", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"))
public class Window extends BaseUIComponent implements INamespace {

    /**
     * Possible display modes for a window.
     */
    public enum Mode {
        /**
         * The window floats and a modal mask prevents interaction with other components.
         */
        MODAL,
        /**
         * The window is inline (embedded).
         */
        INLINE,
        /**
         * The window floats, but interaction with other components is allowed.
         */
        POPUP;
    }

    /**
     * Size state for a window. Default is NORMAL.
     */
    public enum Size {
        /**
         * Sized and positioned according to corresponding property settings.
         */
        NORMAL,
        /**
         * Expanded to fill the view port.
         */
        MAXIMIZED,
        /**
         * Only the title bar is visible; moved to the bottom of the view port.
         */
        MINIMIZED;
    }

    /**
     * Action upon window closure. Default is DESTROY.
     */
    public enum CloseAction {
        /**
         * Detach the window.
         */
        DETACH,
        /**
         * Destroy the window.
         */
        DESTROY,
        /**
         * Hide the window.
         */
        HIDE
    }

    /**
     * Placement of window in view port when first displayed. Default is CENTER.
     */
    public enum Position {
        CENTER, LEFT_CENTER, LEFT_TOP, LEFT_BOTTOM, RIGHT_CENTER, RIGHT_TOP, RIGHT_BOTTOM, CENTER_TOP, CENTER_BOTTOM
    }

    private String title;

    private String image;

    private Position position = Position.CENTER;

    private boolean closable;

    private boolean movable = true;

    private boolean sizable;

    private boolean maximizable;

    private boolean minimizable;

    private Size size = Size.NORMAL;

    private BooleanSupplier onCanClose;

    private CloseAction closeAction = CloseAction.DESTROY;

    private Mode mode;

    private IEventListener closeListener;

    public Window() {
        super();
        setMode(Mode.INLINE);
        addClass("flavor:panel-default");
    }

    /**
     * Returns the title text.
     *
     * @return The title text.
     */
    @PropertyGetter("title")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title text.
     *
     * @param title The title text.
     */
    @PropertySetter("title")
    public void setTitle(String title) {
        if (!areEqual(title, this.title)) {
            sync("title", this.title = title);
        }
    }

    /**
     * Returns the URL of the image to be displayed on the left side of the title bar.
     *
     * @return The URL of the image to be displayed on the left side of the title bar.
     */
    @PropertyGetter("image")
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL of the image to be displayed on the left side of the title bar.
     *
     * @param image The URL of the image to be displayed on the left side of the title bar.
     */
    @PropertySetter("image")
    public void setImage(String image) {
        if (!areEqual(image = nullify(image), this.image)) {
            sync("image", this.image = image);
        }
    }

    /**
     * Returns true if the window is closable. A window that is closable has an icon that, when
     * clicked, triggers a close event.
     *
     * @return True if the window is closable.
     */
    @PropertyGetter("closable")
    public boolean isClosable() {
        return closable;
    }

    /**
     * Set to true to make the window closable. A window that is closable has an icon that, when
     * clicked, triggers a close event.
     *
     * @param closable If true, the window is closable.
     * @see #onCanClose
     */
    @PropertySetter("closable")
    public void setClosable(boolean closable) {
        if (closable != this.closable) {
            sync("closable", this.closable = closable);
        }
    }

    /**
     * Returns the window's sizable property. A window that is sizable has borders that may be
     * dragged to change its dimensions. If the {@link #setMode(Mode) mode} is {@link Mode#INLINE
     * INLINE}, this property has no effect.
     *
     * @return The window's sizable property.
     */
    @PropertyGetter("sizable")
    public boolean isSizable() {
        return sizable;
    }

    /**
     * Sets the window's sizable property. A window that is sizable has borders that may be dragged
     * to change its dimensions. If the {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE},
     * this property has no effect.
     *
     * @param sizable The window's sizable property.
     */
    @PropertySetter("sizable")
    public void setSizable(boolean sizable) {
        if (sizable != this.sizable) {
            sync("sizable", this.sizable = sizable);
        }
    }

    /**
     * Returns the {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     * {@link Mode#POPUP popup} window.
     *
     * @return The {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     *         {@link Mode#POPUP popup} window.
     */
    @PropertyGetter("position")
    public Position getPosition() {
        return position;
    }
    
    /**
     * Sets the {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     * {@link Mode#POPUP popup} window.
     *
     * @param position The {@link Position placement} of a newly opened {@link Mode#MODAL modal} or
     *            {@link Mode#POPUP popup} window.
     */
    @PropertySetter("position")
    public void setPosition(Position position) {
        if (position != this.position) {
            sync("position", this.position = position);
        }
    }
    
    /**
     * Returns whether the window may be moved to a new position by dragging its title bar. If the
     * {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE}, this property has no effect.
     *
     * @return If true, the window may be moved to a new position by dragging its title bar.
     */
    @PropertyGetter("movable")
    public boolean isMovable() {
        return movable;
    }

    /**
     * Sets whether the window may be moved to a new position by dragging its title bar. If the
     * {@link #setMode(Mode) mode} is {@link Mode#INLINE INLINE}, this property has no effect.
     *
     * @param movable If true, the window may be moved to a new position by dragging its title bar.
     */
    @PropertySetter("movable")
    public void setMovable(boolean movable) {
        if (movable != this.movable) {
            sync("movable", this.movable = movable);
        }
    }

    /**
     * Returns true if the window may be maximized. A window that is maximizable has an icon that,
     * when clicked, causes a {@link Mode#MODAL modal} or {@link Mode#POPUP popup} window to be
     * resized to fill the view port. Clicking the icon again restores the window to its original
     * size and position.
     *
     * @return True if the window is maximizable.
     */
    @PropertyGetter("maximizable")
    public boolean isMaximizable() {
        return maximizable;
    }

    /**
     * Sets whether the window may be maximized. A window that is maximizable has an icon that, when
     * clicked, causes a {@link Mode#MODAL modal} or {@link Mode#POPUP popup} window to be resized
     * to fill the view port. Clicking the icon again restores the window to its original size and
     * position.
     *
     * @param maximizable True if the window is maximizable.
     */
    @PropertySetter("maximizable")
    public void setMaximizable(boolean maximizable) {
        if (maximizable != this.maximizable) {
            sync("maximizable", this.maximizable = maximizable);
        }
    }

    /**
     * Returns true if the window may be minimized. A window that is minimizable has an icon that,
     * when clicked, causes a window's body to be hidden. If the window's mode is {@link Mode#MODAL
     * modal} or {@link Mode#POPUP popup}, it will also be resized to a smaller dimension and placed
     * at the lower left corner of the view port. Clicking the icon again restores the window to its
     * original size and position.
     *
     * @return True if the window is minimizable.
     */
    @PropertyGetter("minimizable")
    public boolean isMinimizable() {
        return minimizable;
    }

    /**
     * Sets whether the window may be minimized. A window that is minimizable has an icon that, when
     * clicked, causes a window's body to be hidden. If the window's mode is {@link Mode#MODAL
     * modal} or {@link Mode#POPUP popup}, it will also be resized to a smaller dimension and placed
     * at the lower left corner of the view port. Clicking the icon again restores the window to its
     * original size and position.
     *
     * @param minimizable True if the window is minimizable.
     */
    @PropertySetter("minimizable")
    public void setMinimizable(boolean minimizable) {
        if (minimizable != this.minimizable) {
            sync("minimizable", this.minimizable = minimizable);
        }
    }

    /**
     * Returns the display {@link Mode mode} of the window.
     *
     * @return The display {@link Mode mode} of the window.
     */
    @PropertyGetter("mode")
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the display {@link Mode mode} of the window.
     *
     * @param mode The display {@link Mode mode} of the window.
     */
    @PropertySetter("mode")
    public void setMode(Mode mode) {
        mode = mode == null ? Mode.INLINE : mode;

        if (mode != this.mode) {
            sync("mode", this.mode = mode);
        }
    }

    /**
     * Returns the {@link Size sizing} mode of the window.
     *
     * @return The {@link Size sizing} mode of the window.
     */
    @PropertyGetter("size")
    public Size getSize() {
        return size;
    }

    /**
     * Sets the {@link Size sizing} mode of the window.
     *
     * @param size The {@link Size sizing} mode of the window.
     */
    @PropertySetter("size")
    public void setSize(Size size) {
        size = size == null ? Size.NORMAL : size;

        if (size != this.size) {
            sync("size", this.size = size);
        }
    }

    /**
     * Returns the {@link CloseAction action} to be taken when the window is closed.
     *
     * @return The {@link CloseAction action} to be taken when the window is closed.
     */
    @PropertyGetter("closeAction")
    public CloseAction getCloseAction() {
        return closeAction;
    }

    /**
     * Sets the {@link CloseAction action} to be taken when the window is closed.
     *
     * @param closeAction The {@link CloseAction action} to be taken when the window is closed.
     */
    @PropertySetter("closeAction")
    public void setCloseAction(CloseAction closeAction) {
        this.closeAction = closeAction == null ? CloseAction.DESTROY : closeAction;
    }

    /**
     * Request the window to be closed. Window closure may be prevented if the onCanClose logic
     * returns false.
     *
     * @return True if the window was closed.
     */
    public boolean close() {
        if (canClose()) {
            switch (closeAction) {
                case DESTROY:
                    destroy();
                    break;

                case DETACH:
                    detach();
                    break;

                case HIDE:
                    setVisible(false);
                    break;
            }

            if (closeListener != null) {
                try {
                    closeListener.onEvent(new Event("close", this));
                } finally {
                    closeListener = null;
                }
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * Returns the functional interface that determines whether window closure is permitted.
     *
     * @return The functional interface that determines whether window closure is permitted.
     */
    public BooleanSupplier getOnCanClose() {
        return onCanClose;
    }

    /**
     * Sets whether window closure is permitted using a simple Boolean value. This is a shortcut for
     * calling {@link #setOnCanClose} with a functional interface that returns a fixed Boolean
     * value.
     *
     * @param canClose If true, the tab may be closed.
     */
    public void setOnCanClose(boolean canClose) {
        setOnCanClose(() -> canClose);
    }
    
    /**
     * Sets the functional interface that will determine if window closure is permitted.
     *
     * @param onCanClose The functional interface that will determine if window closure is
     *            permitted.
     */
    public void setOnCanClose(BooleanSupplier onCanClose) {
        this.onCanClose = onCanClose;
    }

    /**
     * Invokes the {@link #getOnCanClose canClose} logic and returns the result.
     *
     * @return The result of invoking the {@link #getOnCanClose canClose} logic.
     */
    public boolean canClose() {
        return onCanClose == null || onCanClose.getAsBoolean();
    }

    /**
     * Opens the window modally.
     *
     * @param closeListener The event listener to be invoked upon window closure.
     */
    public void modal(IEventListener closeListener) {
        doShow(Mode.MODAL, closeListener);
    }

    /**
     * Opens the window as a popup.
     *
     * @param closeListener The event listener to be invoked upon window closure.
     */
    public void popup(IEventListener closeListener) {
        doShow(Mode.POPUP, closeListener);
    }

    private void doShow(Mode mode, IEventListener closeListener) {
        if (this.closeListener != null) {
            throw new IllegalStateException("Window is already open.");
        }

        if (getParent() == null) {
            setParent(ExecutionContext.getPage());
        }

        this.closeListener = closeListener;
        setMode(mode);
        setVisible(true);
        fireEvent("open");
    }

    /**
     * Handles close events from the client.
     */
    @EventHandler(value = "close", syncToClient = false)
    private void _close() {
        close();
    }

}
