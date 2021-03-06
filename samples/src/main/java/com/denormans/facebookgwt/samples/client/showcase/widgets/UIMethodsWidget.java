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

package com.denormans.facebookgwt.samples.client.showcase.widgets;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.ui.callbacks.FriendAddCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.callbacks.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.model.Attachment;
import com.denormans.facebookgwt.api.client.ui.model.Link;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.ui.callbacks.BookmarkApplicationCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.options.FriendAddOptions;
import com.denormans.facebookgwt.api.client.ui.options.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.options.StreamShareOptions;
import com.denormans.facebookgwt.api.shared.ui.DisplayFormats;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;

import java.util.logging.Logger;

public class UIMethodsWidget extends ShowcaseWidget {
  private static final Logger Log = Logger.getLogger(UIMethodsWidget.class.getName());

  interface UIMethodsWidgetUIBinder extends UiBinder<HTMLPanel, UIMethodsWidget> {}
  private static UIMethodsWidgetUIBinder sUIBinder = GWT.create(UIMethodsWidgetUIBinder.class);

  @UiField Button bookmarkButton;

  @UiField Button streamPublishButton;
  @UiField TextBox streamPublishMessageTextBox;

  @UiField Button streamShareButton;
  @UiField TextBox streamShareLinkTextBox;

  @UiField Button friendAddButton;
  @UiField TextBox friendAddIDTextBox;

  public UIMethodsWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        bookmarkButton.setEnabled(FBGWT.Init.isInitialized());
        streamPublishButton.setEnabled(FBGWT.Init.isInitialized());
        streamPublishMessageTextBox.setEnabled(FBGWT.Init.isInitialized());
        streamShareButton.setEnabled(FBGWT.Init.isInitialized());
        streamShareLinkTextBox.setEnabled(FBGWT.Init.isInitialized());
        friendAddButton.setEnabled(FBGWT.Init.isInitialized());
        friendAddIDTextBox.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  @UiHandler ("bookmarkButton")
  public void handleBookmarkButtonClick(final ClickEvent event) {
    FBGWT.UI.bookmarkApplication(DisplayFormats.Dialog, new AsyncCallback<BookmarkApplicationCallbackResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error bookmarking application", caught);
      }

      @Override
      public void onSuccess(final BookmarkApplicationCallbackResponse result) {
        addApiEventMessage("Bookmark Application result (isBookmarked=" + result.isBookmarked() + ")", result);
      }
    });
  }

  @UiHandler ("streamPublishButton")
  public void handleStreamPublishButtonClick(final ClickEvent event) {
    StreamPublishOptions streamPublishOptions =
        StreamPublishOptions.createStreamPublishOptions()
            .setMessage(streamPublishMessageTextBox.getText())
            .setAttachment(Attachment.createAttachment().setName("FacebookGWT").setCaption("Facebook API for GWT").setDescription("Facebook GWT is a Java API of the Facebook JavaScript API for use with Google Web Toolkit.").setHref("http://denormans.github.com/FacebookGWT/"))
            .setActionLinks(Link.createLink("Code", "https://github.com/denormans/FacebookGWT"), Link.createLink("Issues", "https://github.com/denormans/FacebookGWT/issues"))
            .setUserMessagePrompt("Share your thoughts about FacebookGWT");

    Log.info("Stream publish options: " + streamPublishOptions.toJSONString());

    FBGWT.UI.publishToStream(DisplayFormats.Dialog, streamPublishOptions, new AsyncCallback<StreamPublishCallbackResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error publishing to stream", caught);
      }

      @Override
      public void onSuccess(final StreamPublishCallbackResponse result) {
        addApiEventMessage("Stream Publish result (postID=" + result.getPostID() + ")", result);
      }
    });
  }

  @UiHandler ("streamShareButton")
  public void handleStreamShareButtonClick(final ClickEvent event) {
    StreamShareOptions streamShareOptions =
        StreamShareOptions.createStreamShareOptions(streamShareLinkTextBox.getText());

    Log.info("Stream share options: " + streamShareOptions.toJSONString());

    FBGWT.UI.shareLinkToStream(DisplayFormats.Dialog, streamShareOptions, new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error sharing link to stream", caught);
      }

      @Override
      public void onSuccess(final Boolean result) {
        addApiEventMessage("Stream Share result", result);
      }
    });
  }

  @UiHandler ("friendAddButton")
  public void handleFriendAddButtonClick(final ClickEvent event) {
    FriendAddOptions friendAddOptions = FriendAddOptions.createFriendAddOptions(friendAddIDTextBox.getText());

    Log.info("Friend add options: " + friendAddOptions.toJSONString());

    FBGWT.UI.addFriend(DisplayFormats.Dialog, friendAddOptions, new AsyncCallback<FriendAddCallbackResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error adding friend", caught);
      }

      @Override
      public void onSuccess(final FriendAddCallbackResponse result) {
        addApiEventMessage("Add friend result (isAdded=" + result.isAdded() + ")", result);
      }
    });
  }
}