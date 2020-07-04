package com.maks.durov.testapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Parcelable {
    private String imageUrl;
    private String bigImageUrl;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String skype;
    private int age;

    protected Contact(Parcel in) {
        imageUrl = in.readString();
        bigImageUrl = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        skype = in.readString();
        age = in.readInt();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(bigImageUrl);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(skype);
        dest.writeInt(age);
    }
}
