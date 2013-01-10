/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc;

import java.lang.reflect.Method;

import com.google.inject.Injector;

class MappingData {

// ------------------------------------------------------------------------
    private HttpMethodType httpMethodType;
    private String path;
    private String resultName;
    private Injector injector;
    private Class<?> controllerClass;
    private Method method;

// ------------------------------------------------------------------------
    public MappingData(Class<?> controllerClass, Method method, HttpMethodType httpMethodType, String path, String resultName, Injector injector) {
        this.controllerClass = controllerClass;
        this.method = method;
        this.path = path;
        this.resultName = resultName;
        this.httpMethodType = httpMethodType;
        this.injector = injector;
    }
// ------------------------------------------------------------------------

    public HttpMethodType getHttpMethodType() {
        return httpMethodType;
    }

    public void setHttpMethodType(HttpMethodType httpMethodType) {
        this.httpMethodType = httpMethodType;
    }

    public String getPath() {
        return path;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
