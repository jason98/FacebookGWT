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

package com.denormans.facebookgwt.samples.client;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.init.events.FBInitFailureEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitFailureHandler;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.samples.client.showcase.Showcase;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookGWTSamples implements EntryPoint {
  private static final Logger Log = Logger.getLogger(FacebookGWTSamples.class.getName());

  public static final String SamplesFacebookApplicationID = "160704113964450";

  private static FacebookGWTSamples sInstance;

  private Showcase showcase;

  public Showcase getShowcase() {
    return showcase;
  }

  public void onModuleLoad() {
    if (sInstance != null) {
      return;
    }

    sInstance = this;

    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable t) {
        handleError("An unknown error occurred", t);
      }
    });

    try {
      showcase = new Showcase();

      RootPanel.get("FBGWTLoadingTextID").setVisible(false);
      RootLayoutPanel.get().add(showcase);

      FBGWT.Init.addFBInitFailureHandler(new FBInitFailureHandler() {
        @Override
        public void onFBInitFailure(final FBInitFailureEvent event) {
          handleError("Facebook failed to load");
        }
      });

      FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
        @Override
        public void onFBInitSuccess(final FBInitSuccessEvent event) {
          Log.info("Facebook loaded");
        }
      });
    } catch (Exception e) {
      handleError("Error loading module", e);
    }

    Log.info("FacebookGWTSamples Module loaded");
  }

  public void handleError(final String message) {
    handleError(message, null);
  }

  public void handleError(final String message, final Throwable t) {
    if (t != null) {
      Log.log(Level.SEVERE, message, t);
      Window.alert(message + ": " + t);
    } else {
      Log.log(Level.SEVERE, message);
      Window.alert(message);
    }
  }

  public static FacebookGWTSamples get() {
    if (sInstance == null) {
      throw new IllegalStateException("Module not loaded");
    }

    return sInstance;
  }
}
