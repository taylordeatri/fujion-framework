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
package org.fujion.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.fujion.ancillary.DeferredInvocation;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.event.PropertychangeEvent;
import org.fujion.model.IBinding.IReadBinding;
import org.fujion.model.IBinding.IWriteBinding;
import org.springframework.util.Assert;

/**
 * Generic data binder.
 *
 * @param <M> Type of model object.
 */
public class GenericBinder<M> implements IBinder<M>, Observer {
    
    private M model;
    
    private List<GenericBinding> readBindings;
    
    private List<GenericBinding> writeBindings;

    private boolean updating;
    
    private class GenericBinding implements IBinding {
        
        private final String modelProperty;

        private final IConverter readConverter;

        private final IConverter writeConverter;

        private DeferredInvocation<?> getter;

        private DeferredInvocation<?> setter;

        GenericBinding(String modelProperty, IConverter readConverter, IConverter writeConverter) {
            this.modelProperty = modelProperty;
            this.readConverter = readConverter;
            this.writeConverter = writeConverter;
        }

        @Override
        public void init(BaseComponent instance, String propertyName, Method getter, Method setter) {
            if (this instanceof IReadBinding) {
                Assert.notNull(setter, "Property is not writable: " + propertyName);
                this.setter = toDeferred(instance, setter, propertyName, 2);
                readBindings = readBindings == null ? new ArrayList<>() : readBindings;
                readBindings.add(this);
                read();
            } else {
                this.setter = null;
            }

            if (this instanceof IWriteBinding) {
                Assert.notNull(getter, "Property is not readable: " + propertyName);
                this.getter = toDeferred(instance, getter, propertyName, 1);

                if (!(this instanceof IReadBinding)) {
                    writeBindings = writeBindings == null ? new ArrayList<>() : writeBindings;
                    writeBindings.add(this);
                    write();
                }

                instance.addEventListener(PropertychangeEvent.class, (event) -> {
                    if (!updating && ((PropertychangeEvent) event).getPropertyName().equals(propertyName)) {
                        write();
                    }
                });
            } else {
                this.getter = null;
            }
        }
        
        private DeferredInvocation<?> toDeferred(BaseComponent instance, Method method, String name, int maxArgs) {
            return method == null ? null
                    : new DeferredInvocation<>(instance, method,
                            method.getParameterCount() == maxArgs ? new Object[] { name } : null);
        }

        private void read() {
            try {
                if (model != null) {
                    Object value = PropertyUtils.getProperty(model, modelProperty);
                    setter.invoke(readConverter == null ? value : readConverter.convert(value));
                }
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
        
        private void write() {
            try {
                if (model != null) {
                    Object value = getter.invoke();
                    BeanUtils.copyProperty(model, modelProperty,
                        writeConverter == null ? value : writeConverter.convert(value));
                }
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }

        }
    }

    private class ReadBinding extends GenericBinding implements IReadBinding {
        
        ReadBinding(String modelProperty, IConverter readConverter) {
            super(modelProperty, readConverter, null);
        }
        
        @Override
        public void read() {
            super.read();
        }

    }

    private class WriteBinding extends GenericBinding implements IWriteBinding {
        
        WriteBinding(String modelProperty, IConverter writeConverter) {
            super(modelProperty, null, writeConverter);
        }
        
        @Override
        public void write() {
            super.write();
        }

    }

    private class DualBinding extends GenericBinding implements IReadBinding, IWriteBinding {
        
        DualBinding(String modelProperty, IConverter readConverter, IConverter writeConverter) {
            super(modelProperty, readConverter, writeConverter);
        }
        
        @Override
        public void read() {
            super.read();
        }
        
        @Override
        public void write() {
            super.write();
        }
        
    }

    public GenericBinder() {
        this(null);
    }

    public GenericBinder(M model) {
        setModel(model);
    }

    @Override
    public M getModel() {
        return model;
    }

    @Override
    public void setModel(M model) {
        if (this.model instanceof Observable) {
            ((Observable) this.model).deleteObserver(this);
        }

        this.model = model;
        modelChanged(null);
        targetChanged(null);

        if (this.model instanceof Observable) {
            ((Observable) this.model).addObserver(this);
        }

    }
    
    @Override
    public IBinding read(String modelProperty, IConverter converter) {
        return new ReadBinding(modelProperty, converter);
    }
    
    @Override
    public IBinding write(String modelProperty, IConverter converter) {
        return new WriteBinding(modelProperty, converter);
    }
    
    @Override
    public IBinding dual(String modelProperty, IConverter readConverter, IConverter writeConverter) {
        return new DualBinding(modelProperty, readConverter, writeConverter);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            modelChanged(arg instanceof String ? (String) arg : null);
        }
    }

    /**
     * Call when the model has changed to update bindings.
     */
    public void modelChanged() {
        modelChanged(null);
    }
    
    private void targetChanged(String propertyName) {
        processChanged(propertyName, false);
    }

    /**
     * Call when a specific model property has changed.
     *
     * @param propertyName Name of the changed property (or null if unspecified).
     */
    public void modelChanged(String propertyName) {
        processChanged(propertyName, true);
    }

    private void processChanged(String propertyName, boolean read) {
        List<GenericBinding> bindings = read ? readBindings : writeBindings;

        if (!updating && bindings != null) {
            try {
                updating = true;

                for (GenericBinding binding : bindings) {
                    if (propertyName == null || propertyName.equals(binding.modelProperty)) {
                        if (read) {
                            binding.read();
                        } else {
                            binding.write();
                        }
                    }
                }
            } finally {
                updating = false;
            }
        }
    }

}
