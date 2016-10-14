/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.apec.crm.mvp.views.core;

import java.util.ArrayList;

public interface ListView extends View {
    void onRefreshSuccess(ArrayList data);
    void onLoadMoreSuccess(ArrayList data);
    //没有更多数据
    void onNoMore();
}