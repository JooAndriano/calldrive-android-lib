/* Calldrive Android Library is available under MIT license
 *
 *   @author Tobias Kaminsky
 *   Copyright (C) 2019 Tobias Kaminsky
 *   Copyright (C) 2019 Calldrive GmbH
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package com.owncloud.android.lib.resources.users;

import com.owncloud.android.AbstractIT;
import com.owncloud.android.lib.common.UserInfo;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.status.GetCapabilitiesRemoteOperation;
import com.owncloud.android.lib.resources.status.CalldriveVersion;
import com.owncloud.android.lib.resources.status.OCCapability;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SetUserInfoRemoteOperationTest extends AbstractIT {
    @Test
    public void testSetEmail() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().getEmail();

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.EMAIL, "new@mail.com")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("new@mail.com", userInfo.getResultData().email);

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.EMAIL, oldValue)
                .execute(calldriveClient).isSuccess());
    }

    @Test
    public void testSetDisplayName() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());

        String oldUserId = calldriveClient.getUserId();
        assertEquals(calldriveClient.getUserId(), userInfo.getResultData().displayName);

        // set display name
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.DISPLAYNAME, "newName")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("newName", userInfo.getResultData().displayName);

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.DISPLAYNAME, oldUserId)
                .execute(calldriveClient).isSuccess());
    }

    @Test
    public void testSetPhone() {
        RemoteOperationResult result = new GetCapabilitiesRemoteOperation().execute(calldriveClient);
        assertTrue(result.isSuccess());
        OCCapability ocCapability = (OCCapability) result.getSingleData();

        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().phone;

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.PHONE, "+49555-12345")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());

        if (ocCapability.getVersion().isNewerOrEqual(CalldriveVersion.calldrive_21)) {
            assertEquals("+4955512345", userInfo.getResultData().phone);
        } else {
            assertEquals("+49555-12345", userInfo.getResultData().phone);
        }

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.PHONE, oldValue)
                .execute(calldriveClient).isSuccess());
    }

    @Test
    public void testSetAddress() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().address;

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.ADDRESS, "NoName Street 123")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("NoName Street 123", userInfo.getResultData().address);

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.ADDRESS, oldValue)
                .execute(calldriveClient).isSuccess());
    }

    @Test
    public void testSetWebsite() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().website;

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.WEBSITE, "https://calldrive.com")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("https://calldrive.com", userInfo.getResultData().website);

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.WEBSITE, oldValue)
                .execute(calldriveClient).isSuccess());
    }

    @Test
    public void testSetTwitter() {
        RemoteOperationResult<UserInfo> userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        String oldValue = userInfo.getResultData().twitter;

        // set
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.TWITTER, "@Calldriveers")
                .execute(calldriveClient).isSuccess());

        userInfo = new GetUserInfoRemoteOperation().execute(calldriveClient);
        assertTrue(userInfo.isSuccess());
        assertEquals("@Calldriveers", userInfo.getResultData().twitter);

        // reset
        assertTrue(new SetUserInfoRemoteOperation(SetUserInfoRemoteOperation.Field.TWITTER, oldValue)
                .execute(calldriveClient).isSuccess());
    }
}
