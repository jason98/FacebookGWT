/*
 * Copyright (C) 2010 deNormans
 * http://www.denormans.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of deNormans ("Confidential Information"). You 
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with deNormans.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * DENORMANS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.denormans.facebookgwt.api.client.legacy;

import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.shared.legacy.LegacyMethod;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FBLegacy extends FBIntegration {
  /**
   * Executes a legacy REST API method.
   *
   * @param method The method to execute
   * @param methodOptions The method options
   * @param callback Called when the method is complete
   */
  public void executeLegacyMethod(final LegacyMethod method, final FBLegacyMethodOptions methodOptions, final AsyncCallback<?> callback) {
    executeWithFB(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        executeLegacyMethodJS(method, methodOptions, callback);
      }
    });
  }

  private native void executeLegacyMethodJS(final LegacyMethod method, final FBLegacyMethodOptions methodOptions, final AsyncCallback<?> callback) /*-{
    try {
      if (methodOptions == null) {
        methodOptions = {};
      }

      methodOptions.method = method.@com.denormans.facebookgwt.api.shared.ui.UIMethod::getApiValue()();

      var cb;
      if (callback != null) {
        var self = this;
        cb = function(response) {
          if (typeof(response) === "boolean") {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Z)(callback, response);
          } else if (typeof(response) === "number") {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;D)(callback, response);
          } else {
            if (response != null && response.error_code) {
              self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;ILjava/lang/String;)(callback, response.error_code, response.error_msg);
              return;
            }
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
          }
        };
      }

      $wnd.FB.api(methodOptions, cb);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.gwtutil.client.js.JSError::createException(Ljava/lang/Object;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        throw e;
      }
    }
  }-*/;

}
