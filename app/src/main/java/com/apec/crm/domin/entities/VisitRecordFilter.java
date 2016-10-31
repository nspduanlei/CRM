package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.apec.crm.domin.entities.func.BaseFilter;

/**
 * Created by duanlei on 16/9/29.
 * 访问记录过滤
 */
public class VisitRecordFilter extends BaseFilter implements Parcelable {

    //2016-09-19
    private String startDate;
    private String endDate;

    private String customerNo; //客户NO
    private String userNo; //用户NO

    public VisitRecordFilter() {}

    protected VisitRecordFilter(Parcel in) {
        startDate = in.readString();
        endDate = in.readString();
        customerNo = in.readString();
        userNo = in.readString();
    }

    public static final Creator<VisitRecordFilter> CREATOR = new Creator<VisitRecordFilter>() {
        @Override
        public VisitRecordFilter createFromParcel(Parcel in) {
            return new VisitRecordFilter(in);
        }

        @Override
        public VisitRecordFilter[] newArray(int size) {
            return new VisitRecordFilter[size];
        }
    };

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(customerNo);
        dest.writeString(userNo);
    }
}
