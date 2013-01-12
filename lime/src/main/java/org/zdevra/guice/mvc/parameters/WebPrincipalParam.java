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
package org.zdevra.guice.mvc.parameters;

import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.security.WebPrincipal;

import javax.servlet.http.HttpServletRequest;

/**
 * The parameter's  processor is executed when the method has a parameter,
 * which is a instance of HttpServletRequest, and put the request object
 * into this parameter.
 * <p/>
 * <p/>
 * for example:
 * <pre class="prettyprint">
 * {@literal @}Path("/control")
 * public void controllerMethod(HttpServletRequest request) {
 * request.getParameter("param");
 * }
 * </pre>
 */
public class WebPrincipalParam implements ParamProcessor {
    /*----------------------------------------------------------------------*/

    /**
     * Factory class for {@link org.zdevra.guice.mvc.parameters.WebPrincipalParam}
     */
    public static class Factory implements ParamProcessorFactory {

        private final ParamProcessor processor;

        public Factory() {
            processor = new WebPrincipalParam();
        }

        @Override
        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            if (metadata.getType() != WebPrincipal.class) {
                return null;
            }
            return processor;
        }
    }

    /*------------------------------- methods ------------------------------*/

    /**
     * Hidden constructor
     */
    private WebPrincipalParam() {
    }

    @Override
    public Object getValue(InvokeData data) {
        return data.getRequest().getUserPrincipal();
    }

    /*----------------------------------------------------------------------*/
}
