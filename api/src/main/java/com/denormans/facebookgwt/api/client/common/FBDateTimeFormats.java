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

package com.denormans.facebookgwt.api.client.common;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public class FBDateTimeFormats {
  public static final DateTimeFormat BirthdayFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
  public static final DateTimeFormat TimePeriodFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
  public static final DateTimeFormat RFC3339Format = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");

  public static final Date parseDateTime(final DateTimeFormat dateTimeFormat, final String dateTimeText) {
    if (dateTimeText == null) {
      return null;
    }

    return dateTimeFormat.parse(dateTimeText);
  }

  public static Date parseTimePeriodDate(final String dateText) {
    if (dateText == null || "0000-00".equals(dateText)) {
      return null;
    }

    return TimePeriodFormat.parse(dateText + "-01");
  }
}
